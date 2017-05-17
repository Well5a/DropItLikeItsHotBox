package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the filetable database table.
 * 
 */
@Entity
@NamedQuery(name="Filetable.findAll", query="SELECT f FROM Filetable f")
public class Filetable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int oId;

	private String path;

	//bi-directional many-to-one association to Filepermissionstable
	@OneToMany(mappedBy="filetable")
	private List<Filepermissionstable> filepermissionstables;

	//bi-directional many-to-one association to Usertable
	@ManyToOne
	@JoinColumn(name="ownerID")
	private Usertable usertable;

	public Filetable() {
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

	public List<Filepermissionstable> getFilepermissionstables() {
		return this.filepermissionstables;
	}

	public void setFilepermissionstables(List<Filepermissionstable> filepermissionstables) {
		this.filepermissionstables = filepermissionstables;
	}

	public Filepermissionstable addFilepermissionstable(Filepermissionstable filepermissionstable) {
		getFilepermissionstables().add(filepermissionstable);
		filepermissionstable.setFiletable(this);

		return filepermissionstable;
	}

	public Filepermissionstable removeFilepermissionstable(Filepermissionstable filepermissionstable) {
		getFilepermissionstables().remove(filepermissionstable);
		filepermissionstable.setFiletable(null);

		return filepermissionstable;
	}

	public Usertable getUsertable() {
		return this.usertable;
	}

	public void setUsertable(Usertable usertable) {
		this.usertable = usertable;
	}

}