import weatherForecast.WeatherService;

public class Main {

    public static void main(String[] args) {
    	WeatherService weatherServices = new WeatherService();
    	weatherServices.getWeatherFromCity("Stuttgart", "DE");
    }
}