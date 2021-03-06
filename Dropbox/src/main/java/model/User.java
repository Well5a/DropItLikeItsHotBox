package model;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;

import javax.persistence.*;

import com.google.common.hash.Hashing;

import java.util.List;
import com.dropbox.util.PasswordHasher;

/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@TableGenerator( name = "userSeq", table = "SEQUENCE", 
    pkColumnName = "SEQ_NAME", pkColumnValue = "TUSER", 
    valueColumnName = "SEQ_COUNT", initialValue = 1, allocationSize = 1 )
	@GeneratedValue( strategy = GenerationType.TABLE, generator = "userSeq" )
	private int oId;

	private String email;

	private String passwd;

	private String username;

	//bi-directional many-to-one association to File
	@OneToMany(mappedBy="user")
	private List<File> files;

	//bi-directional many-to-one association to Filepermission
	@OneToMany(mappedBy="user")
	private List<Filepermission> filepermissions;

	public User() {
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

	public void setAndHashPasswd(String passwd) {
		String hashed = PasswordHasher.hashPassword(passwd);
		this.passwd = hashed;
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

	public List<File> getFiles() {
		return this.files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public File addFile(File file) {
		getFiles().add(file);
		file.setUser(this);

		return file;
	}

	public File removeFile(File file) {
		getFiles().remove(file);
		file.setUser(null);

		return file;
	}

	public List<Filepermission> getFilepermissions() {
		return this.filepermissions;
	}

	public void setFilepermissions(List<Filepermission> filepermissions) {
		this.filepermissions = filepermissions;
	}

	public Filepermission addFilepermission(Filepermission filepermission) {
		getFilepermissions().add(filepermission);
		filepermission.setUser(this);

		return filepermission;
	}

	public Filepermission removeFilepermission(Filepermission filepermission) {
		getFilepermissions().remove(filepermission);
		filepermission.setUser(null);

		return filepermission;
	}

}