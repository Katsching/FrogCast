package weatherForecast;

public class CombinedForecastData {
	private double maxTemperature;
	private double minTemperature;
	private double averageHumidity;
	private String rainDescription;

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

	public String getRainDescription() {
		return rainDescription;
	}

	public void setRainDescription(String rainDescription) {
		this.rainDescription = rainDescription;
	}

	public CombinedForecastData(double maxTemperature, double minTemperature, double averageHumidity,
			String rainDescription) {
		super();
		this.maxTemperature = maxTemperature;
		this.minTemperature = minTemperature;
		this.averageHumidity = averageHumidity;
		this.rainDescription = rainDescription;
	}

}
