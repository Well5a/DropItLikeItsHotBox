import React from 'react'
import axios from 'axios'

class Registration extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            registrated: false,
            username: '',
            errorMessage: ''
        }
        this.registerHandler = this.registerHandler.bind(this);
        this.submitHandler = this.submitHandler.bind(this);
    }
    
    render()
    {
        return (
                <div>
                    <h1>Welcome to our DropBox</h1>
                    <h2>Register:</h2>
                    <div><p>Username:</p><input id="regUname" type="text"></input></div>
                    <div><p>E-Mail address:</p><input id="regEmail" type="text"></input></div>
                    <div><p>Password:</p><input id="regPwd" type="text"></input></div>
                    <div><p>confirm password:</p><input id="regPwdAck" type="text"></input></div>
                    <div><button id="regSubmitBtn" onClick={this.submitHandler}></button></div>
                    <div><p id="regStatus">{this.state.errorMessage}></p></div>
                </div>
        );
    }
    
    submitHandler()
    {    
        var payload = {
                username: document.getElementById("regUname").value,
                email: document.getElementById("regEmail").value,
                passwd: document.getElementById("regPwd").value,
        }
        if (payload.passwd != document.getElementById("regPwdAck").value)
        {
            this.setState({errorMessage: "Die eingegebenen Passwörter sind nicht identisch."});
            return;
        }
        else
            this.setState({errorMessage: ""});
        axios.post("rest/register", payload)
            .then( function(response){ this.registerHandler(response) }.bind(this) );
    }
    
    registerHandler(response)
    {
        if (response.status < 300)
        {
            this.props.callback(response.data.username);
        }
        else
        {
            this.setState({errorMessage: response.data.message});
        }
    }
}
export default Registration;