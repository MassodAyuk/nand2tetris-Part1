package com.nand2tetrispart1.assembler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CodeTranslator {
	
	private HashMap<String, String> comp;
	private HashMap<String, String> dest;
	private HashMap<String, String> jump;
	private SymbolTable symb;
	private final static int numBitInstruction = 16;
	
	
	
	
	public CodeTranslator(String filename) throws FileNotFoundException, IOException {
		comp = new HashMap<String, String>();
		dest = new HashMap<String, String>();
		jump = new HashMap<String, String>();
		symb = new SymbolTable();
		
		symb.populateTable(filename);
		System.out.println("lookup table");
		System.out.println("\t" + symb.getLookup());
		
		/**
		 * Hack computer CPU machine language specification
		 */
		//computation instruction
		comp.put("0", "0101010");
		comp.put("1", "0111111");
		comp.put("-1", "0111010");
		
		comp.put("D", "0001100");
		comp.put("A", "0110000");
		comp.put("M", "1110000");
		
		comp.put("!D", "0001101");
		comp.put("!A", "0110001");
		comp.put("!M", "1110001");
		
		comp.put("-D", "0001111");
		comp.put("-A", "0110011");
		comp.put("-M", "1110011");
		
		comp.put("D+1", "0011111");
		comp.put("A+1", "0110111");
		comp.put("M+1", "1110111");
		
		comp.put("D-1", "0001110");
		comp.put("A-1", "0110010");
		comp.put("M-1", "1110010");
		
		comp.put("D+A", "0000010");
		comp.put("D+M", "1000010");
		
		comp.put("D-A", "0010011");
		comp.put("D-M", "1010011");
		
		comp.put("A-D", "0000111");
		comp.put("M-D", "1000111");
		
		comp.put("D&A", "0000000");
		comp.put("D&M", "1000000");
		
		comp.put("D|A", "0010101");
		comp.put("D|M", "1010101");
		
		//destination instruction
		dest.put(null, "000");
		dest.put("M",  "001");
		dest.put("D",  "010");
		dest.put("MD", "011");
		dest.put("A",  "100");
		dest.put("AM", "101");
		dest.put("AD", "110");
		dest.put("AMD", "111");
		
		//jump instruction
		jump.put(null, "000");
		jump.put("JGT",  "001");
		jump.put("JEQ",  "010");
		jump.put("JGE", "011");
		jump.put("JLT",  "100");
		jump.put("JNE", "101");
		jump.put("JLE", "110");
		jump.put("JMP", "111");
		
	}
	
	
	
	public String translation(ArrayList<String> instructions) {
		/**
		 * this function take a list of instruction and return the corresponding machine language code
		 * NB: it does nothing at null instruction
		 */
		String value = null;
		int memValue = 0;
	

		if (!(instructions == null)) {
			if (instructions.get(0).equalsIgnoreCase("A")) {

				boolean isStrN = symb.isStringNumeric(instructions.get(1));
				if (isStrN) 
					memValue = Integer.parseInt(instructions.get(1)); // parse the value to integer and convert it in binary
			    else//if the A-instruction has a symbol o label in it
					memValue = symb.getLookup().get(instructions.get(1)); 
				
				value = binaryConversion(memValue);
				
			} else if (instructions.get(0).equalsIgnoreCase("C")) {
				value = cTranslation(instructions.get(1), instructions.get(2), instructions.get(3)); // dest = comp;jump
																										
			}
		}
		return value; //this will return null in case of null instruction and a value otherwise
	}
	
	private String cTranslation(String d, String c, String j) {
		
		return  111 + comp.get(c) + dest.get(d) + jump.get(j); //order matter due to Hack machine language specification (111compdestjump)
		
	}
	
	private String binaryConversion(int value) {
		return String.format("%" + numBitInstruction +"s", Integer.toBinaryString(value)).replace(' ', '0'); // 16 for sixteen bit instructions
	}
	
	

}
