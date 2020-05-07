import React from 'react';
import { getRestrictions, getAutoFill1, getAutoFill2 } from './Window';
import Menu from "./Menu";
import './Decoration.css';
import $ from "jquery";


class Restriction extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          restrictions: null,
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
        const API_URL = "http://localhost:4567/b/getRestriction";
        const params = {"none": null}

        await $.post(API_URL, params,response =>{
          console.log(response.restriction);
          this.setState({restrictions:response.restriction});
        })
        this.setState({loading:false, suggestion:[]});
      }

      async mySelectHandler (item) {

        const API_URL = "http://localhost:4567/b/addRestriction";
        const params = {"item": item}

        await $.post(API_URL, params,response =>{
          console.log(response.restriction);
          this.setState({restrictions:response.restriction});
        })
        
      }

      async myChangeHandler(event) {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({[nam]: val});

        const API_URL = "http://localhost:4567/b/autocorrect";
        const params = {"word": val}

        await $.post(API_URL, params,response =>{
          this.setState({suggestion:response.suggestion});
        })

      }

      async myButtonHandler(item) {

        const API_URL = "http://localhost:4567/b/removeRestriction";
        const params = {"item": item}

        await $.post(API_URL, params,response =>{
          this.setState({restrictions:response.restriction});
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

          <h1> Restrictions </h1>

          <form >
          <p>Add to list of restrictions:</p>
          <input
            type='text'
            name='itemToAdd'
            onChange={this.myChangeHandler}
            autocomplete="off"
          />
          </form>
          {this.state.loading ? (<p>loading...</p>):( !this.state.suggestion ? (<p>loading failed</p>) : (this.listSuggest(this.state.suggestion)))}


          <p>Your current list of restrictions:</p>
          {this.state.loading ? (<p>loading...</p>):( !this.state.restrictions ? (<p>loading failed</p>) : (
            <div className = {"list2"}>

            {this.list(this.state.restrictions)}
            </div>
            ))}

          </div>

                  

        );
      }
    }

  export default Restriction;