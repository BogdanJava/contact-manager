import React, { Component } from 'react'
import "./Header.css"
import "../../node_modules/bootstrap/dist/css/bootstrap.min.css"
import { BrowserRouter as Router, Link, Route } from 'react-router-dom'
import { logout } from '../utils/APIUtils';
import { withRouter } from 'react-router-dom'

class Header extends Component {

    constructor(props) {
        super(props)

        this.handleLogout = this.handleLogout.bind(this)

    }

    handleLogout(event) {
        logout().then(response => {
            console.log(response)
            if (response.success) {
                window.location.href = '/login'
            }
        })
    }

    render() {
        const isAuthenticated = this.props.isAuthenticated;
        const currentUser = this.props.currentUser;
        let headerNavigation = null;

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
                    <li className="nav-item">
                        <Link className="nav-link" to="/upload">Upload files</Link>
                    </li>
                    <li className="nav-item">
                        <a className="nav-link" onClick={this.handleLogout} href="#">Logout</a>
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

export default withRouter(Header)