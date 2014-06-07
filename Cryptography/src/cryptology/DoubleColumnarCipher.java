package cryptology;

public class DoubleColumnarCipher extends Cipher {
	
	private String keyword1;
	private String keyword2;
	private boolean pad_matrix;
	
	public DoubleColumnarCipher(String keyword1, String keyword2, boolean pad_matrix) {
		this.keyword1 = precipitateLettersFromString(keyword1);
		this.keyword2 = precipitateLettersFromString(keyword2);
		this.pad_matrix = pad_matrix;
	}
	
	public void setKeyword(String keyword, int label) {
		if (label == 1) {
			keyword1 = precipitateLettersFromString(keyword);
		} else if (label == 2) {
			keyword2 = precipitateLettersFromString(keyword);
		}
	}

	@Override
	public String encipher(String clear_text) {
		String fin_clear_text = precipitateLettersFromString(clear_text);
		char[] clear_text_char = fin_clear_text.toCharArray();
		
		// Create the first transposition matrix
		int mat1_rows = fin_clear_text.length()/keyword1.length();
		if (fin_clear_text.length()%keyword1.length() != 0) // Add an additional row if there are left over letters
			mat1_rows++;
		
		char[][] trans_mat1 = new char[mat1_rows][keyword1.length()];
		
		// Fill trans_mat1 with clear text and add padding
		fillMatrix(clear_text_char, trans_mat1, true);
		if (pad_matrix) {
			padMatrix(trans_mat1);
		}
		
		// Convert keyword1 into the integer equivalent in ASCII
		int[] keyword1_int = stringToInt(keyword1);
		System.out.println(matrixToString(trans_mat1));
		//System.out.println(matrixToString(trans_mat1));
		// Insertion Sort
		for (int pass = 1; pass < keyword1_int.length; pass++) {
			int min = keyword1_int[pass];
			char[] min_column = getColumn(trans_mat1, pass);
			int comp;
			for (comp = pass-1; comp >= 0 && min < keyword1_int[comp]; comp--) {
				keyword1_int[comp+1] = keyword1_int[comp];
				for (int rows = 0; rows < trans_mat1.length; rows++) {
					trans_mat1[rows][comp+1] = trans_mat1[rows][comp];
				}
			}
			keyword1_int[comp+1] = min;
			for (int rows = 0; rows < trans_mat1.length; rows++) {
				trans_mat1[rows][comp+1] = min_column[rows];
			}
		}
		System.out.println(matrixToString(trans_mat1));
		
		// Convert trans_mat1 into a single character array
		char[] trans_mat1_char_arr = matrixToSingleCharArray(trans_mat1, false);
		System.out.println(arrayToString(trans_mat1_char_arr));

		// Create Second Transposition Matrix
		int mat2_rows = (trans_mat1.length*trans_mat1[0].length)/keyword2.length();
		if ((trans_mat1.length*trans_mat1[0].length)%keyword2.length() != 0) // Add an additional row if there are left over letters
			mat2_rows++;
		
		int mat2_columns = keyword2.length();
		
		char[][] trans_mat2 = new char[mat2_rows][mat2_columns];
		
		// Fill trans_mat2 with trans_mat1_char_arr and add padding
		fillMatrix(trans_mat1_char_arr, trans_mat2, true);
		//if (pad_matrix) {
			//padMatrix(trans_mat2);
		//}
		System.out.println(matrixToString(trans_mat2));
		
		// Convert keyword2 into the integer equivalent in ASCII
		int[] keyword2_int = stringToInt(keyword2);

		// Insertion Sort
		for (int pass = 1; pass < keyword2_int.length; pass++) {
			int min = keyword2_int[pass];
			char[] min_column = getColumn(trans_mat2, pass);
			int comp;
			for (comp = pass-1; comp >= 0 && min < keyword2_int[comp]; comp--) {
				keyword2_int[comp+1] = keyword2_int[comp];
				for (int rows = 0; rows < trans_mat2.length; rows++) {
					trans_mat2[rows][comp+1] = trans_mat2[rows][comp];
				}
			}
			keyword2_int[comp+1] = min;
			for (int rows = 0; rows < trans_mat2.length; rows++) {
				trans_mat2[rows][comp+1] = min_column[rows];
			}
		}
		
		System.out.println(matrixToString(trans_mat2));
		
		// Convert trans_mat2 into a single string
		String fin_cipher_text = arrayToStringRAW(matrixToSingleCharArray(trans_mat2, false));
		
		System.out.println(fin_cipher_text);
		
		return fin_cipher_text;
	}
	
