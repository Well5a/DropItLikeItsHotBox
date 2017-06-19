import React from 'react';
import axios from 'axios';

/**
 * React Component for browsing directories
 * of the DropBox Service
 * 
 * @author David Schick
 */
class Browser extends React.Component
{
    constructor(props)
    {
        super(props);
        this.getDirectory = this.getDirectory.bind(this);
        this.toggleShowFileAddMenu = this.toggleShowFileAddMenu.bind(this);
        this.state = {currentDirectory : {path: props.home},
                showAddFile: false
        };
        this.getDirectory(props.home);
    }
    
    render()
    {
        var list = [];
        if (this.state.currentDirectory.subdirectories != undefined)
        {
            var i;
            for (i = 0; i < this.state.currentDirectory.subdirectories.length; i++)
            {
                list.push(this.renderListElement(this.state.currentDirectory.subdirectories[i])); 
            }   
        }
        
        return (
                <div id="filebrowser">
                    <h2>{this.state.currentDirectory.path + "\\"}</h2>
                    <ul class="directory">
                        {list}
                    </ul>
                    <div id="addFileSection">
                        {this.renderAddFile()}
                    </div>
                    <div id="dropzone">
                        drop it like it's hot
                    </div>    
                </div>
        );
    }
    
    /**
     * Render addFile div
     */
    renderAddFile()
    {
        return(
                <div>
                    <button id="addFileButton" onclick={this.toggleShowFileAddMenu}>+</button>
                    
                </div>
        );
    }
    
    /**
     * clickhandler for #addFileButton
     */
    toggleShowFileAddMenu()
    {
        if (this.state.showAddFile)
            this.setState({showAddFile: false});
        else
            this.setState({showAddFile: true});
    }
    
    /**
     * renders subdirectory of server response
     * as html list element.
     * 
     * @param {any} subdirectory directory to be rendered
     */
    renderListElement(subdirectory)
    {
        return(
                <li><a href="#" onClick={() => this.getDirectory(subdirectory)}>{subdirectory}</a></li>
        );
    }
    
    /**
     * fetches subdirectories of directory
     * and sets state to new directory
     * 
     * @param {any} directorypath path to new directory
     */
    getDirectory(directorypath)
    {
        axios.get("/DropBox/rest/box/browse/" + directorypath)
        .then(
                function(response)
                {
                    //its a file
                    if(response.data.subdirectories == undefined)
                    {
                        //this.downloadFile("/DropBox/rest/box/browse/" + directorypath);
                    }
                    //its a directory
                    else
                    {
                        this.setState({currentDirectory: response.data});
                    }
                }.bind(this)
        );
    }
}
export default Browser;
