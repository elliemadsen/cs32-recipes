import sys
sys.path.append('.')
import pycrfsuite
import pandas
import nltk
from itertools import chain
import re
from ingredient_parser.utils import *
from nltk.stem import WordNetLemmatizer
from nltk.tokenize.treebank import TreebankWordDetokenizer
from sklearn.metrics import classification_report, confusion_matrix
from sklearn.preprocessing import LabelBinarizer

#16105, 
def generate_data(start, count):
    """
    Transforms each row in the data file into an array of tuples, with each tuple representing a word as (word, pos tag, label)
    
    """
    dataframe = pandas.read_csv("/Users/masonzhang/cs/recipe scraper/ingredient_parser/nyt-ingredients.csv").fillna("")
    data_slice = dataframe.iloc[start: start + count]

    sents = []

    for row in data_slice.iterrows():
        ingredient_line = clean(row[1]["input"])
        #print(ingredient_line)
        tokens = list(tokenize(ingredient_line))
        pos = nltk.pos_tag(tokens)
        ingredient_labels = {
                "name": row[1]["name"],
                "qty": row[1]["qty"],
                "unit": row[1]["unit"],
                "comment": row[1]["comment"]
        }
        labels = label(tokens, ingredient_labels)
        sent = []
        for i, (w, p) in enumerate(pos):
            sent.append((w, p, labels[i]))
        if list(map(lambda s: s[2], sent)) != (["UNKNOWN"] * len(sent)):
            sents.append(sent)
    return sents

wnl = WordNetLemmatizer()
def label(ingredient_tokens, ingredient_labels):
    """
    Maps each word in the ingredient line to its label
    """

    labels = ["UNKNOWN"] * len(ingredient_tokens)
    # convert the list representation of an ingredient line back into a string for easier searching
    singularized_ing_tokens = map(lambda x: wnl.lemmatize(x), ingredient_tokens)
    ingredient_line = " ".join(singularized_ing_tokens).lower()

    for label, labelled_words in ingredient_labels.items():
        labelled_word_tokens = tokenize(str(labelled_words))
        singularized_lablled_word_tokens = map(lambda x: wnl.lemmatize(x), labelled_word_tokens)
        labelled_words = " ".join(singularized_lablled_word_tokens)
        if label == "comment":
            # comments label consists of several comment fragments seperated by commas.
            comments = labelled_words.split(",")
            for comment in comments:
                comment = comment.strip().lower()
                # char index of the first word of comment in ingredient_line
                indexof_phrase = ingredient_line.find(comment)
                if indexof_phrase == -1:
                    continue
                #num_special_chars = len(re.findall(r"[,\(\)]", ingredient_line[:indexof_phrase]))
                # the first word in the comment phrase is the word_indexth word of ingredient_line
                word_index = ingredient_line[:indexof_phrase].count(" ")
                comment_tokens = tokenize(comment)
                for i, word in enumerate(comment_tokens):
                    # first word of phrase gets tagged "B", subsequent ones get tagged "I"
                    if i == 0:
                        labels[word_index + i] = "B-" + label.upper()
                    else:
                        labels[word_index + i] = "I-" + label.upper()
        elif label == "qty":
            # we try to convert each ingredient token to a number and see if it's a number equal to the qty.
            # assumes qty corresponds to a single qty, e.g. "1.25".
            for i, word in enumerate(ingredient_tokens):
                num_word = parseNumbers(word)
                # test: all numbers are qtys. add num_word == float(labelled_words) to only mark numbers which match qty as qtys
                if num_word is not None:
                    labels[i] = "B-" + label.upper()

        else:
            # for unit and name
            indexof_phrase = ingredient_line.find(labelled_words.lower())
            if indexof_phrase == -1:
                continue
            word_index = ingredient_line[:indexof_phrase].count(" ")
            phrase_tokens = tokenize(labelled_words)
            a = list(phrase_tokens)
            for i, word in enumerate(phrase_tokens):
                if i == 0:
                    labels[word_index + i] = "B-" + label.upper()
                else:
                    labels[word_index + i] = "I-" + label.upper()    
    return labels


def sent2features(sent):
    """
    Creates features for each word in the sentence
    """
    return [word2features(sent, i) for i in range(len(sent))]


