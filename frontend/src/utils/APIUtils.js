import { AUTH_TOKEN, API_URL } from './constants'

export const request = function (options) {
    const headers = new Headers({
        'Content-Type': 'application/json'
    })

    if (localStorage.getItem(AUTH_TOKEN)) {
        headers.append('Authorization', `Bearer ${localStorage.getItem(AUTH_TOKEN)}`)
    }

    const defaults = { headers: headers }
    options = Object.assign({}, defaults, options)

    return fetch(options.url, options)
        .then(response => {
            return response.json().then(json => {
                if (!response.ok) {
                    return Promise.reject(json)
                }
                return json;
            })
        })
}

export const login = function (loginRequest) {
    return request({
        url: API_URL + '/auth/login',
        method: 'POST',
        body: JSON.stringify(loginRequest)
    })
}

export function logout(component) {
    localStorage.removeItem(AUTH_TOKEN)
    return request({
        url: API_URL + '/auth/logout',
        method: 'POST',
        body: ''
    })
}

export function getCurrentUser() {
    if (!localStorage.getItem(AUTH_TOKEN)) {
        return Promise.reject("No access token set.");
    }
    return request({
        url: API_URL + "/user/me",
        method: 'GET'
    });
}

export function getEmployees() {
    return request({
        url: API_URL + "/employee",
        method: 'GET'
    })
}

export function getEmployeeById(id) {
    return request({
        url: `${API_URL}/employee/${id}`
    })
}

export function updateEmployee(employeeRequest) {
    return request({
        url: `${API_URL}/employee`,
        method: 'PUT',
        body: JSON.stringify(employeeRequest)
    })
}

export function deleteEmployee(id) {
    return request({
        url: `${API_URL}/employee/${id}`,
        method: 'DELETE'
    })
}

export function addEmployee(employeeRequest) {
    return request({
        url: API_URL + "/employee",
        method: 'POST',
        body: JSON.stringify(employeeRequest)
    })
}