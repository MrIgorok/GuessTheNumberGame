package ua.training.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

class GuessTheNumberModelTest {

    private GuessTheNumberModel model;
    private int minBound = -100;
    private int maxBound = 100;
    private int koef = 10;

    @BeforeEach
    void init() {
        model = new GuessTheNumberModel(minBound, maxBound);
    }

    @Test
    void createGuessingNumber() {
        int counts = model.getMaxBound() - model.getMinBound();
        TreeSet<Integer> values = new TreeSet<>();

        for (int i = 0; i < koef*counts; i++) {
            model.createGuessingNumber();
            values.add(model.getGuessesNumber());
        }

        for (int i = model.getMinBound(); i < model.getMaxBound(); i++) {
            assertTrue(values.contains(i));
        }
    }

    @Test
    void putEnteredNumberAndCheckVictoryTest1() {
        model.setGuessesNumber(50);
        assertTrue(model.putEnteredNumberAndCheckVictory(50));
    }

    @Test
    void putEnteredNumberAndCheckVictoryTest2() {
        model.setGuessesNumber(50);
        assertFalse(model.putEnteredNumberAndCheckVictory(70));
        assertEquals(model.getMaxBound(), 70);
        assertEquals(model.getMinBound(), -100);
    }

    @Test
    void checkMaxBound() {
        assertTrue(model.checkMaxBound(50));
        assertFalse(model.checkMaxBound(-120));
    }

    @Test
    void isInBounds() {
        assertTrue(model.isInBounds(30));
        assertTrue(model.isInBounds(100));
        assertTrue(model.isInBounds(-100));
        assertFalse(model.isInBounds(-150));
    }

    @Test
    void isRepeatedEntered() {
        model.setGuessesNumber(50);
        model.putEnteredNumberAndCheckVictory(80);
        assertTrue(model.isRepeatedEntered(80));
        assertFalse(model.isRepeatedEntered(79));
    }

    @Test
    void isLastInputValueGreaterThanGuess() {
        model.setGuessesNumber(50);
        model.putEnteredNumberAndCheckVictory(70);
        assertTrue(model.isLastInputValueGreaterThanGuess());
    }
}