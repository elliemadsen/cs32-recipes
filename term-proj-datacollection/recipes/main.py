
from scrapy.crawler import CrawlerProcess
from scrapy.utils.project import get_project_settings
from recipes.spiders.recipe_spider import RecipeSpider
from recipes.websites.website import *
from recipes.websites.bbc import *

class Main: 


    def run(self):
        #initialize web crawlers list of Websites and run them
        process = CrawlerProcess(get_project_settings())
        food_network = FoodNetwork()
        process.crawl(RecipeSpider, food_network)
        process.start()

    def run_debug(self):
        process = CrawlerProcess(get_project_settings())
        website = Website("Food Network", "foodnetwork.com", 
        ["https://www.foodnetwork.com/recipes/melissa-darabian/garlic-oil-sauteed-pasta-with-broccoli-recipe-1920918"], 
        "foodnetwork.com/recipes")
        process.crawl(RecipeSpider, website)
        process.start()

    def extract_recipes_to_db(self, website_list, settings={}):
        if settings != {}:
            process = CrawlerProcess(settings)
        else: 
            process = CrawlerProcess(get_project_settings())

        for website in website_list:
            process.crawl(RecipeSpider, website)
        process.start()



if __name__ == "__main__":
        #Main().run()
        settings={
            "BOT_NAME": 'recipes',
            "SPIDER_MODULES": ['recipes.spiders'],
            "NEWSPIDER_MODULE": 'recipes.spiders',
            "ITEM_PIPELINES": {'recipes.pipelines.MongoPipeline': 300, },
            "MONGO_URI": "mongodb+srv://masonzhang21:Zmas3.14@cluster0-xndfh.gcp.mongodb.net/test?retryWrites=true&w=majority",
            "MONGO_DATABASE": "recipes",
            "DEPTH_LIMIT": "12"
        }
        Main().extract_recipes_to_db([BBC()], settings)