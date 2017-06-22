import React from 'react';
import axios from 'axios';
import Dropzone from 'react-dropzone'

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
                showAddFile: false,
                link: "#",
                message: ""
        };
        this.getDirectory(props.home);
        this.handleDrop = this.handleDrop.bind(this);
        this.toggleShowFileAddMenu = this.toggleShowFileAddMenu.bind(this);
        //this.addFileHandler = this.addFileHandler.bind(this);
        this.addDirectoryHandler = this.addDirectoryHandler.bind(this);
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
                        <li onClick={() => this.getDirectory(this.state.currentDirectory.parent)}><p>..</p></li>
                        {list}
                    </ul>
                    <div id="addFileSection">
                        {this.renderAddFile()}
                    </div>
                    <p id="browserStatus">{this.state.message}</p>
                </div>
        );
    }
    
    handleDrop(files) {
        files.forEach(
            (file)=>{
                var reader = new FileReader();
                var path = '/DropBox/rest/box/insertFile/' + this.state.currentDirectory.path + '/' + file.name;
                reader.onload = function(theFileData) {
                    var data = theFileData.target.result; // Ergebnis vom FileReader auslesen
                    axios.post(path, data)
                            .then( ()=> {
                                this.getDirectory(this.state.currentDirectory.path);
                            });
                    
                }.bind(this)
                reader.readAsArrayBuffer(file);
            }); 
    }

    /**
     * Render addFile div
     */ 
    renderAddFile()
    {
        return(
                <div>
                    {this.renderAddFileMenu()}
                </div>
        );
    }
    
    /**
     * clickhandler for #addFileButton
     */
    toggleShowFileAddMenu()
    {
        if (this.state.showAddFile === true)
            this.setState({showAddFile: false});
        else
            this.setState({showAddFile: true});
    }
    
    /**
     * renders lower part og the filebrowser,
     * which is used as dropzone and to add
     * directories
     */
    renderAddFileMenu()
    {
        //Style via css
        var style = {}
            return(
                    <div id="addFileMenu">
                        <div id="addDirectoryDiv">
                            <p>add Directory:</p>
                            <input id="addDirectoryInput" type="text"></input>
                            <button onClick={this.addDirectoryHandler}>+</button>
                        </div>
                        <Dropzone 
                            id="dropzone"
                            onDrop={this.handleDrop}
                            style={style}
                        >Drop your files here
                        </Dropzone>
                    </div>
            );
    }
    
    
    /**
     * Handler to be called when the user
     * is adding a directory
     */
    addDirectoryHandler()
    {
        var resPath = "/DropBox/rest/box/insertDir/" 
            + this.state.currentDirectory.path + "/"
            + document.getElementById("addDirectoryInput").value;
        axios.post(resPath, "").then( () => {
            this.getDirectory(this.state.currentDirectory.path);
        });
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
                <li><p onClick={() => this.getDirectory(subdirectory)}>{subdirectory}</p><button onClick={this.getDeleteHandler(subdirectory)}>delete</button></li>
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
        //e.preventDefault();
        axios.get("/DropBox/rest/box/browse/" + directorypath)
        .then(
                function(response)
                {
                    //its a file
                    if(response.data.path == undefined)
                    {
                        //this.setState({link: "/DropBox/rest/box/browse/" + directorypath}); 
                        window.open("/DropBox/rest/box/browse/" + directorypath);
                    }
                    //its a directory
                    else
                    {
                        this.setState({currentDirectory: response.data});
                    }
                }.bind(this)
        );
    }
    
    /**
     * @param {any} path path to the file to be deleted
     * 
     * @return eventhandler for the delte action
     */
    getDeleteHandler(path)
    {
        return function(e)
        {
            e.preventDefault();
            axios.delete("/DropBox/rest/box/remove/" + path)
            .then(
                    function(response)
                    {
                        this.getDirectory(this.state.currentDirectory.path);
                    }.bind(this)
            );
        }.bind(this)
    }
}
export default Browser;
