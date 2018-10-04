//Chapter 4-Lexical Analyzer
//Mark Bell
//CSCI4200-DA
//Dr. Abi Salimi

package lexicalAnalyzerPackage;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class LexicalAnalyzer {
	//Variables
	static FileReader LexText;
	static Scanner line;
	static String nextToken;
	static int Lexnum;
	static int charClass;
	static int letter =0;
	static int number =1;
	static int other =2;
	static int EOF= 3;
	static char nextChar;
	static String[] TokenName = {"INT_LIT","IDENT","END_OF_FILE","ASSIGN_OP","ADD_OP","SUB_OP","MULT_OP","DIV_OP","LEFT_PAREN","RIGHT_PAREN"};
	static char[] Symbol = {'=','+','-','*','/','(',')'};
	static char Lexeme[] = new char[100];
	static String NextLine;
	static String CompareLine ="";
	
	
	//Main Method.
	public static void main(String[] args)throws IOException{

		LexText = new FileReader("../Lexical Analyizer/src/lexicalAnalyzerPackage/lexInput.txt");
		File file= new File("../Lexical Analyizer/src/lexicalAnalyzerPackage/lexInput.txt");
		line = new Scanner(file);
		if (LexText == null )
		{
			System.out.println("OOPS no file");
		}
		else{
			System.out.println("Mark Bell, CSCI4200-DA, Fall 2018, Lexical Analyzer");
			for (int i=1; i<=80; i++){
				System.out.print("*");
			}
			NextLine = line.nextLine();
			System.out.println("\n");
			System.out.println("Input: "+NextLine);
			getChar();
			do{
				lex();
			} while (nextToken != TokenName[2]);
		}
	}

	//Gets the next character in the text file and determines it's class.
	private static String getChar() throws IOException {
		int i = LexText.read();
		nextChar = (char) i;
		
		if (i !=-1){
			if (Character.isDigit(nextChar)){
				charClass = number;
			}
			else if(Character.isLetter(nextChar)){
				charClass = letter;
			}
			else{
				charClass = other;
			}
		}
		else{
			charClass = EOF;
		}
		return CompareLine;
	}
	
	//adds current char to an array for storage and rejects it if it's too long.
	private static void addChar() {
		if (Lexnum <= 98){
			Lexeme[Lexnum++] = nextChar;
			Lexeme[Lexnum]=0;
		}
		else
			System.out.println("Error: Lexeme is too long");
		
	}
	//Skips over spaces to get the next Character.
	@SuppressWarnings("deprecation")
	private static void getNonBlank() throws IOException {
		while (Character.isSpace(nextChar)){
			CompareLine+= nextChar;
			getChar();
		}
	}
	//Looks up any non-letter or non-number character given
	private static void lookup(char ch){
		for (int i=0;i < Symbol.length;i++){
			if (ch == Symbol[i]){
				addChar();
				nextToken = TokenName[i+3];
				break;
			}
		}
	}
	
	//Determines the token and lexeme as well as giving the print out.
	private static String lex() throws IOException {
		Lexnum=0;
		if (CompareLine.contains(NextLine)&&charClass!=EOF){
			NextLine = line.nextLine();
			CompareLine = "";
			for (int i=1; i<=80; i++){
				System.out.print("*");
			}
			System.out.println("\n");
			System.out.println("Input: "+NextLine);
		}
		else{
			getNonBlank();
			switch (charClass){
				case 0:
					addChar();
					CompareLine+= nextChar;
					getChar();
					while (charClass == letter || charClass == number){
						addChar();
						CompareLine+= nextChar;
						getChar();
					}
					nextToken = TokenName[1];
					break;
					
				case 1:
					addChar();
					CompareLine+= nextChar;
					getChar();
					while (charClass == number){
						addChar();
						CompareLine+= nextChar;
						getChar();
					}
					nextToken = TokenName[0];
					break;
				
				case 2:
					lookup(nextChar);
					CompareLine+= nextChar;
					getChar();
					break;
				
				case 3:
					for (int i=1; i<=80; i++){
						System.out.print("*");
					}
					System.out.println("\n");
					nextToken= TokenName[2];
					Lexeme[0]='E';
					Lexeme[1]='O';
					Lexeme[2]='F';
					Lexeme[3]=0;
					break;
				}
		
			int i=0;
			String Lexeme1 = "";
			while (Lexeme[i]!=0){
				Lexeme1 += ""+Lexeme[i];
				i++;
			}
		String format ="%-40s%s%n";
		String TokenLine = "Next token is: "+nextToken;
		String LexemeLine = "Next lexeme is:"+Lexeme1;
		System.out.printf(format, TokenLine, LexemeLine);
		if (nextToken== TokenName[2])
			System.out.println("Lexical analysis of the program is complete!");
		return nextToken;
	}
		return CompareLine;
	}
}


//I Used https://www.geeksforgeeks.org/different-ways-reading-text-file-java/ to help me with reading the Java file