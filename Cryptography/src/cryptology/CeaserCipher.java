package cryptology;
public class CeaserCipher extends Cipher {
	
	private int shift_value;
	
	public CeaserCipher(int shift_value) {
		this.shift_value = shift_value;
	}
	
	public void setShiftValue(int shift_value) {this.shift_value = shift_value;}

	@Override
	public String encipher(String clear_text) {
		String fin_cipher_text = "";

		String fin_clear_text = precipitateLettersFromString(clear_text);
		
		// Shift characters
		for (int i = 0; i < fin_clear_text.length(); i++) {
			int character = (fin_clear_text.charAt(i) + shift_value);
			// Old Algorithm - DOES NOT SUPPORT SHIFT VALUES > 26 OR < 0
			/*
			if (character > 90) {
				//character = (char)(character - 26);
				character -= 26;
			}
			*/
			
			// New Algorithm - Wrap values outside of character set back around
			character -= 64; // Shift down from ascii values
			character %= 26; // Get remainder after dividing 26
			if (character <= 0) // (Special case if shift value is negative) OR (remainder is 0 so it must be z)
				character += 26;
			character += 64; // Shift back up to ascii values
			fin_cipher_text += String.valueOf((char)character); // Append to final string
		}
		return fin_cipher_text;
	}

	@Override
	public String decipher(String cipher_text) {
		String fin_clear_text = "";

		String fin_cipher_text = precipitateLettersFromString(cipher_text);
		
		// Shift characters back
		for (int i = 0; i < fin_cipher_text.length(); i++) {
			int character = (fin_cipher_text.charAt(i) - shift_value);
			// Old Algorithm - DOES NOT SUPPORT SHIFT VALUES > 26 OR < 0
			/*
			if (character < 65) {
				//character = (char)(character + 26);
				character += 26;
			}
			*/
			
			// New Algorithm - Wrap values outside of character set back around
			character -= 64; // Shift down from ascii values
			character %= 26; // Get remainder after dividing 26
			if (character <= 0) // (Special case if shift value is negative) OR (remainder is 0 so it must be z)
				character += 26;
			character += 64; // Shift back up to ascii values
			fin_clear_text += String.valueOf((char)character); // Append to final string
		}
		return fin_clear_text;
	}
	
}
