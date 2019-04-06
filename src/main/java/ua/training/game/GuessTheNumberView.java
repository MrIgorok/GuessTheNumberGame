package ua.training.game;

import ua.training.game.utils.ResourceManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Provides user interface.
 *
 * @version 1.0 04 Apr 2019
 * @author  Igor Klapatnjuk
 */
public class GuessTheNumberView {

    /**
     * Values that require for user interface.
     */
    enum DialogValue {
        /**
         * Game name.
         */
        GUESS_THE_NUMBER_GAME_NAME,

        /**
         * The welcome message that user will be saw.
         */
        WELCOME_MESSAGE,

        /**
         * Suggest the user to enter min or max value.
         */
        INPUT_BOUNDS_SUGGESTION,

        /**
         * Suggest the user to enter next value.
         */
        INPUT_VALUE_SUGGESTION,

        /**
         * Start game string.
         */
        START_GAME,

        /**
         * Current game information.
         */
        GAME_INFORMATION,

        /**
         * Report the user about wrong input.
         */
        WRONG_INPUT,

        /**
         * Report the user about the wrong max value input.
         */
        WRONG_BOUNDS_VALUES,

        /**
         * Report the user that input value is out of bounds.
         */
        OUT_OF_BOUNDS_MESSAGE,

        /**
         * Report he user that the input value was entered before.
         */
        REPEATED_INPUT_MESSAGE,

        /**
         * Notifies the user that he guessed the number.
         */
        CONGRATULATION_MESSAGE,

        /**
         * Result string.
         */
        RESULT,

        /**
         * Value is greater than the secret value
         */
        VALUE_GREATER,

        /**
         * Value is lower than a secret value
         */
        VALUE_LOWER
    }

    /**
     * Policy of text align.
     */
    enum AlignPolicy {
        /**
         * Left align.
         */
        LEFT,
        /**
         * Center align.
         */
        CENTER,
        /**
         * Right align.
         */
        RIGHT
    }

    /**
     * Reads user input
     */
    private Scanner readInput;

    /**
     * Outputs game information
     */
    private PrintStream outputGameInformation;

    /**
     * Provides user dialog resources
     */
    private ResourceManager dialogResource;

    /**
     * Input stream that is used in waitKeyPress.
     */
    private InputStream inStream;

    /**
     * View length.
     */
    private int viewSize;

    /**
     * Creates guess the number view.
     * @param inStream input stream for user input.
     * @param outStream output stream for game information.
     * @param resources game message resource.
     */
    public GuessTheNumberView(InputStream inStream, OutputStream outStream,
                              ResourceManager resources, int viewSize) {
        this.inStream = inStream;
        this.readInput = new Scanner(this.inStream);
        this.outputGameInformation = new PrintStream(outStream);
        this.dialogResource = resources;
        this.viewSize = viewSize;
    }

    /**
     * Prints the message and carry line.
     * @param message the message that will be printed.
     */
    void printMessageLn(String message) {
        outputGameInformation.println(message);
    }

    /**
     * Prints the message and align text and carry line.
     * @param message the message that will be printed.
     * @param policy align policy that will be used.
     * @see AlignPolicy
     */
    void printMessageLn(String message, AlignPolicy policy) {
        createAlign(policy, message.length());

        outputGameInformation.println(message);
    }

    /**
     * Prints the message and align text.
     * @param message the message that will be printed.
     * @param policy align policy that will be used.
     * @see AlignPolicy
     */
    void printMessage(String message, AlignPolicy policy) {
        createAlign(policy, message.length());

        outputGameInformation.print(message);
    }

    /**
     * Reads the string.
     * @return the read string.
     */
    String readString() {
        return readInput.next();
    }

    /**
     * Reads the line.
     */
    void waitKeyPress() {
        try {
            inStream.read();
            inStream.skip(inStream.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets standard dialog value.
     * @param value type of value that will be returned.
     * @param locale locale of the result string.
     * @param strings strings that will be used in format.
     * @return value associated with {@link DialogValue}.
     */
    String getDialogValue(DialogValue value, Locale locale, String ... strings) {
        String resourceValue = dialogResource.getString(value.name());

        dialogResource.changeResource(locale);

        if (strings.length > 0) {
            resourceValue = String.format(resourceValue, strings);
        }

        return resourceValue;
    }

    /**
     * Gets standard dialog value.
     * @param value type of value that will be returned.
     * @param strings strings that will be used in format.
     * @return value associated with {@link DialogValue}.
     */
    String getDialogValue(DialogValue value, String ... strings) {
        String resourceValue = dialogResource.getString(value.name());

        if (strings.length > 0) {
            resourceValue = String.format(resourceValue, strings);
        }

        return resourceValue;
    }

    public int getViewSize() {
        return viewSize;
    }

    public void setViewSize(int viewSize) {
        this.viewSize = viewSize;
    }

    /**
     * Creates align.
     */
    private void createAlign(AlignPolicy policy, int messageLength) {
        char [] fillArr;

        switch (policy) {
            case LEFT:
                break;
            case CENTER:
                fillArr = new char[(viewSize - messageLength)/2];
                Arrays.fill(fillArr, ' ');
                outputGameInformation.print(fillArr);
                break;
            case RIGHT:
                fillArr = new char[viewSize - messageLength];
                Arrays.fill(fillArr, ' ');
                outputGameInformation.print(fillArr);
                break;
            default:
                throw new IllegalArgumentException("Wrong align policy.");
        }
    }
}
