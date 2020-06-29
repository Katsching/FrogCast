package weatherForecast;

public class CombinedForecastData {
	private double maxTemperature;
	private double minTemperature;
	private double averageHumidity;
	private boolean willItRain;

	public double getMaxTemperature() {
		return maxTemperature;
	}

	public void setMaxTemperature(double maxTemperature) {
		this.maxTemperature = maxTemperature;
	}

	public double getMinTemperature() {
		return minTemperature;
	}

	public void setMinTemperature(double minTemperature) {
		this.minTemperature = minTemperature;
	}

	public double getAverageHumidity() {
		return averageHumidity;
	}

	public void setAverageHumidity(double averageHumidity) {
		this.averageHumidity = averageHumidity;
	}

	public boolean isWillItRain() {
		return willItRain;
	}

	public void setWillItRain(boolean willItRain) {
		this.willItRain = willItRain;
	}

	public CombinedForecastData(double maxTemperature, double minTemperature, double averageHumidity,
			boolean willItRain) {
		super();
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
		this.averageHumidity = averageHumidity;
		this.willItRain = willItRain;
	}

}
