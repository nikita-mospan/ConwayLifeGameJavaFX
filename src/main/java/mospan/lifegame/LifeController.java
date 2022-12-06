package mospan.lifegame;

class LifeController {

    private final GameField gameField;
    private final int fieldWidth;
    private final int fieldHeight;
    private final LifeState[][] nextLifeStates;

    LifeController(GameField gameField) {
        this.gameField = gameField;
        fieldWidth = gameField.getFieldWidth();
        fieldHeight = gameField.getFieldHeight();
        nextLifeStates = new LifeState[fieldWidth][fieldHeight];
    }

    public void runNextCycle() {

        if (!StaticInfo.getStopButtonPressed()) {
            for (int columnIndex = 0; columnIndex < fieldWidth; columnIndex++) {
                for (int rowIndex = 0; rowIndex < fieldHeight; rowIndex++) {
                    nextLifeStates[columnIndex][rowIndex] = gameField.computeNextLifeState(columnIndex, rowIndex);
                }
            }

            //updates model
            gameField.setCellStates(nextLifeStates);

            StaticInfo.incrementGenerationCount();
        } else {
            synchronized (Synchronization.keyStartStopButton) {
                try {
                    while (StaticInfo.getStopButtonPressed()) {
                        Synchronization.keyStartStopButton.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }


    }

}
