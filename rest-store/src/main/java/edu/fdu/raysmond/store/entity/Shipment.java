package edu.fdu.raysmond.store.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import edu.fdu.raysmond.store.util.BaseModel;

/**
 * Shipment artifact
 * 
 * @author Raysmond
 */
@Entity
@XmlRootElement
public class Shipment extends BaseModel {
	private int orderId;
	private String customerName;
	private String ShippingAddress;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ShipStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date ShipEndDate;
	
	@Enumerated(EnumType.STRING)
	private ShipmentState state;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getShippingAddress() {
		return ShippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		ShippingAddress = shippingAddress;
	}

	public Date getShipStartDate() {
		return ShipStartDate;
	}

	public void setShipStartDate(Date shipStartDate) {
		ShipStartDate = shipStartDate;
	}

	public Date getShipEndDate() {
		return ShipEndDate;
	}

	public void setShipEndDate(Date shipEndDate) {
		ShipEndDate = shipEndDate;
	}

	public ShipmentState getState() {
		return state;
	}

	public void setState(ShipmentState state) {
		this.state = state;
	}
}
