package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usergrouptable database table.
 * 
 */
@Entity
@NamedQuery(name="Usergrouptable.findAll", query="SELECT u FROM Usergrouptable u")
public class Usergrouptable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oId;

	//bi-directional many-to-one association to Grouptable
	@ManyToOne
	@JoinColumn(name="groupId")
	private Grouptable grouptable;

	//bi-directional many-to-one association to Usertable
	@ManyToOne
	@JoinColumn(name="userId")
	private Usertable usertable;

	public Usergrouptable() {
	}

	public int getOId() {
		return this.oId;
	}

	public void setOId(int oId) {
		this.oId = oId;
	}

	public Grouptable getGrouptable() {
		return this.grouptable;
	}

	public void setGrouptable(Grouptable grouptable) {
		this.grouptable = grouptable;
	}

	public Usertable getUsertable() {
		return this.usertable;
	}

	public void setUsertable(Usertable usertable) {
		this.usertable = usertable;
	}

}