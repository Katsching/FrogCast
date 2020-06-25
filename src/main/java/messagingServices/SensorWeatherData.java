package messagingServices;

public class SensorWeatherData {

	private double temperature;
	private double humidity;
	private boolean rain;

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getHumidity() {
		return humidity;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public boolean isRain() {
		return rain;
	}

	public void setRain(boolean rain) {
		this.rain = rain;
	}

	public SensorWeatherData(double temperature, double humidity, boolean rain) {
		super();
		this.temperature = temperature;
		this.humidity = humidity;
		this.rain = rain;
	}

	public SensorWeatherData() {
	}
}
