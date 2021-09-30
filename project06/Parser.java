package com.nand2tetrispart1.assembler;

import java.util.ArrayList;

public class Parser {

	// this class assume a well write program in the hack programming language

	public ArrayList<String> tokenization(ArrayList<Character> temp) {
		/**
		 * This function take a line and return a list containing the type of
		 * instruction and the character specifying the instruction NB: they can be null
		 * instruction (dealing with comment and space)
		 * 
		 * NOTE: it deals only with A/C-instruction all other instruction are null instruction (including LABELs)
		 */

		ArrayList<String> instruction = new ArrayList<String>();

		if(temp.size() != 0) { // se diverso da null instruction (while space comment etc..)

			if(temp.get(0) == '@') { // A-instruction

				String value = "";
				for (int i = 1; i < temp.size(); i++) {
					Character c = Character.valueOf(temp.get(i));
					value += c.toString();
				}

				// order matter
				instruction.add("A");
				instruction.add(value);

			}else if (temp.get(0) == '(') { // if the instructions is a label setting as null instruction 
				instruction = null;

			}else { // otherwise C-instruction
				String dest = "";
				String comp = "";
				String jump = "";
				Character d;
				Character c;
				Character ju;

				// contains only equal
				if (temp.contains('=') && !temp.contains(';')) {

					int index = temp.indexOf('=');
					for (int i = 0, j = index + 1;;) {
						if (i < index) {
							d = Character.valueOf(temp.get(i));
							dest += d.toString();
							i++;
						}
						if (j < temp.size()) {
							c = Character.valueOf(temp.get(j));
							comp += c.toString();
							j++;

						}

						if (j == temp.size() && i == index) {
							break;
						}

					}
					jump = null;

					// contains only ;
				} else if (temp.contains(';') && !temp.contains('=')) {

					int index = temp.indexOf(';');
					for (int i = 0, j = index + 1;;) {
						if (i < index) {
							d = Character.valueOf(temp.get(i));
							comp += d.toString();
							i++;
						}
						if (j < temp.size()) {
							c = Character.valueOf(temp.get(j));
							jump += c.toString();
							j++;

						}

						if (j == temp.size() && i == index) {
							break;
						}

					}
					dest = null;

					// contains both = and ;
				} else if ((temp.contains(';') && temp.contains('='))) {

					int index = temp.indexOf('=');
					int ji = temp.indexOf(';');
					for (int i = 0, j = index + 1, k = ji + 1;;) { // comp = dest ; jump
						if (i < index) {
							d = Character.valueOf(temp.get(i));
							dest += d.toString();
							i++;
						}
						if (j < ji) {

							c = Character.valueOf(temp.get(j));
							comp += c.toString();
							j++;

						}
						if (k < temp.size()) {
							ju = Character.valueOf(temp.get(k));
							jump += ju.toString();

							k++;
						}

						if (j == ji && i == index && k == temp.size()) {
							break;
						}

					}

				}

				// orders matter
				instruction.add("C");
				instruction.add(dest); // destination
				instruction.add(comp);// comptutation
				instruction.add(jump); // JUMP
			}

		} else // if is a null instruction (white space, comment etc..)
			instruction = null;

		return instruction;
	}

	public ArrayList<Character> lineParsing(String line) {
		/**
		 * this function read a line skipping comment, white space and reading only valuable character and
		 * return a List of those characters
		 */
		ArrayList<Character> token = new ArrayList<>();
		for (int i = 0; i < line.length(); i++) {
			if (line.charAt(i) == ' ' || line.charAt(i) == '\t') // skip white space
				continue;
			else if (line.charAt(i) == '/') // assuming a well writing program we have the next character been / so a comment							
				break;
			else {
				token.add(line.charAt(i));
				if (line.charAt(i) == ' ' || line.charAt(i) == '/') // as soon as we encounter a space o / we stop because it's irrelevant o a comment
																	
					break;
			}

		}
		return token;
	}
	
	public boolean isInstruction(ArrayList<Character> listChar) {
		/**
		 * an instruction is everything in the list character which does not start with '(', cause they are label
		 *  NB : will also help distguish between A/C-instructions and labels(to be add in the lookup table)
		 */
		boolean result = false;
		if(!(listChar.size() == 0)) { //first if deals with white space and comments
			if(!(listChar.get(0) == '('))
				result = true;
		}
		return result;
	}
	
	public String retrieveLabel(ArrayList<Character> listChar) {
		/**
		 * this function deals with label 
		 * syntax: '(LABEL)' that's why we stop at size()-1
		 */
		String label = "" ;
		Character c;
		for (int i = 1; i < listChar.size() - 1; i++) {
			c = Character.valueOf(listChar.get(i));
			label += c.toString();
		}
		return label;
	}
}
