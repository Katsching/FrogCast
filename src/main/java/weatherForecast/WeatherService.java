package weatherForecast;

import java.util.List;

import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
import org.openweathermap.api.model.Weather;
import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.query.Language;
import org.openweathermap.api.query.QueryBuilderPicker;
import org.openweathermap.api.query.ResponseFormat;
import org.openweathermap.api.query.Type;
import org.openweathermap.api.query.UnitFormat;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;

import com.google.gson.JsonObject;

public class WeatherService {

	public CombinedWeatherData getWeatherFromCity(String city, String countryCode) {
		DataWeatherClient client = new UrlConnectionDataWeatherClient("e030cee405d4b9b32704de57c13778d2");
		CurrentWeatherOneLocationQuery currentWeatherOneLocationQuery = QueryBuilderPicker.pick().currentWeather() // get
																													// current
																													// weather
				.oneLocation() // for one location
				.byCityName(city).countryCode(countryCode).type(Type.ACCURATE) // with Accurate search
				.language(Language.ENGLISH) // in English language
				.responseFormat(ResponseFormat.JSON)// with JSON response format
				.unitFormat(UnitFormat.METRIC) // in metric units
				.build();
		CurrentWeather currentWeather = client.getCurrentWeather(currentWeatherOneLocationQuery);
		String weatherData = client.getWeatherData(currentWeatherOneLocationQuery);
		System.out.println(weatherData);
		System.out.println(currentWeather.getWeather());
//     client.getForecastInformation(currentWeatherOneLocationQuery);

//		System.out.println(client.getWeatherData(currentWeatherOneLocationQuery));
//		System.out.println(currentWeather.getClouds());
		System.out.println(prettyPrint(currentWeather));
		
		CombinedWeatherData combinedWeatherData = new CombinedWeatherData(currentWeather.getWeather(), currentWeather);
		return combinedWeatherData;
		
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

	public CalendarWeatherData createCalendarData(CombinedWeatherData combinedWeatherData) {

		List<Weather> weatherData = combinedWeatherData.getWeatherData();
		
		Weather weather = weatherData.get(0);
		
		String weatherDescription = weather.getDescription();
		
		CurrentWeather currentWeather = combinedWeatherData.getCurrentWeather();
		
		double temperature = currentWeather.getMainParameters().getTemperature();
		double humidity = currentWeather.getMainParameters().getHumidity(); 
		
		CalendarWeatherData calendarData = new CalendarWeatherData(temperature, humidity, weatherDescription);
		return calendarData;
	}

}
