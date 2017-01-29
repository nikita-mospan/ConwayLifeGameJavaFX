package mospan.lifegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Properties properties = new Properties();
        try(InputStream inputStream = Main.class.getResourceAsStream("/properties.xml")) {
            properties.loadFromXML(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        final int fieldWidth = Integer.valueOf(properties.getProperty("fieldWidth"));
        final int fieldHeight = Integer.valueOf(properties.getProperty("fieldHeight"));
        final int cellSize = Integer.valueOf(properties.getProperty("cellSize"));
        final String aliveCellColor = properties.getProperty("aliveCellColor");
        final String deadCellColor = properties.getProperty("deadCellColor");
        final int refreshTimeMills = Integer.valueOf(properties.getProperty("refreshTimeMills"));

        GameField gameField = new GameField(fieldWidth, fieldHeight);

        ViewTemplate viewTemplate = new ViewTemplate(fieldWidth, fieldHeight, cellSize);

        Scene scene = new Scene(viewTemplate.getRootBox());
        primaryStage.setScene(scene);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Width: " + fieldWidth + " " + "Height: " + fieldHeight);


        for (int columnIndex = 0; columnIndex < fieldWidth; columnIndex++) {
            for (int rowIndex = 0; rowIndex < fieldHeight; rowIndex++) {
                final int lambdaColIndex = columnIndex;
                final int lambdaRowIndex = rowIndex;
                gameField.getCells().get(columnIndex).get(rowIndex).addListener((observable, oldValue, newValue) -> {
                    if(newValue == LifeState.ALIVE) {
                        viewTemplate.getCell(lambdaColIndex, lambdaRowIndex).setStyle(aliveCellColor);
                    } else {
                        viewTemplate.getCell(lambdaColIndex, lambdaRowIndex).setStyle(deadCellColor);
                    }
                });
            }
        }

        for (int columnIndex = 0; columnIndex < fieldWidth; columnIndex++) {
            for (int rowIndex = 0; rowIndex < fieldHeight; rowIndex++) {
                final int lambdaColIndex = columnIndex;
                final int lambdaRowIndex = rowIndex;
                viewTemplate.getCell(columnIndex, rowIndex).addEventHandler(MouseEvent.MOUSE_PRESSED, (event) -> {
                                            if(viewTemplate.getStartStopButton().getText().equals(ViewTemplate.START_BUTTON_LABEL)
                                                    && StaticInfo.getGenerationCount() == 0) {
                                                gameField.toggleLifeState(lambdaColIndex, lambdaRowIndex);
                                            }

                });
            }
        }

        primaryStage.show();

        Runnable lifeCycle = new LifeController(gameField, refreshTimeMills);

        Thread lifeCycleThread = new Thread(lifeCycle);

        primaryStage.setOnCloseRequest((event) -> {
                    lifeCycleThread.interrupt();
                    try {
                        lifeCycleThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );

        lifeCycleThread.start();

    }
}
