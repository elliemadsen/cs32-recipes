import re
import decimal
from nltk.tokenize import word_tokenize

def parseNumbers(s):
    """
    Parses a string that represents a number into a decimal data type so that
    we can match the quantity field in the db with the quantity that appears
    in the display name. Rounds the result to 2 places.
    """
    ss = unclump(s)

    m3 = re.match(r'^\d+$', ss)
    if m3 is not None:
        return decimal.Decimal(round(float(ss), 2))

    m1 = re.match(r'(\d+)\s+(\d)/(\d)', ss)
    if m1 is not None:
        num = int(m1.group(1)) + (float(m1.group(2)) / float(m1.group(3)))
        return decimal.Decimal(str(round(num,2)))

    m2 = re.match(r'^(\d)/(\d)$', ss)
    if m2 is not None:
        num = float(m2.group(1)) / float(m2.group(2))
        return decimal.Decimal(str(round(num,2)))

    return None

def tokenize(s):
    """
    Tokenize on parenthesis, punctuation, spaces and American units followed by a slash.
    We sometimes give American units and metric units for baking recipes. For example:
        * 2 tablespoons/30 mililiters milk or cream
        * 2 1/2 cups/300 grams all-purpose flour
    The recipe database only allows for one unit, and we want to use the American one.
    But we must split the text on "cups/" etc. in order to pick it up.
    """

    american_units = ['cup', 'tablespoon', 'teaspoon', 'pound', 'ounce', 'quart', 'pint']
    for unit in american_units:
        s = s.replace(unit + '/', unit + ' ')
        s = s.replace(unit + 's/', unit + 's ')

    return word_tokenize(clump(s))



def clump(s):
    """
    Replaces the whitespace between the integer and fractional part of a quantity
    with a dollar sign, so it's interpreted as a single token. The rest of the
    string is left alone.
        clumpFractions("aaa 1 2/3 bbb")
        # => "aaa 1$2/3 bbb"
    """

    return re.sub(r'(\d+)\s+(\d)/(\d)', r'\1-space-\2/\3', s)

def unclump(s):
    """
    Replacess $$$'s with spaces. The reverse of clumpFractions.
    """
    return re.sub(r'-space-', " ", s)

# some data entries include an html tag
def clean(s):
    return re.sub("(<.*>)", "", s)

