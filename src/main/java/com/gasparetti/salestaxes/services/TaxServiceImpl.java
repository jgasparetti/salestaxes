package com.gasparetti.salestaxes.services;

import java.util.ArrayList;
import java.util.List;

import com.gasparetti.salestaxes.entities.Product;
import com.gasparetti.salestaxes.enumerations.CategoryEnum;
import com.gasparetti.salestaxes.exceptions.WordsFileException;
import com.gasparetti.salestaxes.helpers.DoubleHelper;
import com.gasparetti.salestaxes.helpers.StringHelper;
import com.gasparetti.salestaxes.helpers.file.FileConstants;
import com.gasparetti.salestaxes.helpers.file.ReadFileManager;

public class TaxServiceImpl extends FileConstants implements TaxService {

	private List<String> bookWords;
	private List<String> foodWords;
	private List<String> medicalWords;

	private List<String> importedWords;

	public TaxServiceImpl() throws WordsFileException {

		bookWords = ReadFileManager.getInstance().getWords(BOOK_WORDS_PATH, true);
		foodWords = ReadFileManager.getInstance().getWords(FOOD_WORDS_PATH, true);
		medicalWords = ReadFileManager.getInstance().getWords(MEDICAL_WORDS_PATH, true);

		importedWords = ReadFileManager.getInstance().getWords(IMPORTED_WORDS_PATH, false);
	}

	@Override
	public List<CategoryEnum> getCategoriesFromProduct(Product product) {

		List<CategoryEnum> returnValue = new ArrayList<CategoryEnum>();

		String[] nameSplitted = product.getName().split("\\s+");

		if (StringHelper.isIntersectionNotEmpty(nameSplitted, bookWords)) {
			returnValue.add(CategoryEnum.BOOK);
		}

		if (StringHelper.isIntersectionNotEmpty(nameSplitted, foodWords)) {
			returnValue.add(CategoryEnum.FOOD);
		}

		if (StringHelper.isIntersectionNotEmpty(nameSplitted, medicalWords)) {
			returnValue.add(CategoryEnum.MEDICAL);
		}

		return returnValue;

	}

	@Override
	public boolean getIsImportedFromProduct(Product product) {

		boolean returnValue = false;

		String[] nameSplitted = product.getName().split("\\s+");

		if (StringHelper.isIntersectionNotEmpty(nameSplitted, importedWords)) {
			returnValue = true;
		} else {
			returnValue = false;
		}

		return returnValue;
	}

	@Override
	public double calculateTaxForCategories(Product product) {

		double returnValue = 0d;
		
		if (!product.getCategories().contains(CategoryEnum.BOOK)
				&& !product.getCategories().contains(CategoryEnum.FOOD)
				&& !product.getCategories().contains(CategoryEnum.MEDICAL)) {
			
			returnValue = DoubleHelper.roundForTax(product.getPrice() * 0.10);
		}
		
		return returnValue;
	}

	@Override
	public double calculateTaxForImported(Product product) {

		double returnValue = 0d;
		
		if (product.isImported()) {

			returnValue = DoubleHelper.roundForTax(product.getPrice() * 0.05);
		}
		
		return returnValue;
	}

}
