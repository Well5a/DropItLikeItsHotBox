package com.dropbox.dao;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the group database table.
 * 
 */
@Entity
@NamedQuery(name="Group.findAll", query="SELECT g FROM Group g")
public class Group implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oId;

	private String groupName;

	//bi-directional many-to-one association to Usergroup
	@OneToMany(mappedBy="group")
	private List<Usergroup> usergroups;

	public Group() {
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

	public List<Usergroup> getUsergroups() {
		return this.usergroups;
	}

	public void setUsergroups(List<Usergroup> usergroups) {
		this.usergroups = usergroups;
	}

	public Usergroup addUsergroup(Usergroup usergroup) {
		getUsergroups().add(usergroup);
		usergroup.setGroup(this);

		return usergroup;
	}

	public Usergroup removeUsergroup(Usergroup usergroup) {
		getUsergroups().remove(usergroup);
		usergroup.setGroup(null);

		return usergroup;
	}

}