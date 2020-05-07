import sys
from bs4 import BeautifulSoup
from textblob import TextBlob
from .website import Website

class BBC(Website):
    def __init__(self, mongo_collection="bbc"):
        name = "BBC"
        domain="bbc.co.uk"
        start_urls=["https://www.bbc.co.uk/food/recipes/curried_carrot_soup_29946"]
        recipe_path="bbc.co.uk/food/recipes"
        super().__init__(name, domain, start_urls, recipe_path, mongo_collection)

    def extract_recipe(self, html):
        soup = BeautifulSoup(html, 'lxml')    
        schema = soup.find("script", type="application/ld+json")
        if schema is None:
            return None
        # kind of sketchy but schema is a JSON string and should directly translate to a list of Python dicts with exception of bools
        schema_data = eval(schema.string.replace("true", "True").replace("false", "False"))

        if (isinstance(schema_data, dict) and schema_data.get("@type")=="Recipe"):
            recipe = schema_data
        else:
            return None

        def parse_ingredient(ingredient):
            parsed_ingredient = ingredient.find(class_="recipe-ingredients__link")
            if parsed_ingredient is None:
                return ingredient.get_text()
            else:
                return parsed_ingredient.get_text()

        ingredients = soup.find_all("li", class_="recipe-ingredients__list-item" )
        parsed_ingredients = map(lambda ing: parse_ingredient(ing), ingredients)
        recipe_dict = {
            "name": recipe.get("name"),
            "url": recipe.get("url"),
            "author": recipe.get("author").get("name") if ("author" in recipe) else None,
            "image": {
                "url": recipe.get("image")[0] if recipe.get("image") is not None else None,
                "description": None
            },
            "date": recipe.get("datePublished"),
            "keywords": recipe.get("keywords"),
            "cookTime": recipe.get("cookTime"),
            "prepTime": recipe.get("prepTime"),
            "totalTime": recipe.get("totalTime"),
            "ingredients": recipe.get("recipeIngredient"),
            "parsedIngredients": list(parsed_ingredients),
            "nutrition": recipe.get("nutrition"),
            "instructions": recipe.get("recipeInstructions"),
            "rating": {
                "average": recipe.get("aggregateRating").get("ratingValue") if recipe.get("aggregateRating") is not None else None,
                "count": recipe.get("aggregateRating").get("reviewCount") if recipe.get("aggregateRating") is not None else None
            },
            "yield": recipe.get("recipeYield")
        }

        return recipe_dict        


