<#assign content>

<link rel="stylesheet" href="css/timdb.css">

<form method="GET" action="/stars" class = "change-form">
 <button type="submit" class="btn btn-dark">Search Stars</button>
</form>

<h1> Connect the Actors </h1>

<h2> Enter two actor names to find out the most likely path of a golf ball between them! </h2>


<form method="GET" action="/connect" class = "actors-form">

<div class = "row">

<div class = "col">
<h2> Starting Actor </h2>
  <textarea class= "form-control" name="start-actor-query"></textarea>
 </div>
 
<div class = "col-md-6">
	<img src = "css/golfball_viz.png" class = "ball-path">
	
</div>

<div class = "col">
<h2> Ending Actor </h2>
  <textarea class= "form-control" name="end-actor-query"></textarea>
</div>

</div>

<button type="submit" class="btn btn-dark submit-btn">Submit</button> 


 
</form>


 <div class = "results"> ${results} </div>


</#assign>
<#include "main.ftl">