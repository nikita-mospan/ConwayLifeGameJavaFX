package mospan.lifegame;

/**
 * Created by mospnik on 1/13/2017.
 */
public enum Constants {
    PropertiesFileName("/properties.xml")
    , StartButtonValue("Start")
    , StopButtonValue("Stop")
    ;

    private String value;

    Constants(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
