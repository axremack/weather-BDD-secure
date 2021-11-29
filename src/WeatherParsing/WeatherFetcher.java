package WeatherParsing;

import CityWeather.CityWeather;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

public class WeatherFetcher {

    public CityWeather getWeatherIn(String city) throws Exception {
        final String api_key = "47631728a917d56bffde4b26e7e461e3";
        String line;
        String json;
        HttpURLConnection urlConnection = null ;

        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + api_key + "&units=Metric");
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader input_reader = new InputStreamReader(in, "UTF-8");
            BufferedReader input_buff_reader = new BufferedReader(input_reader);
            StringBuilder content = new StringBuilder();

            while ((line = input_buff_reader.readLine()) != null) {
                content.append(line);
            }

            json = content.toString();
            Gson gson = new Gson();
            CityWeather weather = gson.fromJson(json, CityWeather.class);

            return weather;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
