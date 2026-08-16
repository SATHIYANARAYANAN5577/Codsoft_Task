import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    private static final int MIN_RANGE = 1;
    private static final int MAX_RANGE = 100;
    private static final int MAX_ATTEMPTS = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int totalScore = 0;
        int roundsWon = 0;
        int roundNumber = 1;
        boolean playAgain = true;

        System.out.println("*****************************************");
        System.out.println(" WELCOME TO THE NUMBER GUESSING GAME");
        System.out.println("*****************************************");
        System.out.println("Guess a number between " + MIN_RANGE + " and " + MAX_RANGE + ".");
        System.out.println("You have " + MAX_ATTEMPTS + " attempts per round.\n");

        while (playAgain) {
            System.out.println("----------- ROUND " + roundNumber + " -----------");

            int targetNumber = MIN_RANGE + random.nextInt(MAX_RANGE - MIN_RANGE + 1);
            int attemptsUsed = 0;
            boolean guessedCorrectly = false;

            while (attemptsUsed < MAX_ATTEMPTS && !guessedCorrectly) {
                int remainingAttempts = MAX_ATTEMPTS - attemptsUsed;
                System.out.print("Attempt " + (attemptsUsed + 1) + "/" + MAX_ATTEMPTS
                        + " - Enter your guess: ");

                int guess;
                if (scanner.hasNextInt()) {
                    guess = scanner.nextInt();
                } else {
                    System.out.println("That's not a valid number. Please try again.");
                    scanner.next();
                    continue;
                }

                attemptsUsed++;

                if (guess < MIN_RANGE || guess > MAX_RANGE) {
                    System.out.println("Please guess a number within the range "
                            + MIN_RANGE + " to " + MAX_RANGE + ".");
                    attemptsUsed--; 
                    continue;
                }

                if (guess == targetNumber) {
                    guessedCorrectly = true;
                    int roundScore = calculateScore(attemptsUsed);
                    totalScore += roundScore;
                    roundsWon++;
                    System.out.println("Correct! You guessed it in " + attemptsUsed + " attempt(s).");
                    System.out.println("Points earned this round: " + roundScore);
                } else if (guess < targetNumber) {
                    System.out.println("Too low!");
                    remainingAttempts = MAX_ATTEMPTS - attemptsUsed;
                    if (remainingAttempts > 0) {
                        System.out.println("Attempts remaining: " + remainingAttempts);
                    }
                } else {
                    System.out.println("Too high!");
                    remainingAttempts = MAX_ATTEMPTS - attemptsUsed;
                    if (remainingAttempts > 0) {
                        System.out.println("Attempts remaining: " + remainingAttempts);
                    }
                }
            }

            if (!guessedCorrectly) {
                System.out.println("Out of attempts! The number was: " + targetNumber);
            }

            System.out.println("\nCurrent total score: " + totalScore);
            System.out.println("Rounds won so far: " + roundsWon + "/" + roundNumber);

            System.out.print("\nDo you want to play another round? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();
            playAgain = response.equals("yes") || response.equals("y");
            roundNumber++;
            System.out.println();
        }

        System.out.println("*****************************************");
        System.out.println(" GAME OVER - FINAL RESULTS");
        System.out.println("*****************************************");
        System.out.println("Total rounds played: " + (roundNumber - 1));
        System.out.println("Rounds won: " + roundsWon);
        System.out.println("Final score: " + totalScore);
        System.out.println("Thanks for playing!");

        scanner.close();
    }

    private static int calculateScore(int attemptsUsed) {
        int score = 100 - (attemptsUsed - 1) * 10;
        return Math.max(score, 10);
    }
}