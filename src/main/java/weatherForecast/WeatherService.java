package weatherForecast;

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

public class WeatherService {
	final String key = "e030cee405d4b9b32704de57c13778d2";

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
//		System.out.println(weatherData);
//		System.out.println(currentWeather.getWeather());
//     client.getForecastInformation(currentWeatherOneLocationQuery);

//		System.out.println(client.getWeatherData(currentWeatherOneLocationQuery));
//		System.out.println(currentWeather.getClouds());
//		System.out.println(prettyPrint(currentWeather));

		// CombinedWeatherData combinedWeatherData = new
		// CombinedWeatherData(currentWeather.getWeather(), currentWeather);
		return currentWeather;

	}

	public CombinedForecastData getWeatherForecastFromCity(String city, String countryCode) {

		DataWeatherClient client = new UrlConnectionDataWeatherClient("e030cee405d4b9b32704de57c13778d2");
		ByCityName byCityNameForecast = QueryBuilderPicker.pick().forecast() // get forecast
				.hourly() // it should be hourly forecast
				.byCityName(city) // for Kharkiv city
				.countryCode(countryCode) // in Ukraine
				.unitFormat(UnitFormat.METRIC) // in Metric units
				.language(Language.ENGLISH) // in English
				.count(16) // limit results to 5 forecasts
				.build();
		ForecastInformation<HourlyForecast> forecastInformation = client.getForecastInformation(byCityNameForecast);
		System.out.println("Forecasts for " + forecastInformation.getCity() + ":");

		int count = (int) findMinMaxTemperature();

		double minimalTemperature = Double.MAX_VALUE;
		double maximalTemperature = Double.MIN_VALUE;
		List<Double> humidityList = new ArrayList<Double>();
		Map<Integer, Rain> rainTimeMap = new HashMap<Integer, Rain>();
		boolean willItRainTomorrow = false;

		List<HourlyForecast> foreCastList = forecastInformation.getForecasts();
		for (int i = count; i < count + 8; i++) {
			HourlyForecast forecast = foreCastList.get(i);
			double currentMaxTemp = forecast.getMainParameters().getMaximumTemperature();
			double currentMinTemp = forecast.getMainParameters().getMinimumTemperature();

			if (currentMaxTemp > maximalTemperature) {
				maximalTemperature = currentMaxTemp;
			}

			if (currentMinTemp < minimalTemperature) {
				minimalTemperature = currentMinTemp;
			}

			humidityList.add(forecast.getMainParameters().getHumidity());
			rainTimeMap.put(i, forecast.getRain());

//			System.out.println("count: " + i + " max: " + currentMaxTemp + " min: " + currentMinTemp);

		}

		for (Rain value : rainTimeMap.values()) {
			if (value != null) {
				willItRainTomorrow = true;
			}
		}

		Double averageHumidity = humidityList.stream().mapToDouble(val -> val).average().orElse(0.0);

		CombinedForecastData combinedForecastData = new CombinedForecastData(maximalTemperature, minimalTemperature,
				averageHumidity, willItRainTomorrow);
		
		return combinedForecastData;

//		System.out.println(maximalTemperature);
//		System.out.println(minimalTemperature);
//		System.out.println(humidityList);
//		System.out.println(averageHumidity);
//		System.out.println("Will it rain: " + willItRainTomorrow);
//		System.out.println(rainTimeMap);

	}

	public double findMinMaxTemperature() {
		int hour = LocalDateTime.now().getHour();

		double timeLeftTill24 = 24 - hour;
		double countTill24 = Math.ceil(timeLeftTill24 / 3);

		System.out.println(hour);
		System.out.println(timeLeftTill24);
		System.out.println(countTill24);

		return countTill24;

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

		CalendarWeatherData calendarData = new CalendarWeatherData(temperature, humidity, weatherDescription,
				maxTemperature, minTemperature);
		return calendarData;

	}

}
