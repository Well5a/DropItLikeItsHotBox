package com.dropbox.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oId;

	private String email;

	private String passwd;

	private String username;

	//bi-directional many-to-one association to File
	@OneToMany(mappedBy="user")
	private List<File> files;

	//bi-directional many-to-one association to Filepermission
	@OneToMany(mappedBy="user")
	private List<Filepermission> filepermissions;

	//bi-directional many-to-one association to Usergroup
	@OneToMany(mappedBy="user")
	private List<Usergroup> usergroups;

	public User() {
	}

	public int getOId() {
		return this.oId;
	}

	public void setOId(int oId) {
		this.oId = oId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<File> getFiles() {
		return this.files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public File addFile(File file) {
		getFiles().add(file);
		file.setUser(this);

		return file;
	}

	public File removeFile(File file) {
		getFiles().remove(file);
		file.setUser(null);

		return file;
	}

	public List<Filepermission> getFilepermissions() {
		return this.filepermissions;
	}

	public void setFilepermissions(List<Filepermission> filepermissions) {
		this.filepermissions = filepermissions;
	}

	public Filepermission addFilepermission(Filepermission filepermission) {
		getFilepermissions().add(filepermission);
		filepermission.setUser(this);

		return filepermission;
	}

	public Filepermission removeFilepermission(Filepermission filepermission) {
		getFilepermissions().remove(filepermission);
		filepermission.setUser(null);

		return filepermission;
	}

	public List<Usergroup> getUsergroups() {
		return this.usergroups;
	}

	public void setUsergroups(List<Usergroup> usergroups) {
		this.usergroups = usergroups;
	}

	public Usergroup addUsergroup(Usergroup usergroup) {
		getUsergroups().add(usergroup);
		usergroup.setUser(this);

		return usergroup;
	}

	public Usergroup removeUsergroup(Usergroup usergroup) {
		getUsergroups().remove(usergroup);
		usergroup.setUser(null);

		return usergroup;
	}

}