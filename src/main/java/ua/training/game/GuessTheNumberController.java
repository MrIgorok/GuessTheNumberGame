package ua.training.game;

import static ua.training.game.GuessTheNumberView.AlignPolicy.CENTER;
import static ua.training.game.GuessTheNumberView.AlignPolicy.LEFT;
import static ua.training.game.GuessTheNumberView.AlignPolicy.RIGHT;
import static ua.training.game.GuessTheNumberView.DialogValue.*;

/**
 * Class controls the game and dialog
 * with the user.
 *
 * @version 1.0 04 Apr 2019
 * @author  Igor Klapatnjuk
 */
public class GuessTheNumberController {

    /**
     * Integer number regex.
     */
    private final String INTEGER_NUMBER_REGEX = "^[-+]?\\d*$";

    /**
     * Represent max.
     */
    private final String MAX_STRING = "MAX";

    /**
     * Represent min.
     */
    private final String MIN_STRING = "MIN";

    /**
     * Provides game Model.
     * @see GuessTheNumberModel
     */
    private GuessTheNumberModel gameModel;
    /**
     * Provides game view.
     * @see GuessTheNumberView
     */
    private GuessTheNumberView gameView;

    /**
     * Creates game controller.
     * @param gameModel object provides game data.
     * @param gameView object provides game view.
     */
    public GuessTheNumberController(GuessTheNumberModel gameModel,
                                    GuessTheNumberView gameView) {
        this.gameModel = gameModel;
        this.gameView = gameView;
    }

    /**
     * Executing "Guess the number game".
     */
    public void execute() {
        showGameInfoAndWaitForGameStart();
        inputValidBounds();
        performGame();
    }

    /**
     * Shows game info and
     * waiting user press enter key.
     */
    private void showGameInfoAndWaitForGameStart() {
        gameView.printMessageLn(gameView.getDialogValue(
                GUESS_THE_NUMBER_GAME_NAME),
                CENTER);
        gameView.printMessage(gameView.getDialogValue(
                WELCOME_MESSAGE),
                CENTER);

        gameView.waitKeyPress();
    }

    /**
     * Dialog with user to input valid bounds values.
     */
    private void inputValidBounds() {
        inputMinBound();
        inputMaxBoundAndCheckBounds();
    }

    /**
     * Perform the game.
     */
    private void performGame() {
        gameModel.createGuessingNumber();

        gameView.printMessageLn(gameView.getDialogValue(START_GAME), CENTER);

        guessing();

        gameView.printMessageLn(
                gameView.getDialogValue(CONGRATULATION_MESSAGE, Integer.toString(gameModel.getGuessesNumber())),
                CENTER);
        gameView.printMessageLn(
                gameView.getDialogValue(RESULT, Integer.toString(gameModel.getTries())),
                LEFT);
    }

    /**
     * Perform guessing the number.
     */
    private void guessing() {
        showGameInformation("None");
        while (!gameModel.putEnteredNumberAndCheckVictory(inputNextGuessValue())) {
            showGameInformation(createLastMoveResult());
        }
    }

    /**
     * Input min bound value
     */
    private void inputMinBound() {
        gameModel.setMinBound(
                inputValidValue(gameView.getDialogValue(INPUT_BOUNDS_SUGGESTION, MIN_STRING),
                        gameView.getDialogValue(WRONG_INPUT), LEFT, CENTER));
    }

    /**
     * Input max bound value and
     * check that it is greater than min bound.
     */
    private void inputMaxBoundAndCheckBounds() {
        int max_bound = Integer.MIN_VALUE;

        do {
            max_bound = inputValidValue(gameView.getDialogValue(INPUT_BOUNDS_SUGGESTION, MAX_STRING),
                    gameView.getDialogValue(WRONG_INPUT), LEFT, CENTER);

            if (gameModel.checkMaxBound(max_bound)) {
                break;
            }

            gameView.printMessageLn(
                    gameView.getDialogValue(WRONG_BOUNDS_VALUES,
                            Integer.toString(gameModel.getMinBound())));
        } while (true);

        gameModel.setMaxBound(max_bound);
    }

    /**
     * Input next value and validate it.
     * @return true if the number was guessed.
     */
    private int inputNextGuessValue() {
        int value;

        do {
            value = inputValidValue(gameView.getDialogValue(INPUT_VALUE_SUGGESTION),
                    gameView.getDialogValue(WRONG_INPUT), LEFT, CENTER);
        } while (!validateInputtedValue(value));

        return value;
    }

    /**
     * Shows game information.
     * @param lastMoveResult represent last user move.
     */
    private void showGameInformation(String lastMoveResult) {
        gameView.printMessageLn(
                gameView.getDialogValue(
                        GAME_INFORMATION,
                        gameModel.getPreviouslyEnteredNumbers().toString(),
                        Integer.toString(gameModel.getMinBound()),
                        Integer.toString(gameModel.getMaxBound()),
                        lastMoveResult),
                RIGHT);
    }

    /**
     * Validates inputted value.
     * @param value inputted value.
     * @return is value valid.
     */
    private boolean validateInputtedValue(int value) {
        if (!gameModel.isInBounds(value)) {
            gameView.printMessageLn(
                    gameView.getDialogValue(WRONG_INPUT),
                    CENTER);
            gameView.printMessageLn(
                    gameView.getDialogValue(OUT_OF_BOUNDS_MESSAGE,
                            Integer.toString(gameModel.getMinBound()),
                            Integer.toString(gameModel.getMaxBound())),
                    CENTER);
            return false;
        }

        if (gameModel.isRepeatedEntered(value)) {
            gameView.printMessageLn(
                    gameView.getDialogValue(WRONG_INPUT), CENTER);
            gameView.printMessageLn(
                    gameView.getDialogValue(REPEATED_INPUT_MESSAGE), CENTER);
            return false;
        }

        return true;
    }

    /**
     * Returns valid inputted integer value.
     * @param inputSuggestion string that represent input suggestion.
     * @param wrongInput string that represent wrong input.
     * @param inputSuggestionPolicy input suggestion align policy.
     * @param wrongInputPolicy wrong input align policy.
     * @return valid integer value.
     */
    private int inputValidValue(String inputSuggestion, String wrongInput,
                                GuessTheNumberView.AlignPolicy inputSuggestionPolicy,
                                GuessTheNumberView.AlignPolicy wrongInputPolicy) {
        int result = 0;

        while (true) {
            String resultStr;
            gameView.printMessage(inputSuggestion, inputSuggestionPolicy);
            resultStr = gameView.readString();

            if (resultStr.matches(INTEGER_NUMBER_REGEX)) {
                result = Integer.parseInt(resultStr);
                break;
            }

            gameView.printMessageLn(wrongInput, wrongInputPolicy);
        }

        return result;
    }

    /**
     * Create last move result string.
     * @return string.
     */
    private String createLastMoveResult() {
        return Integer.toString(gameModel.getLastEnteredNumber())
                + (gameModel.isLastInputValueGreaterThanGuess()
                ? gameView.getDialogValue(VALUE_GREATER)
                : gameView.getDialogValue(VALUE_LOWER));
    }
}
