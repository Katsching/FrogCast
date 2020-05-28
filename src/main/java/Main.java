import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import calendarFunctions.CalendarQuickstart;
import weatherForecast.WeatherService;

public class Main {

    public static void main(String[] args) {
    	WeatherService weatherServices = new WeatherService();
    	weatherServices.getWeatherFromCity("Stuttgart", "DE");
    	
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Date currentDateObj = new Date();
       	
    	String dateString = df.format(currentDateObj);
//    	createNewEvent("Test", "Stuttgart", "Wetterinfo", dateString);
    	try {
			CalendarQuickstart.updateEvent("UpdatedTest6", "Stuggi", "Wetterinfo", dateString);
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    

}