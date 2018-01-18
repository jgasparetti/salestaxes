package com.gasparetti.salestaxes.services;

import com.gasparetti.salestaxes.entities.Bill;
import com.gasparetti.salestaxes.entities.Product;
import com.gasparetti.salestaxes.exceptions.IOStringException;
import com.gasparetti.salestaxes.helpers.DoubleHelper;

public class IOStringServiceImpl implements IOStringService {

	public IOStringServiceImpl() {
		
	}

	@Override
	public Product createProduct(String productString) throws IOStringException {

		String[] splitted = productString.trim().split("\\s+");

		if (splitted.length < 3) {
			throw new IOStringException("Invalid product");
		}

		Integer quantity;
		try {

			quantity = Integer.parseInt(splitted[0]);

		} catch (NumberFormatException nfe) {

			throw new IOStringException("Invalid quantity");
		}

		Double price;
		try {

			price = Double.parseDouble(splitted[splitted.length - 1]);

		} catch (NumberFormatException nfe) {

			throw new IOStringException("Invalid price");
		}

		if (!"at".equals(splitted[splitted.length - 2])) {
			throw new IOStringException("Invalid product");
		}

		StringBuilder nameSb = new StringBuilder();
		for (int i = 1; i < (splitted.length - 2); i++) {
			if (i > 1) {
				nameSb.append(" ");
			}
			nameSb.append(splitted[i]);
		}

		if (nameSb.length() == 0) {
			throw new IOStringException("Invalid name");
		}

		return new Product(quantity, nameSb.toString(), price);
	}

	@Override
	public String printBill(Bill bill) throws IOStringException {

		StringBuilder sb = new StringBuilder();

		for (Product product : bill.getProducts()) {
			sb.append(product.getQuantity());
			sb.append(" ");
			sb.append(product.getName());
			sb.append(": ");
			sb.append(DoubleHelper.formatForMoney(product.getPriceTaxIncluded()));
			sb.append("\n");
		}
		sb.append("Sales Taxes: ");
		sb.append(DoubleHelper.formatForMoney(bill.getTax()));
		sb.append("\n");
		sb.append("Total: ");
		sb.append(DoubleHelper.formatForMoney(bill.getTotal()));

		return sb.toString();
	}
}
