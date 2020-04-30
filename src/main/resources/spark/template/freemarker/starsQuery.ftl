<#assign content>

<link rel="stylesheet" href="css/stars.css">

<form method="GET" action="/timdb" class = "change-form">
 <button type="submit" class="btn btn-dark">Search TIMDB</button>
</form>

<h1> Search the Stars </h1>

<form method="GET" action="/neighbors" class = "stars-form">
<h2> k nearest neighbors </h2>
  <textarea class= "form-control" name="neighbors-query"></textarea>
  <div class = "example">k x y z <span class = "space"></span><b>OR</b> <span class = "space"></span>k "name" </div>
  <button type="submit" class="btn btn-dark">Submit</button>
</form>

<div class = "results"> ${neighborResults} </div>

<form method="GET" action="/radius" class = "stars-form">
<h2> nearest neighbors within radius </h2>
   <textarea class= "form-control" name="radius-query" ></textarea>
   <div class = "example">r x y z <span class = "space"></span><b>OR</b> <span class = "space"></span>r "name" </div>
   <button type="submit" class="btn btn-dark">Submit</button>
</form>

<div class = "results"> ${radiusResults} </div>

</#assign>
<#include "main.ftl">