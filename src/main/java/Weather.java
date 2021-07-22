import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    //method while send message to Model
    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=3231569cf78da550f740173bdc7b6a9f");

        Scanner in = new Scanner((InputStream) url.getContent()); //reading the content
        String result = "";
        //until we read the line - reading
        while (in.hasNext()){
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result); //for json file
        model.setName(object.getString("name")); //setName - assign a name(city), getString - got the name city

        JSONObject main = object.getJSONObject("main"); //from big jsonobject pick up a less jsonobject main
        model.setTemp(main.getDouble("temp")); //got the temp from jsonobject main
        model.setHumidity(main.getDouble("humidity")); //got the humidity from main
        model.setPressure(main.getDouble("pressure")); //got the pressure from main

        JSONObject wind = object.getJSONObject("wind"); //from big jsnobj pick up a less jsnobj wind
        model.setWind(wind.getDouble("speed"));

        JSONArray getArray = object.getJSONArray("weather");
        //we go through the array to get the objects we need
        for (int i=0; i<getArray.length(); i++){
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }

        return "City: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + " °C" + "\n" +
                "Humidity: " + model.getHumidity() + " %" + "\n" +
                "Pressure: " + model.getPressure() + " mm Hg" + "\n" +
                "Wind: " + model.getWind() + " m/s" + "\n" +
                "Main: " + model.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }
}

