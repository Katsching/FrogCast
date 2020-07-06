package aiPlanning;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JavaFF_AI {

	public static void main(String[] args){
//		File domainFile = new File("C:\\Users\\dYTe\\git\\FrogCast\\src\\main\\java\\aiPlanning\\domain.pddl");
//		File problemFile = new File("C:\\Users\\dYTe\\git\\FrogCast\\src\\main\\java\\aiPlanning\\problem.pddl");
//
//		String domainContent = Files.readString(domainFile.toPath(), StandardCharsets.US_ASCII);
//		String problemContent = Files.readString(problemFile.toPath(), StandardCharsets.US_ASCII);
//
//		String test = "{\r\n" + 
//				"    \"domain\": \"(define (domain LEDPlanner)\\n\\t\\n\\t(:requirements :strips :typing) \\n\\t\\n\\t(:types\\n\\t\\tcolor position -object\\n\\t)\\n\\n\\t(:predicates\\n\\t\\t(LEDStatus ?s - position)\\n\\t\\t(LEDCanMove ?f -position ?t - position)\\n\\t\\t(colorStatus ?c - color)\\n\\t\\t(colorCanChange ?b - color ?e -color)\\n\\t\\t\\n\\t)\\n\\n\\t(:action moveState\\n\\t\\t:parameters (?f ?t - position)\\n\\t\\t:precondition (and \\n\\t\\t\\t(LEDStatus ?f)\\n\\t\\t\\t(LEDCanMove ?f ?t)\\n\\t\\t)\\n\\t\\t:effect (and \\n\\t\\t\\t(LEDStatus ?t)\\n\\t\\t\\t(not (LEDStatus ?f)) \\n\\t\\t)\\n\\t)\\n\\n\\t(:action changeColor\\n\\t\\t:parameters (?from ?to - color)\\n\\t\\t:precondition (and\\n\\t\\t\\t(colorStatus ?from)\\n\\t\\t\\t(colorCanChange ?from ?to)\\n\\t\\t\\t\\n\\t\\t)\\n\\t\\t:effect (and\\n\\t\\t\\t(colorStatus ?to )\\n\\t\\t\\t(not (colorStatus ?from))\\n\\t\\t)\\n\\t)\\n\\n\\n\\n)\\t\",\r\n" + 
//				"    \"problem\": \"(define (problem changeposition)\\n    \\n    (:domain LEDPlanner)\\n    (:objects \\n        bot mid top -position\\n        white blue magenta -color\\n    )\\n\\n    (:init \\n        (LEDStatus bot)\\n        (colorStatus white)\\n\\n        (LEDCanMove bot mid)\\n        (LEDCanMove mid top)\\n        (LEDCanMove mid bot)\\n        (LEDCanMove top mid)\\n\\n        (colorCanChange white blue)\\n        (colorCanChange blue magenta)\\n        (colorCanChange magenta white)\\n\\n        (colorCanChange magenta blue)\\n        (colorCanChange blue white)\\n        (colorCanChange white magenta)\\n    )\\n\\n    (:goal (and \\n        (LEDStatus top)\\n        (colorStatus blue)\\n    )\\n    )\\n  \\n)\"\r\n" + 
//				"}";
//
//		
//		JsonObject fullJson = new JsonObject();
//		fullJson.addProperty("domain", domainContent);
//		fullJson.addProperty("problem", problemContent);
//		
//		System.out.println(fullJson.toString());
//
//		CloseableHttpClient client = HttpClients.createDefault();
//		HttpPost httpPost = new HttpPost("http://solver.planning.domains/solve");
//
//		StringEntity entity = new StringEntity(fullJson.toString());
//		httpPost.setEntity(entity);
//		httpPost.setHeader("Accept", "application/json");
//		httpPost.setHeader("Content-type", "application/json");
//
//		CloseableHttpResponse response = client.execute(httpPost);
//		response.getEntity();
//		String responseBody = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
//		
//		JsonObject responseJson = new JsonParser().parse(responseBody).getAsJsonObject();
//		JsonObject result = responseJson.getAsJsonObject("result");
//		JsonArray planJsonArray = result.getAsJsonArray("plan");
//		
//		for(JsonElement planElement: planJsonArray) {
//			JsonObject planJson = planElement.getAsJsonObject();
//			JsonElement name = planJson.getAsJsonPrimitive("name");
//			System.out.println("test: " + name);	
//		}
////		JsonElement jsonElement = planJsonArray.get(0)
////		System.out.println(result.toString());
//		client.close();

		try {
			getPlanAndUpdateProblem("bot", "blue");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static List<String> sendPostToSolver() throws ClientProtocolException, IOException, URISyntaxException {

		URL domainURL = JavaFF_AI.class.getClassLoader().getResource("domain.pddl");
		File domainFile = Paths.get(domainURL.toURI()).toFile();
		
		URL problemURL = JavaFF_AI.class.getClassLoader().getResource("problem.pddl");
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
			System.out.println("test: " + name);
			planList.add(name.toString());
		}
//		JsonElement jsonElement = planJsonArray.get(0)
//		System.out.println(result.toString());
		client.close();
		
		return(planList);
	}
	
	private static List<String> getPlanAndUpdateProblem(String position, String color) throws IOException, URISyntaxException {
		writeLine(30, "\t\t(LEDStatus " + position + ")");
		writeLine(31, "\t\t(colorStatus " + color + ")");
		
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
	
	private static void writeLine(int lineNumber, String data) throws IOException, URISyntaxException {
		URL res = JavaFF_AI.class.getClassLoader().getResource("problem.pddl");
		File file = Paths.get(res.toURI()).toFile();
		String absolutePath = file.getAbsolutePath();
	    Path problemPath = Paths.get(absolutePath);
	    List<String> lines = Files.readAllLines(problemPath, StandardCharsets.UTF_8);
	    lines.set(lineNumber - 1, data);
	    Files.write(problemPath, lines, StandardCharsets.UTF_8);
	    System.out.println("test umschreiben");
	}
}
