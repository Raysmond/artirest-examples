package edu.fdu.raysmond.loan.controller;

import java.util.ArrayList;
import java.util.Collection;

/**
 * BankController singleton
 * 
 * @author Raysmond
 */
public class BankController {
	private static BankController controller = new BankController();

	private static Collection<String> banks = new ArrayList<String>();

	public static BankController controller() {
		if (banks.isEmpty()) {
			banks.add("CS");
			banks.add("UBS");
		}
		
		return controller;
	}

	private BankController() {
		
	}

	public boolean exist(String bank) {
		return banks.contains(bank);
	}

	public Collection<String> getAll() {
		return banks;
	}

	public void addBank(String bank) {
		banks.add(bank);
	}

	public void remove(String bank) {
		banks.remove(bank);
	}
}
