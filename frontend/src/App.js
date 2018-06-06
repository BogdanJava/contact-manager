import React, { Component } from 'react';
import './App.css';
import Header from './Header/Header'
import Main from './Main/Main'
import Footer from './Footer/Footer'
import { BrowserRouter as Router, Link, Route } from 'react-router-dom'
import { getCurrentUser } from './utils/APIUtils'

class App extends Component {

  constructor(props) {
    super(props)

    this.state = {
      currentUser: null,
      isAuthenticated: false
    }
  }

  loadCurrentUser() {
    console.log(this)
    getCurrentUser()
      .then(response => {
        console.log(response)
        this.setState({
          currentUser: response,
          isAuthenticated: true
        });
      }).catch(error => {
        console.log(error)
      });
  }

  componentWillMount() {
    this.loadCurrentUser()
  }

  render() {
    return (
      <Router>
        <div className="app">
          <Header isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} />
          <Main currentUser={this.state.currentUser} updateUser={this.loadCurrentUser.bind(this)} />
          <Footer />
        </div>
      </Router>
    );
  }
}

export default App;
