package weatherForecast;

import java.util.List;

import org.openweathermap.api.model.Weather;
import org.openweathermap.api.model.currentweather.CurrentWeather;

public class CombinedWeatherData {

	private List<Weather> weatherData;
	private CurrentWeather currentWeather;
	

	
	public CombinedWeatherData(List<Weather> weatherData, CurrentWeather currentWeather) {
		super();
		this.weatherData = weatherData;
		this.currentWeather = currentWeather;
	}
	public List<Weather> getWeatherData() {
		return weatherData;
	}
	public void setWeatherData(List<Weather> weatherData) {
		this.weatherData = weatherData;
	}
	public CurrentWeather getCurrentWeather() {
		return currentWeather;
	}
	public void setCurrentWeather(CurrentWeather currentWeather) {
		this.currentWeather = currentWeather;
	}
	
	
}
