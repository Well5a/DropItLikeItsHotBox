package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the grouptable database table.
 * 
 */
@Entity
@NamedQuery(name="Grouptable.findAll", query="SELECT g FROM Grouptable g")
public class Grouptable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oId;

	private String groupName;

	//bi-directional many-to-one association to Usergrouptable
	@OneToMany(mappedBy="grouptable")
	private List<Usergrouptable> usergrouptables;

	public Grouptable() {
	}

	public int getOId() {
		return this.oId;
	}

	public void setOId(int oId) {
		this.oId = oId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public List<Usergrouptable> getUsergrouptables() {
		return this.usergrouptables;
	}

	public void setUsergrouptables(List<Usergrouptable> usergrouptables) {
		this.usergrouptables = usergrouptables;
	}

	public Usergrouptable addUsergrouptable(Usergrouptable usergrouptable) {
		getUsergrouptables().add(usergrouptable);
		usergrouptable.setGrouptable(this);

		return usergrouptable;
	}

	public Usergrouptable removeUsergrouptable(Usergrouptable usergrouptable) {
		getUsergrouptables().remove(usergrouptable);
		usergrouptable.setGrouptable(null);

		return usergrouptable;
	}

}