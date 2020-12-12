package mospan.lifegame;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

class StaticInfo {
    private static final IntegerProperty generationCount;
    private static boolean stopButtonPressed;

    static {
        generationCount = new SimpleIntegerProperty(0);
        stopButtonPressed = true;
        generationCount.addListener((observable, oldValue, newValue) -> ViewTemplate.setGenerationCountInfo((int)newValue) );
    }

    static int getGenerationCount() {
        return generationCount.getValue();
    }

    static void incrementGenerationCount() {
        generationCount.setValue(generationCount.getValue() + 1);
    }

    static void resetGenerationCount() {generationCount.setValue(0);}

    static void toggleStopButtonPressed() {
        synchronized (Synchronization.keyStartStopButton) {
            stopButtonPressed = !stopButtonPressed;
        }
    }

    static  boolean getStopButtonPressed () {
        synchronized (Synchronization.keyStartStopButton) {
            return stopButtonPressed;
        }
    }
}
