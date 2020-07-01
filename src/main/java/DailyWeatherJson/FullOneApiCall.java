package DailyWeatherJson;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class FullOneApiCall {
	private double lat;
	private double lon;
	private String timezone;
	private String timezone_offset;
	
	@SerializedName("daily")
	private List<DailyWeather> daily;
	
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}
	public String getTimezone_offset() {
		return timezone_offset;
	}
	public void setTimezone_offset(String timezone_offset) {
		this.timezone_offset = timezone_offset;
	}
	public List<DailyWeather> getDaily() {
		return daily;
	}
	public void setDaily(List<DailyWeather> daily) {
		this.daily = daily;
	}

	
	
}
