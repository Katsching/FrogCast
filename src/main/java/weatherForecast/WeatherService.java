package weatherForecast;

import org.openweathermap.api.DataWeatherClient;
import org.openweathermap.api.UrlConnectionDataWeatherClient;
import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.query.Language;
import org.openweathermap.api.query.QueryBuilderPicker;
import org.openweathermap.api.query.ResponseFormat;
import org.openweathermap.api.query.Type;
import org.openweathermap.api.query.UnitFormat;
import org.openweathermap.api.query.currentweather.CurrentWeatherOneLocationQuery;

public class WeatherService {

	public void getWeatherFromCity(String city, String countryCode) {
	 DataWeatherClient client = new UrlConnectionDataWeatherClient("e030cee405d4b9b32704de57c13778d2");
     CurrentWeatherOneLocationQuery currentWeatherOneLocationQuery = QueryBuilderPicker.pick()
             .currentWeather()                   // get current weather
             .oneLocation()                      // for one location
             .byCityName(city)             
             .countryCode(countryCode)            
             .type(Type.ACCURATE)                // with Accurate search
             .language(Language.ENGLISH)         // in English language
             .responseFormat(ResponseFormat.JSON)// with JSON response format
             .unitFormat(UnitFormat.METRIC)      // in metric units
             .build();
     CurrentWeather currentWeather = client.getCurrentWeather(currentWeatherOneLocationQuery);
     System.out.println(prettyPrint(currentWeather));
 }

	private static String prettyPrint(CurrentWeather currentWeather) {
		return String.format("City: " + currentWeather.getCityName() + ", country: " + currentWeather.getSystemParameters().getCountry() +
				", temperature: " + currentWeather.getMainParameters().getTemperature() + ", humidity: " + currentWeather.getMainParameters().getHumidity() +
				", pressure: " + currentWeather.getMainParameters().getPressure());
	}
}
