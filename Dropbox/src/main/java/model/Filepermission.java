package model;

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

	private byte allowRead;

	private byte allowWrite;

	//bi-directional many-to-one association to File
	@ManyToOne
	@JoinColumn(name="fileId")
	private File file;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	public Filepermission() {
	}

	public int getOid() {
		return this.oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	public byte getAllowRead() {
		return this.allowRead;
	}

	public void setAllowRead(byte allowRead) {
		this.allowRead = allowRead;
	}

	public byte getAllowWrite() {
		return this.allowWrite;
	}

	public void setAllowWrite(byte allowWrite) {
		this.allowWrite = allowWrite;
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