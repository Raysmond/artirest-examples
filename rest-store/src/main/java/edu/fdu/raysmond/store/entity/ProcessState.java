package edu.fdu.raysmond.store.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import edu.fdu.raysmond.store.util.BaseModel;

@Entity
@XmlRootElement
public class ProcessState extends BaseModel {

	Integer artifactId;

	private String name;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String user;

	public ProcessState() {

	}

	public ProcessState(int artifactId, String name, String user) {
		this.name = name;
		this.date = new Date();
		this.user = user;
		this.artifactId = artifactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(Integer artifactId) {
		this.artifactId = artifactId;
	}
}
