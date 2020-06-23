import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This program implements the word guessing game called Hangman.
 *
 * Some of this code is originally from a CS instructor (@author CS302, 2009,2012 by Jim Skrentny) used as course work, where students are asked to complete the program in order to make it work.
 *
 * The original code can be found here: "http://pages.cs.wisc.edu/~cs302/index_201509.cgi?r=labs&labNumber=4"
 *
 * This program has been not only completed by me (CIMAN01) per original instructions, but I have further modified it to make it simpler (i.e. splitting up code across several classes),
 * more efficient (i.e using enhanced for loops), and more robust (handling special cases for better overall user experience).
 *
 */

public class Hangman {

    /** instance variables **/

    // a String array of words (a list of words)
    private static String[] words =
            {"geography", "cat", "yesterday", "java", "truck", "opportunity", "fish",
                    "token", "transportation", "bottom", "apple", "cake", "remote",
                    "pocket", "terminology", "arm", "cranberry", "tool", "caterpillar",
                    "spoon", "watermelon", "laptop", "toe", "toad", "fundamental",
                    "capitol", "garbage", "anticipate"};

    // the selected word from words
    private final String secretWord;
    // ArrayList characters or letters used for guessing
    private final ArrayList<Character> correctLetters;   // correct guesses
    private final ArrayList<Character> incorrectLetters; // incorrect guesses
    // scanner
    private final Scanner consoleInput = new Scanner(System.in); // user input


    /** constructor - constructs a Hangman game. **/

    public Hangman() {
        // Randomly choose a word from the list of words
        Random randWord = new Random();
        // create random index
        int index = randWord.nextInt(Hangman.words.length);
        // use index to choose a word from list
        this.secretWord = Hangman.words[index];
        // create ArrayList object
        this.correctLetters = new ArrayList<>();
        // add underscores for guesses
        for (int i = 0; i < this.secretWord.length(); i++) {
            this.correctLetters.add('_');
        }
        // create ArrayList object
        this.incorrectLetters = new ArrayList<>();
    }

    /** getters and setters **/

    public ArrayList<Character> getCorrectLetters() {
        return correctLetters;
    }

    public ArrayList<Character> getIncorrectLetters() {
        return incorrectLetters;
    }


    /** instance methods **/

    // play one game of Hangman until the user wins (guesses all of the letters in the secret word) or loses (guesses 7 incorrect letters)
    public void playGame() {
        // while the game is still played
        while (!gameOver()) {
            // print a hangman picture
            Print.printHangman(getIncorrectLetters().size()); // int badGuesses = getIncorrectLetters().size();
            // print the correct guesses in the secret word
            for (Character correctLetter : this.correctLetters) {
                System.out.print(correctLetter + " ");
            }
            // print the incorrect letters that have been guessed
            System.out.print("\nWrong letters: ");
            //
            for (Character incorrectLetter : this.incorrectLetters) {
                System.out.print(incorrectLetter + " ");
            }
            // prompt and read the next guess
            System.out.print("\nEnter a letter as your guess: "); // upper-cases OK (see handleGuess method)
            //
            String guess = consoleInput.nextLine();
            // process the next guess
            handleGuess(guess); // Changed the code here to deal with strings instead of 'guess.charAt(0)'
        }
        // let the user know what the secret word was when
        System.out.println("The secret word was: " + secretWord);
        // tell user the outcome of the game (a win or a loss)
        if (gameWon()) {
            System.out.println("Congratulations, you won!");
        }
        else {
            System.out.println("Sorry, you lost.");
            // print a hangman picture
            Print.printHangman(getIncorrectLetters().size()); // int badGuesses = getIncorrectLetters().size();
        }
    }

    /** helper methods **/

    //////////////////////////////////////////////////////////////////////////////
    // Method has been modified by CIMAN01 to exclude special characters, numbers
    // and repeated characters as valid guesses as well as taking in a string
    // as opposed to a char in case user decides to enter multiple characters
    //////////////////////////////////////////////////////////////////////////////

    /**
     This method will be called each time the user guesses a letter. The job of
     this method is to determine whether the guessed letter (parameter ch) is in
     the secret word. If it is, the array list of correct guesses should be updated
     so that the underscores are replaced with the guessed letter at each position
     where the letter occurs in the secret word. If the guessed letter is not in
     the secret word, then the array of incorrect letters should be updated to
     include the guessed letter. This method should also tell the user whether
     the guess was correct or wrong.
     **/
    private void handleGuess(String str) {
        // set initial boolean conditions
        boolean isNumber = false;
        boolean isSpecialChar = false;
        boolean isRepeat = false;
        boolean keyFound = false;
        // as long as there are no empty or blank spaces we proceed
        if (!str.isEmpty()) {
            // only grab first character in the string
            char key = str.charAt(0);
            // change to lowercase letter
            key = Character.toLowerCase(key);
            // flag for numbers (letters only)
            if (Character.isDigit(key))
                isNumber = true;
            // flag for special characters (alphabetical letters only)
            if (!Character.isLetter(key))
                isSpecialChar = true;
            // flag repeated characters (for right guesses)
            if (this.correctLetters.contains(key))
                isRepeat = true;
            // flag repeated characters (for wrong guesses)
            if (this.incorrectLetters.contains(key))
                isRepeat = true;
            // go through the secret word character by character and if a match is found, update the array list
            for (int i = 0; i < this.secretWord.length(); i++) {
                // if a match is found
                if (this.secretWord.charAt(i) == key) {
                    // the guess is correct
                    keyFound = true;
                    // update the user's guess
                    this.correctLetters.set(i, key);
                }
            }
            // let the user know if guess is correct, repeated, invalid, or wrong; handle repeated guesses
            if (keyFound) {
                if (isRepeat) {
                    System.out.println("\nYou have already guessed the letter: '" + key + "'");
                    System.out.println("Try something else\n");
                }
                else {
                    System.out.println("\nYou have chosen the letter: '" + key + "'");
                    System.out.println("\nYour guess was correct\n");
                }
            }
            // handle numbers
            else if (isNumber)
                System.out.println("\nNumbers are not allowed! Try again\n");
                // handle special characters
            else if (isSpecialChar)
                System.out.println("\nSpecial character are not allowed! Try again\n");
                // otherwise keep track of wrong guesses
            else {
                // handle wrong repeated guesses
                if (isRepeat) {
                    System.out.println("\nYou have already guessed the letter:  '" + key + "'");
                    System.out.println("Try something else\n");
                }
                else {
                    this.incorrectLetters.add(key);
                    System.out.println("\nYou have chosen the letter: '" + key + "'");
                    System.out.println("\nYour guess was wrong\n");
                }
            }
        }
        else {
            // input is empty
            System.out.println("\nEmpty spaces are not allowed! Try again\n");
        }
    }

    /*
    this method should return true if, and only if, the user has won the game (i.e., has guessed all of the letters in the secret word). One way to tell whether the
    user has won is to check whether the array list of correct guesses contains any underscores (if it doesn't, the user has guessed all of the letters).
    */
    private boolean gameWon() {
        return !getCorrectLetters().contains('_');
    }

    // this method should return true if, and only if, the user has lost the game (i.e., has made 7 wrong guesses).
    private boolean gameLost() {
        int wrongGuesses = getIncorrectLetters().size();
        return wrongGuesses >= 7;
    }

    /*
    this method should return true if, and only if, the user has either won or lost the game:
    • the user has correctly guessed all the letters in the secret word (a win), or
    • the user has made seven wrong guesses (a loss).
    */
    private boolean gameOver() {
        return gameWon() || gameLost();
    }

}

