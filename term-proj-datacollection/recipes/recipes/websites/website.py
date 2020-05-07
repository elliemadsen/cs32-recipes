from bs4 import BeautifulSoup
from recipes.items import RecipeItem
class Website():

    def __init__(self, name, domain, start_urls, recipe_path="", mongo_collection=None):
        self.name = name
        self.domain = domain
        self.start_urls = start_urls
        self.recipe_path = recipe_path
        self.mongo_collection = mongo_collection

    def extract_recipe(self, html):
        soup = BeautifulSoup(html, 'lxml')
        schema = soup.find("script", type="application/ld+json")
        if schema is None:
            return None
        # kind of sketchy but schema is a JSON string and should directly translate to a list of Python dicts with exception of bools
        schema_data = eval(schema.string.replace("true", "True").replace("false", "False"))

        if (isinstance(schema_data, dict) and schema_data.get("@type")=="Recipe"):
            recipe = schema_data
        elif not(schema_data is list and len(schema_data) > 0 and "@type" in schema_data[0] and schema_data[0]["@type"] == "Recipe"):
            recipe = schema_data[0]
        else:
            return None

        recipe_dict = {
            "name": recipe.get("name"),
            "url": recipe.get("url"),
            "author": recipe.get("author")[0].get("name") if ("author" in recipe and 0 in recipe.get("author")) else None,
            "image": {
                "url": recipe.get("image").get("url") if recipe.get("image") is not None else None,
                "description": recipe.get("image").get("description") if recipe.get("image") is not None else None
            },
            "date": recipe.get("datePublished"),
            "publisher": {
                "name": recipe.get("publisher").get("name") if recipe.get("publisher") is not None else None,
                "logo": recipe.get("publisher").get("logo").get("url") if recipe.get("publisher") is not None else None
            },
            "keywords": recipe.get("keywords"),
            "cookTime": recipe.get("cookTime"),
            "totalTime": recipe.get("totalTime"),
            "ingredients": recipe.get("recipeIngredient"),
            "nutrition": recipe.get("nutrition"),
            "instructions": recipe.get("recipeInstructions"),
            "rating": {
                "average": recipe.get("aggregateRating").get("ratingValue") if recipe.get("aggregateRating") is not None else None,
                "count": recipe.get("aggregateRating").get("reviewCount") if recipe.get("aggregateRating") is not None else None
            },
            "yield": recipe.get("recipeYield")
        }

        instructs = map(lambda x: x.get("text"), recipe_dict.get("instructions"))
        #parsed_ingredients = map(lambda x: predict("test2", x), recipe_dict["ingredients"])
        #recipe_dict["parsedIngredients"] = list(parsed_ingredients)
        recipe_dict["instructions"] = list(instructs)
        return recipe_dict        

class FoodNetwork(Website):
    def __init__(self, mongo_collection="foodnetwork"):
        super().__init__("Food Network", "foodnetwork.com", ["https://www.foodnetwork.com/"], "foodnetwork.com/recipes", mongo_collection) 

    def extract_recipe(self, html):
        return super().extract_recipe(html) 

