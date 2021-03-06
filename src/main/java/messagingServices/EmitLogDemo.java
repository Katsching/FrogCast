package messagingServices;
import com.rabbitmq.client.ConnectionFactory;

import aiPlanning.AI_Solver;
import aiPlanning.AI_SolverDemo;
import weatherForecast.WeatherService;

import com.rabbitmq.client.Connection;

import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.openweathermap.api.model.currentweather.CurrentWeather;

import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;

/**
 * Send messages via RabbitMQ to a topic with the name frog_data
 */
public class EmitLogDemo {

	private static final String EXCHANGE_NAME = "frog_data";
	private static final String city = "Backnang";
	private static final String countryCode = "DE";

	public static void main(String[] argv) throws Exception {
		sendMessageToRaspberry(20, 50);
	}

	/**
	 * Sends a message to the Raspberry with the position and the color of the smart weather frog
	 * 
	 * @param temperature
	 * @param humidity
	 * @throws Exception
	 */
	public static void sendMessageToRaspberry(double temperature, double humidity) throws Exception {
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
			CurrentWeather currentWeather = weatherService.getWeatherFromCity(city, countryCode);
			String weather = currentWeather.getWeather().get(0).getMain();
			String position = weatherService.convertWeatherToPosition(weather);
			String color = weatherService.convertWeatherToColor(weather);
			
			URL res = AI_SolverDemo.class.getClassLoader().getResource("problem.pddl");
			File file = Paths.get(res.toURI()).toFile();
			String absolutePath = file.getAbsolutePath();
		    Path problemPath = Paths.get(absolutePath);
		    List<String> lines = Files.readAllLines(problemPath, StandardCharsets.UTF_8);
		    String ledStatusString = lines.get(9);
		    String colorStatusString = lines.get(10);
		    
		    String ledStatus = lines.get(9).substring(ledStatusString.indexOf("LEDStatus") + 10, ledStatusString.length()).replace(")", "");
		    String colorStatus = lines.get(10).substring(colorStatusString.indexOf("colorStatus") + 12, colorStatusString.length()).replace(")", "");
		    
			
			List<String> planList = AI_SolverDemo.getPlanAndUpdateProblem(position, color);
					    

		    System.out.println(planList);
		    
			
			for(String plan : planList) {
				if(plan.contains("movestate")) {
					String[] planParts = plan.split(" ");
					String fromPosition = planParts[1];
					String toPosition = planParts[2].replace(")\"", "");
					String message = createJsonObject(fromPosition, colorStatus, temperature , humidity).toString();
					channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
					System.out.println(" [x] Sent '" + message + "'");
					String message2 = createJsonObject(toPosition, colorStatus, temperature , humidity).toString();
					channel.basicPublish(EXCHANGE_NAME, "", null, message2.getBytes("UTF-8"));
					System.out.println(" [x] Sent '" + message2 + "'");
					ledStatus = toPosition;
				}
				else {
					String[] planParts = plan.split(" ");
					String fromColor = planParts[1];
					String toColor = planParts[2].replace(")\"", "");
					String message = createJsonObject(ledStatus, fromColor, temperature , humidity).toString();
					channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
					System.out.println(" [x] Sent '" + message + "'");
					String message2 = createJsonObject(ledStatus, toColor, temperature , humidity).toString();
					channel.basicPublish(EXCHANGE_NAME, "", null, message2.getBytes("UTF-8"));
					System.out.println(" [x] Sent '" + message2 + "'");
					colorStatus = toColor;
				}
			}
			System.out.println(weather);
			
		}
	}
	
	/**
	 * Converts the parameters into a valid JSON Object
	 * 
	 * @param position
	 * @param color
	 * @param temperature
	 * @param humidity
	 * @return
	 */
	private static JsonObject createJsonObject(String position, String color, double temperature, double humidity) {
		JsonObject outputActuators = new JsonObject();
		outputActuators.addProperty("position", position);
		outputActuators.addProperty("color", color);
		outputActuators.addProperty("temperature", temperature);
		outputActuators.addProperty("humidity", humidity);
		return outputActuators;
	}
}
