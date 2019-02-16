package com.slate.apiTasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import com.google.gson.Gson;
import com.slate.dataaccess.ConfigReader;
import com.slate.utils.ApiUtil;
import com.slate.utils.LoggerUtils;

public class ApiTasks {
	
	/**
     * This Method is used to create unique project via API.
     * From response i am fetching project id
     */
	public static void createProjectViaApi() throws ParseException, URISyntaxException, IOException {
		/** Generating random and unique project name to make the test case dynamic */
		LoggerUtils.info("Generating random and unique project name");
		setProject_name("Test project " + generateRandomNumber());
		LoggerUtils.info("Project name is " + project_name);
		HashMap<String,String> hm = new HashMap<String, String>();
        hm.put("content-type","application/json");
        hm.put("X-Request-Id", generateRandomNumber());
        hm.put("Authorization", ConfigReader.getProperty("userAuth"));   
        Map<String, Object> requestParams = new HashMap<String, Object>();
        requestParams.put("id", 1234);
        requestParams.put("name", project_name);
        requestParams.put("comment_count", 0);
        requestParams.put("order", 1);
        requestParams.put("indent", 1);
        String jsonStr = jSONString(requestParams);
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(ApiUtil.post(ConfigReader.getProperty("todoistApi"), "API/v8/projects", hm, jsonStr));
        LoggerUtils.info("Response from create project api is: " + json);
        setProject_id(json.get("id").toString());
        LoggerUtils.info("Project id from the response is " + project_id);
	}
	
	/**
     * This Method is used to generate random number so that we generate unique project / task name
     */
	public static String generateRandomNumber() {
		Random rand = new Random();
		int  n = rand.nextInt(1000000);
		return Integer.toString(n);
	}
	
	/**
     * This Method is used to get all active tasks using getTasks api.
     * Note: It returns json array.
     */
	public static String getTasksApi() throws URISyntaxException, IOException, ParseException {
		HashMap<String,String> hm=new HashMap<String, String>();
        hm.put("Authorization", ConfigReader.getProperty("userAuth"));
        JSONParser parser = new JSONParser();
        Object json = parser.parse(ApiUtil.get(ConfigReader.getProperty("todoistApi"), "API/v8/tasks" , hm));

        return json.toString();
	}
	
	/**
     * This Method is used to get list of all projects from the account.
     * Note: It returns json array.
     */
	public static String getProject() throws URISyntaxException, IOException, ParseException {
		HashMap<String,String> hm=new HashMap<String, String>();
        hm.put("Authorization", ConfigReader.getProperty("userAuth"));
        JSONParser parser = new JSONParser();
        Object json = parser.parse(ApiUtil.get(ConfigReader.getProperty("todoistApi"), "API/v8/projects" , hm));

        return json.toString();
	}
	
	/**
     * This Method is used to delete all the project which was created during test.
     * Note: This helps us to clean up test data during after completion of test suite.
     */
	public static void deleteAllProjects() throws ParseException, URISyntaxException, IOException {
		HashMap<String,String> hm=new HashMap<String, String>();
		String response = getProject(); //Getting all projects from the account
		JSONParser parser = new JSONParser();
		JSONObject js = new JSONObject();
		Object obj = parser.parse(response);
		JSONArray jsons = (JSONArray) obj; 
		for(int i=0;i<jsons.size();i++) { //traverse through all the projects id from response
			js = (JSONObject) jsons.get(i);
			hm.put("Authorization", ConfigReader.getProperty("userAuth"));   
	        ApiUtil.delete(ConfigReader.getProperty("todoistApi"), "API/v8/projects/" + js.get("id"), hm);
		}
	}
	
	public static String jSONString(Map<String, Object> requestParams) {
        return new Gson().toJson(requestParams);
    }
	
	public static String project_name, project_id, task_name;
	
	/** Getter and setter methods for project name, project id and task name */
	
	public static String getProject_name() {
		return project_name;
	}

	public static String getProject_id() {
		return project_id;
	}

	public static void setProject_id(String project_id) {
		ApiTasks.project_id = project_id;
	}

	public static void setProject_name(String project_name) {
		ApiTasks.project_name = project_name;
	}
	
	public static String getTask_name() {
		return task_name;
	}

	public static void setTask_name(String task_name) {
		ApiTasks.task_name = task_name;
	}
	
}
