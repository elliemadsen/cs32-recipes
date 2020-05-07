import React from 'react';
import { getRestrictions, getAutoFill1, getAutoFill2, getPantry } from './Window';
import Menu from "./Menu";
import './Decoration.css';
import $ from "jquery";


class Pantry extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          pantry: null,
          itemToAdd: '',
          loading: true,
          suggestion: null,
          test: true
        };
        this.mySelectHandler = this.mySelectHandler.bind(this);
        this.myButtonHandler = this.myButtonHandler.bind(this);
        this.myChangeHandler = this.myChangeHandler.bind(this);

      }

      async componentDidMount(){
        const API_URL = "http://localhost:4567/b/getPantry";
        const params = {"none": null}

        await $.post(API_URL, params,response =>{
          console.log(response.pantry);
          this.setState({pantry:response.pantry});
        })
        this.setState({loading:false, suggestion:[]});
      }

      async mySelectHandler (item) {

        const API_URL = "http://localhost:4567/b/addPantry";
        const params = {"item": item}

        await $.post(API_URL, params,response =>{
          console.log(response.pantry);
          this.setState({pantry:response.pantry});
        })
        
      }

      async myChangeHandler(event) {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({[nam]: val});


        // CALL Backend.getAutocorrect(val) and setState(suggestion:)
        const API_URL = "http://localhost:4567/b/autocorrect";
        const params = {"word": val}

        await $.post(API_URL, params,response =>{
          this.setState({suggestion:response.suggestion});
        })


      }

      async myButtonHandler(item) {
   
        const API_URL = "http://localhost:4567/b/removePantry";
        const params = {"item": item}

        await $.post(API_URL, params,response =>{
          this.setState({pantry:response.pantry});
        })
  
     }

      list = (array) =>{
          return array.map(item=>(
              <li className = {"noSelect2"}>
              <button onClick={() => this.myButtonHandler(item)}> X </button>
              {item}
              </li> 
          ));
      }

      listSuggest = (array) =>{
        return array.map(item=>(
            <li onClick={() => this.mySelectHandler(item)}>{item} 
            
            </li> 
        ));
    }

      render() {
        return (
        <div>
                <Menu/>
          <h1> Pantry </h1>

          <form >
          <p>Add to yourPantry:</p>
          <input
            type='text'
            name='itemToAdd'
            onChange={this.myChangeHandler}
            autocomplete="off"
          />
          </form>
          {this.state.loading ? (<p>loading...</p>):( !this.state.suggestion ? (<p>loading failed</p>) : (this.listSuggest(this.state.suggestion)))}


          <p>Your current Pantry:</p>
          {this.state.loading ? (<p>loading...</p>):( !this.state.pantry ? (<p>loading failed</p>) : (
            <div className = {"list2"}>

            {this.list(this.state.pantry)}
            </div>
            ))}

          </div>

                  

        );
      }
    }

  export default Pantry;