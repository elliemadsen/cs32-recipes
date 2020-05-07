import React from 'react';
import $ from "jquery";


class Signup extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      fail: false,
      username: null,
      password: null,
      comfirmPassword: null,
      empty: false,
      mismatch: false,
      suc:"no"
    };
    this.mySubmitHandler = this.mySubmitHandler.bind(this);

  }

  async mySubmitHandler(event) {
    event.preventDefault();

    if (this.state.username == null || this.state.password == null || this.state.comfirmPassword == null) {
      this.setState({ empty: true });
    }
    else {
      this.setState({ empty: false });
      if (this.state.password !== this.state.comfirmPassword) {
        this.setState({ mismatch: true });
      } else {

        this.setState({ mismatch: false });

        const API_URL = "http://localhost:4567/b/signup";
        const params = {"username": this.state.username, "password":this.state.password}

        await $.post(API_URL, params,response =>{
          console.log(response.results);
          this.setState({suc:response.results});
        })

        if (this.state.suc === "yes"){
          this.setState({ fail: false });
           window.location.href = "/search";

        }
        else{
          this.setState({fail:true});
        }
      }
    }
  }

  myChangeHandler = (event) => {
    let nam = event.target.name;
    let val = event.target.value;
    this.setState({ [nam]: val });
  }

  myButtonHandler = () => {
    window.location.href = "/login";

  }

  render() {
    return (
      <div>
        <form onSubmit={this.mySubmitHandler}>
          <h1> Sign Up </h1>
          {this.state.fail ? <p> Username is taken. Please try again. </p> : <></>}
          {this.state.empty ? <p> Please enter username, password and comfirm password. </p> : <></>}
          {this.state.mismatch ? <p> Passwords do not match. Please try again. </p> : <></>}

          <p>Username:</p>
          <input
            type='text'
            name='username'
            onChange={this.myChangeHandler}
          />
          <p>Password:</p>
          <input
            type='password'
            name='password'
            onChange={this.myChangeHandler}
          />
          <p>Comfirm Password:</p>
          <input
            type='password'
            name='comfirmPassword'
            onChange={this.myChangeHandler}
          />
          <input
            type='submit'
          />
        </form>
        <p>Already have an account?:</p>
        <button onClick={this.myButtonHandler}>Click here!</button>
      </div>



    );
  }
}


export default Signup;