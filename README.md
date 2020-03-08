# cs0320 Term Project 2020

**Team Members:** 

## Yuna Hiraide 

Strengths:
Organized
love making gui
ReactJs

Weaknesses:
This is my first cs course with group projects
Github is vvv scary


## Ellie Madsen

Strengths
I have experience with the design process/user testing
organized

Weaknesses
No super broad experience related to swe
git


## Mason Zhang

Strengths 
I’ve done a bit of React/front end JS stuff and also Node.js

Weaknesses
First CS course with big group project
Don’t really know how to code with other people
Github is scary


## Melia Okura

Strengths:
I have done a semester group CS project before
Avid Google Calendar user
Have some experience with, Java Node

Weaknesses
I’m not super good at backend/server things
I still get confused when working with GitHub

### Brown/RISD Buy+Sell (edited)
_Rejected - no algorithm_

What is it?
A web app that combines the functionality of brown buying and selling, depop, and on-campus clothing swaps! It would display photos in a grid like depop and instagram, organized by item type instead of seller, to support quick and easy browsing. It will also compile a list of suggested items using an algorithm on what you like, what you are looking for, and previous things that you have purchased.

Required features
*Accounts
  *User logins (linked to brown gmail?)
  *mailbox number!
  *rating
*Clean interface -> photo grid
*Post things to sell
  *Tags
  *Categories
  *Price
  *recent	
(*Maybe) Helps buyer set prices by using existing data of similar product
*Either meet up to exchange item or drop off in mailbox
*When you are searching for items, in addition to being organized by price or posting date they could be organized by user specific specs like size or style.
*Allow users to save posts they like
challenges
*If you are looking for something to buy you can input the amount of money you have and maybe a hint of what you are looking for and it can suggest to you a list of item(s) that you can buy
managing multiple accounts
Should we include built in pay feature?
Should we include built in messaging?

### Goal Setter (edited)
_Rejected - no algorithm_

What is it?
A web application that takes the availability in your calendar and sets times for you to do daily/weekly/recurring tasks. Maybe you have something that you want to do for 10 minutes each day, or twice a week. This app would be able to automatically look for openings in your schedule to do these tasks.
Problems the project is attempting to solve / how
Scheduling times to do smaller more flexible tasks around other things that you have scheduled in your calendar. Maybe your schedule is pretty inconsistent, but you still want to find time to meditate everyday or do your laundry every week. Using this app, it will help find openings in your schedule to take care of tasks like that

Required features
A list of recurring tasks that you want to find time for 
You will also enter how often these tasks need to be done and how long it takes to do them. 
You can even set what time of the day you would like to do these things (morning, afternoon, evening, night, ect)
Reminders, set up the event in google calendar
If you schedule something during the time one of your smaller tasks was scheduled, it will just move it to the next most convenient time
If these tasks keep needing to be rescheduled, the app will let you know if you are not meeting your inputted requirement for how often these tasks need to be done and you will then either have to work with your calendar manually or ignore it for now.
Some tasks could be collaborative, and hopefully if we can use Google Calendar with this app we can make shared events and the like, 

challenges
What do we use as a calendar where you keep all your usual events? Ideally it would be cool to be able to use this with Google Calendar
Otherwise, we don’t know what other kinds of calendar APIs or applications there are
Will we have to implement our own calendar?
Bonus features
In app achievement awards for getting things done on a consistent basis


### Reverse Recipes (edited)
_Unsure - likely, but you have to make recommendations beyond simple filtering_

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


Bonus features
Add in natural language processing/voice recognition so you can say what ingredients you have to speed up input time
Add a food classifier algorithm so you can take a picture of your refrigerator/pantry and upload it to find ingredients.
Challenges
Integrating app with Recipes APIs to retrieve and store recipes
Efficiently finding recipes that satisfy constraints from very large database of possibilities


**Mentor TA:** _Put your mentor TA's name and email here once you're assigned one!_

## Meetings
_On your first meeting with your mentor TA, you should plan dates for at least the following meetings:_

**Specs, Mockup, and Design Meeting:** _(Schedule for on or before March 13)_

**4-Way Checkpoint:** _(Schedule for on or before April 23)_

**Adversary Checkpoint:** _(Schedule for on or before April 29 once you are assigned an adversary TA)_

## How to Build and Run
_A necessary part of any README!_
