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
        this.state = {currentDirectory : undefined,
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
    
    /**
     * renders the component
     */
    render()
    {
        if (this.state.currentDirectory == undefined)
            return null;
        var list = [];
        if (this.state.currentDirectory.subdirectories != undefined)
        {
            var i;
            for (i = 0; i < this.state.currentDirectory.subdirectories.length; i++)
            {
                list.push(this.renderListElement(this.state.currentDirectory.subdirectories[i])); 
            }   
        }
        
        var current = this.state.currentDirectory;
        return (
                <div id="filebrowser">
                    <h2>{current.self.name}</h2>
                    <table class="directory">
                        {this.renderTableHead()}
                        {this.renderParentDir()}
                        {list}
                    </table>
                    <div id="addFileSection">
                        {this.renderAddFile()}
                    </div>
                    <p id="browserStatus">{this.state.message}</p>
                </div>
        );
    }
    
    /**
     * renders the head of the filetable, containing
     * the column names
     */
    renderTableHead()
    {
        return(
                <tr>
                            <td></td>
                            <td>name</td>
                            <td>lastChanged</td>
                            <td></td>
                </tr>
              );
    }
    
    /**
     * Renders the entry for the current directories parent
     */
    renderParentDir() {
        var current = this.state.currentDirectory;
        if (current.parent == undefined || current.parent == null)
            return;
        
        var parentLastChanged = new Date(current.parent.lastChanged).toTimeString();
        return(
                <tr onClick={   function(e){
                                    e.preventDefault();
                                    this.getDirectory(current.parent.path);
                                }.bind(this)
                            }>
                            <td>..</td>
                            <td>{parentLastChanged}</td>
                </tr>
              );
    }
    
    /**
     * Handler to be called when the user drops 
     * files into the dropzone
     * 
     * @param {any} files
     */
    handleDrop(files) {
        files.forEach(
            (file)=>{
                var reader = new FileReader();
                var path = '/DropBox/rest/box/insertFile/' + this.state.currentDirectory.self.path + '/' + file.name;
                reader.onload = function(theFileData) {
                    var data = theFileData.target.result; // Ergebnis vom FileReader auslesen
                    axios.post(path, data)
                            .then( ()=> {
                                this.getDirectory(this.state.currentDirectory.self.path);
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
     * renders lower part of the filebrowser,
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
            + this.state.currentDirectory.self.path + "/"
            + document.getElementById("addDirectoryInput").value;
        axios.post(resPath, "").then( () => {
            this.getDirectory(this.state.currentDirectory.self.path);
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
        var date = new Date(subdirectory.lastChanged);
        var datestr = this.toDateString(date);
        return(
                <tr onClick={ function(e) { e.preventDefault(); this.getDirectory(subdirectory.path); }.bind(this) } >
                    <td>{this.getImage(subdirectory.type)}</td>
                    <td>{subdirectory.name}</td>
                    <td>{datestr}</td>
                    <td>
                        <button onClick={this.getDeleteHandler(subdirectory)}>delete</button>
                    </td>
                </tr>
        );
    }
    
    /**
     * returns the corrensponding icon
     * @param {any} type ( "directory" | "file" )
     */
    getImage(type)
    {
        return <img alt={type + "-icon"} src={"public/icon/" + type + ".png"}/>
    }
    
    /**
     * Converts the value of a date object into a german-style
     * String representation.
     * 
     * @param {any} date date to be converted
     * @return String representation of date
     */
    toDateString(date)
    {
        return this.zeroPadding(date.getDate(), 2) + '.' + this.zeroPadding(date.getMonth(), 2) + '.' 
        + date.getFullYear() + ' ' + this.zeroPadding(date.getHours(), 2) + ':' + this.zeroPadding(date.getMinutes(), 2);
    }
    
    /**
     * returns a string containing a number padded with zeros to
     * the designated length.
     * 
     * @param {any} value value to be padded
     * @param {any} length lenght to padd to
     * 
     * return zero padded string representation of value
     */
    zeroPadding(value, length)
    {
        var valLen = value.toString().length;
        var ret = "";
        var padLength = (length+1) - valLen;
        return padLength > 0 ? new Array(padLength).join('0') + value : value.toString();
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
                    if(response.data.self.type == "file")
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
                        this.getDirectory(this.state.currentDirectory.self.path);
                    }.bind(this)
            );
        }.bind(this)
    }
}
export default Browser;
