package edu.fdu.raysmond.store.util;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseModel implements IBaseModel, Comparable<IBaseModel> {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer _id) {
		id = _id;
	}

	@Override
	public int compareTo(IBaseModel o) {
		return this.getId().compareTo(o.getId());
	}

	public boolean equals(Object other) {
		if (other == null || other.getClass() != this.getClass())
			return false;
		return this.getId() == ((IBaseModel) other).getId();
	}
	
	/**
	 * use HashCodeBuilder to calculate a hashcode
	 */
	// public int hashCode() {
	// return new HashCodeBuilder().append(getId()).toHashCode();
	// }

}