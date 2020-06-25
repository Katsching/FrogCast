package messagingServices;

import com.rabbitmq.client.Channel;

public class ConnectionHelper {
	private String queueName;
	private Channel channel;
	
	public String getQueueName() {
		return queueName;
	}
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public Channel getChannel() {
		return channel;
	}
	public void setChannel(Channel channel) {
		this.channel = channel;
	}
	
	public ConnectionHelper(String queueName, Channel channel) {
		super();
		this.queueName = queueName;
		this.channel = channel;
	}
	
	public ConnectionHelper() {
		
	}
}
