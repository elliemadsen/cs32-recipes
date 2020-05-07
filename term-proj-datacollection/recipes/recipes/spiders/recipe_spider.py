import sys
sys.path.append('.')
import scrapy
from scrapy.linkextractors import LinkExtractor
from scrapy.http import Request, HtmlResponse
from recipes.items import RecipeItem
from recipes.websites.website import Website

class RecipeSpider(scrapy.Spider):
    
    def __init__(self, website):
        self.website = website
        self.name = website.name
        self.start_urls = website.start_urls
        self.link_extractor = LinkExtractor(allow=website.recipe_path, allow_domains=website.domain)


    def parse(self, response):
        # a list containing a recipe and all links on the page in the form of Requests
        ret = []
        # some websites have a path for recipes, i.e. "foodnetwork.com/recipes". Checks if current url matches that path.
        if self.website.recipe_path in response.url:
            recipe = self.website.extract_recipe(response.text)
            if recipe is not None:
                recipe["url"] = response.url
                ret.append(recipe)

        links_on_page = self._extract_requests(response)
        ret.extend(links_on_page)
        return ret

    def _extract_requests(self, response):
        r = []
        if isinstance(response, HtmlResponse):
            links = self.link_extractor.extract_links(response)
            r.extend(Request(x.url, callback=self.parse) for x in links)
        return r
