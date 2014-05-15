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
 * Invoice artifact
 * 
 * @author Raysmond
 */
@Entity
@XmlRootElement
public class Invoice extends BaseModel{
	private int orderId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date invoiceDate;
	private String billingAddress;
	private double total = 0.0;
	private double amountPaied = 0.0;
	
	@Enumerated(EnumType.STRING)
	private InvoiceState state;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getAmountPaied() {
		return amountPaied;
	}

	public void setAmountPaied(double amountPaied) {
		this.amountPaied = amountPaied;
	}

	public InvoiceState getState() {
		return state;
	}

	public void setState(InvoiceState state) {
		this.state = state;
	}

}
