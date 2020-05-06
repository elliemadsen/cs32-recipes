import React from 'react';
import Menu from "./Menu";

class Recipe extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          recipe: null,
          ingredients: null,
          instructions: null,
          loading: true
        };
      }

      async componentDidMount(){
        //CALL Backend.getIngredients()
        //CALL Backend.getInstructions()

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

          <h1> Recipe </h1>
          {this.state.loading ? (<p>loading...</p>):
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
          ))}

          </div>

                  

        );
      }
    }

  export default Recipe;