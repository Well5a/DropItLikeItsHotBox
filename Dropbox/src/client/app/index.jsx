import React from 'react';
import Login from './login.jsx'
import Browser from './browser.jsx'
import Registration from './registration.jsx'

import axios from 'axios'

import {render} from 'react-dom';

/**
 * Main class for Dropbox web application
 * 
 * @author David Schick
 */
class App extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            loggedin: false,
            browsing: false,
            username: "",
            registering: false
        }
        //Bind callback functions
        this.loginCallback = this.loginCallback.bind(this);
        this.logoutHandler = this.logoutHandler.bind(this);
        this.authenticationCallback = this.authenticationCallback.bind(this);
        this.registerCallback = this.registerCallback.bind(this);
        this.registerButtonHandler = this.registerButtonHandler.bind(this);
        this.checkAuthentication();
    }
    
    render()
    {
        return (
                <div id="app_main">
                    {this.renderLoggedInBar()}
                    {this.renderMain()}
                </div>
                );
    }
    
    /**
     * checks if client is already logged in
     */
    checkAuthentication()
    {
        axios.get("/DropBox/rest/authenticate")
            .then(
                    function (response)
                    {
                        this.authenticationCallback(response);
                    }.bind(this)
                );
    }
    
    /**
     * callback for authentication resource
     * 
     * @param {any} response
     */
    authenticationCallback(response)
    {
        if (response.status < 300)
        {
            this.setState({username: response.data});
            this.setState({loggedin: true});
        }
        else
            this.setState({loggedin: false});
    }
    
    loginCallback(response)
    {
        //alert(response.data.username + " " + response.data.password);
        if (response.status > 199 && response.status < 300)
        {
            this.setState({username: response.data.username});
            this.setState({browsing: true});
            this.setState({loggedin: true});
        }
        else
        {
            this.setState({username: ""});
            this.setState({loggedIn: false});
            this.setState({browsing: false});
        }
    }
    
    /**
     * render main part of the application
     */
    renderMain()
    {
        if (this.state.registering)
        {
            return this.renderRegistration();
        }
        else if(!this.state.loggedin)
        {
            return this.renderLogIn();
        }
        else
        {
            return this.renderBrowser();
        }
    }
    
    renderLoggedInBar()
    {
        if (this.state.loggedin)
        {
            return(
                    <div id="loggedInBar">
                        <p>logged in as: {this.state.username}</p>
                        <a href="#" onClick={this.logoutHandler}>(logout)</a>
                    </div>
                 );
        }
        else
            return;
    }
    
    /**
     * handler for logout button
     */
    logoutHandler()
    {
        axios.get("/DropBox/rest/authenticate/logout")
            .then(
                    function (response)
                    {
                        this.setState({loggedin:false});
                    }.bind(this)
            );
    }
    
    /**
     * render filebrowser
     */
    renderBrowser()
    {
        return (
                <div>
                    <Browser
                        home={this.state.username}>
                    </Browser>
                </div>
        );
    }
    
    /**
     * render Login form
     */
    renderLogIn()
    {
        return (
            <div id="login">
                <Login
                callback={this.loginCallback}
                />
                <p>no account? <button onClick={this.registerButtonHandler}>register here</button></p>
            </div>
        );
    }
    
    renderRegistration()
    {
        return (
                <div id="registration">
                    <Registration
                    callback={this.registerCallback}
                    />
                </div>
        );
    }
    
    registerButtonHandler()
    {
        this.setState({registering: true});
    }
    
    registerCallback(username)
    {
        //login abgebrochen, der nutzer will zurück zum login
        if (username === "")
        {
            this.setState({registering: false});
        }
        else
        {
            this.setState({username: username});
            this.setState({registering: false});
            this.setState({browsing: true});
        }
    }
};

render(<App/>, document.getElementById("App"));