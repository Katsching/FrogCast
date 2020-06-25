package messagingServices;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
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

			String message = argv.length < 1 ? "info: Hello World!" : String.join(" ", argv);

			channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
			System.out.println(" [x] Sent '" + message + "'");
		}
	}
	
	private void createJsonObject() {
		
	}
}
