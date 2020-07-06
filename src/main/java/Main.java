import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Date;
import java.util.Calendar;

import calendarFunctions.CalendarQuickstart;
import weatherForecast.WeatherService;

public class Main {

    public static void main(String[] args) {
    	WeatherService weatherServices = new WeatherService();
    	weatherServices.getWeatherFromCity("Backnang", "DE");
    	
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	
    	// get today's date
    	Calendar todaysDate = Calendar.getInstance();
        String todaysDateString = df.format(todaysDate.getTime());
    	
    	// get tomorrow's date
    	Calendar tomorrowsDate = Calendar.getInstance();
        tomorrowsDate.add(Calendar.DAY_OF_MONTH, 1);
        String tomorrowsDateString = df.format(tomorrowsDate.getTime());
    	
//    	Date currentDateObj = new Date();
//       	
//    	String dateString = df.format(currentDateObj);
//    	System.out.println(dateString);
    	
//    	try {
//			CalendarQuickstart.createNewEvent("Test", "Stuttgart", "Wetterinfo", dateString);
//		} catch (GeneralSecurityException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
    	
//    	try {
//			CalendarQuickstart.updateEvent("Updated Today", "Stuggi", "Wetterinfo", todaysDateString, "thisistheeventidofthese24hours");
//		} catch (GeneralSecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
//    	try {
//			CalendarQuickstart.updateEvent("Updated Tomorrow", "Stuggi", "Wetterinfo", tomorrowsDateString, "thisistheeventidofthecoming24hours");
//		} catch (GeneralSecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

    }
    
    

}