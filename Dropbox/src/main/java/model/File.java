package com.dropbox.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the file database table.
 * 
 */
@Entity
@NamedQuery(name="File.findAll", query="SELECT f FROM File f")
public class File implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oId;

	private String path;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="ownerID")
	private User user;

	//bi-directional many-to-one association to Filepermission
	@OneToMany(mappedBy="file")
	private List<Filepermission> filepermissions;

	public File() {
	}

	public int getOId() {
		return this.oId;
	}

	public void setOId(int oId) {
		this.oId = oId;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Filepermission> getFilepermissions() {
		return this.filepermissions;
	}

	public void setFilepermissions(List<Filepermission> filepermissions) {
		this.filepermissions = filepermissions;
	}

	public Filepermission addFilepermission(Filepermission filepermission) {
		getFilepermissions().add(filepermission);
		filepermission.setFile(this);

		return filepermission;
	}

	public Filepermission removeFilepermission(Filepermission filepermission) {
		getFilepermissions().remove(filepermission);
		filepermission.setFile(null);

		return filepermission;
	}

}