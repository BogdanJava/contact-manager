import React from 'react'
import './NewEmployee.css'
import { addEmployee, updateEmployee, getEmployeeById } from './../../../utils/APIUtils';
import { withRouter } from 'react-router-dom';
import { createNotification, MessageType } from '../../../utils/notifications'

class NewEmployee extends React.Component {

    constructor(props) {
        super(props)
        if (!this.props.edit) {
            this.state = {
                name: '',
                surname: '',
                patronymic: '',
                birthday: '',
                address: {
                    country: '',
                    city: '',
                    street: ''
                }
            }
        } else {
            this.state = {
                loading: true
            }
        }

        this.handleChange = this.handleChange.bind(this)
        this.handleSubmit = this.handleSubmit.bind(this)
        this.fetchEmployee = this.fetchEmployee.bind(this)
    }

    componentWillMount() {
        this.fetchEmployee()
    }

    fetchEmployee() {
        if (this.props.edit) {
            let promise = getEmployeeById(this.props.match.params.id)
            this.setState({
                loading: true
            })
            promise.then(response => {
                this.setState({
                    id: response.id,
                    name: response.name,
                    surname: response.surname,
                    patronymic: response.patronymic,
                    birthday: response.birthday,
                    address: response.address
                })
                this.setState({
                    loading: false
                })
            }).catch(error => {
                console.log(error)
                this.setState({
                    loading: false
                })
            })
        }
    }


    handleChange(event) {
        let field = event.target.name
        let value = event.target.value;
        let state = {}
        switch (field) {
            case 'name': state = { name: value }; break;
            case 'surname': state = { surname: value }; break;
            case 'patronymic': state = { patronymic: value }; break;
            case 'birthday': state = { birthday: value }; break;
            case 'country': state = {
                address: {
                    country: value, city: this.state.address.city, street: this.state.address.street
                }
            }; break;
            case 'city': state = {
                address: { country: this.state.address.country, city: value, street: this.state.address.street }
            }; break;
            case 'street': state = {
                address: { country: this.state.address.country, city: this.state.address.city, street: value }
            }; break;
        }
        this.setState(state)
    }

    handleSubmit(event) {
        event.preventDefault();
        if (!this.validateState(this.state)) {
            createNotification(MessageType.WARNING, "Invalid form", 4000)
            console.log('invalid form')
            return;
        }
        if (this.props.edit) {
            updateEmployee(this.state).then(response => {
                console.log(response)
                createNotification(MessageType.INFO, "Employee edited", 4000)
                this.props.history.push('/employees')
            }).catch(error => {
                console.log(error)
            })
        } else {
            addEmployee(this.state).then(response => {
                console.log(`added: ${response}`)
                createNotification(MessageType.INFO, "Employee added", 4000)
                this.props.history.push('/employees')
            }).catch(error => {
                console.log('error')
            })
        }
    }

    validateState(state) {
        if(state.birthday.match(/[\d]{4}-[\d]{2}-[\d]{2}/) == null) {
            createNotification(MessageType.ERROR, "Birthday field is invalid", 2000)
            return false;
        }
        if(state.name == "" || state.surname == "" || state.patronymic == "") {
            createNotification(MessageType.ERROR, "Please fill required fields", 2000)
            return false;
        }
        return true;
    }

    render() {
        console.log("2")
        return <section className="new-employee">
            <div className="new-employee__header">
                {this.props.edit ? <h2>Edit employee</h2> : <h2>New employee</h2>}
            </div>
            {!this.state.loading ? (
                <form className="new-employee__form" onSubmit={this.handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Name</label>
                        <input required={true} value={this.state.name} onChange={this.handleChange} type="text" className="form-control" id="name" name="name" placeholder="Name" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="surname">Surname</label>
                        <input required={true} value={this.state.surname} onChange={this.handleChange} type="text" className="form-control" id="surname" name="surname" placeholder="Surname" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="patronymic">Patronymic</label>
                        <input required={true} value={this.state.patronymic} onChange={this.handleChange} type="text" className="form-control" id="patronymic" name="patronymic" placeholder="Patronymic" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="birthday">Birthday</label>
                        <input required={true} value={this.state.birthday} onChange={this.handleChange} type="text" className="form-control" id="birthday" name="birthday" placeholder="yyyy-mm-dd" />
                    </div>
                    <h5>Address info</h5>
                    <div className="form-group">
                        <label htmlFor="country">Country</label>
                        <input value={this.state.address.country} onChange={this.handleChange} type="text" className="form-control" id="country" name="country" placeholder="Country" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="city">City</label>
                        <input value={this.state.address.city} onChange={this.handleChange} type="text" className="form-control" id="city" name="city" placeholder="City" />
                    </div>
                    <div className="form-group">
                        <label htmlFor="street">Street</label>
                        <input value={this.state.address.street} onChange={this.handleChange} type="text" className="form-control" id="street" name="street" placeholder="Street" />
                    </div>
                    <button className="btn btn-primary" type="submit" value="Submit" onClick={this.handleSubmit}>{this.props.edit ? "Edit" : "Add"}</button>
                </form>
            ) : null}
            {this.state.loading ? (
                "Loading..."
            ) : null}
        </section>
    }
}

export default withRouter(NewEmployee)