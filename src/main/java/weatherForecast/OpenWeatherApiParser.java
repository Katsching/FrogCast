package weatherForecast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import DailyWeatherJson.Daily;
import DailyWeatherJson.FullOneApiCall;
import communication.JsonCreator;

public class OpenWeatherApiParser {

	public static FullOneApiCall getResponseFromWebsite(double latitude, double longitude, String apiKey) throws IOException {
		JsonObject messageJson = null; // url
		FullOneApiCall fullOneApiCall = new FullOneApiCall();
		URL url = combineURL(latitude, longitude, apiKey); // i.e
		// .
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
		}
		InputStreamReader in = new InputStreamReader(conn.getInputStream());
		BufferedReader br = new BufferedReader(in);
		String output;

		while ((output = br.readLine()) != null) {
//	                System.out.println(output);
//			messageJson = JsonCreator.convertStringToJsonObject(output);
			Gson gson = new Gson();
			fullOneApiCall = gson.fromJson(output, FullOneApiCall.class);
		}
		conn.disconnect();

		System.out.println(fullOneApiCall.getDaily().get(1).getDt());

		return fullOneApiCall;
	}

	private static URL combineURL(double latitude, double longitude, String apiKey) throws MalformedURLException {

		String urlFirstPart = "https://api.openweathermap.org/data/2.5/onecall?lat=";
		String urlSecondPart = "&lon=";
		String urlThirdPart = "&units=metric&exclude=hourly,minutely&appid=";

		String combinedURL = urlFirstPart + latitude + urlSecondPart + longitude + urlThirdPart + apiKey;
		URL url = new URL(combinedURL);
		return url;
	}

}