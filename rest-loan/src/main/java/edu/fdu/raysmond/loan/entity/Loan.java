package edu.fdu.raysmond.loan.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import edu.fdu.raysmond.loan.util.BaseModel;

/**
 * Loan artifact
 * 
 * @author Raysmond
 */
@Entity
@XmlRootElement
public class Loan extends BaseModel {
	private String customerName;

	private double amount;

	private String bank;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	@Enumerated(EnumType.STRING)
	private LoanState state;

	public Loan() {
		this.created = new Date();
		this.state = LoanState.CREATED;
	}

	public Loan(String name, double amount) {
		this.amount = amount;
		this.customerName = name;
		this.created = new Date();
		this.state = LoanState.CREATED;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public LoanState getState() {
		return state;
	}

	public void setState(LoanState status) {
		this.state = status;
	}

	public JSONObject toJson() throws JSONException {
		JSONObject json = new JSONObject();
		json.put("customerName", this.customerName).put("amount", this.amount).put("created", this.created)
				.put("state", this.state);
		if (this.bank != null)
			json.put("bank", this.bank);
		return json;
	}
}
