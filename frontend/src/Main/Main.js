import React, { Component } from 'react'
import { Route, Redirect, Switch } from 'react-router-dom'
import Login from './Login/Login';
import Signup from './Signup/Signup'
import UploadFiles from './UploadFiles/UploadFiles.js'
import Employees from './Employees/Employees'
import Profile from './Profile/Profile'
import './Main.css'
import { withRouter } from 'react-router-dom'
import NewEmployee from './Employees/NewEmployee/NewEmployee';
import Phones from './Employees/Phones/Phones'
import Home from './Home/Home'

class Main extends Component {

    render() {
        const currentUser = this.props.currentUser;
        if (!currentUser) {
            return (
                <main className="main">
                    <div className="container">
                        <Switch>
                            <Route path="/home" component={Home} />
                            <Route path="/login" render={(props) => <Login updateUser={this.props.updateUser} />} />
                            <Route path="/signup" component={Signup} />
                            <Redirect from="/" to="/home" />
                        </Switch>
                    </div>
                </main>
            )
        } else {
            return (
                <main className="main">
                    <div className="container">
                        <Route path="/employees" component={Employees} />
                        <Route path="/me" component={Profile} />
                        <Route path="/employee-new" component={NewEmployee} />
                        <Route path="/employee-update/:id" render={(props) => <NewEmployee edit="true" />} />
                        <Route path="/employee-phones/:id" component={Phones} />
                        <Route path="/upload" component={UploadFiles} />
                    </div>
                </main>
            )
        }
    }
}

export default withRouter(Main)