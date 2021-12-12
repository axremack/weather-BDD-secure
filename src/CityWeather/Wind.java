package CityWeather;

public final class Wind {
    // Paramètres obligatoires
    private double speed;

    // Constructeurs
    private Wind(double speed) {
        this.speed = speed;
    }

    // Getters et setters
    public double getSpeed() {
        return speed;
    }

    private void setSpeed(double speed) {
        this.speed = speed;
    }
}
