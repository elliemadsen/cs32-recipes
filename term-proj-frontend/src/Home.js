import React from 'react';
import getRecipes from './requests'
import { getElementError } from '@testing-library/dom';
class Home extends React.Component {
    constructor(props) {
      super(props);  
      this.recipeHandler = this.recipeHandler.bind(this)
    }
  
    async recipeHandler() {
      getRecipes(["hi"], ["ho", "ho"], ["ha"], 1).then(recipes => console.log(recipes))
    }
    render(){
      return (
      <div className="App">
        <button onClick={this.recipeHandler}></button>
      </div>
    )};
  }
  
  export default Home;