import React from 'react'
import './Phones.css'
import { withRouter } from 'react-router-dom'
import { getPhonesByEmployeeId, deletePhone } from './../../../utils/APIUtils'
import '../../../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import { BrowserRouter as Router, Route, Link } from 'react-router-dom'
import NewPhone from './NewPhone/NewPhone'
import { createNotification, MessageType } from '../../../utils/notifications'

class Phones extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            phones: null,
            loading: false
        }
        this.fetchPhones = this.fetchPhones.bind(this)
        this.handleDelete = this.handleDelete.bind(this)
    }

    fetchPhones() {
        let promise = getPhonesByEmployeeId(this.props.match.params.id)
        this.setState({
            loading: true
        })
        promise.then(response => {
            console.log(response)
            this.setState({
                phones: response,
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
        this.fetchPhones();
    }

    handleDelete(event) {
        let phoneId = event.target.value
        deletePhone(phoneId).then(response => {
            if (response.success) {
                console.log(response.message)
                this.fetchPhones()
                createNotification(MessageType.SUCCESS, response.message, 3000)
            }
        }).catch(error => {
            console.log(error)
        })
    }


    render() {
        return (
            <section className="phone-table">
                <div className="phone-table__header">
                    <h2>Phones</h2>
                </div>
                <div className="phone-table__table">
                    <table className="table table-striped">
                        <thead>
                            <tr>
                                <td>Id</td>
                                <td>Number</td>
                                <td>Actions</td>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                !this.state.loading ? (
                                    this.state.phones.map(phone => {
                                        return (
                                            <tr key={phone.id}>
                                                <td>
                                                    {phone.id}
                                                </td>
                                                <td>
                                                    {phone.number}
                                                </td>
                                                <td>
                                                    <button className="btn btn-danger" value={phone.id}
                                                        onClick={this.handleDelete} >Delete</button>
                                                </td>
                                            </tr>
                                        )
                                    })
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
                </div>
                <NewPhone fetchPhones={this.fetchPhones} />
            </section>
        )
    }
}

export default withRouter(Phones)