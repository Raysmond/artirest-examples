package edu.fdu.raysmond.loan.entity;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Loan states
 * 
 * @author Raysmond
 */

@XmlRootElement
public enum LoanState {
	CREATED, APPLIED, APPROVED, CANCELED
}
