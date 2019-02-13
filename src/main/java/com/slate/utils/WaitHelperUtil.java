package com.slate.utils;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class WaitHelperUtil {
	
	private static final int DEFAULT_TIMEOUT = 20;
	public static final long Expicit_wait_time = 100;
	
	/**
     * This Method is used to check if specified element is displayed
     * @param driver      -  Driver object
     * @param mobileElement      -  element reference
     */
	public static boolean isElementDisplayed(AppiumDriver<MobileElement> driver, MobileElement element) {
		boolean elementDisplayed = false;
        try {
            if (element.isDisplayed()) {
                elementDisplayed = true;
            }
        }catch (Exception e){}
        return elementDisplayed;
    }
	
	/**
     * This Method is used to set implicit wait
     * @param timeout      -  Timeout value
     * @param unit      -  Timeout unit
     * @param driver      -  Driver object
     */
	public static void setImplicitWait(long timeout, TimeUnit unit, AppiumDriver<MobileElement> driver) {
		driver.manage().timeouts().implicitlyWait(timeout, unit == null ? TimeUnit.SECONDS : unit);
	}

	/**
     * This Method is used to wait until an element is present and timesout at specified time.
     * @param driver      -  Driver object
     * @param mobileElement      -  element reference
     * @param timeout      -  Timeout value
     */
	public static void waitForElementPresence(AppiumDriver<MobileElement> driver, MobileElement element, int timeout) throws Exception {
		WebDriverWait wait = new WebDriverWait(driver, timeout);
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
     * This Method is used to wait until an element is present and timesout at default time.
     * @param driver      -  Driver object
     * @param mobileElement      -  element reference
     */
	public static void waitForElementPresence(AppiumDriver<MobileElement> driver, MobileElement element) throws Exception {
		waitForElementPresence(driver, element, DEFAULT_TIMEOUT);
	}

	/**
     * This Method is used for hard wait
     * @param seconds      -  Wait time
     */
	public static void waitfor(int seconds) throws InterruptedException {
		Thread.sleep(seconds * 1000);
	}

}
