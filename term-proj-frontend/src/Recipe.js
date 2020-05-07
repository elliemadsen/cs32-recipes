import React from 'react';
import Menu from "./Menu";
import $ from "jquery";

class Recipe extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          recipe: null,
          ingredients: null,
          instructions: null,
          loading: true,
          url: null,
          name:null
        };
      }

      async componentDidMount(){
        //CALL Backend.getIngredients()
        //CALL Backend.getInstructions()

        const API_URL = "http://localhost:4567/b/getRecipe";
        const params = {"none": null}

        await $.post(API_URL, params,response =>{
          this.setState({url:response.recipe, name:response.name});
        })

        this.setState({loading:false,  recipe: 'Spaghetti', ingredients: ['tomato', 'pasta'], instructions: ['cook pasta', 'make sauce']});
      }

      list = (array) =>{
          return array.map(item=>(
              <li className={'noSelect'}>{item}</li>
          ));
      }


      render() {
   
        return (
        <div>
                <Menu />

        <h1> {this.state.name} </h1>
          <iframe src={this.state.url} allowfullscreen></iframe>

          {/* {this.state.loading ? (<p>loading...</p>):
          ( this.state.ingredients === null||!this.state.instruction ===null||this.state.recipe === null ? 
          (<p>loading failed</p>) : (

          

          <div>
          <h1> {this.state.recipe} </h1>
          <column className = {"column2"}>

          <p>Instructions:</p>
          <div className = {"list2"}>

          {this.list(this.state.instructions)}
          </div>

          </column>
          <column className = {"column2"}>
          <p>Ingredients:</p>
          <div className = {"list2"}>

          {this.list(this.state.ingredients)}
          </div>
          </column>
          </div>
          ))} */}

          </div>

                  

        );
      }
    }

  export default Recipe;