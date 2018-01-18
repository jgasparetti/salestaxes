package com.gasparetti.salestaxes.services;

import java.util.ArrayList;
import java.util.List;

import com.gasparetti.salestaxes.entities.Bill;
import com.gasparetti.salestaxes.entities.Product;
import com.gasparetti.salestaxes.exceptions.IOStringException;
import com.gasparetti.salestaxes.exceptions.WordsFileException;
import com.gasparetti.salestaxes.helpers.StringHelper;

public class BillServiceImpl implements BillService {
	
	private IOStringService ioService;
	private TaxService taxService;
	
	public BillServiceImpl() throws WordsFileException {
		
		ioService = new IOStringServiceImpl();
		taxService = new TaxServiceImpl();
	}
	
	public String createBillFromInput(String input) throws IOStringException {
		
		String returnValue = null;
		
		List<String> productsString = StringHelper.getListStringFromText(input);

		List<Product> products = new ArrayList<Product>();
		for (String productString : productsString) {
			products.add(ioService.createProduct(productString));
		}
		
		Bill bill = createBill(products);
		
		returnValue = ioService.printBill(bill);
		
		return returnValue;
	}
	
	public Bill createBill(List<Product> products) throws IOStringException {
		
		Bill returnValue = null;
		
		// Check if is for taxes
		for (Product product : products) {
			
			product.setCategories(taxService.getCategoriesFromProduct(product));
			product.setImported(taxService.getIsImportedFromProduct(product));
		}
		
		// Calculate final price
		for (Product product : products) {
			
			product.addTax(taxService.calculateTaxForCategories(product));
			product.addTax(taxService.calculateTaxForImported(product));
		}
		
		returnValue = new Bill(products);
		
		return returnValue;
	}
}
