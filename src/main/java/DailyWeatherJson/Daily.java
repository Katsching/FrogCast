package DailyWeatherJson;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Daily {

	private DailyWeather dailyWeather;

	public DailyWeather getDailyWeather() {
		return dailyWeather;
	}

	public void setDailyWeather(DailyWeather dailyWeather) {
		this.dailyWeather = dailyWeather;
	}



}
