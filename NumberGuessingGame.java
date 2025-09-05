import java.util.Scanner;
import java.util.Random;

public class NumberGuessingGame {
    private static final int MAX_ATTEMPTS = 7;
    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND = 100;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int totalScore = 0;
        int roundsPlayed = 0;

        while (true) {
            int target = random.nextInt(UPPER_BOUND - LOWER_BOUND + 1) + LOWER_BOUND;
            int attemptsLeft = MAX_ATTEMPTS;
            boolean guessedCorrectly = false;

            System.out.println("\nRound " + (roundsPlayed + 1) + " begins! Guess the number between " + LOWER_BOUND + " and " + UPPER_BOUND + ".");

            while (attemptsLeft > 0) {
                System.out.print("Enter your guess (" + attemptsLeft + " attempts left): ");
                int guess;

                if (scanner.hasNextInt()) {
                    guess = scanner.nextInt();
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    scanner.next(); // clear invalid input
                    continue;
                }

                if (guess < LOWER_BOUND || guess > UPPER_BOUND) {
                    System.out.println("Please enter a number between " + LOWER_BOUND + " and " + UPPER_BOUND + ".");
                    continue;
                }

                if (guess == target) {
                    System.out.println("Correct! You've guessed the number.");
                    totalScore += attemptsLeft * 10;
                    guessedCorrectly = true;
                    break;
                } else if (guess < target) {
                    System.out.println("Too low.");
                } else {
                    System.out.println("Too high.");
                }

                attemptsLeft--;
            }

            if (!guessedCorrectly) {
                System.out.println("Out of attempts! The number was: " + target);
            }

            roundsPlayed++;

            System.out.print("Do you want to play another round? (yes/no): ");
            String response = scanner.next();
            if (!response.equalsIgnoreCase("yes")) {
                break;
            }
        }

        System.out.println("\nGame Over.");
        System.out.println("Rounds Played: " + roundsPlayed);
        System.out.println("Total Score: " + totalScore);

        scanner.close();
    }
}
