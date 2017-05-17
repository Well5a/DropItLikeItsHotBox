package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the filepermissionstable database table.
 * 
 */
@Entity
@NamedQuery(name="Filepermissionstable.findAll", query="SELECT f FROM Filepermissionstable f")
public class Filepermissionstable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oid;

	private byte read;

	private byte write;

	//bi-directional many-to-one association to Filetable
	@ManyToOne
	@JoinColumn(name="fileID")
	private Filetable filetable;

	//bi-directional many-to-one association to Usertable
	@ManyToOne
	@JoinColumn(name="userID")
	private Usertable usertable;

	public Filepermissionstable() {
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

	public Filetable getFiletable() {
		return this.filetable;
	}

	public void setFiletable(Filetable filetable) {
		this.filetable = filetable;
	}

	public Usertable getUsertable() {
		return this.usertable;
	}

	public void setUsertable(Usertable usertable) {
		this.usertable = usertable;
	}

}