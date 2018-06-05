import React, { Component } from 'react'
import "./Header.css"
import "../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import { BrowserRouter as Router, Link, Route } from 'react-router-dom'

class Header extends Component {

    render() {
        const isAuthenticated = this.props.isAuthenticated;
        const currentUser = this.props.currentUser;
        let headerNavigation = null;

        console.log('auth: ' + isAuthenticated)
        if (!isAuthenticated) {
            headerNavigation = (
                <ul className="nav">
                    <li className="nav-item">
                        <Link className="nav-link active" to="/login">Login</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/signup">Sign up</Link>
                    </li>
                </ul>);
        } else {
            headerNavigation = (
                <ul className="nav">
                    <li className="nav-item">
                        <Link className="nav-link active" to="/employees">Employees</Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link" to="/me">{currentUser.username.toUpperCase()}</Link>
                    </li>
                </ul>);
        }

        return (
            <header className="header">
                <div className="container">
                    <div className="header__nav">
                        {headerNavigation}
                    </div>
                </div>
            </header>
        )
    }
}

export default Header