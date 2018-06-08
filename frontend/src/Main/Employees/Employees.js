import React from 'react'
import './Employees.css'
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import { getEmployees } from '../../utils/APIUtils'
import { BrowserRouter as Router, Route, Link } from 'react-router-dom'
import { deleteEmployee } from './../../utils/APIUtils';
import { createNotification, MessageType } from '../../utils/notifications'

export default class Employees extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            employees: null,
            loading: false
        }

        this.fetchEmployees = this.fetchEmployees.bind(this)
        this.deleteEmployeeById = this.deleteEmployeeById.bind(this)
    }

    deleteEmployeeById(event) {
        let id = event.target.value
        deleteEmployee(id).then(response => {
            console.log(response)
            if (response.success) {
                this.fetchEmployees()
                createNotification(MessageType.SUCCESS, response.message, 3000)
            }
        }).catch(error => {
            createNotification(MessageType.ERROR, error.message, 3000)
            console.log(error)
        })
    }

    fetchEmployees() {
        let promise = getEmployees()
        this.setState({
            loading: true
        })
        promise.then(response => {
            this.setState({
                employees: response,
                loading: false
            })
        }).catch(error => {
            console.log(error)
            this.setState({
                loading: false
            })
        })
    }

    componentWillMount() {
        this.fetchEmployees()
    }

    render() {
        return (
            <section className="employees-table">
                <div className="employees-table__header">
                    <h2>Employees</h2>
                </div>
                <div className="employees-table__table">
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <td>Name</td>
                                <td>Surname</td>
                                <td>Patronymic</td>
                                <td>Address</td>
                                <td>Birthday</td>
                                <td>Actions</td>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                !this.state.loading ? (
                                    this.state.employees.map(employee => {
                                        return (
                                            <tr key={employee.id}>
                                                <td>{employee.name}</td>
                                                <td>{employee.surname}</td>
                                                <td>{employee.patronymic}</td>
                                                <td>
                                                    {employee.address.country},
                                                    {employee.address.city},
                                                    {employee.address.street}
                                                </td>
                                                <td>{employee.birthday}</td>
                                                <td>
                                                    <Link className="btn btn-success" to={`/employee-phones/${employee.id}`}>Show phones</Link>
                                                    <Link className="btn btn-warning" to={`/employee-update/${employee.id}`}>Edit</Link>
                                                    <button className="btn btn-danger" value={employee.id} onClick={this.deleteEmployeeById}>Delete</button>
                                                </td>
                                            </tr>
                                        )
                                    }
                                    )
                                ) : null
                            }
                            {
                                this.state.loading ? (
                                    <tr>
                                        <td>Loading...</td>
                                    </tr>
                                ) : null
                            }
                        </tbody>
                    </table>
                    <Link className="btn btn-primary" id="button-add" to="/employee-new">Add</Link>
                </div>
            </section>
        )
    }
}