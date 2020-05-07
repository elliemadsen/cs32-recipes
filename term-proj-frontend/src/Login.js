import React from 'react';
import $ from "jquery";


class Login extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      fail: false,
      username: null,
      password: null,
      empty: false,
      suc: "no"
    };
    this.mySubmitHandler = this.mySubmitHandler.bind(this);

  }


  async mySubmitHandler(event) {

    event.preventDefault();

    if (this.state.username == null || this.state.password == null) {
      this.setState({ empty: true });
    }

    else {
      this.setState({ empty: false });

        const API_URL = "http://localhost:4567/b/login";
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

  myChangeHandler = (event) => {
    let nam = event.target.name;
    let val = event.target.value;
    this.setState({ [nam]: val });
  }

  myButtonHandler = () => {
    window.location.href = "/signup";

  }

  render() {
    return (
      <div>
        <form onSubmit={this.mySubmitHandler}>
          <h1> Login </h1>
          {this.state.empty ? <p> Please enter username and password. </p> : <></>}
          {this.state.fail ? <p> Username or password is incorrect. </p> : <></>}
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
          <input className={"submit"}
            type='submit'
          />
        </form>
        <p>New to Reverse Recipe?:</p>
        <button onClick={this.myButtonHandler}>Click here!</button>
      </div>



    );
  }
}



export default Login;