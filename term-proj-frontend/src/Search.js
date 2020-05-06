import React from 'react';
import { getPantry, getRestrictions, getSuggestions, getAutoFill1, getAutoFill2 } from './Window';
import Menu from "./Menu";
import './Decoration.css';
import Restriction from './Restriction';
import Pantry from './Pantry';


class Search extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          pantry: null,
          restrictions: null,
          suggestion:null,
          loading: true,
          test:true, //delete later
        };

      }

      async componentDidMount(){
        //CALL Backend.getPantry()
        //CALL Backend.getRestriction()
        
        this.setState({loading:false, pantry:getPantry(), restrictions:getRestrictions(), 
        
        });
      }


     async mySelectHandler (recipe) {

      //CALL Backend.setRecipe(recipe) // ??? 
      window.location.href = "/recipe";
  
    }

    async mySelectHandlerInclude (toInclude) {

      //CALL Backend.getSearch(toInclude) //set suggestionbg 
      alert("Searching recipes that include " + toInclude);
      this.setState({suggestion:getSuggestions()})

    }

      list = (array) =>{
        return array.map(item=>(
            <li className={'noSelect'}>{item} </li> 
        ));
    }

    listInclude = (array) =>{
      return array.map(item=>(
        <li  onClick={() => this.mySelectHandlerInclude(item)}>{item} 
        </li> 
      ));
  }



  ////display only recipe.name but pass whole recipe to handler
    listSelect = (array) =>{
        return array.map(item=>(
            <li  onClick={() => this.mySelectHandler(item)}>{item}  
            </li> 
        ));
    }


      render() {
        return (
        <div>
                <Menu/>

                {this.state.loading ? (<p>loading...</p>):
                (  this.state.pantry === null||this.state.restrictions ===null ? 
                (<p>loading failed</p>) : (

          <div>
          <form >
        <h1> Search </h1>
     
          </form>
          <column className = {"column2"}>
          <p>Your current pantry:</p>
          <h4>Click on the item to search for recipes that include that item:</h4>
          <div className = {"list2"}>

          {this.listInclude(this.state.pantry)}
          </div>

          <p>Your current list of restrictions:</p>
          <div className = {"list2"}>

          {this.list(this.state.restrictions)}
          </div>
          </column>


          <column className = {"column2"}>

          <p>Suggestion:</p>
          <div className = {"list2"}>
          
          {this.state.suggestion === null ? <></>: this.listSelect(this.state.suggestion)}
          </div>
          </column>
          </div>
          ))}

          </div>

                  

        );
      }
    }

  export default Search;