import React from 'react';
import { getRestrictions, getAutoFill1, getAutoFill2 } from './Window';
import Menu from "./Menu";
import './Decoration.css';


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
       
        //CALL Backend.getRestriction()
        this.setState({loading:false, restrictions:getRestrictions(), suggestion:[]});
      }

      async mySelectHandler (item) {

       //CALL Backend.addToRestriction(item)
        alert("Added " + item + "to list of restrictions");
        //CALL Backend.getRestriction()
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

        //CALL Backend.removeFromRestriction(item)
        alert("removed "+item+" from restrictions" );
        //CALL Backend.getRestriction() and setState()


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