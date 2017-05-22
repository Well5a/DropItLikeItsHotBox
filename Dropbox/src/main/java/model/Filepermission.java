package com.dropbox.dao;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the filepermission database table.
 * 
 */
@Entity
@NamedQuery(name="Filepermission.findAll", query="SELECT f FROM Filepermission f")
public class Filepermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oid;

	private byte read;

	private byte write;

	//bi-directional many-to-one association to File
	@ManyToOne
	@JoinColumn(name="fileID")
	private File file;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userID")
	private User user;

	public Filepermission() {
	}

	public int getOid() {
		return this.oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public byte getRead() {
		return this.read;
	}

	public void setRead(byte read) {
		this.read = read;
	}

	public byte getWrite() {
		return this.write;
	}

	public void setWrite(byte write) {
		this.write = write;
	}

	public File getFile() {
		return this.file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}