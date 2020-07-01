package DailyWeatherJson;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DailyWeather {
	private int dt;
	private int sunrise;
	private int sunset;
	private Temp temp;
	private Feels_like feels_like;
	private int pressure;
	private int humidity;
	private double dew_point;
	private double windspeed;
	private int wind_deg;
	
	@SerializedName("weather")
	private List<MainWeather> mainWeather;
	
	private int clouds;
	private double rain;
	private double uvi;
	public int getDt() {
		return dt;
	}
	public void setDt(int dt) {
		this.dt = dt;
	}
	public int getSunrise() {
		return sunrise;
	}
	public void setSunrise(int sunrise) {
		this.sunrise = sunrise;
	}
	public int getSunset() {
		return sunset;
	}
	public void setSunset(int sunset) {
		this.sunset = sunset;
	}
	public Temp getTemp() {
		return temp;
	}
	public void setTemp(Temp temp) {
		this.temp = temp;
	}
	public Feels_like getFeels_like() {
		return feels_like;
	}
	public void setFeels_like(Feels_like feels_like) {
		this.feels_like = feels_like;
	}
	public int getPressure() {
		return pressure;
	}
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}
	public int getHumidity() {
		return humidity;
	}
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}
	public double getDew_point() {
		return dew_point;
	}
	public void setDew_point(double dew_point) {
		this.dew_point = dew_point;
	}
	public double getWindspeed() {
		return windspeed;
	}
	public void setWindspeed(double windspeed) {
		this.windspeed = windspeed;
	}
	public int getWind_deg() {
		return wind_deg;
	}
	public void setWind_deg(int wind_deg) {
		this.wind_deg = wind_deg;
	}
	public List<MainWeather> getMainWeather() {
		return mainWeather;
	}
	public void setMainWeather(List<MainWeather> mainWeather) {
		this.mainWeather = mainWeather;
	}
	public int getClouds() {
		return clouds;
	}
	public void setClouds(int clouds) {
		this.clouds = clouds;
	}
	public double getRain() {
		return rain;
	}
	public void setRain(double rain) {
		this.rain = rain;
	}
	public double getUvi() {
		return uvi;
	}
	public void setUvi(double uvi) {
		this.uvi = uvi;
	}
	
	

}
