import React, { Component } from 'react'
import { Route } from 'react-router-dom'
import Login from './Login/Login';
import Signup from './Signup/Signup'
import Employees from './Employees/Employees'
import Profile from './Profile/Profile'
import './Main.css'
import { withRouter } from 'react-router-dom'
import NewEmployee from './Employees/NewEmployee/NewEmployee';

class Main extends Component {

    render() {
        const currentUser = this.props.currentUser;
        if (!currentUser) {
            return (
                <main className="main">
                    <div className="container">
                        <Route path="/login" render={(props) => <Login updateUser={this.props.updateUser} />} />
                        <Route path="/signup" component={Signup} />
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
                        <Route path="/employee-update/:id" render={(props) => <NewEmployee edit="true"/>}/>
                    </div>
                </main>
            )
        }
    }
}

export default withRouter(Main)