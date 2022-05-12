import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;


public class Wordle {
	
	/**
	 * 
	 * This method reads data from our words.txt file
	 * @param fileName the name of our file
	 * @return returns the data in an array
	 * @throws FileNotFoundException
	 */
	public static String[] wordsFromFile(String fileName) throws FileNotFoundException {
		File file = new File(fileName);
		Scanner fileScanner = new Scanner(file);
		String[] textWords = new String[fileScanner.nextInt()];
		if (fileScanner.hasNext()) {
			for (int line = 0; line < textWords.length; line++) {
				textWords[line] = fileScanner.next();
			}
		}
		fileScanner.close();
		return textWords;
	}
			
	/**
	 * 
	 * Given an array of words from the parameter, pick one randomly
	 * @param randomWords an array of String with at least one word
	 * @return a string
	 */
	public static String pickRandomWord(String[] randomWords) {
		Random generator = new Random();
		int index = generator.nextInt(randomWords.length);
		String words = randomWords[index];
		return words;
	}
	
	/**
	 * 
	 * This method will return true if the String parameter matches one of the values
	 * in the array of String parameter and false otherwise
	 * @param someString a string
	 * @param arrOfString an array of String
	 * @return a boolean (true or false)
	 */
	public static boolean wordInArray(String someString, String[] arrOfString) {
		for (String words : arrOfString) {
			if (someString.equals(words)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * This method should ask a user for a 5 letter word and return the word
	 * It will repeatedly ask for a word until the provided word meets both of these requirements
	 * @param userInput an array of String
	 * @return a String
	 */
	public static String getUserGuess(String[] userInput) {
		System.out.println("Please enter a 5 letter word.");
		Scanner inputScanner = new Scanner(System.in);
		String enteredWord = inputScanner.next();
		boolean isWordValid = false;
		while (isWordValid == false) {
			if (enteredWord.length() != 5) {
				System.out.println("Invalid word length. Please enter a 5 letter word.");
				enteredWord = inputScanner.next();
			} else if (enteredWord.length() == 5 && !(wordInArray(enteredWord, userInput))) {
				System.out.println("Invalid word. Please enter a 5 letter word.");
				enteredWord = inputScanner.next();
			} else if (enteredWord.length() == 5 && wordInArray(enteredWord, userInput)) {
				isWordValid = true;
			}

		}
		return enteredWord;
	}
	
	/**
	 * 
	 * This will return true if the char matches one of the letters in the String and false otherwise
	 * @param letters a char
	 * @param chosenWord a String
	 * @return a boolean (true or false)
	 */
	public static boolean letterInWord(char letters, String chosenWord) {
		char[] lettersInWord = chosenWord.toCharArray();
		for (char character : lettersInWord) {
			if (letters == character) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * This method prints out the result of the guess
	 * @param userGuess a String that is the users guess
	 * @param secretWord a String that is the secret word
	 */
	public static void displayMatching(String userGuess, String secretWord) {
		for (int index = 0; index < 5; index++) {
			if (userGuess.charAt(index) == secretWord.charAt(index)) {
				System.out.print(Character.toUpperCase(userGuess.charAt(index)));
			} else if (letterInWord(userGuess.charAt(index), secretWord)){
				System.out.print(userGuess.charAt(index));
			} else {
				System.out.print("-");
			}
		}
		System.out.println();
	}

	public static void main(String[] args) {
		try {
			String secretWord = pickRandomWord(wordsFromFile("words.txt"));
			int userAttempts = 6;
			while (userAttempts > 0 ) {
				String userGuess = getUserGuess(wordsFromFile("words.txt"));
				displayMatching(userGuess, secretWord);
				if (userGuess.equals(secretWord)) {
					System.out.println("You Win!");
					System.exit(0);
				}
				userAttempts--;
				System.out.println("Remaining attempts left: " + userAttempts);
			}
			System.out.println("You lose! The secret words was " + secretWord);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
	}

}
