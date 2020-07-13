package weatherForecast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
import org.openweathermap.api.model.Rain;
import org.openweathermap.api.model.Weather;
import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.model.forecast.ForecastInformation;
import org.openweathermap.api.model.forecast.daily.DailyForecast;
import org.openweathermap.api.model.forecast.hourly.HourlyForecast;
import org.openweathermap.api.query.Language;
import org.openweathermap.api.query.QueryBuilderPicker;
import org.openweathermap.api.query.ResponseFormat;
import org.openweathermap.api.query.Type;
import org.openweathermap.api.query.UnitFormat;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;
import org.openweathermap.api.query.forecast.hourly.ByCityName;

import com.google.gson.JsonObject;

import DailyWeatherJson.FullOneApiCall;

/**
 * This class includes all methods which are related to the weather
 */
public class WeatherService {
	private static final String APIKEY = "e030cee405d4b9b32704de57c13778d2";
	// Backnang weather
	private static final double LATITUDE = 48.94744;
	private static final double LONGITUDE = 9.43718;

	/**
	 * Get current weather from the city
	 * 
	 * @param city
	 * @param countryCode
	 * @return
	 */
	public CurrentWeather getWeatherFromCity(String city, String countryCode) {
		DataWeatherClient client = new UrlConnectionDataWeatherClient("e030cee405d4b9b32704de57c13778d2");
		CurrentWeatherOneLocationQuery currentWeatherOneLocationQuery = QueryBuilderPicker.pick().currentWeather() // get
																													// current
																													// weather
																													// //
																													// weather
				.oneLocation() // for one location
				.byCityName(city).countryCode(countryCode).type(Type.ACCURATE) // with Accurate search
				.language(Language.ENGLISH) // in English language
				.responseFormat(ResponseFormat.JSON)// with JSON response format
				.unitFormat(UnitFormat.METRIC) // in metric units
				.build();
		CurrentWeather currentWeather = client.getCurrentWeather(currentWeatherOneLocationQuery);
		String weatherData = client.getWeatherData(currentWeatherOneLocationQuery);
		return currentWeather;

	}

	/**
	 * Get the next day weather forecast and combines the information
	 * 
	 * @return
	 * @throws IOException
	 */
	public CombinedForecastData getWeatherForecastFromCity() throws IOException {
	
		OpenWeatherApiParser openWeatherApiParser = new OpenWeatherApiParser();

		FullOneApiCall fullOneApiCall = openWeatherApiParser.getResponseFromWebsite(LATITUDE, LONGITUDE, APIKEY);

		double maximalTemperature = fullOneApiCall.getDaily().get(1).getTemp().getMax();
		double minimalTemperature = fullOneApiCall.getDaily().get(1).getTemp().getMin();
		int averageHumidity = fullOneApiCall.getDaily().get(1).getHumidity();
		String weatherDescription = fullOneApiCall.getDaily().get(1).getMainWeather().get(0).getDescription();
		String mainWeather = fullOneApiCall.getDaily().get(1).getMainWeather().get(0).getMain();
		
		CombinedForecastData combinedForecastData = new CombinedForecastData(maximalTemperature, minimalTemperature,
				averageHumidity, weatherDescription, mainWeather);
		
		return combinedForecastData;
	}


	/**
	 * Refactors the weather into a pretty print
	 * @param currentWeather
	 * @return
	 */
	private static String prettyPrint(CurrentWeather currentWeather) {
		return String.format("City: " + currentWeather.getCityName() + ", country: "
				+ currentWeather.getSystemParameters().getCountry() + ", temperature: "
				+ currentWeather.getMainParameters().getTemperature() + ", humidity: "
				+ currentWeather.getMainParameters().getHumidity() + ", pressure: "
				+ currentWeather.getMainParameters().getPressure());
	}



	/**
	 * Converts the main weather into a position for the weather frog
	 * @param weather
	 * @return
	 */
	public String convertWeatherToPosition(String weather) {	
		switch(weather) {
			case "Thunderstorm": case "Drizzle": case "Rain": case "Snow": return "bot";
			case "Haze": case "Mist": case "Fog": case "Squal": return "mid";
			case "Clear": case "Clouds": return "top";
			default: return "mid";
		}
	}

	/**
	 * Converts the main weather into a color for the weather frog
	 * @param weather
	 * @return
	 */
	public String convertWeatherToColor(String weather) {
		switch(weather) {
			case "Thunderstorm": case "Fog": return "magenta";
			case "Drizzle": case "Haze": case "Clouds": return "lightblue";
			case "Clear": case "Squal": case "Snow": return "white";
			case "Mist": case "Rain": return "blue";
			default: return "white";
		}
	}

	/**
	 * Method to create today's all-day entry for Google Calendar
	 * 
	 * @param currentWeather
	 * @return
	 */
	public CalendarWeatherData createCalendarDataCurrent(CurrentWeather currentWeather) {

		List<Weather> weatherData = currentWeather.getWeather();

		Weather weather = weatherData.get(0);

		String weatherDescription = weather.getDescription();

		double temperature = currentWeather.getMainParameters().getTemperature();

		double humidity = currentWeather.getMainParameters().getHumidity();

		CalendarWeatherData calendarData = new CalendarWeatherData();
		calendarData.setTemperature(temperature);
		calendarData.setHumidity(humidity);
		calendarData.setMainWeather(weatherDescription);
		return calendarData;
	}

	/**
	 * Method to create tomorrow's all-day entry for Google Calendar
	 * 
	 * @param dailyForecast
	 * @return
	 */
	public CalendarWeatherData createCalendarDataForecast(DailyForecast dailyForecast) {
		List<Weather> weatherData = dailyForecast.getWeather();
		Weather weatherForecast = weatherData.get(0);
		String weatherDescription = weatherForecast.getDescription();

		double temperature = dailyForecast.getTemperature().getDay();
		double maxTemperature = dailyForecast.getTemperature().getMax();
		double minTemperature = dailyForecast.getTemperature().getMin();

		double humidity = dailyForecast.getHumidity();

		CalendarWeatherData calendarData = new CalendarWeatherData(temperature, humidity, weatherDescription,
				maxTemperature, minTemperature);
		return calendarData;

	}
}
