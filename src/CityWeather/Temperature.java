package CityWeather;

public class Temperature {
    private double temp;

    // Constructeur
    protected Temperature(double temp) {
        this.temp = temp;
    }

    // Getters et setters
    public final double getTemp() { return temp; }

    private void setTemp(double temp) {
        this.temp = temp;
    }
}
