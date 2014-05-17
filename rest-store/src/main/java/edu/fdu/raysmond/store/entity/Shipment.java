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
	private String shippingAddress;

	@Temporal(TemporalType.TIMESTAMP)
	private Date shipStartDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date shipEndDate;

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
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Date getShipStartDate() {
		return shipStartDate;
	}

	public void setShipStartDate(Date shipStartDate) {
		this.shipStartDate = shipStartDate;
	}

	public Date getShipEndDate() {
		return shipEndDate;
	}

	public void setShipEndDate(Date shipEndDate) {
		this.shipEndDate = shipEndDate;
	}

	public ShipmentState getState() {
		return state;
	}

	public void setState(ShipmentState state) {
		this.state = state;
	}
}
