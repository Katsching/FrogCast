package aiPlanning;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.jni.Time;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A class which sends a domain.pddl and problem.pddl file to solver.planning.domains and parses the result to get the plan.
 */
public class AI_Solver {

	/**
	 * main class for testing
	 * @param args
	 */
	public static void main(String[] args){
		try {
			getPlanAndUpdateProblem("top", "white");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Converts the domain and problem PDDL files to a JSON document and sends it via POST request to solver.planning.domains,
	 * afterwards parsing the result for the plan
	 * 
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static List<String> sendPostToSolver() throws ClientProtocolException, IOException, URISyntaxException {

		URL domainURL = AI_Solver.class.getClassLoader().getResource("domain.pddl");
		File domainFile = Paths.get(domainURL.toURI()).toFile();
		
		URL problemURL = AI_Solver.class.getClassLoader().getResource("problem.pddl");
		File problemFile = Paths.get(problemURL.toURI()).toFile();

		String domainContent = Files.readString(domainFile.toPath(), StandardCharsets.US_ASCII);
		String problemContent = Files.readString(problemFile.toPath(), StandardCharsets.US_ASCII);
		
		JsonObject fullJson = new JsonObject();
		fullJson.addProperty("domain", domainContent);
		fullJson.addProperty("problem", problemContent);
		
		System.out.println(fullJson.toString());

		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://solver.planning.domains/solve");
		
		

		StringEntity entity = new StringEntity(fullJson.toString());
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		CloseableHttpResponse response = client.execute(httpPost);
		response.getEntity();
		String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		
		JsonObject responseJson = new JsonParser().parse(responseBody).getAsJsonObject();
		JsonObject result = responseJson.getAsJsonObject("result");
		JsonArray planJsonArray = result.getAsJsonArray("plan");
		
		List<String> planList = new ArrayList<String>();
		
		for(JsonElement planElement: planJsonArray) {
			JsonObject planJson = planElement.getAsJsonObject();
			JsonElement name = planJson.getAsJsonPrimitive("name");
			planList.add(name.toString());
		}
//		JsonElement jsonElement = planJsonArray.get(0)
//		System.out.println(result.toString());
		client.close();
		
		return(planList);
	}
	
	/**
	 * Replaces the goal of the problem.pddl file with the goal we are searching for, calls the sendPostToSolver method and
	 * updates the initial state to the previous goal state since that is the position and color the smart weather frog is in.
	 * 
	 * @param position
	 * @param color
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static List<String> getPlanAndUpdateProblem(String position, String color) throws IOException, URISyntaxException {
		writeLine(30, "\t\t(LEDStatus " + position + ")");
		writeLine(31, "\t\t(colorStatus " + color + ")");
		//Dummy data for demo
		
//		writeLine(10, "\t\t(LEDStatus " + "bot)");
//		writeLine(11, "\t\t(colorStatus " + "magenta)");
//		
		
		List<String> planList;
		try {
			planList = sendPostToSolver();
		} catch (NullPointerException e) {
			List<String> emptyList = new ArrayList<String>();
			return emptyList;
		}
		
		writeLine(10, "\t\t(LEDStatus " + position + ")");
		writeLine(11, "\t\t(colorStatus " + color + ")");
		
		return(planList);
	}
	
	/**
	 * Updates a specific line in the problem.pddl file
	 * @param lineNumber
	 * @param data
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private static void writeLine(int lineNumber, String data) throws IOException, URISyntaxException {
		URL res = AI_Solver.class.getClassLoader().getResource("problem.pddl");
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();
	    Path problemPath = Paths.get(absolutePath);
	    List<String> lines = Files.readAllLines(problemPath, StandardCharsets.UTF_8);
	    lines.set(lineNumber - 1, data);
	    Files.write(problemPath, lines, StandardCharsets.UTF_8);
	}
}
