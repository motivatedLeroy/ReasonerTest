package EvaluationClasses.WaterLevells;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.impl.PropertyImpl;
import org.apache.jena.sparql.vocabulary.FOAF;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WaterLevels {

    public String uuid;
    public String shortname;
    public String longname;
    public String number;
    public double km;
    public String agency;
    public double longitude;
    public double latitude;
    public double value;

    @JsonIgnore
    public boolean water;

    @JsonIgnore
    public static Model model;


    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getLongname() {
        return longname;
    }

    public void setLongname(String longname) {
        this.longname = longname;
    }

    public double getKm() {
        return km;
    }

    public void setKm(double km) {
        this.km = km;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /*public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }*/

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String getIRI(String identifier){
        return "http://pegelonline.de/"+identifier;
    }

    public String serialize(String syntax){
        Resource resource = model.createResource(getIRI(longname));
        // add the property
        resource.addProperty(new PropertyImpl(getIRI("uuid")), uuid);
        resource.addProperty(new PropertyImpl(getIRI("shortname")), shortname);
        resource.addProperty(new PropertyImpl(getIRI("longname")), longname);
        resource.addProperty(new PropertyImpl(getIRI("shortname")), shortname);
        resource.addProperty(new PropertyImpl(getIRI("km")), String.valueOf(km));
        resource.addProperty(new PropertyImpl(getIRI("agency")), agency.replace(" ", "_"));
        resource.addProperty(new PropertyImpl(getIRI("longitude")), String.valueOf(longitude));
        resource.addProperty(new PropertyImpl(getIRI("latitude")), String.valueOf(latitude));
        resource.addProperty(new PropertyImpl(getIRI("value")), String.valueOf(value));

        StringWriter out = new StringWriter();
        model.write(out, syntax);
        return out.toString();
    }

    public static Model loadLiveData() throws IOException {
        model = ModelFactory.createDefaultModel();
        URL urlObject = new URL("https://www.pegelonline.wsv.de/webservices/rest-api/v2/stations.json?waters=RHEIN,NECKAR");
        URLConnection urlConnection = null;
        urlConnection = urlObject.openConnection();
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)             AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
        String s = toString(urlConnection.getInputStream());


        ObjectMapper mapper = new ObjectMapper();
        WaterLevels[] waterLevels = mapper.readValue(s, WaterLevels[].class);
        int count = 0;
        for(int i = 0; i < waterLevels.length; i++){

            String encodedUrl = null;
            try {
                encodedUrl = URLEncoder.encode(waterLevels[i].longname, "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException ignored) {
                // Can be safely ignored because UTF-8 is always supported
            }


            URL urlObject0 = new URL("https://www.pegelonline.wsv.de/webservices/rest-api/v2/stations/"+encodedUrl+"/W/measurements.json");
            URLConnection urlConnection0 = null;
            urlConnection = urlObject0.openConnection();

            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64)             AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            try{
                String s0 = toString(urlConnection.getInputStream());
                Values[] values = mapper.readValue(s0, Values[].class);
                waterLevels[i].value = values[values.length-1].value;

            }catch (FileNotFoundException e){
            }
            waterLevels[i].serialize("RDF/XML-ABBREV");
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


}
