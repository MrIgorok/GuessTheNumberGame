package ua.training.game;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Class manages the game data.
 *
 * @version 1.0 04 Apr 2019
 * @author  Igor Klapantjuk
 */
public class GuessTheNumberModel {
    /**
     * Standard minimal value
     */
    final int RAND_MIN = 0;

    /**
     * Standard maximum value
     */
    final int RAND_MAX = 100;

    /**
     * Minimal value that uses in the game
     */
    private int minBound = Integer.MIN_VALUE;

    /**
     * Maximum value that uses in the game
     */
    private int maxBound = Integer.MAX_VALUE;

    /**
     * Contains user's previously entered numbers.
     * @see TreeSet
     */
    private TreeSet<Integer> previouslyEnteredNumbers = new TreeSet<>();

    /**
     * Last user's entered number
     */
    private int lastEnteredNumber = -1;

    /**
     * The value that need to guess
     */
    private int guessesNumber;

    /**
     * Creates model with default game data
     */
    public GuessTheNumberModel() {

    }

    /**
     * Creates model with specific minimal and
     * maximum values. Minimal value must be less
     * than maximum value.
     * @param minBound minimal game value.
     * @param maxBound maximum game value.
     */
    public GuessTheNumberModel(int minBound, int maxBound) {
        if (minBound >= maxBound) {
            throw new IllegalArgumentException("Max bound value can't" +
                    " be less than min bound");
        }
        this.minBound = minBound;
        this.maxBound = maxBound;
    }

    /**
     * Creates guessing number.
     */
    void createGuessingNumber() {
        guessesNumber = rand(minBound, maxBound);
    }

    /**
     * Put new entered number and check for victories.
     * @param enteredNumber last user entered number.
     * @return check result.
     */
    boolean putEnteredNumberAndCheckVictory(int enteredNumber) {
        previouslyEnteredNumbers.add(enteredNumber);
        lastEnteredNumber = enteredNumber;
        createNewBounds();
        return checkVictory();
    }

    /**
     * Creates new bounds considering new inputted value
     */
    private void createNewBounds() {
        if (lastEnteredNumber > guessesNumber) {
            maxBound = lastEnteredNumber;
        } else if (lastEnteredNumber < guessesNumber) {
            minBound = lastEnteredNumber;
        }
    }

    /**
     * Checks victory.
     * @return check result.
     */
    private boolean checkVictory() {
        return lastEnteredNumber == guessesNumber;
    }

    /**
     * Checks max bound.
     */
    boolean checkMaxBound(int value) {
        return value >= minBound;
    }

    /**
     * Returns random value
     * in interval [min, max].
     * @param min minimal interval value.
     * @param max max interval value.
     * @return random value
     */
    private int rand(int min, int max) {
        Random r = new Random();

        return r.nextInt((max - min) + 1) + min;
    }

    /**
     * Checks is value in bounds.
     * @param value the value that will be checked.
     * @return true if value is in bounds.
     */
    boolean isInBounds(int value) {
        return (value >= minBound) && (value <= maxBound);
    }

    /**
     * Checks is the value entered repeated.
     * @param value parameter that will be checked.
     * @return true if the value was entered before.
     */
    boolean isRepeatedEntered(int value) {
        return previouslyEnteredNumbers.contains(value);
    }

    /**
     * Checks is last input value grater than secret value.
     * @return result.
     */
    boolean isLastInputValueGreaterThanGuess() {
        return lastEnteredNumber > guessesNumber;
    }

    /**
     * Sets max bound value.
     * Max bound can't be lower than current min bound.
     * @param maxBound max bound that will be set.
     */
    void setMaxBound(int maxBound) {
        if (this.minBound > maxBound) throw new IllegalArgumentException(
                "Max bound value can't be less than min bound");

        this.maxBound = maxBound;
    }

    /**
     * Gets max bound value.
     * @return max bound value.
     */
    int getMaxBound() {
        return this.maxBound;
    }

    /**
     * Sets min bound value.
     * Min bound can't be greater than current max bound.
     * @param minBound min bound that will be set.
     */
    void setMinBound(int minBound) {
        if (minBound > this.maxBound) throw new IllegalArgumentException(
                "Min bound value can't be greater than min bound");

        this.minBound = minBound;
    }

    /**
     * Gets min bound value.
     * @return min bound value.
     */
    int getMinBound() {
        return this.minBound;
    }

    /**
     * Gets Sorted set of previous entered values.
     * @return set of previous entered values.
     */
    SortedSet<Integer> getPreviouslyEnteredNumbers() {
        return previouslyEnteredNumbers;
    }

    /**
     * Sets value that need to guess.
     * The value must be in
     * the interval [maxValue, minValue].
     * Otherwise the exception will be thrown.
     * @param guessesNumber the number that will be guessed.
     */
    void setGuessesNumber(int guessesNumber) {
        this.guessesNumber = guessesNumber;
    }

    /**
     * Gets value that need to guess.
     * @return guesses number.
     */
    int getGuessesNumber() {
        return guessesNumber;
    }

    /**
     * Gets last entered number.
     * @return last entered number.
     */
    int getLastEnteredNumber() {
        return lastEnteredNumber;
    }

    /**
     * Gets tries counts.
     * @return tries count.
     */
    int getTries() {
        return previouslyEnteredNumbers.size();
    }
}