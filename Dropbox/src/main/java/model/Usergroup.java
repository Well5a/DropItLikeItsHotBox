package model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the usergroup database table.
 * 
 */
@Entity
@NamedQuery(name="Usergroup.findAll", query="SELECT u FROM Usergroup u")
public class Usergroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oId;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="groupId")
	private Group group;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userId")
	private User user;

	public Usergroup() {
	}

	public int getOId() {
		return this.oId;
	}

	public void setOId(int oId) {
		this.oId = oId;
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}