def word2features(sent, i):
    """
    Maps the ith word in sent to its features dictionary
    """
    word = sent[i][0]
    postag = sent[i][1]
    features = [
        'bias',
        'word.lower=' + word.lower(),
        'word[-3:]=' + word[-3:],
        'word[-2:]=' + word[-2:],
        'word.isupper=%s' % word.isupper(),
        'word.istitle=%s' % word.istitle(),
        'word.isdigit=%s' % word.isdigit(),
        'postag=' + postag,
        'postag[:2]=' + postag[:2],
    ]
    if i > 0:
        word1 = sent[i-1][0]
        postag1 = sent[i-1][1]
        features.extend([
            '-1:word.lower=' + word1.lower(),
            '-1:word.istitle=%s' % word1.istitle(),
            '-1:word.isupper=%s' % word1.isupper(),
            '-1:postag=' + postag1,
            '-1:postag[:2]=' + postag1[:2],
        ])
    else:
        features.append('BOS')
        
    if i < len(sent)-1:
        word1 = sent[i+1][0]
        postag1 = sent[i+1][1]
        features.extend([
            '+1:word.lower=' + word1.lower(),
            '+1:word.istitle=%s' % word1.istitle(),
            '+1:word.isupper=%s' % word1.isupper(),
            '+1:postag=' + postag1,
            '+1:postag[:2]=' + postag1[:2],
        ])
    else:
        features.append('EOS')
                
    return features

def sent2labels(sent):
    """
    Generates an array of labels corresponding to each word in the sentence
    """
    return [label for token, postag, label in sent]
    

def bio_classification_report(y_true, y_pred):
    """
    Classification report for a list of BIO-encoded sequences.
    It computes token-level metrics and discards "O" labels.
    
    Note that it requires scikit-learn 0.15+ (or a version from github master)
    to calculate averages properly!
    """
    lb = LabelBinarizer()
    y_true_combined = lb.fit_transform(list(chain.from_iterable(y_true)))
    y_pred_combined = lb.transform(list(chain.from_iterable(y_pred)))
        
    tagset = set(lb.classes_) - {'O'}
    tagset = sorted(tagset, key=lambda tag: tag.split('-', 1)[::-1])
    class_indices = {cls: idx for idx, cls in enumerate(lb.classes_)}
    
    return classification_report(
        y_true_combined,
        y_pred_combined,
        labels = [class_indices[cls] for cls in tagset],
        target_names = tagset,
    )


def train(output_path, start_index, num_data):
    train_sents = generate_data(start_index, num_data)
    X_train = [sent2features(s) for s in train_sents]
    y_train = [sent2labels(s) for s in train_sents]
    trainer = pycrfsuite.Trainer(verbose=False)
    for xseq, yseq in zip(X_train, y_train):
        trainer.append(xseq, yseq)
    
    trainer.set_params({
        'c1': 1.0,   # coefficient for L1 penalty
        'c2': 1e-3,  # coefficient for L2 penalty
        'max_iterations': 50,  # stop earlier

        # include transitions that are possible, but not observed
        'feature.possible_transitions': True
    })
    trainer.train(output_path+'.crfsuite')

def test(model_path, start_index, num_data):
    test_sents = generate_data(120000, 2000)
    X_test = [sent2features(s) for s in test_sents]
    y_test = [sent2labels(s) for s in test_sents]
    tagger = pycrfsuite.Tagger()
    tagger.open(model_path+'.crfsuite')
    y_pred = [tagger.tag(xseq) for xseq in X_test]
    print(bio_classification_report(y_test, y_pred))

def pos_tag(ingredient_line):
    tokens = list(tokenize(ingredient_line))
    return nltk.pos_tag(tokens)

def predict(model_path, ingredient_line):
    pos_tagged = pos_tag(ingredient_line)
    tagger = pycrfsuite.Tagger()
    tagger.open(model_path+'.crfsuite')
    pred = tagger.tag(sent2features(pos_tagged))
    return format_prediction(pos_tagged, pred)

def format_prediction(ingredient_line, pred):
    tokens = list(map(lambda x: x[0], ingredient_line))
    prediction = {
        "QTY": [],
        "UNIT": [],
        "NAME": [],
        "COMMENT": [],
        "UNKNOWN": []
    }
    curr_string = []
    curr_type = None
    for i, pr in enumerate(pred):
        if curr_type is not None and curr_type != pr[2:]:
            untokenized_str = TreebankWordDetokenizer().detokenize(curr_string)
            prediction[curr_type].append(untokenized_str)
            curr_string = []
            curr_type = None          
        if pr == "UNKNOWN":
            prediction["UNKNOWN"].append(tokens[i])
            curr_type = None
            curr_string = []
        elif curr_type is None:
            curr_string.append(tokens[i])
            curr_type = pr[2:]
        elif curr_type == pr[2:]:
            curr_string.append(tokens[i])
        
        if i == len(pred) - 1:
            untokenized_str = TreebankWordDetokenizer().detokenize(curr_string)
            prediction[curr_type].append(untokenized_str)  
            
    for key,value in prediction.items():
        print(key + ": " + " | ".join(value))
    return prediction


if __name__ == "__main__":
    #train("test3", 0, 100000)
    #test("test3", 100000, 5000)

    prediction = predict("test2", "1 large green bell pepper, cored, seeded and thinly sliced")
    for key,value in prediction.items():
        print(key + ": " + " | ".join(value))




