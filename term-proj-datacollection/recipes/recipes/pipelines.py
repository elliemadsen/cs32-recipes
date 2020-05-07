# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://docs.scrapy.org/en/latest/topics/item-pipeline.html


import pymongo

class MongoPipeline:


    def __init__(self, mongo_uri, mongo_db):
        self.mongo_uri = mongo_uri
        self.mongo_db = mongo_db

    @classmethod
    def from_crawler(cls, crawler):
        return cls(
            mongo_uri=crawler.settings.get('MONGO_URI'),
            mongo_db=crawler.settings.get('MONGO_DATABASE')
        )

    def open_spider(self, spider):
        self.client = pymongo.MongoClient(self.mongo_uri)
        self.db = self.client[self.mongo_db]

    def close_spider(self, spider):
        self.client.close()

    def process_item(self, item, spider):
        collection = spider.website.mongo_collection
        if collection is not None and self.db[collection].count_documents({ 'url': item.get("url") }, limit = 1) == 0:
            self.db[collection].insert_one(dict(item))
            parsed_ingredients = dict.fromkeys(item.get("parsedIngredients"), item.get("url"))
            self.db["ingredients search"].update({"site": collection}, {"$push": parsed_ingredients }, True)
        return item