/**
 * printHangman
 *
 * Print the Hangman that corresponds to the given number of
 * wrong guesses so far.
 *
 */

public class Print {
    // a method that in a number of bad guesses and prints a Hangman
    public static void printHangman(int badGuesses) {
        // number of lines for hanging pole
        int poleLines = 6;

        System.out.println("  ____");
        System.out.println("  |  |");

        if (badGuesses == 7) {
            System.out.println("  |  |");
            System.out.println("  |  |");
        }

        if (badGuesses > 0) {
            System.out.println("  |  O");
            poleLines = 5;
        }

        if (badGuesses > 1) {
            poleLines = 4;
            if (badGuesses == 2) {
                System.out.println("  |  |");
            }
            else if (badGuesses == 3) {
                System.out.println("  | \\|");
            }
            else if (badGuesses >= 4) {
                System.out.println("  | \\|/");
            }
        }

        if (badGuesses > 4) {
            poleLines = 3;
            if (badGuesses == 5) {
                System.out.println("  | /");
            }
            else if (badGuesses >= 6) {
                System.out.println("  | / \\");
            }
        }

        if (badGuesses == 7) {
            poleLines = 1;
        }

        for (int k = 0; k < poleLines; k++) {
            System.out.println("  |");
        }

        System.out.println("__|__");
        System.out.println();
    }

}
