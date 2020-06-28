package weatherForecast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
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

public class WeatherService {
	final String key =  "e030cee405d4b9b32704de57c13778d2";
	
	
	public CurrentWeather getWeatherFromCity(String city, String countryCode) {
		DataWeatherClient client = new UrlConnectionDataWeatherClient("e030cee405d4b9b32704de57c13778d2");
		CurrentWeatherOneLocationQuery currentWeatherOneLocationQuery = QueryBuilderPicker.pick().currentWeather() // get current weather															// weather
				.oneLocation() // for one location
				.byCityName(city).countryCode(countryCode).type(Type.ACCURATE) // with Accurate search
				.language(Language.ENGLISH) // in English language
				.responseFormat(ResponseFormat.JSON)// with JSON response format
				.unitFormat(UnitFormat.METRIC) // in metric units
				.build();
		CurrentWeather currentWeather = client.getCurrentWeather(currentWeatherOneLocationQuery);
		String weatherData = client.getWeatherData(currentWeatherOneLocationQuery);
//		System.out.println(weatherData);
//		System.out.println(currentWeather.getWeather());
//     client.getForecastInformation(currentWeatherOneLocationQuery);

//		System.out.println(client.getWeatherData(currentWeatherOneLocationQuery));
//		System.out.println(currentWeather.getClouds());
//		System.out.println(prettyPrint(currentWeather));
		
		
		
		//CombinedWeatherData combinedWeatherData = new CombinedWeatherData(currentWeather.getWeather(), currentWeather);
		return currentWeather;
		
	}

	public void getWeatherForecastFromCity(String city, String countryCode) {
		DataWeatherClient client = new UrlConnectionDataWeatherClient("e030cee405d4b9b32704de57c13778d2");
	        ByCityName byCityNameForecast = QueryBuilderPicker.pick()
	                .forecast()                                         // get forecast
	                .hourly()                                           // it should be hourly forecast
	                .byCityName("Kharkiv")                              // for Kharkiv city
	                .countryCode("UA")                                  // in Ukraine
	                .unitFormat(UnitFormat.METRIC)                      // in Metric units
	                .language(Language.ENGLISH)                         // in English
	                .count(16)                                           // limit results to 5 forecasts
	                .build();
	        ForecastInformation<HourlyForecast> forecastInformation = client.getForecastInformation(byCityNameForecast);
	        System.out.println("Forecasts for " + forecastInformation.getCity() + ":");
	        for (HourlyForecast forecast : forecastInformation.getForecasts()) {
	            System.out.println(forecast.getMainParameters().getTemperature());
	        }
	}
	
	
	public void findMinMaxTemperature() {
		int hour = LocalDateTime.now().getHour();
		
		double timeLeftTill24 = 24 - hour;
		double countTill24 = Math.ceil(timeLeftTill24 / 3);
		
		System.out.println(hour);
		System.out.println(timeLeftTill24);
		System.out.println(countTill24);
		
	}
	
	
	
	
	
	
	private static String prettyPrint(CurrentWeather currentWeather) {
		return String.format("City: " + currentWeather.getCityName() + ", country: "
				+ currentWeather.getSystemParameters().getCountry() + ", temperature: "
				+ currentWeather.getMainParameters().getTemperature() + ", humidity: "
				+ currentWeather.getMainParameters().getHumidity() + ", pressure: "
				+ currentWeather.getMainParameters().getPressure());
	}

	private JsonObject addWeatherDataToJson(double rain, double cloud) {

		Integer position = convertWeatherToPosition();
		String color = convertWeatherToColor();

		JsonObject json = new JsonObject();
		json.addProperty("position", position);
		json.addProperty("color", color);

		return json;
	}

	private Integer convertWeatherToPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	private String convertWeatherToColor() {
		// TODO Auto-generated method stub
		String color = "";

		return color;
	}

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
	
	public CalendarWeatherData createCalendarDataForecast(DailyForecast dailyForecast) {
		List<Weather> weatherData = dailyForecast.getWeather();
		Weather weatherForecast = weatherData.get(0);
		String weatherDescription = weatherForecast.getDescription();
		
		
		double temperature = dailyForecast.getTemperature().getDay();
		double maxTemperature = dailyForecast.getTemperature().getMax();
		double minTemperature = dailyForecast.getTemperature().getMin();
		
		double humidity = dailyForecast.getHumidity(); 
		
		CalendarWeatherData calendarData = new CalendarWeatherData(temperature, humidity, weatherDescription, maxTemperature, minTemperature);
		return calendarData;
		
	}

}
