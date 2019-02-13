package com.slate.utils;


import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class AppiumDriverUtility {
	
	/**
     * This Method is used to swipe
     * @param driver      -  Driver object
     * @param direction      -  Direction of swipe
     * @param offset      -  Offset
     */
	public static void swipe(AppiumDriver<MobileElement> driver, String direction, int offset) {
        int y = driver.manage().window().getSize().getHeight();
        int x = driver.manage().window().getSize().getWidth();
        TouchAction touchAction = new TouchAction(driver);
        if("left".equalsIgnoreCase(direction))
        {
            LoggerUtils.info("Swipping Left");
            touchAction.press(x-offset, y/2).moveTo(-(x-(2*offset)), 0).release().perform();
        }else if("right".equalsIgnoreCase(direction)){
            LoggerUtils.info("Swipping Right");
            touchAction.press(offset, y/2).moveTo((x-(2*offset)), 0).release().perform();
        }else if("up".equalsIgnoreCase(direction)){
        	LoggerUtils.info("Swipping Up");
            touchAction.press(x/2, offset).moveTo(0, y-(2*offset)).release().perform();
        }else if("down".equalsIgnoreCase(direction)){
        	LoggerUtils.info("Swipping Down");
            touchAction.press(x/2, y-offset).moveTo(0, -(y-(2*offset))).release().perform();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
	
	/**
     * This Method is used to hide keyboard
     * @param driver      -  Driver object
     */
	public static void hideKeyboard(AppiumDriver<MobileElement> driver) {
		try {
			Thread.sleep(1000);
			driver.hideKeyboard();	
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
     * This Method is used to swipe until element
     * @param driver      -  Driver object
     * @param mobileElement      -  element reference
     * @param direction      -  Direction of swipe
     */
	public static void swipeUntilElement(AppiumDriver<MobileElement> driver, MobileElement mobileElement, String direction) {
        for (int i = 0; i <= 4; i++) {
            try {
                if (WaitHelperUtil.isElementDisplayed(driver, mobileElement) == true) {
                    break;
                } else {
                    if (direction.toLowerCase() == "up") {
                        swipe(driver, "up", 10);
                    } else if (direction.toLowerCase() == "down") {
                        swipe(driver, "down", 10);
                    } else if (direction.toLowerCase() == "right") {
                        swipe(driver, "right", 10);
                    } else if (direction.toLowerCase() == "left") {
                        swipe(driver, "left", 10);
                    }
                }
            } catch (Exception e) {
                System.err.println("Element not visible");
            }
        }
    }
}
