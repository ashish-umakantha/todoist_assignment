package com.slate.base;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import com.slate.dataaccess.ConfigReader;
import com.slate.page.Onboarding;
import com.slate.utils.LoggerUtils;
import com.slate.utils.WaitHelperUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class InitDriver {
	
	private AppiumDriver<MobileElement> driver;
	public Onboarding onboarding;
	
	/**
     * Getter method to get instance of driver
     */
	public AppiumDriver<MobileElement> getDriver() {
		return driver;
	}

	/**
     * Setter method to set instance of driver
     */
	public void setDriver(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}

	@BeforeSuite
	public void before() throws Exception {
		/**Commenting the below line because to read appium logs, However the code works.
		 * Below method is used start appium server
		 */
//		startAppium();
	}
	
	public static AppiumDriverLocalService appiumService;
	public static String appiumServiceUrl;
	public static AppiumServiceBuilder builder;
	public static String currentDir = System.getProperty("user.dir");
	
	/**
     * Method to start appium server
     */
	public static void startAppium() {
		try {
			if (appiumService != null) {
				appiumService.stop();

			}
			builder = new AppiumServiceBuilder();
			builder.withLogFile(new File(currentDir + "/appiumLogs/" + System.currentTimeMillis() + ".text"));
			appiumService = AppiumDriverLocalService.buildService(builder);
			appiumService.start();
			appiumServiceUrl = appiumService.getUrl().toString();
			LoggerUtils.info("Appium server started at: - " + appiumServiceUrl);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}
		
	@AfterMethod(alwaysRun=true)
	  public void resetApp(Method method) throws Exception {
		//Cleaning up test data if any
		AppiumDriver<MobileElement> driver = getDriver();
		if (driver != null) {
			driver.quit();
	    }
	}
		
	@BeforeMethod(alwaysRun=true)  
	public void beforeMethod() throws Exception {
		setup();
	}
	
	/**
     * Method to setup driver capability
     */
	public void setup() throws Exception {
		new ConfigReader();
		LoggerUtils.info("Thread: " + Thread.currentThread().getName() + " :: " + "Testing in Device: " + ConfigReader.getProperty("deviceName"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformVersion", ConfigReader.getProperty("platformVersion")); 
        capabilities.setCapability("deviceName", ConfigReader.getProperty("deviceName"));
    	capabilities.setCapability("platformName", ConfigReader.getProperty("platformName"));
    	capabilities.setCapability("app", ConfigReader.getProperty("apkPath"));
        capabilities.setCapability("appWaitActivity", ConfigReader.getProperty("appWaitActivity"));
        capabilities.setCapability("newCommandTimeout", 60 * 7);
        /** Create AndroidDriver instance and connect to the Appium server */
        driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        WaitHelperUtil.setImplicitWait(10, TimeUnit.SECONDS, driver);
        setDriver(driver);
       /** Initializing onboarding page */
        onboarding = new Onboarding(driver);
	}
	
}
