package messagingServices;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import communication.JsonCreator;
import weatherForecast.CalendarWeatherData;
import weatherForecast.CombinedWeatherData;
import weatherForecast.WeatherService;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.spi.CalendarDataProvider;

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
        CombinedWeatherData combinedData = weatherService.getWeatherFromCity("Stuttgart", "DE");
        CalendarWeatherData calendarData = weatherService.createCalendarData(combinedData);
        
        System.out.println(calendarData.getMainWeather());
        System.out.println("City temperature: " + calendarData.getTemperature());
        
        
        
        
    };

    channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    
  }
  
  
  
  public ConnectionHelper openConnection() throws IOException, TimeoutException {
	  ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
	    String queueName = channel.queueDeclare().getQueue();
	    channel.queueBind(queueName, EXCHANGE_NAME, "");
	    
	    
	    ConnectionHelper connectionHelper = new ConnectionHelper();
	    connectionHelper.setChannel(channel);
	    connectionHelper.setQueueName(queueName);
	    return connectionHelper;
  }
  
  public SensorWeatherData getWeatherDataFromChannel(ConnectionHelper connectionHelper) throws IOException {
	  SensorWeatherData sensorWeatherData = new SensorWeatherData();
	  
	  DeliverCallback deliverCallback = (consumerTag, delivery) -> {
	    	
	    	// get the message as String object
	        String messageString = new String(delivery.getBody(), "UTF-8");
	        
	        // convert the message from String to JSON object
	        JsonObject messageJson = JsonCreator.convertStringToJsonObject(messageString);
	        
	        // extract values from message
	        double temperature = JsonCreator.getSpecificDoubleAttribute(messageJson, "temperature");
	        double humidity = JsonCreator.getSpecificDoubleAttribute(messageJson, "humidity");
	        boolean rain = JsonCreator.getSpecificBooleanAttribute(messageJson, "rain");
	        
	        sensorWeatherData.setTemperature(temperature);
	        sensorWeatherData.setHumidity(humidity);
	        sensorWeatherData.setRain(rain);
	        
	        // print extracted values
	        System.out.println("Temperature: " + temperature + "\nHumidity: " + humidity + "\nRain: " + rain);
	    };
	    connectionHelper.getChannel().basicConsume(connectionHelper.getQueueName(), true, deliverCallback, consumerTag -> { });
	    
	    return sensorWeatherData;    
  }
}
