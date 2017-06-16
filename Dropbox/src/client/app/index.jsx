import React from 'react';
import Login from './login.jsx'
import {render} from 'react-dom';


class App extends React.Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            loggedIn: false,
            browsing: false,
            username: "",
            password: "",
            currentDirectory: ""
        }
    }
    
    render()
    {
        return <div id="app_main">{this.renderMain()}</div>;
    }
    
    loginCallback(response, payload)
    {
        if (response.data.code == 200)
        {
            this.setState("loggedIn", true);
            this.setState("currentDirectory", fetchDirectroy(username));
            this.setState("browsing", true);
            this.setState("username", payload.username);
            this.setState("password", payload.password);
        }
        else
        {
            this.setState("username", "");
            this.setState("password", "");
            this.setState("loggedIn", false);
            this.setState("browsing", false);
        }
    }
    
    renderMain()
    {
        if(!this.state.loggedIn)
        {
            return this.renderLogIn();
        }
        else if(!this.state.browsing)
        {
            this.fetchDirectory(username);
        }
    }
    
    fetchDirectory(uri)
    {
        this.setState("currentDirectory", fetch("/browse/" + uri)
                            .then = (response) => { this.state.browsing = true }
                            );
    }
    
    toListItem(c)
    {
        return (
            <li class={c.type}><a href={c.path}>{c.path}</a></li>
        );
    }
    
    renderDirectory()
    {
        var path = this.state.currentDirectory.path;
        var childitems = this.state.currentDirectory.children;
        
        var res = <div><ul>;
        for (child c : childitems)
            res += toListItem(c);
        
        return res += </ul></div>
    }
    
    renderLogIn()
    {
        return (
            <div id="login">
                <Login
                callback={this.loginCallback}
                />
            </div>
        );
    }
    
    handleLogin(response, payload)
    {
        axios.post("./authenticate/login", payload)
            .then(
                function(response)
                {
                    if (response.data.code == 200)
                    {
                        this.setState("loggedIn", true);
                        this.setState("currentDirectory", fetchDirectroy(username));
                        this.setState("browsing", true);
                    }
                    else
                    {
                        this.setState("username", "");
                        this.setState("password", "");
                        this.setState("loggedIn", false);
                        this.setState("browsing", false);
                    }
                });      
    }
};

render(<App/>, document.getElementById("App"));