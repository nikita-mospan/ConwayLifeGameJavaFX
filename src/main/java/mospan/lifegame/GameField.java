package mospan.lifegame;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameField {

    private final List<List<ObjectProperty<LifeState>>> cells;

    private final int fieldWidth;

    private final int fieldHeight;

    List<List<ObjectProperty<LifeState>>> getCells() {
        return cells;
    }

    private void setCellState(int columnIndex, int rowIndex, LifeState newValue) {
        cells.get(columnIndex).get(rowIndex).set(newValue);
    }

    void toggleLifeState(final int columnIndex, final int rowIndex) {
        if(cells.get(columnIndex).get(rowIndex).get() == LifeState.ALIVE) {
            cells.get(columnIndex).get(rowIndex).set(LifeState.DEAD);
        } else {
            cells.get(columnIndex).get(rowIndex).set(LifeState.ALIVE);
        }
    }

    int getFieldWidth() {
        return fieldWidth;
    }

    int getFieldHeight() {
        return fieldHeight;
    }

    GameField(int fieldWidth, int fieldHeight) {
        this.fieldWidth = fieldWidth;
        this.fieldHeight = fieldHeight;
        cells = new ArrayList<>();
        for(int columnIndex = 0; columnIndex < fieldWidth; columnIndex++) {
            List<ObjectProperty<LifeState>> singleRow = new ArrayList<>();
            for(int rowIndex = 0; rowIndex < fieldHeight; rowIndex++) {
                singleRow.add(new SimpleObjectProperty<>(LifeState.DEAD));
            }
            cells.add(singleRow);
        }
    }

    void resetGameField() {
        for (int columnIndex = 0; columnIndex < fieldWidth; columnIndex++) {
            for (int rowIndex = 0; rowIndex < fieldHeight; rowIndex++) {
                setCellState(columnIndex, rowIndex, LifeState.DEAD);
            }
        }
    }

    void setCellStates(LifeState[][] lifeStates) {
        for (int columnIndex = 0; columnIndex < fieldWidth; columnIndex++) {
            for (int rowIndex = 0; rowIndex < fieldHeight; rowIndex++) {
                setCellState(columnIndex, rowIndex, lifeStates[columnIndex][rowIndex]);
            }
        }
    }

    private static boolean isCellAlive(LifeState cell) {return cell == LifeState.ALIVE; }

    private int getCountOfAliveNeighbours(int columnIndex, int rowIndex) {

        final int leftIndex = (columnIndex == 0) ? fieldWidth - 1 : columnIndex - 1;
        final int rightIndex = (columnIndex == fieldWidth - 1) ? 0 : columnIndex + 1;
        final int topIndex = (rowIndex == 0) ? fieldHeight - 1 : rowIndex - 1;
        final int bottomIndex = (rowIndex == fieldHeight - 1) ? 0 : rowIndex + 1;

        final List<ObjectProperty<LifeState>> leftColumn = cells.get(leftIndex);
        final List<ObjectProperty<LifeState>> rightColumn = cells.get(rightIndex);

        final LifeState leftNeighbour = leftColumn.get(rowIndex).get();
        final LifeState rightNeighbour = rightColumn.get(rowIndex).get();
        final LifeState topNeighbour = cells.get(columnIndex).get(topIndex).get();
        final LifeState bottomNeighbour = cells.get(columnIndex).get(bottomIndex).get();
        final LifeState leftTopNeighbour = leftColumn.get(topIndex).get();
        final LifeState rightTopNeighbour = rightColumn.get(topIndex).get();
        final LifeState leftBottomNeighbour = leftColumn.get(bottomIndex).get();
        final LifeState rightBottomNeighbour = rightColumn.get(bottomIndex).get();

        List<LifeState> neighbours = Arrays.asList(leftNeighbour, rightNeighbour, topNeighbour, bottomNeighbour, leftTopNeighbour,
                rightTopNeighbour, leftBottomNeighbour, rightBottomNeighbour);

        return (int) neighbours.stream().filter(GameField::isCellAlive).count();

    }

    LifeState computeNextLifeState(int columnIndex, int rowIndex) {
        final int countOfAliveNeighbours = getCountOfAliveNeighbours(columnIndex, rowIndex);
        final LifeState curLifeState = cells.get(columnIndex).get(rowIndex).get();
        LifeState nextLifeState;

        if (curLifeState == LifeState.DEAD && countOfAliveNeighbours == 3) {
            nextLifeState = LifeState.ALIVE;
        } else if (curLifeState == LifeState.ALIVE && ((countOfAliveNeighbours == 3) || (countOfAliveNeighbours == 2))) {
            nextLifeState = LifeState.ALIVE;
        } else {
            nextLifeState = LifeState.DEAD;
        }

        return nextLifeState;
    }
}
