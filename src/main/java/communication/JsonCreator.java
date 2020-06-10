package communication;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonCreator {
	
	
	public static void main(String[] args) {
		JsonObject json = convertStringToJsonObject();
		getSpecificBooleanAttribute(json, "rain");
	}
	

	private static JsonObject convertStringToJsonObject() {
		
		String jsonString = "{\r\n" + 
				"    \"rain\": false\r\n" + 
				"}";
		JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
		return jsonObject;
	}
	
	
	private static boolean getSpecificBooleanAttribute(JsonObject json, String attribute) {
		Boolean attributeValue = json.get("rain").getAsBoolean();
		System.out.println(attributeValue);
		return attributeValue;
	}
}
