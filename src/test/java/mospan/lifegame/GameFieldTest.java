package mospan.lifegame;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameFieldTest {
    private static GameField gameField;
    private static final int FIELD_WIDTH = 10;
    private static final int FIELD_HEIGHT = 10;

    private void cleanupGameField () {
        for (int colIndex = 0; colIndex < FIELD_WIDTH; colIndex++) {
            for (int rowIndex = 0; rowIndex < FIELD_HEIGHT; rowIndex++) {
                gameField.getCells().get(colIndex).get(rowIndex).set(LifeState.DEAD);
            }
        }
    }

    @BeforeClass
    public static void beforeClass() {
        gameField = new GameField(FIELD_WIDTH, FIELD_HEIGHT);
    }

    @Before
    public void before() {
        cleanupGameField();
    }

    @After
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
        Assert.assertEquals(gameField.computeNextLifeState(1, 0), LifeState.DEAD);
        Assert.assertEquals(gameField.computeNextLifeState(1, 1), LifeState.ALIVE);
        Assert.assertEquals(gameField.computeNextLifeState(1, 2), LifeState.DEAD);

        Assert.assertEquals(gameField.computeNextLifeState(0, 1), LifeState.ALIVE);
        Assert.assertEquals(gameField.computeNextLifeState(2, 1), LifeState.ALIVE);
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
        Assert.assertEquals(gameField.computeNextLifeState(0, 0), LifeState.ALIVE);
        Assert.assertEquals(gameField.computeNextLifeState(0, 1), LifeState.ALIVE);
        Assert.assertEquals(gameField.computeNextLifeState(1, 0), LifeState.ALIVE);
        Assert.assertEquals(gameField.computeNextLifeState(1, 1), LifeState.ALIVE);

    }
}
