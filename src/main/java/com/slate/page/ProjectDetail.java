package com.slate.page;

import static org.testng.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import com.slate.apiTasks.ApiTasks;
import com.slate.utils.AppiumDriverUtility;
import com.slate.utils.LoggerUtils;
import com.slate.utils.WaitHelperUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProjectDetail {
	
	@AndroidFindBy(xpath = "//*[@resource-id='android:id/text1'][@text='Manage projects']")
	private MobileElement manageProjects;
			
	@AndroidFindBy(id="com.todoist:id/name")
    private List <MobileElement>  projectNames;
	
	@AndroidFindBy(id="com.todoist:id/fab")
    private MobileElement addTask;
	
	@AndroidFindBy(id="android:id/message")
    private MobileElement taskField;
	
	@AndroidFindBy(id="android:id/button1")
    private MobileElement enter;
	
	@AndroidFindBy(id="com.todoist:id/text")
    private MobileElement taskName;
	
	/**
     * Constructor to initialize elements
     */
	public ProjectDetail(AppiumDriver<MobileElement> driver) {
	      
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	/**
     * This Method is to verify project from manage projects, Will swipe if the list is long
     * @param driver      -  Driver object
     */
	public Boolean verifyProject(AppiumDriver<MobileElement> driver) throws InterruptedException {
		LoggerUtils.info("Verifying project name in manage project tab");
		AppiumDriverUtility.swipeUntilElement(driver, manageProjects, "down");
		manageProjects.click();
		int k = projectNames.size();
		int i = 0;
		while(k != 0 && i <= 2){
			for (int j=0; j<=projectNames.size()-1;j++) {
				if (projectNames.get(j).getText().equals(ApiTasks.project_name)) {
					return true;
				}
			}
			AppiumDriverUtility.swipe(driver, "down", 10);
			WaitHelperUtil.waitfor(2);
			k = projectNames.size();
			i++;
		}
		return false;	
	}
	
	/**
     * This Method is to verify project from manage projects, Will swipe if the list is long
     * @param driver      -  Driver object
     */
	private void selectProject(AppiumDriver<MobileElement> driver) {
		//Logic to select the correct project from the list
		if (projectNames.size() == 1 && projectNames.get(0).getText().equals(ApiTasks.getProject_name())) {
			projectNames.get(0).click();
		}
		
		else if(projectNames.size() >= 1) {
			driver.findElement(By.xpath("//*[@resource-id='com.todoist:id/name']"+ "[@text=" + "'" + ApiTasks.getProject_name()+ "'" + "]")).click();
		}
		
		else {
			fail("Cannot find project with name: " + ApiTasks.getProject_name());
		}	
	}
	
	/**
     * This Method is to Create Task from mobile app
     * @param driver      -  Driver object
     */
	public void createTask(AppiumDriver<MobileElement> driver) throws Exception {
		selectProject(driver);
		addTask.click();
		ApiTasks.setTask_name("Test task " + ApiTasks.generateRandomNumber());
		LoggerUtils.info("Creating uniquie and random task " + ApiTasks.task_name + " for the project " + ApiTasks.project_name);
		taskField.sendKeys(ApiTasks.getTask_name());
		enter.click();
		/** Verify if successfully created on the UI */
		AppiumDriverUtility.hideKeyboard(driver);
		driver.navigate().back();
		Homepage home = new Homepage(driver);
		home.clickHomeMenu(driver);
		selectProject(driver);
		LoggerUtils.info("Veryfying if task is successfully created on the app");
		Assert.assertEquals(taskName.getText(), ApiTasks.task_name, "Task is not created");
	}
	
	/**
     * This Method is to verify task name from get tasks API response
     */
	public Boolean verifyTaskName() throws ParseException, URISyntaxException, IOException {
		LoggerUtils.info("Verifying task name from getTasks api response");
		String response = ApiTasks.getTasksApi();
		LoggerUtils.info("Response from get Task name api is: " + response);
		JSONParser parser = new JSONParser();
		JSONObject js = new JSONObject();
		Object obj = parser.parse(response);
		JSONArray jsons = (JSONArray) obj; 
		for(int i=0;i<jsons.size();i++) { //traverse through all the task names from response
			js = (JSONObject) jsons.get(i);			
			if (js.get("content").equals(ApiTasks.task_name)) {
				return true;
			}
		}
		return false;
	}

}
