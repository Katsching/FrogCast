package weatherForecast;

public class CalendarWeatherData {

	private double temperature;
	private double humidity;
	private String mainWeather;
	
	
	public CalendarWeatherData(double temperature, double humidity, String mainWeather) {
		super();
		this.temperature = temperature;
		this.humidity = humidity;
		this.mainWeather = mainWeather;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public String getMainWeather() {
		return mainWeather;
	}
	public void setMainWeather(String mainWeather) {
		this.mainWeather = mainWeather;
	}
	
	
	
	
}
