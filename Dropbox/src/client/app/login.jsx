import React from 'react'
import {render} from 'react-dom'
import axios from 'axios'

class Login extends React.Component
{
    constructor(callback)
    {
        super();
        this.callback = callback;
    }
    
    render()
    {
        return(
                <div>
                    username:<input id="login_uname" type="text" name="user"/><br/>
                    password:<input id="login_pwd" type="password" name="password"/><br/>
                    <button onClick={this.handleLogin}>login</button>
                </div>
        );
    }
    
    handleLogin()
    {
        var payload = {
            username: document.getElementById("login_uname").value,
            password: document.getElementById("login_pwd").value
        }
        
        axios.post("/DropBox/rest/authenticate/login", payload)
            .then(this.callback(response, payload));      
    }
}
export default Login;