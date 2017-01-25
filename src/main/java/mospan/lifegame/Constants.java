package mospan.lifegame;

/**
 * Created by mospnik on 1/13/2017.
 */
public enum Constants {
    FieldWidth("fieldWidth")
    , FieldHeight("fieldHeight")
    , PropertiesFileName("/properties.xml")
    , CellSize("20")
    , AliveCellColor("-fx-background-color: black")
    , DeadCellColor(null)
    , StartButtonValue("Start")
    , StopButtonValue("Stop")
    , RefreshTimeMills("300")
    ;

    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
