package mospan.lifegame;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

class ViewTemplate {
    private GridPane gridPane;
    private Pane cells[][];

    private Button startStopButton;

    private VBox rootBox;

    VBox getRootBox() {
        return rootBox;
    }

    Button getStartStopButton() {
        return startStopButton;
    }

    private void toggleStartStopButton() {
        synchronized (Synchronization.keyStartStopButton) {
            StaticInfo.toggleStopButtonPressed();
            if (startStopButton.getText().equals(Constants.StartButtonValue.getValue())) {
                startStopButton.setText(Constants.StopButtonValue.getValue());
                Synchronization.keyStartStopButton.notify();
            } else {
                startStopButton.setText(Constants.StartButtonValue.getValue());
            }
        }
    }

    ViewTemplate(final int width, final int height) {
        rootBox = new VBox();
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        //
        startStopButton = new Button("Start");
        startStopButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> toggleStartStopButton());
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(startStopButton);
        cells = new Pane [width][height];
        //
        final int cellSize = Integer.valueOf(Constants.CellSize.getValue());

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

                gridPane.setConstraints(cells[columnIndex][rowIndex], columnIndex, rowIndex);
                gridPane.getChildren().add(cells[columnIndex][rowIndex]);
            }
        }

        rootBox.getChildren().addAll(borderPane, gridPane);
    }

    Pane getCell(Integer columnIndex, Integer rowIndex) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == columnIndex && GridPane.getRowIndex(node) == rowIndex) {
                return (Pane) node;
            }
        }

        return null;
    }

}
