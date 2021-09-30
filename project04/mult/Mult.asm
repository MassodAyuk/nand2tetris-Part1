// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)
//
// This program only needs to handle arguments that satisfy
// R0 >= 0, R1 >= 0, and R0*R1 < 32768.

// Put your code here.
  	
	//NEL CASO ALMENO 1 vale 0
	@R0
	D = M
	@NULL
	D;JEQ
	
	@R1
	D = M
	@NULL
	D;JEQ

	@R1
	D = A
	D = D - M
	@UNITYA
	D;JEQ
		
	@R1
	D = A
	@R0
	D = D - M
	@UNITYB
	D;JEQ

	//SE R0 >= R1 fa la somma di R0 R1 volte
	@R0
	D = M
	@R1
	D = D - M
	@FGS
	D;JGE
	
	//SE R0 < R1 fa la somma di R1 R0 volte
	@SGF
	D;JLT
	
	@END
	0;JMP

(NULL)
	@R0
	D = A
	@R2
	M = D
	@END
	0;JMP

(UNITYA)
	@R0
	D = M
	@R2
	M = D
	@END
	0;JMP

(UNITYB)
	@R1
	D = M
	@R2
	M = D
	@END
	0;JMP
	
(FGS) //first number is greather than second
	//initialize variable
	@i
	M = 1 //set the counter to 1
	@sum
	M = 0 //set the sum to 0
	@R0
	D = M //set the D register to RAM[0]
	@temp
	M = D //set the RAM[16] = RAM[0] 

	(LOOPA)
		//stop if R1 > i
		@R1
		D = M
		@i
		D = D - M
		@STOPA
		D; JEQ
		
		//increment R0, R1 volte
		@R0
		D = M
		@temp
		D = D + M
		@temp
		M = D
		@sum
		M = D
		@LESSUMA //STOP as soon as the R0*R1 < 32768 and set the product to 0
		D;JLT
		@i
		M = M + 1 //increment 
		@LOOPA
		0;JMP

	(STOPA)
		@sum
		D = M
		@R2
		M = D
		@END
		0;JMP
			
		
	(LESSUMA)	
		@R2
		M = 0
		@END
		0;JMP
		
	(END)
		@END
		0;JMP

(SGF) //first number is greather than second
	//initialisation
	@i
	M = 1
	@sum
	M = 0
	@R1
	D = M
	@temp
	M = D

	(LOOPB)
		@R0
		D = M
		@i
		D = D - M
		@STOPB
		D; JEQ

		@R1
		D = M
		@temp
		D = D + M
		@temp
		M = D
		@sum
		M = D
		@LESSUMB
		D;JLT
		@i
		M = M + 1
		@LOOPB
		0;JMP

	(STOPB)
		@sum
		D = M
		@R2
		M = D
		
		@END
		0;JMP
		
	(LESSUMB)	
		@R2
		M = 0
		@END
		0;JMP
		
	(END)
		@END
		0;JMP
		
(END)
	@END
	0;JMP		