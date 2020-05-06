import React from 'react';
import './Decoration.css';

class Menu extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
      };
    this.changeHandler = this.changeHandler.bind(this);

    }
    async changeHandler (event) {
        event.preventDefault();
        const choice = event.target.value;
        window.location.href="/"+choice;
      }


    render() {
      return (
        <form >
          <div className = {"menu"} >
        <select onChange={this.changeHandler}>
          <option value=''>Select...</option>
          <option value='search'>Search</option>
          <option value='pantry'>Pantry</option>
          <option value='restrictions'>Restrictions</option>
          <option value='signup'>Sign out</option>
        </select>
        </div>
        </form>

      );
    }
  }
  export default Menu;