package mospan.lifegame;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.util.Objects;

class ViewTemplate {
    private final GridPane gridPane;

    static final String STOP_BUTTON_LABEL = "Stop";
    static final String START_BUTTON_LABEL = "Start";
    private static final String GENERATION_INFO_HEADER = "Generation: ";

    private final Button startStopButton;

    private final Button resetButton;

    private final VBox rootBox;

    private static final TextField generationCountInfo = new TextField(GENERATION_INFO_HEADER + StaticInfo.getGenerationCount());

    Button getResetButton() {
        return resetButton;
    }

    static void setGenerationCountInfo(final int generationCount) {
        generationCountInfo.setText(GENERATION_INFO_HEADER + generationCount);
    }

    VBox getRootBox() {
        return rootBox;
    }

    Button getStartStopButton() {
        return startStopButton;
    }

    void toggleStartStopButton() {
        synchronized (Synchronization.keyStartStopButton) {
            StaticInfo.toggleStopButtonPressed();
            if (startStopButton.getText().equals(START_BUTTON_LABEL)) {
                startStopButton.setText(STOP_BUTTON_LABEL);
                Synchronization.keyStartStopButton.notifyAll();
            } else {
                startStopButton.setText(START_BUTTON_LABEL);
            }
        }
    }

    ViewTemplate(final int width, final int height, final int cellSize) {
        rootBox = new VBox();


        HBox boxForButtons = new HBox();

        //
        startStopButton = new Button(START_BUTTON_LABEL);
        startStopButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> toggleStartStopButton());
        //
        resetButton = new Button("Reset");

        boxForButtons.getChildren().add(startStopButton);
        boxForButtons.getChildren().add(resetButton);
        boxForButtons.getChildren().add(generationCountInfo);
        Pane[][] cells = new Pane [width][height];
        //

        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        for(int columnIndex = 0; columnIndex < width; columnIndex++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPrefWidth(cellSize);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for(int rowIndex = 0; rowIndex < height; rowIndex++) {
            RowConstraints rowConstraints = new RowConstraints();
            rowConstraints.setPrefHeight(cellSize);
            gridPane.getRowConstraints().add(rowConstraints);
        }

        for(int columnIndex = 0; columnIndex < width; columnIndex++) {
            for(int rowIndex = 0; rowIndex < height; rowIndex++) {
                cells[columnIndex][rowIndex] = new Pane();

                GridPane.setConstraints(cells[columnIndex][rowIndex], columnIndex, rowIndex);
                gridPane.getChildren().add(cells[columnIndex][rowIndex]);
            }
        }

        rootBox.getChildren().addAll(boxForButtons, gridPane);
    }

    Pane getCell(Integer columnIndex, Integer rowIndex) {
        for (Node node : gridPane.getChildren()) {
            if (Objects.equals(GridPane.getColumnIndex(node), columnIndex) && Objects.equals(GridPane.getRowIndex(node), rowIndex)) {
                return (Pane) node;
            }
        }

        return null;
    }

}
