import React from 'react'
import axios from 'axios'

class Login extends React.Component
{
    constructor(props)
    {
        super(props);
        this.handleLogin = this.handleLogin.bind(this);
    }
    
    render()
    {
        return(
               <div>
                   Username:   <input id="login_uname" type="text" name="user"/><br/>
                   Password:   <input id="login_pwd" type="password" name="password"/><br/>
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
            .then(function(response){
                this.props.callback(response);
            }.bind(this)
        );      
    }
}
export default Login;