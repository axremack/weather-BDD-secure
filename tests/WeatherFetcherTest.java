import WeatherParsing.WeatherFetcher;
import CityWeather.CityWeather;
import junit.framework.TestCase;
import org.junit.Test;

public class WeatherFetcherTest extends TestCase {

    @Test
    public void testGoodCity() {
        WeatherFetcher wf = new WeatherFetcher();
        CityWeather weather = null;

        try {
            weather = wf.getWeatherIn("Clermont-Ferrand");
        } catch (Exception e) {
            fail("API call should'nt have failed (valid name was given)");
        }

        assertNotNull(weather);
        assertEquals("Arrondissement de Clermont-Ferrand", weather.getCity());
    }

    @Test
    public void testInvalidCity() {
        WeatherFetcher wf = new WeatherFetcher();

        try {
            wf.getWeatherIn("invalid_city_:(");
            fail("API call should have failed (invalid city name was given)");
        } catch (Exception e) {
            assertEquals(1,1); // Test only passing if exception is thrown
        }
    }

}