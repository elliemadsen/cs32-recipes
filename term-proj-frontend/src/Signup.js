import React from 'react';


class Signup extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      fail: false,
      username: null,
      password: null,
      comfirmPassword: null,
      empty: false,
      mismatch: false
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

        const params = {
          username: this.state.username,
          password: this.state.password
        }
        //CALL Backend.signup(this.state.username, this.state.password)

        //if return value is 1, success
        this.setState({ fail: false });
        window.location.href="/search";

        //else, fail
        this.setState({ fail: true });
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