package EvaluationClasses.WeatherData;

import EvaluationClasses.WaterLevells.Values;
import EvaluationClasses.WaterLevells.WaterLevels;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.PropertyImpl;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

public class WeatherDataComplete {

    public WeatherDataWrapper[] list;

    @JsonIgnore
    public static Model model;
    @JsonIgnore
    public boolean cod;
    @JsonIgnore
    public double calctime;
    @JsonIgnore
    public int cnt;



    public static Model loadLiveData() throws IOException {
        model = ModelFactory.createDefaultModel();
        URL urlObject = new URL("http://api.openweathermap.org/data/2.5/box/city?bbox=7.5113934084,47.5338000528,10.4918239143,49.7913749328,10&lang=de&appid=0f4e460063387d91c92d56eb5e57d05d");
        URLConnection urlConnection = null;
        urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)             AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        String s = toString(urlConnection.getInputStream());

        ObjectMapper mapper = new ObjectMapper();
        WeatherDataComplete weatherDataComplete = mapper.readValue(s, WeatherDataComplete.class);
        for(int i = 0; i < weatherDataComplete.list.length; i++){
            serialize("RDF/XML-ABBREV", weatherDataComplete.list[i]);
        }
        return model;
    }

    private static String toString(InputStream inputStream) throws IOException
    {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")))
        {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(inputLine);
            }

            return stringBuilder.toString();
        }
    }

    private static String getIRI(String identifier){
        return "http://openweathermap.org/"+identifier.replace(" ", "_");
    }

    public static void serialize(String syntax, WeatherDataWrapper weatherDataWrapper){
        try{
            Resource resource = model.createResource(getIRI(weatherDataWrapper.name));
            // add the property
            resource.addProperty(new PropertyImpl(getIRI("temp")), String.valueOf(weatherDataWrapper.main.temp));
            resource.addProperty(new PropertyImpl(getIRI("max_Temp")), String.valueOf(weatherDataWrapper.main.temp_max));
            resource.addProperty(new PropertyImpl(getIRI("min_Temp")), String.valueOf(weatherDataWrapper.main.temp_min));
            resource.addProperty(new PropertyImpl(getIRI("Pressure")), String.valueOf(weatherDataWrapper.main.pressure));
            resource.addProperty(new PropertyImpl(getIRI("Humidity")), String.valueOf(weatherDataWrapper.main.humidity));
            resource.addProperty(new PropertyImpl(getIRI("Weather_Description")), weatherDataWrapper.weather[0].description);
            resource.addProperty(new PropertyImpl(getIRI("Weather_Description_Main")), weatherDataWrapper.weather[0].main);
            resource.addProperty(new PropertyImpl(getIRI("Wind_speed")), String.valueOf(weatherDataWrapper.wind.speed));
            resource.addProperty(new PropertyImpl(getIRI("Wind_direction_DEG")), String.valueOf(weatherDataWrapper.wind.deg));
            resource.addProperty(new PropertyImpl(getIRI("Clouds")), String.valueOf(weatherDataWrapper.clouds.today));
            resource.addProperty(new PropertyImpl(getIRI("Rain_last_three_hours")), String.valueOf(weatherDataWrapper.rain.rain));
            resource.addProperty(new PropertyImpl(getIRI("Snow_last_three_hours")), String.valueOf(weatherDataWrapper.snow.snow));
            resource.addProperty(new PropertyImpl(getIRI("longitude")), String.valueOf(weatherDataWrapper.coord.Lon));
            resource.addProperty(new PropertyImpl(getIRI("latitude")), String.valueOf(weatherDataWrapper.coord.Lat));

            StringWriter out = new StringWriter();
            model.write(out, syntax);
        }catch (NullPointerException e){

        }
    }


}
