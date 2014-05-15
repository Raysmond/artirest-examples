package edu.fdu.raysmond.loan.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import edu.fdu.raysmond.loan.util.BaseModel;

/**
 * @author Raysmond
 */
@Entity
@XmlRootElement
public class User extends BaseModel {
	private String name;

	@XmlTransient
	private String password;

	private String role;

	public User() {

	}

	public User(Integer id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public User getProfile() {
		User user = new User();
		user.setName(this.name);
		user.setId(this.getId());
		user.setRole(this.getRole());
		return user;
	}
}
