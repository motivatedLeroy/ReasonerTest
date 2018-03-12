package EvaluationClasses.WeatherData;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Snow {

    @JsonAlias("3h")
    public double snow;

}
