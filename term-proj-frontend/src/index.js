import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';

import * as serviceWorker from './serviceWorker';
import { Component } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import {getName, getFavorite, setFavorite, setPantry, setRestrictions, setRecipe, setSuggestions} from './Window.js'


setFavorite(['Spaghetti', 'pancakes', 'cheese burger']);
setPantry(['tomato', 'eggs', 'milk']);
setRecipe("FIRSTRECIPE");
setRestrictions(['nuts', 'soy']);
setSuggestions(['Spaghetti', "Scrambled Eggs"]);

ReactDOM.render(
  <React.StrictMode>

        <BrowserRouter>
        
<App/>
    </BrowserRouter>

  </React.StrictMode>,
  
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
