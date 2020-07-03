package weatherForecast;

public class CombinedForecastData {
	private double maxTemperature;
	private double minTemperature;
	private double averageHumidity;
	private String weatherDescription;
	private String mainWeather;
	
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
	public double getAverageHumidity() {
		return averageHumidity;
	}
	public void setAverageHumidity(double averageHumidity) {
		this.averageHumidity = averageHumidity;
	}
	public String getWeatherDescription() {
		return weatherDescription;
	}
	public void setWeatherDescription(String weatherDescription) {
		this.weatherDescription = weatherDescription;
	}
	public String getMainWeather() {
		return mainWeather;
	}
	public void setMainWeather(String mainWeather) {
		this.mainWeather = mainWeather;
	}
	public CombinedForecastData(double maxTemperature, double minTemperature, double averageHumidity,
			String weatherDescription, String mainWeather) {
		super();
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
		this.averageHumidity = averageHumidity;
		this.weatherDescription = weatherDescription;
		this.mainWeather = mainWeather;
	}

	public CombinedForecastData() {
		
	}
	
}
