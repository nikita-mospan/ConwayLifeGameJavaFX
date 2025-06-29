package mospan.lifegame;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameFieldTest {
    private static GameField gameField;
    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 10;

    private void cleanupGameField() {
        for (int colIndex = 0; colIndex < FIELD_WIDTH; colIndex++) {
            for (int rowIndex = 0; rowIndex < FIELD_HEIGHT; rowIndex++) {
                gameField.getCells().get(colIndex).get(rowIndex).set(LifeState.DEAD);
            }
        }
    }

    @BeforeAll
    public static void beforeClass() {
        gameField = new GameField(FIELD_WIDTH, FIELD_HEIGHT);
    }

    @BeforeEach
    public void before() {
        cleanupGameField();
    }

    @AfterEach
    public void after() {
        cleanupGameField();
    }

    @Test
    //  1 - means cell is alive
    //  0 - means cell is dead
    //  010         000
    //  010   -->   111
    //  010         000
    public void computeNextLifeStateSimpleOscillator() {
        //init
        gameField.getCells().get(1).get(0).set(LifeState.ALIVE);
        gameField.getCells().get(1).get(1).set(LifeState.ALIVE);
        gameField.getCells().get(1).get(2).set(LifeState.ALIVE);

        //verify
        assertEquals(LifeState.DEAD, gameField.computeNextLifeState(1, 0));
        assertEquals(LifeState.ALIVE, gameField.computeNextLifeState(1, 1));
        assertEquals(LifeState.DEAD, gameField.computeNextLifeState(1, 2));

        assertEquals(LifeState.ALIVE, gameField.computeNextLifeState(0, 1));
        assertEquals(LifeState.ALIVE, gameField.computeNextLifeState(2, 1));
    }

    @Test
    //  11   -->   11
    //  11   -->   11
    public void computeNextLifeStateSquare() {
        //init
        gameField.getCells().get(0).get(0).set(LifeState.ALIVE);
        gameField.getCells().get(0).get(1).set(LifeState.ALIVE);
        gameField.getCells().get(1).get(0).set(LifeState.ALIVE);
        gameField.getCells().get(1).get(1).set(LifeState.ALIVE);

        //verify
        assertEquals(LifeState.ALIVE, gameField.computeNextLifeState(0, 0));
        assertEquals(LifeState.ALIVE, gameField.computeNextLifeState(0, 1));
        assertEquals(LifeState.ALIVE, gameField.computeNextLifeState(1, 0));
        assertEquals(LifeState.ALIVE, gameField.computeNextLifeState(1, 1));
    }
}
