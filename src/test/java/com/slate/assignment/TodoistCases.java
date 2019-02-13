package com.slate.assignment;

import org.testng.annotations.Test;
import com.slate.base.InitDriver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class TodoistCases extends InitDriver {
  
	/**
     * Test case to create project via api and verify in mobile
     */
	@Test(priority=1)
	public void createProjectCase() throws Exception {
		AppiumDriver<MobileElement> driver = getDriver();
	}
}