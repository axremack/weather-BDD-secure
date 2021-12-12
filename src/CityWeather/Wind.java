package CityWeather;

public class Wind {
    // Paramètres obligatoires
    private double speed;

    // Constructeurs
    protected Wind(double speed) {
        this.speed = speed;
    }

    // Getters et setters
    public final double getSpeed() {
        return speed;
    }

    private void setSpeed(double speed) {
        this.speed = speed;
    }
}
