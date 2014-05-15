package edu.fdu.raysmond.store.entity;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

import edu.fdu.raysmond.store.util.BaseModel;

@Entity
@XmlRootElement
public class Item extends BaseModel {
	private String name;
	private double price = 0.0;

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
