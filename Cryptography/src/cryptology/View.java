package cryptology;
import java.util.InputMismatchException;
import java.util.Scanner;


public class View {
	// Instance Variables
	private Scanner keyboard_scanner;
	
	// Constants
	private final String	EXIT						= "E";
	private final String	SUBSTITUTION_CIPHER			= "S";
	private final String	TRANSPOSITON_CIPHER			= "T";
	private final String	CEASERS_CIPHER				= "C";
	private final String	UTILITIES					= "U";
	private final String[]	MENU_OPTION_INFO			= {	"Types of Ciphers:",
															"Substituion Cipher - [S]",
															"Transposition Cipher - [T]",
															"",
															"Various Utilities - [U]"};
	private final String[]	SUBSTITUTION_CIPHER_INFO	= { "Types of Substitution Ciphers:",
															"Ceaser's Cipher - [C]"};
	private final String[]	CEASERS_CIPHER_INFO			= { "Options:",
															"Encipher - [CE]",
															"Decipher - [CD]",
															"Change Shift Value - [CS]"};
	private final String[]	TRANSPOSITION_CIPHER_INFO	= {	"Types of Transposition Ciphers:",
															"Single Columnar Cipher - [SC]",
															"Double Columnar Cipher - [DC]"};
	private final String[]	DOUBLE_COLUMNAR_CIPHER_INFO	= {	"Options:",
															"Encipher - [DE]",
															"Decipher - [DD]",
															"Change Keywords - [CK]"};
	
	// Constructor
	public View() {
		
	}
	
