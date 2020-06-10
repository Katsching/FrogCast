package communication;

import java.util.Date;

public class OutputMessage {

	private String sender;
	private String message;
	private String date;
	
	
	public OutputMessage(String sender, String message, String date) {
		super();
		this.sender = sender;
		this.message = message;
		this.date = date;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	
}
