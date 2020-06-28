package messagingServices;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import calendarFunctions.CalendarQuickstart;
import communication.JsonCreator;
import weatherForecast.CalendarWeatherData;
import weatherForecast.CombinedWeatherData;
import weatherForecast.WeatherService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeoutException;
import java.util.spi.CalendarDataProvider;

import org.openweathermap.api.model.currentweather.CurrentWeather;

import com.google.gson.JsonObject;
public class ReceiveLogs {
  private static final String EXCHANGE_NAME = "sensor data";

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
        
        // extract values from message
        double temperature = JsonCreator.getSpecificDoubleAttribute(messageJson, "temperature");
        double humidity = JsonCreator.getSpecificDoubleAttribute(messageJson, "humidity");
        boolean rain = JsonCreator.getSpecificBooleanAttribute(messageJson, "rain");
        
        // print extracted values
        System.out.println("Measured Temperature: " + temperature + "\nHumidity: " + humidity + "\nRain: " + rain);
        
        WeatherService weatherService = new WeatherService();
        CurrentWeather combinedData = weatherService.getWeatherFromCity("Stuttgart", "DE");
        CalendarWeatherData calendarData = weatherService.createCalendarData(combinedData);
        
        System.out.println(calendarData.getMainWeather());
        System.out.println("City temperature: " + calendarData.getTemperature());

        updateCalendar(temperature, humidity, rain, calendarData);
        
        
    };

    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
 
  }
  
  private static void updateCalendar(double temperature, double humidity, boolean rain, CalendarWeatherData calenderWeatherData) {
  	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
  	// get today's date
  	Calendar todaysDate = Calendar.getInstance();
      String todaysDateString = df.format(todaysDate.getTime());
  	
  	// get tomorrow's date
  	Calendar tomorrowsDate = Calendar.getInstance();
      tomorrowsDate.add(Calendar.DAY_OF_MONTH, 1);
      String tomorrowsDateString = df.format(tomorrowsDate.getTime());
      
     String prettyTodaysWeatherString = 
    		 "Main Weather: " + calenderWeatherData.getMainWeather() +
    		 "\nMeasured Temperature: " + temperature + 
    		 "\nHumidity: " + humidity + 
    		 "\nRain: " + rain + 
    		 "\nCity temperature: " + calenderWeatherData.getTemperature();
  
      // create/update calendar entries
  	try {
			CalendarQuickstart.updateEvent(calenderWeatherData.getMainWeather(), "Stuggi", prettyTodaysWeatherString, todaysDateString, "thisistheeventidofthese24hours");
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	
  	try {
			CalendarQuickstart.updateEvent("Updated Tomorrow", "Stuggi", "Wetterinfo", tomorrowsDateString, "thisistheeventidofthecoming24hours");
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
  
      

}
