package messagingServices;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import calendarFunctions.CalendarQuickstart;
import communication.JsonCreator;
import weatherForecast.CalendarWeatherData;
import weatherForecast.CombinedForecastData;
import weatherForecast.CombinedWeatherData;
import weatherForecast.WeatherService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;
import java.util.spi.CalendarDataProvider;

import org.openweathermap.api.model.currentweather.CurrentWeather;
import org.openweathermap.api.model.forecast.daily.DailyForecast;

import com.google.gson.JsonObject;

/**
 * 
 * Main class of the system. Waits for messages from the sensors, processes the data and send to the smart weather frog 
 *
 */
public class ReceiveLogs {
	private static final String EXCHANGE_NAME = "sensor_data";
	private static final String LOCATION = "Backnang";
	private static final String COUNTRYCODE = "DE";


	/**
	 * Main method of the system
	 * 
	 * @param argv
	 * @throws Exception
	 */
	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, EXCHANGE_NAME, "");

		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {

			// get the message as String object
			String messageString = new String(delivery.getBody(), "UTF-8");

			// convert the message from String to JSON object
			JsonObject messageJson = JsonCreator.convertStringToJsonObject(messageString);
			System.out.println("test");
			// extract values from message
			double temperature = JsonCreator.getSpecificDoubleAttribute(messageJson, "temperature");
			double humidity = JsonCreator.getSpecificDoubleAttribute(messageJson, "humidity");
			boolean rain = JsonCreator.getSpecificBooleanAttribute(messageJson, "rain");
			
			double roundedTemperature = (double)Math.round(temperature * 1d) / 1d;
			double roundedHumidity = (double)Math.round(humidity * 1d) / 1d;

			// print extracted values
			System.out.println("Measured Temperature: " + temperature + "\nHumidity: " + humidity + "\nRain: " + rain);

			WeatherService weatherService = new WeatherService();
			CurrentWeather currentWeather = weatherService.getWeatherFromCity(LOCATION, COUNTRYCODE);
			CalendarWeatherData calendarData = weatherService.createCalendarDataCurrent(currentWeather);
			
			CombinedForecastData combinedForecastData = weatherService.getWeatherForecastFromCity();

			System.out.println(calendarData.getMainWeather());
			System.out.println("City temperature: " + calendarData.getTemperature());

			updateCalendar(roundedTemperature, roundedHumidity, rain, calendarData, combinedForecastData);
			EmitLog emitLog = new EmitLog();
			try {
				emitLog.sendMessageToRaspberry(roundedTemperature, roundedHumidity);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		};

		channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
		});

	}

	/**
	 * Method to update 2 all-day entries in the Google Calendar of the user
	 * 
	 * @param temperature
	 * @param humidity
	 * @param rain
	 * @param calenderCurrentWeatherData
	 * @param combinedForecastData
	 */
	private static void updateCalendar(double temperature, double humidity, boolean rain,
			CalendarWeatherData calenderCurrentWeatherData, CombinedForecastData combinedForecastData) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		// get today's date
		Calendar todaysDate = Calendar.getInstance();
		String todaysDateString = df.format(todaysDate.getTime());

		// get tomorrow's date
		Calendar tomorrowsDate = Calendar.getInstance();
		tomorrowsDate.add(Calendar.DAY_OF_MONTH, 1);
		String tomorrowsDateString = df.format(tomorrowsDate.getTime());

		String prettyTodaysWeatherString = "Main Weather: " + calenderCurrentWeatherData.getMainWeather()
				+ "\nMeasured Temperature: " + temperature + "\nHumidity: " + humidity + "\nRain: " + rain
				+ "\nCity temperature: " + calenderCurrentWeatherData.getTemperature();

		String prettyTomorrowsWeatherString = "Maximum Temperature: " + combinedForecastData.getMaxTemperature()
				+ "\nMinimal Temperature: " + combinedForecastData.getMinTemperature() + "\nHumidity: "
				+ combinedForecastData.getAverageHumidity() + "\nMain Weather: "
				+ combinedForecastData.getWeatherDescription();

		// create/update calendar entries
		try {
			CalendarQuickstart.updateEvent(calenderCurrentWeatherData.getMainWeather(), LOCATION + ", " + COUNTRYCODE,
					prettyTodaysWeatherString, todaysDateString, "thisistheeventidofthese24hours");
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
		try {
			CalendarQuickstart.updateEvent("Frog Cast", LOCATION + ", " + COUNTRYCODE,
					prettyTomorrowsWeatherString, tomorrowsDateString, "thisistheeventidofthecoming24hours");
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
