package messagingServices;
import com.rabbitmq.client.ConnectionFactory;

import aiPlanning.AI_Solver;
import weatherForecast.CombinedForecastData;
import weatherForecast.WeatherService;

import com.rabbitmq.client.Connection;
import com.google.api.services.calendar.model.CreateConferenceRequest;
import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;

public class EmitLog {

	private static final String EXCHANGE_NAME = "sensor data";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
	    factory.setUsername("rasp");
	    factory.setPassword("1234");
	    factory.setHost("192.168.178.52");
	    factory.setPort(5672);
	    factory.setVirtualHost("/");
		try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
			channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

//			String message = argv.length < 1 ? "info: Hello World!" : String.join(" ", argv);
			WeatherService weatherService = new WeatherService();
			CombinedForecastData combinedForecastData = weatherService.getWeatherForecastFromCity();
			String weather = combinedForecastData.getMainWeather();
			String position = weatherService.convertWeatherToPosition(weather);
			String color = weatherService.convertWeatherToColor(weather);
			
			AI_Solver.getPlanAndUpdateProblem(position, color);
			
			String message = createJsonObject(position, color, 5.0 , 60.0).toString();

			System.out.println(weather);
			
			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
		}
	}
	
	private static JsonObject createJsonObject(String position, String color, double temperature, double humidity) {
		JsonObject outputActuators = new JsonObject();
		outputActuators.addProperty("position", position);
		outputActuators.addProperty("color", color);
		outputActuators.addProperty("temperature", temperature);
		outputActuators.addProperty("humidity", humidity);
		return outputActuators;
	}
}
