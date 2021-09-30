package com.nand2tetrispart1.assembler;


import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.LinkedHashMap;

public class SymbolTable {
	
	private LinkedHashMap<String, Integer> lookup;
	private static int n = 0;
	private Parser parser;
	private static int numInstructions = -1; //because in hack programming the counter start at 0 in a .asm file
	 
	public SymbolTable() {
		lookup = new LinkedHashMap<String, Integer>();
		parser = new Parser();
		
		//first 16 memories
		for (int i = 0; i <= 15; i++) {
			lookup.put("R"+i, i);
		}
		
		//SP, LCL, ARG, THIS, and THAT
		lookup.put("SP", 0);
		lookup.put("LCL", 1);
		lookup.put("ARG", 2);
		lookup.put("THIS", 3);
		lookup.put("THAT", 4);
		
		//SCREEN and KBD
		lookup.put("SCREEN", 16384);
		lookup.put("KBD", 24576);
		
		//start of variables and labels in memories
		n = 16;
		
	}
	
	public void addSymbol(String symbol) {
		//add a symbol in the table staring at location 16 if not present
		if(!lookup.containsKey(symbol)) {
			lookup.put(symbol, n);
			n++;
		}
			
	}
	
	public void populateTable(String filename) throws FileNotFoundException, IOException {
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			for (String line; (line = br.readLine()) != null;) {

				ArrayList<Character> tokenCharsList = parser.lineParsing(line);
				ArrayList<String> tokensList = parser.tokenization(tokenCharsList);
				
				
				if(parser.isInstruction(tokenCharsList)){
					numInstructions++;
					if(tokensList.get(0).equalsIgnoreCase("A")) {
						boolean isStrN = this.isStringNumeric(tokensList.get(1));
						if (!isStrN) {
							boolean isInTable = this.getLookup().containsKey(tokensList.get(1));
							if (!isInTable) 
								this.addSymbol(tokensList.get(1)); //add it in the table if not present
						}//do nothing if A-instruction with decimal value/or already in table
					}//C-instruction has no symbol (this else can be omitted, i've add it for understanding purpose)
					
				}else { //if it's not an A/C-instruction then is a label
					
					String label = parser.retrieveLabel(tokenCharsList);
					
					//add in the table in case the token is not blank
					if(!label.isBlank()) {
						boolean isInTable = this.getLookup().containsKey(label);
						if (!isInTable) 
							this.addSymbol(label); 
						this.lookup.replace(label, numInstructions + 1); //and replcae it with the next instruction
					}//if label is blank do nothing, cause they are no label to add (i add this else just for better comprehension purpose it can be omitted
				}
	
			}

		}
		System.out.println("numero instruzioni: " + (numInstructions + 1));
	}

	public LinkedHashMap<String, Integer> getLookup() {
		return lookup;
	}
	
	public boolean isStringNumeric(String value) {
		return !value.matches(".*[a-zA-Z]+.*"); //it's string numeric if it does not contains at least one char
	}
	

}
