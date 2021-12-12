package CityWeather;

public final class Temperature {
    private double temp;

    // Constructeur
    private Temperature(double temp) {
        this.temp = temp;
    }

    // Getters et setters
    public double getTemp() { return temp; }

    private void setTemp(double temp) {
        this.temp = temp;
    }
}
