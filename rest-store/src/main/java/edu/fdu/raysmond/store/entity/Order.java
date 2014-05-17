package edu.fdu.raysmond.store.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import edu.fdu.raysmond.store.util.BaseModel;

/**
 * Order artifact
 * 
 * @author Raysmond
 */
@Entity
@Table(name = "Customer_Order")
@XmlRootElement
public class Order extends BaseModel {
	private String customerName;

	@ManyToMany(targetEntity = edu.fdu.raysmond.store.entity.Item.class, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.EAGER)
	@JoinTable(name = "Order_Items")
	private Collection<Item> items = new ArrayList<Item>();

	private double total = 0.0;

	@Temporal(TemporalType.TIMESTAMP)
	private Date billDate;

	private String customerAddress;

	@Enumerated(EnumType.STRING)
	private OrderState state;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Collection<Item> getItems() {
		return items;
	}

	public void setItems(Collection<Item> items) {
		this.items = items;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public OrderState getState() {
		return state;
	}

	public void setState(OrderState state) {
		this.state = state;
	}

	public void addItem(Item i) {
		this.items.add(i);
		this.total += i.getPrice();
	}

}