	@Override
	public String decipher(String cipher_text) {
		char[] cipher_text_char = precipitateLettersFromString(cipher_text).toCharArray();
		
		int mat2_remainder = cipher_text_char.length%keyword2.length();
		int mat2_rows = cipher_text_char.length/keyword2.length();
		if (mat2_remainder != 0) {
			mat2_rows++;
		}
		int mat2_columns = keyword2.length();
		
		char[][] trans_mat2 = new char[mat2_rows][mat2_columns];
		System.out.println("- - - - - - - TEST - - - - - - -");
		// Check if irregular
		if (mat2_remainder != 0) {
			System.out.println(mat2_remainder);
			char[] blocked_elements = new char[keyword2.length()];
			for (int i = mat2_remainder; i < keyword2.length(); i++) {
				blocked_elements[i] = 64;
			}
			System.out.println("Blocked elements: ");
			System.out.println(arrayToString(blocked_elements)+ "\n");
			
			// convert keyword2 to ascii int
			int[] keyword2_int = stringToInt(keyword2);
			// Sort blocked elements according to keyword2
			for (int pass = 1; pass < keyword2_int.length; pass++) {
				int min = keyword2_int[pass];
				char min_element = blocked_elements[pass];
				int comp;
				for (comp = pass-1; comp >= 0 && min < keyword2_int[comp]; comp--) {
					keyword2_int[comp+1] = keyword2_int[comp];
					blocked_elements[comp+1] = blocked_elements[comp];
				}
				keyword2_int[comp+1] = min;
				blocked_elements[comp+1] = min_element;
			}
			
			// transfer blocked elements to trans_mat2
			for (int i = 0; i < blocked_elements.length; i++) {
				trans_mat2[trans_mat2.length-1][i] = blocked_elements[i];
			}
			System.out.println(matrixToString(trans_mat2));
			
			System.out.println("- - - - - - END TEST - - - - - -");
		}
		// Fill trans_mat2 with cipher text (This matrix is sorted from the start, must desort to continue)
		
		fillMatrix(cipher_text_char, trans_mat2, false);
		
		System.out.println(matrixToString(trans_mat2));
		
		// Convert keyword2 into the integer equivalent in ASCII
		int[] keyword2_int = stringToInt(keyword2);
		
		// Insertion Sort just the keyword to match the sorted transposition matrix
		int[] sorted_keyword2_int = insertionSort(keyword2_int.clone());
		
		//System.out.println(arrayToString(keyword2_int));
		//System.out.println(arrayToString(sorted_keyword2_int));
		
		// Undo the insertion sort from the second transposition matrix
		char[][] trans_mat2_desorted = new char[mat2_rows][mat2_columns];
		for (int trans_mat2_column_index = 0; trans_mat2_column_index < trans_mat2[0].length; trans_mat2_column_index++) {
			int sorted_char_int = sorted_keyword2_int[trans_mat2_column_index]; // sorted_char_int is the corresponding letter (in ascii decimal equivalent) to the column of the second sorted transposition matrix
			// find the original position of the column
			int original_char_index = -1;
			for (int keyword2_index = 0; keyword2_index < keyword2_int.length; keyword2_index++) {
				if (keyword2_int[keyword2_index] == sorted_char_int) {
					keyword2_int[keyword2_index] = -1;  // Delete keyword letter to make sure it doesn't go over it twice -- RENDERS keyword2_int UNUSABLE afterwards
					original_char_index = keyword2_index;
					break;
				}
			}
			
			// copy over the column from the sorted matrix to the desorted matrix in the appropriate index
			for (int row = 0; row < trans_mat2.length; row++) {
				trans_mat2_desorted[row][original_char_index] = trans_mat2[row][trans_mat2_column_index];
			}
		}
		System.out.println(matrixToString(trans_mat2_desorted));
		//System.out.println(matrixToString(trans_mat2_desorted));
		
		// Convert trans_mat2_desorted to a single char[] for easy use
		char[] trans_mat2_desorted_arr = matrixToSingleCharArray(trans_mat2_desorted, true);
		
		System.out.println(arrayToString(trans_mat2_desorted_arr));
		
		// Convert keyword1 to ints
		int[] keyword1_int = stringToInt(keyword1);
		
		// Create trans_mat1
		int mat1_remainder = trans_mat2_desorted_arr.length%keyword1.length();
		int mat1_rows = trans_mat2_desorted_arr.length/keyword1.length();
		if (mat1_remainder != 0)
			mat1_rows++;
		char[][] trans_mat1 = new char[mat1_rows][keyword1.length()];
		
		// Fill trans_mat1 with desorted cipher text
		if (mat1_remainder != 0) {
			mat1_rows++;
			
			char[] blocked_elements = new char[keyword1.length()];
			for (int i = mat1_remainder; i < keyword1.length(); i++) {
				blocked_elements[i] = 64;
			}
			
			// Sort blocked elements according to keyword1
			for (int pass = 1; pass < keyword1_int.length; pass++) {
				int min = keyword1_int[pass];
				char min_element = blocked_elements[pass];
				int comp;
				for (comp = pass-1; comp >= 0 && min < keyword1_int[comp]; comp--) {
					keyword1_int[comp+1] = keyword1_int[comp];
					blocked_elements[comp+1] = blocked_elements[comp];
				}
				keyword1_int[comp+1] = min;
				blocked_elements[comp+1] = min_element;
			}
			System.out.println(arrayToString(blocked_elements));
			// transfer blocked elements to mat1
			for (int i = 0; i < blocked_elements.length; i++) {
				trans_mat1[trans_mat1.length-1][i] = blocked_elements[i];
			}
		}
		
		fillMatrix(trans_mat2_desorted_arr, trans_mat1, false);
		
		System.out.println(matrixToString(trans_mat1));
		keyword1_int = stringToInt(keyword1);
		
		// Insertion Sort just the keyword to match the sorted transposition matrix
		int[] sorted_keyword1_int = insertionSort(stringToInt(keyword1));
		
		System.out.println(arrayToString(sorted_keyword1_int));
		
		// Undo the insertion sort from the second transposition matrix
		char[][] trans_mat1_desorted = new char[trans_mat1.length][trans_mat1[0].length];
		for (int trans_mat1_column_index = 0; trans_mat1_column_index < trans_mat1[0].length; trans_mat1_column_index++) {
			int sorted_char_int = sorted_keyword1_int[trans_mat1_column_index]; // sorted_char_int is the corresponding letter (in ascii decimal equivalent) to the column of the second sorted transposition matrix
			// find the original position of the column
			int original_char_index = -1;
			for (int keyword1_index = 0; keyword1_index < keyword1_int.length; keyword1_index++) {
				if (keyword1_int[keyword1_index] == sorted_char_int) {
					keyword1_int[keyword1_index] = -1;  // Delete keyword letter to make sure it doesn't go over it twice -- RENDERS keyword1_int UNUSABLE afterwards
					original_char_index = keyword1_index;
					break;
				}
			}
			
			// copy over the column from the sorted matrix to the desorted matrix in the appropriate index
			for (int row = 0; row < trans_mat1.length; row++) {
				trans_mat1_desorted[row][original_char_index] = trans_mat1[row][trans_mat1_column_index];
			}
		}
		System.out.println(matrixToString(trans_mat1_desorted));
		// Convert trans_mat1 back to clear text
		String fin_clear_text = arrayToStringRAW(matrixToSingleCharArray(trans_mat1_desorted, true));
		
		return fin_clear_text;
	}
	
