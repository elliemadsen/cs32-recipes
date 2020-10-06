# cs0320 Term Project 2020

**Team Members:** 

## Yuna Hiraide 

## Ellie Madsen

## Mason Zhang



### Reverse Recipes
<Reverse Recipes>
Problem the project is attempting to solve/how
With millions of delicious recipes on the web, the biggest limitation to what we can make are the ingredients we currently have. The primary purpose of this project would be finding popular and highly-rated recipes that the user can make given several constraints (ingredients, utensils, time spent, cuisine), and maybe even suggest what things you could easily buy to make even more recipes.
Required features
Allows you to input the food/utensils/appliances that you have as a list and outputs a couple of recipes you can make
Have an account that stores a ‘default’ list of ingredients you have, which you can add and delete items from
Keep track of allergies, dietary restrictions (vegetarian, kosher)
The user can set a ratings/number of reviews filter to only get popular recipes. 
The app should suggest things that you could buy to make more recipes ranking them by how difficult/expensive it would be to get these additional items. The user can select a price cap of say, $10 and the recipe engine will suggest additional recipes that can be made by buying ingredients that cost less than $10. 
In the same spirit, the user can add ‘friends’ on the app and search their friends’ pantries (default list of ingredients they usually have) for any missing ingredients. So the recipe engine will return recipes that you can make if you borrow a couple of your friends’ ingredients. We can make a counter keeping track of the net number of times two friends have borrowed from each other (for example, if I’ve borrowed from you 6 times and you’ve borrowed from me 3 times, I’d see -3 on the counter).
Of course, you can turn this feature off, and if you do then neither you nor your friends can access each others’ pantries. 
This will be a somewhat complex algorithm that lets the user specify how many items they want to borrow, the price of the items, how far to search, etc. We’d likely have to make it faster than the naive solution of brute forcing recipes + friends’ items.


## How to Build and Run
_A necessary part of any README!_
