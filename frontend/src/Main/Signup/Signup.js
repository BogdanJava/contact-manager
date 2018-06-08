import React, { Component } from 'react'
import './Signup.css'
import { API_URL, AUTH_TOKEN } from '../../utils/constants.js'
import { request } from '../../utils/APIUtils'
import { withRouter } from 'react-router-dom'
import { createNotification, MessageType } from '../../utils/notifications'

class Signup extends Component {
  constructor (props) {
    super(props)
    this.state = {
      username: '',
      password: ''
    }
    this.handleSubmit = this.handleSubmit.bind(this)
    this.handleChange = this.handleChange.bind(this)
  }

  signup (loginRequest) {
    return request({
      url: API_URL + '/auth/signup',
      method: 'POST',
      body: JSON.stringify(loginRequest)
    })
  }

  handleSubmit (event) {
    event.preventDefault()
    this.signup(this.state)
            .then(response => {
              console.log(response)
              if (response.success) {
                createNotification(MessageType.SUCCESS, response.message, 2000)
                this.props.history.push('/login')
              } else {
                createNotification(MessageType.ERROR, response.message, 5000)
              }
            })
            .catch(error => {
              console.log(error)
              createNotification(MessageType.ERROR, error.message, 5000)
            })
  }

  handleChange (event) {
    let username = this.state.username
    let password = this.state.password
    let field = event.target.name

    if (field == 'username') {
      username = event.target.value
    } else {
      password = event.target.value
    }
    this.setState({
      username: username,
      password: password
    })
  }

  render () {
    return (
      <section className='login-section'>
        <div className='login-section__header'>
          <h2>Sign up</h2>
        </div>
        <form className='login-form' onSubmit={this.handleSubmit}>
          <div className='form-group'>
            <label htmlFor='username'>Username</label>
            <input
              onChange={this.handleChange}
              type='text'
              className='form-control'
              id='username'
              name='username'
              placeholder='Enter username'
                        />
          </div>
          <div className='form-group'>
            <label htmlFor='password'>Password</label>
            <input
              onChange={this.handleChange}
              type='password'
              className='form-control'
              name='password'
              id='password'
              placeholder='Password'
                        />
          </div>
          <button type='submit' value='Submit' className='btn btn-primary'>Submit</button>
        </form>
      </section>
    )
  }
}

export default withRouter(Signup)
