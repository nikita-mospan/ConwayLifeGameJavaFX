package mospan.lifegame;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class GameField {

    private List<List<ObjectProperty<LifeState>>> cells;

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

    void setCellStates(LifeState lifeStates[][]) {
        for (int columnIndex = 0; columnIndex < fieldWidth; columnIndex++) {
            for (int rowIndex = 0; rowIndex < fieldHeight; rowIndex++) {
                setCellState(columnIndex, rowIndex, lifeStates[columnIndex][rowIndex]);
            }
        }
    }

    private static boolean isCellAlive(LifeState cell) {return cell == LifeState.ALIVE; }

    private int getCountOfAliveNeighbours(int columnIndex, int rowIndex) {

        final int newLeftColumnIndex = (columnIndex == 0) ? fieldWidth - 1 : columnIndex - 1;
        final int newRightColumnIndex = (columnIndex == fieldWidth - 1) ? 0 : columnIndex + 1;
        final int newTopRowIndex = (rowIndex == 0) ? fieldHeight - 1 : rowIndex - 1;
        final int newBottomRowIndex = (rowIndex == fieldHeight - 1) ? 0 : rowIndex + 1;

        final LifeState leftNeighbour = cells.get(newLeftColumnIndex).get(rowIndex).get();
        final LifeState rightNeighbour = cells.get(newRightColumnIndex).get(rowIndex).get();
        final LifeState topNeighbour = cells.get(columnIndex).get(newTopRowIndex).get();
        final LifeState bottomNeighbour = cells.get(columnIndex).get(newBottomRowIndex).get();
        final LifeState leftTopNeighbour = cells.get(newLeftColumnIndex).get(newTopRowIndex).get();
        final LifeState rightTopNeighbour = cells.get(newRightColumnIndex).get(newTopRowIndex).get();
        final LifeState leftBottomNeighbour = cells.get(newLeftColumnIndex).get(newBottomRowIndex).get();
        final LifeState rightBottomNeighbour = cells.get(newRightColumnIndex).get(newBottomRowIndex).get();

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
