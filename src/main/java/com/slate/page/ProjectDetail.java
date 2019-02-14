package com.slate.page;

import java.util.List;
import org.openqa.selenium.support.PageFactory;
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

}
