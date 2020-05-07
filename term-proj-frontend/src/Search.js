import React from 'react';
import { getPantry, getRestrictions, getSuggestions, getAutoFill1, getAutoFill2 } from './Window';
import Menu from "./Menu";
import './Decoration.css';
import Restriction from './Restriction';
import Pantry from './Pantry';
import $ from "jquery";


class Search extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          pantry: null,
          restrictions: null,
          suggestion:null,
          loading: true,
          test:true, //delete later
          load:false,
          image:"undefined.jpg"

        };

      }

      async componentDidMount(){
        const API_URL = "http://localhost:4567/b/getPantry";
        const params = {"none": null}

        await $.post(API_URL, params,response =>{
          console.log(response.pantry);
          this.setState({pantry:response.pantry});
        })

        const API_URL2 = "http://localhost:4567/b/getRestriction";
        const params2 = {"none": null}

        await $.post(API_URL2, params2,response =>{
          console.log(response.restriction);
          this.setState({restrictions:response.restriction});
        })
        
        this.setState({loading:false, 
        
        });
      }


     async mySelectHandler (recipe) {

      //CALL Backend.setRecipe(recipe) // ??? 
      console.log(recipe.url)
      const API_URL = "http://localhost:4567/b/setRecipe";
      const params = {"recipe": recipe.url, "name":recipe.name}

      await $.post(API_URL, params, response =>{
        console.log(response);
      })


      window.location.href = "/recipe";
  
    }

    async mySelectHandlerInclude (toInclude) {
      this.setState({load:true});
      const API_URL = "http://localhost:4567/b/search";
      const params = {"item": toInclude}

      await $.post(API_URL, params,response =>{
        console.log(response.recipe);
        this.setState({suggestion:response.recipe});
        this.setState({load:false});

      })


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
            <li  onMouseEnter = {()=>this.mouse(item)} onMouseLeave ={()=>this.mouseleave()}  onClick={() => this.mySelectHandler(item)}>{item.name}  
            </li> 
        ));
    }

    async mouse (menu) {
      console.log(menu.image)
      this.setState({image:menu.image});
      // await $.post(API_URL, params,response =>{
      //   console.log(response.recipe);
      //   this.setState({suggestion:response.recipe});
      //   this.setState({load:false});

      // })
    }
    async mouseleave (menu) {
      console.log("leave")
      this.setState({image:""});

      // await $.post(API_URL, params,response =>{
      //   console.log(response.recipe);
      //   this.setState({suggestion:response.recipe});
      //   this.setState({load:false});

      // })
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
          { <img src={this.state.image}></img>}

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
          {!this.state.load ? (<></>):(<h4> Loading ... </h4>)}
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