package com.nand2tetrispart1.assembler;

import java.io.FileNotFoundException;

import java.io.IOException;

public class Demo {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		
		
		  String filename = "poplocalStack"; //should have an asm extension, but her must not ne specify
		  double start = System.currentTimeMillis();
		  HackAssembler assembler = new HackAssembler(filename);
		  assembler.hackMachineLanguage();
		  double end = System.currentTimeMillis(); System.out.println("final time " +
		  (end-start) / 1000.0 + "s");
		 
		
	}
}


