import React from 'react';
import { getRestrictions, getAutoFill1, getAutoFill2, getPantry } from './Window';
import Menu from "./Menu";
import './Decoration.css';


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
        //CALL Backend.getPantry()
        this.setState({loading:false, pantry:getPantry(), suggestion:[]});
      }

      async mySelectHandler (item) {

        //CALL Backend.addToPantry(item)
        alert("Added " + item + "to list of pantry");
        //CALL Backend.getPantry() and setState())
      }

      async myChangeHandler(event) {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({[nam]: val});


        //CALL Backend.getAutocorrect(val) and setState(suggestion:)

        //delete later
        if (this.state.test){
          this.setState({suggestion: getAutoFill1(), test:false});
        } else{
          this.setState({suggestion: getAutoFill2(), test:true});

        }

      }

      async myButtonHandler(item) {
        //CALL Backend.removeFromPantry(item)
        alert("removed "+item+" from pantry" );
        //CALL Bckend.getPantry and setState()
  
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