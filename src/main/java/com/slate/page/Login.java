package com.slate.page;

import org.openqa.selenium.support.PageFactory;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Login {
	
	/**
     * Constructor to initialize elements
     */
	public Login(AppiumDriver<MobileElement> driver) {
	      
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

}
