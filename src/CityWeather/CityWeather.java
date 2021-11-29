package CityWeather;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class CityWeather {
    @SerializedName("name")
    private String city;
    private Integer dt;

    @SerializedName("main")
    private Temperature temp;

    private Wind wind;

    // Constructeur
    public CityWeather(Temperature temp, Wind wind, String city, Integer dt) {
        this.temp = temp;
        this.wind = wind;
        this.city = city;
        this.dt = dt;
    }

    // Getters et setters
    public Temperature getTemp() { return temp; }

    public void setTemp(Temperature temp) { this.temp = temp; }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getDT() {
        return dt;
    }

    public void setDT(Integer dt) {
        this.dt = dt;
    }

    public String toString() {
        StringBuilder summary = new StringBuilder();
        return summary
                .append("Weather fetched at : ").append(new Date(Long.parseLong(dt.toString()) * 1000)).append("\n")
                .append("Weather for city : ").append(this.city).append("\n")
                .append("\tCurrent temperature : ").append(temp.getTemp()).append("°C\n")
                .append("\tWind speed : ").append(wind.getSpeed()).append(" m/s\n")
                .toString();
    }
}