	private int[] insertionSort(int[] arr) {
		for (int pass = 1; pass < arr.length; pass++) {
			int min = arr[pass];
			int comp;
			for (comp = pass-1; comp >= 0 && min < arr[comp]; comp--) {
				arr[comp+1] = arr[comp];
			}
			arr[comp+1] = min;
		}
		return arr;
	}
	
	private char[] matrixToSingleCharArray(char[][] matrix, boolean row_first) {
		int char_length = 0;
		for (char[] characters : matrix) {
			for (char character : characters) {
				if (character >= 65 && character <= 90) {
					char_length++;
				}
			}
		}
		char[] chars = new char[char_length];
		int chars_index = 0;
		if (row_first) {
			for (int row = 0; row < matrix.length; row++)
				for (int column = 0; column < matrix[row].length; column++)
					if (matrix[row][column] >= 65 && matrix[row][column] <= 90) { // Only accept letters -- Will exclude all placeholder characters
						chars[chars_index++] = matrix[row][column];
					}
		} else {
			for (int column = 0; column < matrix[0].length; column++)
				for (int row = 0; row < matrix.length; row++)
					if (matrix[row][column] >= 65 && matrix[row][column] <= 90) { // Only accept letters -- Will exclude all placeholder characters
						chars[chars_index++] = matrix[row][column];
					}
		}
		return chars;
	}
	
