package com.slate.page;

import org.openqa.selenium.support.PageFactory;
import com.slate.dataaccess.ConfigReader;
import com.slate.utils.AppiumDriverUtility;
import com.slate.utils.LoggerUtils;
import com.slate.utils.WaitHelperUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Login {
	
	@AndroidFindBy(id="com.todoist:id/email_exists_input")
    private MobileElement emailField;
	
	@AndroidFindBy(id="com.todoist:id/btn_continue_with_email")
    private MobileElement continueButton;
	
	@AndroidFindBy(id="com.todoist:id/log_in_password")
    private MobileElement passwordField;
	
	@AndroidFindBy(id="com.todoist:id/btn_log_in")
    private MobileElement loginBtn;
	
	@AndroidFindBy(id="com.todoist:id/log_in_forgot_password")
    private MobileElement forgotPassword;
	
	/**
     * Constructor to initialize elements
     */
	public Login(AppiumDriver<MobileElement> driver) {
	      
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	/**
     *  Method to login with email
     *  @param driver      -  Driver object
     */
	public Homepage loginWithEmail(AppiumDriver<MobileElement> driver) throws Exception {
		LoggerUtils.info("Entering credentials after reading from config file");
		WaitHelperUtil.waitForElementPresence(driver, emailField);
		emailField.sendKeys(ConfigReader.getProperty("email"));
		AppiumDriverUtility.hideKeyboard(driver);
		continueButton.click();
		WaitHelperUtil.waitForElementPresence(driver, passwordField);
		passwordField.sendKeys(ConfigReader.getProperty("password"));
		loginBtn.click();
		return new Homepage(driver);
	}

}
