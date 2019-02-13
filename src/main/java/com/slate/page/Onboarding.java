package com.slate.page;

import org.openqa.selenium.support.PageFactory;
import com.slate.utils.LoggerUtils;
import com.slate.utils.WaitHelperUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Onboarding {
	
	@AndroidFindBy(id="com.todoist:id/btn_welcome_continue_with_email")
    private MobileElement continueWithEmail;
	
	@AndroidFindBy(id="com.todoist:id/btn_google")
    private MobileElement continueWithGoogle;
	
	@AndroidFindBy(id="com.todoist:id/btn_facebook")
    private MobileElement continueWithFacebook;
	
	/**
     * Constructor to initialize elements
     */
	public Onboarding(AppiumDriver<MobileElement> driver) {
	  
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	/**
     *  Method to navigate to login with email
     *  @param driver      -  Driver object
     */
	public Login navigateToLoginWithEmail(AppiumDriver<MobileElement> driver) throws Exception { 
		LoggerUtils.info("Navigating to login screen");
		WaitHelperUtil.waitForElementPresence(driver, continueWithEmail);
		continueWithEmail.click();
		return new Login(driver);	
	}

}
