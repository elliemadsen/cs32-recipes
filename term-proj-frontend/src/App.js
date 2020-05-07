import React from 'react';
import './App.css';
import Signup from './Signup';
import Login from './Login';
import Search from './Search';
import Recipe from './Recipe';
import Pantry from './Pantry';
import Home from './Home';
import Restriction from './Restriction';
import {Route, Switch } from 'react-router-dom';



class App extends React.Component {
  constructor(props) {
    super(props);

  }

  render(){
    return (
    <div className="App">
   
  <h2> Reverse Recipe </h2>


      <Switch>
      {/* <Route path='/' render={(props)=><Home {...props} />} />       */}
      <Route path='/signup' render={(props)=><Signup {...props} />} />      
      <Route path='/login' render={(props)=><Login {...props}  />} />
      <Route path='/search' render={(props)=><Search {...props}  />} />
      <Route path='/recipe' render={(props)=><Recipe {...props} />} />
      <Route path='/pantry' render={(props)=><Pantry {...props} />} />
      <Route path='/restrictions' render={(props)=><Restriction {...props} />} />

      </Switch>

    </div>
  )};
}

export default App;
