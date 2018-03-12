package EvaluationClasses.WeatherData;

import com.fasterxml.jackson.annotation.JsonAlias;

public class Rain {

    @JsonAlias("3h")
    public double rain;
}
