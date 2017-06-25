import React from 'react'
import axios from 'axios'

/**
 * Login form for DropBox client.
 * 
 * @author David Schick
 */
class Login extends React.Component
{
    /**
     * Constructs new Login form
     * @param {any} props callback for Login call
     */
    constructor(props)
    {
        super(props);
        this.handleLogin = this.handleLogin.bind(this);
    }
    
    /**
     * renders the component
     */
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
    
    /**
     * handler for login button
     */
    handleLogin()
    {
        //values to be send in post-body to the server
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
