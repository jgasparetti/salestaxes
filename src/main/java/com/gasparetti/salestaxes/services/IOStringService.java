package com.gasparetti.salestaxes.services;

import com.gasparetti.salestaxes.entities.Bill;
import com.gasparetti.salestaxes.entities.Product;
import com.gasparetti.salestaxes.exceptions.IOStringException;

public interface IOStringService extends Service {
	
	/*
	 * Create an object product from string
	 * 
	 * @param productString	the product as string
	 * 
	 * @return				the product object
	 */
	public Product createProduct(String productString) throws IOStringException;

	/*
	 * Print a bill
	 * 
	 * @param bill	the bill object to print
	 * 
	 * @return		the bill as string
	 */
	public String printBill(Bill bill) throws IOStringException;
	
}
