package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usertable database table.
 * 
 */
@Entity
@NamedQuery(name="Usertable.findAll", query="SELECT u FROM Usertable u")
public class Usertable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oId;

	private String email;

	private String passwd;

	private String username;

	//bi-directional many-to-one association to Filepermissionstable
	@OneToMany(mappedBy="usertable")
	private List<Filepermissionstable> filepermissionstables;

	//bi-directional many-to-one association to Filetable
	@OneToMany(mappedBy="usertable")
	private List<Filetable> filetables;

	//bi-directional many-to-one association to Usergrouptable
	@OneToMany(mappedBy="usertable")
	private List<Usergrouptable> usergrouptables;

	public Usertable() {
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

	public List<Filepermissionstable> getFilepermissionstables() {
		return this.filepermissionstables;
	}

	public void setFilepermissionstables(List<Filepermissionstable> filepermissionstables) {
		this.filepermissionstables = filepermissionstables;
	}

	public Filepermissionstable addFilepermissionstable(Filepermissionstable filepermissionstable) {
		getFilepermissionstables().add(filepermissionstable);
		filepermissionstable.setUsertable(this);

		return filepermissionstable;
	}

	public Filepermissionstable removeFilepermissionstable(Filepermissionstable filepermissionstable) {
		getFilepermissionstables().remove(filepermissionstable);
		filepermissionstable.setUsertable(null);

		return filepermissionstable;
	}

	public List<Filetable> getFiletables() {
		return this.filetables;
	}

	public void setFiletables(List<Filetable> filetables) {
		this.filetables = filetables;
	}

	public Filetable addFiletable(Filetable filetable) {
		getFiletables().add(filetable);
		filetable.setUsertable(this);

		return filetable;
	}

	public Filetable removeFiletable(Filetable filetable) {
		getFiletables().remove(filetable);
		filetable.setUsertable(null);

		return filetable;
	}

	public List<Usergrouptable> getUsergrouptables() {
		return this.usergrouptables;
	}

	public void setUsergrouptables(List<Usergrouptable> usergrouptables) {
		this.usergrouptables = usergrouptables;
	}

	public Usergrouptable addUsergrouptable(Usergrouptable usergrouptable) {
		getUsergrouptables().add(usergrouptable);
		usergrouptable.setUsertable(this);

		return usergrouptable;
	}

	public Usergrouptable removeUsergrouptable(Usergrouptable usergrouptable) {
		getUsergrouptables().remove(usergrouptable);
		usergrouptable.setUsertable(null);

		return usergrouptable;
	}

}