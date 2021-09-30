package com.nand2tetrispart1.assembler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

public class HackAssembler {

	private String filename;
	private final String binExt = ".hack";
	private final String extension = ".asm";
	private CodeTranslator code;
	private Parser parser;
	
	
	/**
	 * 
	 * @param filename
	 * 
	 * Note: the file name is without the extension '.asm'
	 * if you want to run it again, you have to delete the previous generated 'filename.hack' file
	 */

	public HackAssembler(String filename) {
		this.filename = filename;
	}

	public void hackMachineLanguage() throws FileNotFoundException, IOException {

		parser = new Parser();
		code = new CodeTranslator(filename + extension);
		

		try (BufferedReader br = new BufferedReader(new FileReader(filename + extension))) {
			for (String line; (line = br.readLine()) != null;) {
				
				ArrayList<Character> tokenCharsList = parser.lineParsing(line);

				ArrayList<String> token = parser.tokenization(tokenCharsList);
				//System.out.println(token);
				String value = code.translation(token);

				if (value == null) //deal with null instruction
					continue;
				System.out.println(value);

				try {
					final Path path = Paths.get(filename + binExt);
					Files.write(path, Arrays.asList(value), StandardCharsets.UTF_8,
							Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
				} catch (final IOException ioe) { // Add your own exception handling... }

				}
				
			}

		}
	}

	public String getFilename() {
		return filename;
	}

}
