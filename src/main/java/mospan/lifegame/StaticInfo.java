package mospan.lifegame;

import javafx.beans.property.SimpleIntegerProperty;

class StaticInfo {
    private static int generationCount = 0;
    private static boolean stopButtonPressed = true;

    static int getGenerationCount() {
        return generationCount;
    }

    static void incrementGenerationCount() {
        generationCount++;
    }

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
