package edu.fdu.raysmond.store.entity;

/**
 * Order states
 * 
 * @author Raysmond
 */
public enum OrderState {
	Adding_order_item,
	Customer_creating_shipping,
	Billed,
	Order_confirmed,
	Manager_creating_shipping,
	Ready_for_shipping,
	In_shipping,
	Shipped
}
