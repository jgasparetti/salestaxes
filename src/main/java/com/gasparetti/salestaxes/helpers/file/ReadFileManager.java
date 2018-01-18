package com.gasparetti.salestaxes.helpers.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.atteo.evo.inflector.English;

import com.gasparetti.salestaxes.exceptions.WordsFileException;

public class ReadFileManager {
	
	private static ReadFileManager instance;
	
	private ClassLoader classLoader;
	
	private ReadFileManager() {
		
		classLoader = getClass().getClassLoader();
	}
	
	public static ReadFileManager getInstance() {
		
		if (instance == null) {
			instance = new ReadFileManager();
		}
		
		return instance;
	}

	public List<String> getWords(String fileName, boolean withPlural) throws WordsFileException {
		
		List<String> returnValue = new ArrayList<String>();

		Scanner scanner = null;
		
		try {
			
			File file = new File(classLoader.getResource("resources/" + fileName).getFile());
			
			scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				
				String stopword = scanner.nextLine();
				
				if (stopword == null || stopword.isEmpty()) {
					continue;
				}
				
				stopword = stopword.toUpperCase();
				
				returnValue.add(stopword);
				
				// To insert the plural
				if (withPlural) {
					String plural = English.plural(stopword);
					if (plural != null && !plural.isEmpty() && !plural.equals(stopword)) {
						returnValue.add(plural);
					}
				}
			}

		} catch (FileNotFoundException | IllegalStateException | NoSuchElementException | NullPointerException e) {
			
			e.printStackTrace();
			throw new WordsFileException(e.getMessage());
			
		} finally {
			
			if (scanner != null) {
				try {
					scanner.close();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}
		}
		
		return returnValue;
	}
}
