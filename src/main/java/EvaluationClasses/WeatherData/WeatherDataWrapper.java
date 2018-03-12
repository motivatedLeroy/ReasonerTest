package EvaluationClasses.WeatherData;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class WeatherDataWrapper {

    public String base;
    public Coordinates coord;
    public Weather[] weather;
    public Main main;
    public Wind wind;
    public Clouds clouds;
    public Rain rain;
    public Snow snow;
    public String name;



    @JsonIgnore
    public boolean id;
    @JsonIgnore
    public boolean dt;
    @JsonIgnore
    public boolean sys;


}
