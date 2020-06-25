package calendarFunctions;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.EventDateTime;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

public class CalendarQuickstart {
    private static final String APPLICATION_NAME = "Google Calendar API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CalendarQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    
    public static void createEvent(String summary, String location, String description, String date, String eventId) throws GeneralSecurityException, IOException {
    	Calendar service = initiateTransport();
        
    	Event event = new Event()
        		    .setSummary(summary)
        		    .setLocation(location)
        		    .setDescription(description);
//         String eventId = date.replace("-", "");
//    	String eventID = "today";
            
         DateTime startDateTime = new DateTime(date);
         EventDateTime allDayTime = new EventDateTime()
             .setDate(startDateTime)
             .setTimeZone("Europe/Berlin");
         event.setStart(allDayTime);
         event.setEnd(allDayTime);
         
         event.setId(eventId);

         
         String calendarId = "primary";
    	 event = service.events().insert(calendarId, event).execute();
         
         System.out.printf("Event created: %s\n", event.getHtmlLink());
    }
    
    public static void updateEvent(String summary, String location, String description, String date, String eventId) throws GeneralSecurityException, IOException {
         Calendar service = initiateTransport();

         Event updatedEvent = new Event()
     		    .setSummary(summary)
     		    .setLocation(location)
     		    .setDescription(description);
      
      DateTime startDateTime = new DateTime(date);
      EventDateTime allDayTime = new EventDateTime()
          .setDate(startDateTime)
          .setTimeZone("Europe/Berlin");
      updatedEvent.setStart(allDayTime);
      updatedEvent.setEnd(allDayTime);
      
      String calendarId = "primary";
//      String eventId = date.replace("-", "");
     
      try {
    	  updatedEvent = service.events().update(calendarId, eventId, updatedEvent).execute();
      } catch (GoogleJsonResponseException e) {
    	  createEvent(summary, location, description, date, eventId);
      }
    }
    
    private static Calendar initiateTransport() throws GeneralSecurityException, IOException {
    	final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }
    
    
    private void createEventTitle() {
    	// format example: Rainy - 18�C
    }
    
    private void createWeatherDescription() {
    	//TODO
    	// example: UV value
    }
    
    private boolean eventIdExists(String eventId) throws GeneralSecurityException, IOException {
    	Calendar service = initiateTransport();
    	
    	Events events = service.events().list("primary").setPageToken(null).execute();
    	List<Event> items = events.getItems();
    	return true;
    }
    
}