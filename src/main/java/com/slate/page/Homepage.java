package com.slate.page;

import org.openqa.selenium.support.PageFactory;
import com.slate.utils.WaitHelperUtil;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class Homepage {
	
	@AndroidFindBy(id="android:id/button3")
    private MobileElement neverAsk;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.todoist:id/toolbar']/android.widget.ImageButton[@index='0']")
	private MobileElement menuTab;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.todoist:id/name'][@text='Projects']")
	private MobileElement projectTab;
	
	@AndroidFindBy(id="android:id/content")
    private MobileElement projectExpanded;
	
	/**
     * Constructor to initialize elements
     */
	public Homepage(AppiumDriver<MobileElement> driver) {
	      
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	/**
     * This Method clicks on menu option
     * @param driver      -  Driver object
     */
	public void clickHomeMenu(AppiumDriver<MobileElement> driver) throws Exception {
		closePopIfAny(driver);
		WaitHelperUtil.waitfor(1);
		WaitHelperUtil.waitForElementPresence(driver, menuTab);
		menuTab.click();
		WaitHelperUtil.waitfor(1);
	}
	
	/**
     * This Method checks if pop up appears and closes if any
     * @param driver      -  Driver object
     */
	public void closePopIfAny(AppiumDriver<MobileElement> driver) {
		if (WaitHelperUtil.isElementDisplayed(driver, neverAsk)) {
			neverAsk.click();
		}
	}
	
	/**
     * This Method is used to navigate to project tab
     * @param driver      -  Driver object
     */
	public ProjectDetail navigateToProjectTab(AppiumDriver<MobileElement> driver) throws Exception {
		clickHomeMenu(driver);
		projectTab.click();
	
		return new ProjectDetail(driver);
	}
	
}
