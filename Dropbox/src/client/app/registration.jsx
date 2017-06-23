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
    
    /**
     * renders the component
     */
    render()
    {
        return (
                <div>
                    <p><a href="#" onClick={(e) => {e.preventDefault(); this.props.callback("")}}>(back to login)</a></p>
                    <h1>Welcome to our DropBox</h1>
                    <h2>Register:</h2>
                    <div><p>Username:</p><input id="regUname" type="text"></input></div>
                    <div><p>E-Mail address:</p><input id="regEmail" type="text"></input></div>
                    <div><p>Password:</p><input id="regPwd" type="password"></input></div>
                    <div><p>confirm password:</p><input id="regPwdAck" type="password"></input></div>
                    <div><button id="regSubmitBtn" onClick={this.submitHandler}>submit</button></div>
                    <div><p id="regStatus">{this.state.errorMessage}</p></div>
                </div>
        );
    }
    
    /**
     * handles submit action
     */
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
        axios.post("rest/register", payload)
            .then( function(response){ this.registerHandler(response) }.bind(this) )
            .catch( function(error){ this.setState({errorMessage: error.response.data.message})}.bind(this) );
    }
    
    /**
     * callback for post-request in submitHandler().
     * sets the error message and calls props.callback
     * 
     * @param {any} response response of request
     */
    registerHandler(response)
    {
        if (response.status < 300)
        {
            this.props.callback(response.data.username);
            this.setState({errorMessage: ""});
        }
    }
}
export default Registration;