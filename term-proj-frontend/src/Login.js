import React from 'react';



class Login extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      fail: false,
      username: null,
      password: null,
      empty: false,
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

      //CALL Backend.login(this.state.username, this.state.password)

      //if return value is 1, success.
      this.setState({ fail: false });
      window.location.href = "/search";

      //else, fail
      this.setState({ fail: true });
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