package communication;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * used for creation of JSON from String objects and extraction of attribute values
 * @author dYTe
 *
 */
public class JsonCreator {
	
	
	public static void main(String[] args) {
		JsonObject json = convertStringToJsonObject("{\r\n" + 
				"    \"rain\": false\r\n" + 
				"}");
		getSpecificBooleanAttribute(json, "rain");
	}
	
	/**
	 * converts a String to a JSON object
	 * @param jsonString the String that is to be converted
	 * @return the converted JSON object
	 */
	public static JsonObject convertStringToJsonObject(String jsonString) {
		
		/*String jsonString = "{\r\n" + 
				"    \"rain\": false\r\n" + 
				"}";*/
		JsonObject jsonObject = new JsonParser().parse(jsonString).getAsJsonObject();
		return jsonObject;
	}
	
	/**
	 * gets a specified boolean attribute from a JSON object
	 * @param json the JSON object from which the extraction will be done
	 * @param attribute the attribute as String object of the boolean attribute
	 * @return the value of the boolean attribute
	 */
	public static boolean getSpecificBooleanAttribute(JsonObject json, String attribute) {
		boolean attributeValue = json.get(attribute).getAsBoolean();
		//System.out.println(attributeValue);
		return attributeValue;
	}
	
	/**
	 * gets a specified double attribute from a JSON object
	 * @param json the JSON object from which the extraction will be done
	 * @param attribute the attribute as String object of the double attribute
	 * @return the value of the double attribute
	 */
	public static double getSpecificDoubleAttribute(JsonObject json, String attribute) {
		double attributeValue = json.get(attribute).getAsDouble();
		return attributeValue;
	}
}
