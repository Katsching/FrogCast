package weatherForecast;

public class CalendarWeatherData {

	private double temperature;
	private double humidity;
	private String mainWeather;
	private double maxTemperature;
	private double minTemperature;

	public CalendarWeatherData() {
		
	}
	
	public CalendarWeatherData(double temperature, double humidity, String mainWeather, double maxTemperature,
			double minTemperature) {
		super();
		this.temperature = temperature;
		this.humidity = humidity;
		this.mainWeather = mainWeather;
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
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
	public double getMaxTemperature() {
		return maxTemperature;
	}
	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}
	public double getMinTemperature() {
		return minTemperature;
	}
	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}
	

	
	
	
}
