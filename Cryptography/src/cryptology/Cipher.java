package cryptology;
import java.util.Random;


public abstract class Cipher {
	
	public static final double[]	letterFrequenciesEnglish 	= { 8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074 };
	public static final char[]		letters						= { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	public static final char		BLANK_CHAR					= '-';
	public abstract String encipher(String clear_text);
	public abstract String decipher(String cipher_text);
	
	public String precipitateLettersFromString(String string) {
		
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
	
	public char generateWeightedLetter() {
		Random random = new Random();
		char letter = 0;
		boolean letter_found = false;
		while (!letter_found) {
			for (int letter_index = 0; letter_index < letters.length; letter_index++) {
				if (random.nextInt(100000)*0.001 < letterFrequenciesEnglish[letter_index]) {
					letter = letters[letter_index];
					letter_found = true;
					break;
				}
			}
		}
		return letter;
	}

}