	private void fillMatrix(char[] raw_text, char[][] arr, boolean row_first) {
		int text_index = 0;
		if (row_first) {
			for (int row = 0; row < arr.length; row++) {
				for (int column = 0; column < arr[row].length; column++) {
					if (text_index < raw_text.length) {
						if (arr[row][column] != '@') { // Before writing to the array element, check if the spot has been blocked off (designated as a blank spot after sorting)
							arr[row][column] = raw_text[text_index++];
						}
					} else {
						if ( arr[row][column] != '@') { // Before writing to the array element, check if the spot has been blocked off (designated as a blank spot after sorting)
							arr[row][column] = '-'; // If the char array has run out of characters, fill it in with a placeholder character
						}
					}
				}
			}
		} else {
			for (int column = 0; column < arr[0].length; column++) {
				for (int row = 0; row < arr.length; row++) {
					if (text_index < raw_text.length) {
						if (arr[row][column] != '@') { // Before writing to the array element, check if the spot has been blocked off (designated as a blank spot after sorting)
							arr[row][column] = raw_text[text_index++];
						}
					} else {
						if (arr[row][column] != '@') { // Before writing to the array element, check if the spot has been blocked off (designated as a blank spot after sorting)
							arr[row][column] = '-'; // If the char array has run out of characters, fill it in with a placeholder character
						}
					}
				}
			}
		}
	}
	
	private void padMatrix(char[][] matrix) {
		for (int row = 0; row < matrix.length; row++) {
			for (int column = 0; column < matrix[row].length; column++) {
				if (matrix[row][column] == '-') {
					matrix[row][column] = generateWeightedLetter();
				}
			}
		}
	}
	
	private int[] stringToInt(String string) {
		int[] ints = new int[string.length()];
		for (int i = 0; i < ints.length; i++) {
			ints[i] = (int) string.toCharArray()[i];
		}
		return ints;
	}
	
	private String arrayToString(int[] arr) {
		String out = "{";
		for (int x = 0; x < arr.length; x++) {
			out += arr[x];
			out += ", ";
				
		}
		out = out.substring(0, out.length()-2);
		out += "}";
		return out;
	}
	
	private String arrayToString(char[] arr) {
		String out = "{";
		for (int x = 0; x < arr.length; x++) {
			out += arr[x];
			out += ", ";
				
		}
		out = out.substring(0, out.length()-2);
		out += "}";
		return out;
	}
	
	private String arrayToStringRAW(char[] arr) {
		String out = "";
		for (int x = 0; x < arr.length; x++) {
			out += arr[x];
		}
		return out;
	}
	
	private char[] getColumn(char[][] arr, int column) {
		char[] fin_arr = new char[arr.length];
		for (int i = 0; i < fin_arr.length; i++) {
			fin_arr[i] = arr[i][column];
		}
		return fin_arr;
	}
	
	private String matrixToString(char[][] matrix) {
		String out = "";
		for (int x = 0; x < matrix.length; x++) {
			out += "{";
			for (int y = 0; y < matrix[0].length-1; y++) {
				out += matrix[x][y]+", ";
			}
			out += matrix[x][matrix[0].length-1]+"},\n";
		}
		return out;
	}
	
	private String matrixToString(int[][] matrix) {
		String out = "";
		for (int x = 0; x < matrix.length; x++) {
			out += "{";
			for (int y = 0; y < matrix[0].length-1; y++) {
				out += matrix[x][y]+",	";
			}
			out += matrix[x][matrix[0].length-1]+"},\n";
		}
		return out;
	}

}
