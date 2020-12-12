package mospan.lifegame;

class LifeController implements Runnable {

    private final GameField gameField;
    private final int refreshTimeMills;

    LifeController(GameField gameField, int refreshTimeMills) {
        this.gameField = gameField;
        this.refreshTimeMills = refreshTimeMills;
    }

    @Override
    public void run() {
        final int fieldWidth = gameField.getFieldWidth();
        final int fieldHeight = gameField.getFieldHeight();
        final LifeState[][] nextLifeStates = new LifeState[fieldWidth][fieldHeight];

        while(! Thread.currentThread().isInterrupted()) {

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

            try {
                Thread.sleep(refreshTimeMills);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

}
