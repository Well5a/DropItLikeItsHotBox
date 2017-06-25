import React from 'react';
import Login from './login.jsx'                 //Login Form
import Browser from './browser.jsx'             //Filebrowser
import Registration from './registration.jsx'   //Registration-Form

// web-client used in this application
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
        // first function called when loading the script, checks if user is already
        // logged in and sets the state accordingly
        this.checkAuthentication();
    }
    
    /**
     * renders the component
     */
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
    
    /**
     * callback-function for the Login React-Component,
     * defined in client/app/Login.jsx
     * 
     * @param {any} response
     */
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
    
    /**
     * renders bar containing the name of the active
     * user and a link for loggin the user out.
     */
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
     * renders div containing the Login React-Component,
     * defined in client/app/Login.jsx
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
    
    /**
     * renders div containing the Registration React-Component,
     * defined in client/app/Registration.jsx
     */
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
    
    /**
     * handler for the "register here" button
     */
    registerButtonHandler()
    {
        this.setState({registering: true});
    }
    
    /**
     * Callback for the Registration React-Component, 
     * defined in client/app/Registration.jsx
     * @param {any} username
     */
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