	public void displayMenu() {
		String menu_option = "";
		keyboard_scanner = new Scanner(System.in);
		
		// Main Menu
		while (!menu_option.equalsIgnoreCase(EXIT)) {
			System.out.println("\n-----------------------------------------");
			System.out.println("--Welcome to the world of Cryptography!--\n");
			for (String info : MENU_OPTION_INFO) {
				System.out.println(info);
			}
			System.out.print("\n[E]xit or choose a menu option: ");
			menu_option = keyboard_scanner.nextLine();
			
			switch (menu_option.toUpperCase()) {
				case SUBSTITUTION_CIPHER:
					displaySubstitutionCiphers();
					break;
				case TRANSPOSITON_CIPHER:
					displayTranspositionCiphers();
					break;
				case UTILITIES:
					displayUtilities();
					break;
				case "TEST":
					Cryptanalysis test = new Cryptanalysis();
					System.out.println(arrayToString(test.testGetDifference(getString("Random String"))));
					break;
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	// Utilities
	private void displayUtilities() {
		String menu_option = "";
		while (!menu_option.equalsIgnoreCase(EXIT)) {
			
			// List options
			System.out.println("\n--------------");
			System.out.println("--Utilities:--\n");
			System.out.println("Digest String - [DS]");
			
			// Select Menu Option
			System.out.print("\n[E]xit or choose a menu option: ");
			menu_option = keyboard_scanner.nextLine();
			switch (menu_option.toUpperCase()) {
				case "DS":
					System.out.println("Enter Text to be digested:  ");
					System.out.println("Digested String:    " + digestString(keyboard_scanner.nextLine()));
					break;
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	// Substitution Cipher
	private void displaySubstitutionCiphers() {
		String menu_option = "";
		while (!menu_option.equalsIgnoreCase(EXIT)) {
			
			// List options
			System.out.println("\n-------------------------");
			System.out.println("--Substitution Ciphers:--\n");
			for (String info : SUBSTITUTION_CIPHER_INFO) {
				System.out.println(info);
			}
			
			// Select Menu Option
			System.out.print("\n[E]xit or choose a menu option: ");
			menu_option = keyboard_scanner.nextLine();
			switch (menu_option.toUpperCase()) {
				case CEASERS_CIPHER:
					displayCeasersCipher();
					break;
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	private void displayTranspositionCiphers() {
		final String DOUBLE_COLUMNAR_CIPHER = "DC";
		String menu_option = "";
		while (!menu_option.equalsIgnoreCase(EXIT)) {
			// List options
			System.out.println("\n--------------------------");
			System.out.println("--Transposition Ciphers:--\n");
			for (String info : TRANSPOSITION_CIPHER_INFO) {
				System.out.println(info);
			}
			
			// Select Menu Option
			System.out.print("\n[E]xit or choose a menu option: ");
			menu_option = keyboard_scanner.nextLine();
			switch (menu_option.toUpperCase()) {
				case DOUBLE_COLUMNAR_CIPHER:
					displayDoubleColumnarCipher();
					break;
				case EXIT: break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	private void displayDoubleColumnarCipher() {
		final String DOUBLE_COLUMNAR_CIPHER_ENCIPHER	= "DE";
		final String DOUBLE_COLUMNAR_CIPHER_DECIPHER	= "DD";
		final String CHANGE_KEYWORDS					= "CK";
		String menu_option								= "";
		
		// Display title
		System.out.println("\n--------------------------");
		System.out.println("--Double Columnar Cipher--\n");
		
		// Get keywords
		DoubleColumnarCipher double_columnar_cipher = new DoubleColumnarCipher(getString("Keyword 1"), getString("Keyword 2"), getBoolean("Pad Ciphertext"));
		while (!menu_option.equalsIgnoreCase(EXIT)) {
			
			// Select Menu Option
			System.out.println("-------------------------------");
			for (String info : DOUBLE_COLUMNAR_CIPHER_INFO) {
				System.out.println(info);
			}
			System.out.print("\n[E]xit or choose a menu option: ");

			menu_option = keyboard_scanner.nextLine();
			switch (menu_option.toUpperCase()) {
				case CHANGE_KEYWORDS:
					double_columnar_cipher.setKeyword(getString("1"), 1);
					double_columnar_cipher.setKeyword(getString("2"), 2);
					break;
				case DOUBLE_COLUMNAR_CIPHER_ENCIPHER:
					System.out.println("-----------");
					System.out.print  ("Clear Text: ");
					System.out.println("Enciphered Text:\n"+double_columnar_cipher.encipher(keyboard_scanner.nextLine()));
					break;
				case DOUBLE_COLUMNAR_CIPHER_DECIPHER:
					System.out.println("------------");
					System.out.print  ("Cipher Text: ");
					System.out.println("Deciphered Text:\n"+double_columnar_cipher.decipher(keyboard_scanner.nextLine()));
					break;
				case EXIT:break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	private boolean getBoolean(String label) {
		boolean value = false;
		boolean value_not_found;
		do {
			try {
				System.out.println("\nPlease enter boolean \"" + label + "\" [true/false]:  ");
				value = keyboard_scanner.nextBoolean();
				keyboard_scanner.nextLine();
				value_not_found = false;
			} catch (Exception e) {
				System.out.println("Invalid Input");
				keyboard_scanner.nextLine();
				value_not_found = true;
			}
		} while (value_not_found);
		return value;
	}
	
	private String getString(String label) {
		System.out.print("\nPlease enter string \"" + label + "\":  ");
		return digestString(keyboard_scanner.nextLine());
	}
	
	private int getInteger(String label) {
		int value = 0;
		boolean value_not_found;
		do {
			try {
				System.out.print("\nPlease enter integer value \"" + label + "\":  ");
				value = keyboard_scanner.nextInt();
				keyboard_scanner.nextLine();
				value_not_found = false;
			} catch (Exception e) {
				System.out.println("Invalid Input");
				keyboard_scanner.nextLine();
				value_not_found = true;
			}
		} while (value_not_found);
		return value;
	}
	
	// Ceaser's Cipher
	private void displayCeasersCipher() {
		final String CEASERS_CIPHER_ENCIPHER	= "CE";
		final String CEASERS_CIPHER_DECIPHER	= "CD";
		final String CHANGE_SHIFT				= "CS";
		String menu_option						= "";
		
		// Display title
		System.out.println("\n-------------------");
		System.out.println("--Ceaser's Cipher--\n");
		
		// Get shift value
		System.out.println("\nPlease enter a shift value: ");
		CeaserCipher ceaser_cipher = new CeaserCipher(getInteger("Shift Value"));
		while (!menu_option.equalsIgnoreCase(EXIT)) {
			
			// Select Menu Option
			System.out.println("-------------------------------");
			for (String info : CEASERS_CIPHER_INFO) {
				System.out.println(info);
			}
			System.out.print("\n[E]xit or choose a menu option: ");

			menu_option = keyboard_scanner.nextLine();
			switch (menu_option.toUpperCase()) {
				case CHANGE_SHIFT:
					ceaser_cipher.setShiftValue(getInteger("Shift Value"));
					break;
				case CEASERS_CIPHER_ENCIPHER:
					System.out.println("-----------");
					System.out.print  ("Clear Text: ");
					System.out.println("Enciphered Text:\n"+ceaser_cipher.encipher(keyboard_scanner.nextLine()));
					break;
				case CEASERS_CIPHER_DECIPHER:
					System.out.println("------------");
					System.out.print  ("Cipher Text: ");
					System.out.println("Deciphered Text:\n"+ceaser_cipher.decipher(keyboard_scanner.nextLine()));
					break;
				case EXIT:break;
				default:
					System.out.println("INVALID OPTION");
					break;
			}
		}
	}
	
	private String arrayToString(double[] arr) {
		String out = "{";
		for (int x = 0; x < arr.length; x++) {
			out += arr[x];
			out += ", ";
				
		}
		out = out.substring(0, out.length()-2);
		out += "}";
		return out;
	}
	
	private String digestString(String string) {
		
		// Extract words consisting of only letters, Exclude all digits, punctuation and whitespace
		// \\W means non-word character
		// \\d means digit
		// + means one or more than one such character
		String[] string_array = string.split("[\\W\\d]+");
		
		// Merge array into one upper case string
		String fin_string = "";
		for (String text : string_array) {
			fin_string += text;
		}
		return fin_string.toUpperCase();
	}
}
