import React from 'react'
import './NewPhone.css'
import { withRouter } from 'react-router-dom'
import { addPhone } from '../../../../utils/APIUtils'

class NewPhone extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            number: '',
            employeeId: this.props.match.params.id
        }
        this.handleChange = this.handleChange.bind(this)
        this.handleAddPhone = this.handleAddPhone.bind(this)
    }

    handleAddPhone() {
        addPhone(this.state).then(response => {
            if (response.success) {
                this.props.fetchPhones()
            }
        }).catch(error => {
            console.log(error)
        })
    }

    handleChange(event) {
        this.setState({
            number: event.target.value
        })
    }    

    render() {
        return (
            <div className="new-phone">
                <div></div>
                <div className="new-phone__prefix"><span className="plus">+</span></div>
                <input autoComplete={"off"} name="number" onChange={this.handleChange} className="new-phone__number" placeholder="Size should be from 7 to 14" />
                <button className="new-phone__button btn btn-primary" onClick={this.handleAddPhone}>Add</button>
            </div>
        )
    }
}

export default withRouter(NewPhone)