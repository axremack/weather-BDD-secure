import CityWeather.CityWeather;
import Database.DBManager;
import WeatherParsing.WeatherFetcher;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:src/Database/weather.db";
        CityWeather weather = null;

        if (args.length != 1) {
            System.err.println("Usage : /usr/lib/jvm/java-11-openjdk-amd64/bin/java -Dfile.encoding=UTF-8 -classpath /mnt/c/Users/axrem/Documents/ZZ3/Java/TP6/out/production/TP6:/mnt/c/Users/axrem/Downloads/sqlite-jdbc-3.32.3.2.jar:/mnt/c/Users/axrem/Documents/ZZ3/Java/gson-2.8.8.jar Main city_name\n");
            throw new IllegalArgumentException();
        }

        Pattern p;
        Matcher m;
        // Regex searching for digit or special character except "-"
        p = Pattern.compile("\\d");
        m = p.matcher("Clermont");
        if(m.find()) {
            System.out.println("motif trouvé");
        }

        try {
            DBManager d = new DBManager(url);
            d.deleteOldData(); // Deleting data if too old

            boolean found = d.findInDB(args[0]);

            if(!found) {
                WeatherFetcher weatherFetcher = new WeatherFetcher();
                weather = weatherFetcher.getWeatherIn(args[0]);
                System.out.println(weather); // Printing out weather fetched

                List<Object> list = new ArrayList<>();
                list.add(weather.getDT());
                list.add(weather.getCity());
                list.add(weather.getTemp().getTemp());
                list.add(weather.getWind().getSpeed());

                d.createWeatherTable(); // Creating table only if it does not already exist
                d.insertValues(list);   // Inserting new values in the database
            }

            d.displayDBOrderedBy("city");
        } catch (SQLException e){
            System.err.println("Error : invalid SQL request");
        } catch (ClassNotFoundException e) {
            System.err.println("Error : invalid city entered");
        } catch (Exception e) {
            System.err.println("Error");
        }

    }
}
