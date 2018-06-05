import React, { Component } from 'react'
import { Route } from 'react-router-dom'
import Login from './Login/Login';
import Signup from './Signup/Signup'
import './Main.css'

class Main extends Component {

    render() {
        const currentUser = this.props.currentUser;
        if (!currentUser) {
            return (
                <main className="main">
                    <Route path="/login" exact component={Login} />
                    <Route path="/signup" component={Signup} />
                </main>
            )
        } else {
            return "Hello world"
        }
    }
}

export default Main