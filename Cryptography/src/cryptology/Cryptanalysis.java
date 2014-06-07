package cryptology;

public class Cryptanalysis {
	
	public static final double[]	letterFrequenciesEnglish 	= { 8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 1.974, 0.074 };
	public static final char[]		letters						= { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	// Constructor
	public Cryptanalysis() {
		
	}
	
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

	public double[] testGetLetterFreq(String text) {
		return calculateFrequencies(countLetters(text));
	}
	
	public double[] testGetDifference(String text) {
		return calculateDifference(letterFrequenciesEnglish, testGetLetterFreq(text));
	}
	
	private int[] countLetters(String text) {
		char[] text_chars = precipitateLettersFromString(text).toCharArray();
		int[] letter_count = new int[26];
		
		// Initialize letter_count
		for (int i = 0; i < letter_count.length; i++) {
			letter_count[i] = 0;
		}
		
		// Count Letters
		for (char letter : text_chars) {
			for (int letter_index = 0; letter_index < letters.length; letter_index++) {
				if (letter == letters[letter_index]) {
					letter_count[letter_index]++;
				}
			}
		}
		return letter_count;
	}
	
	private double[] calculateFrequencies(int[] data) {
		
		// Final Total Number of Elements
		int total_elements = 0;
		for (int element : data) {
			total_elements += element;
		}
		
		// Create and initialize frequencies array
		double[] frequencies = new double[26];
		for (int i = 0; i < frequencies.length; i++) {
			frequencies[i] = 0d;
		}
		
		// Calculate frequencies
		for (int index = 0; index < data.length; index++) {
			frequencies[index] = Math.round( ((double) data[index] / (double) total_elements) * 100000d) / 1000d;
		}
		
		return frequencies;
	}
	
	private double[] calculateDifference(double[] arr1, double[] arr2){
		if (arr1.length != arr2.length) {
			throw new IllegalArgumentException("The length of 'arr1' ("+ arr1.length +") must be equal to the length of 'arr2' ("+ arr2.length +")");
		}
		
		// TODO TEST
		System.out.println("arr1 = " + arrayToString(arr1));
		System.out.println("arr2 = " + arrayToString(arr2));
		
		double[] difference = new double[arr1.length];
		
		for (int i = 0; i < difference.length; i++) {
			difference[i] = Math.round( (arr1[i] - arr2[i]) * 1000d) / 1000d;
		}
		
		return difference;
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
}
