package com.slate.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.slate.base.InitDriver;
import com.slate.utils.LoggerUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ExtentReportListener implements ITestListener, ISuiteListener{

	public ExtentReports extentReports = ExtentManager.getReporter("."+File.separator + "reports"+File.separator + "AutomationReport.html");
	public ExtentTest extentTest;

	/**
     *  This Method to belongs to ISuiteListener and will execute before the Suite start
     */
	@Override
	public synchronized void onStart(ISuite arg0) {
		LoggerUtils.info("About to begin executing Suite " + arg0.getName());
	}

	/**
     *  This Method to belongs to ISuiteListener and will execute once the Suite is finished
     */
	@Override
	public synchronized void onFinish(ISuite arg0) {
		LoggerUtils.info("About to end executing Suite " + arg0.getName());
		for (String s : Reporter.getOutput()) {
			extentReports.setTestRunnerOutput(s);
		}
		extentReports.flush();
	}

	/**
     *  This Method to belongs to ITestListener and will execute before starting of each Test
     */
	@Override
	public void onStart(ITestContext arg0) {
		LoggerUtils.info("About to begin executing Test cases");
	}

	/**
     *  This Method to belongs to ITestListener and will execute once the Test is finished
     */
	@Override
	public void onFinish(ITestContext arg0) {
		LoggerUtils.info("About to end executing Test " + arg0.getName());

	}
	
	/**
     *  This Method to belongs to ITestListener and will execute only when test pass
     */
	@Override
	public void onTestSuccess(ITestResult arg0) {
		String[] groups = getGroupsName(arg0);
		extentTest = extentReports.startTest(arg0.getName());
		extentTest.setStartedTime(getTime(arg0.getStartMillis()));
		if (groups.length > 0) {
			extentTest.assignCategory(groups);
		}
		try {
			for (int i = 0; i < ListenerUtil.utilList.size(); i++) {
				extentTest.log(LogStatus.INFO, arg0.getName(), ListenerUtil.utilList.get(i));
			}

		} catch (Throwable e) {
			e.printStackTrace();

		}
		printTestResults(arg0);
		logTestResults(arg0);
		LoggerUtils.info("Test case :" + arg0.getName() + " got passed");
		// clearing list
		ListenerUtil.utilList.clear();
	}
	
	/**
     *  This Method to belongs to ITestListener and will execute only when test fails
     */
	@Override
	public void onTestFailure(ITestResult result) {
		String[] groups = getGroupsName(result);
		extentTest = extentReports.startTest(result.getName());
		extentTest.setStartedTime(getTime(result.getStartMillis()));
		if (groups.length > 0) {
			extentTest.assignCategory(groups);
		}
		try {

			for (int i = 0; i < ListenerUtil.utilList.size(); i++) {
				extentTest.log(LogStatus.INFO, result.getName(), ListenerUtil.utilList.get(i));
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}

		printTestResults(result);
		logTestResults(result);
		// clearing list
		ListenerUtil.utilList.clear();
	}

	/**
     *  This Method to belongs to ITestListener and will execute before the main test start
     */
	@Override
	public void onTestStart(ITestResult arg0) {
		LoggerUtils.info("Test case started for " + arg0.getName());
	}

	/**
     *  This Method to belongs to ITestListener and will execute only if any of the main test(@Test) gets skipped
     */
	@Override
	public void onTestSkipped(ITestResult arg0) {
		LoggerUtils.info("Test case :" + arg0.getName() + " got skipped");
		String[] groups = getGroupsName(arg0);
		extentTest = extentReports.startTest(arg0.getName());
		extentTest.setStartedTime(getTime(arg0.getStartMillis()));
		if (groups.length > 0) {
			extentTest.assignCategory(groups);

		}
		printTestResults(arg0);
		logTestResults(arg0);
		// clearing list
		ListenerUtil.utilList.clear();
	}

	/**
     *  This Method provide test result info
     */
	private void printTestResults(ITestResult result) {
		LoggerUtils.info("Test Method resides in " + result.getTestClass().getName());
		String status = null;
		switch (result.getStatus()) {
		case ITestResult.SUCCESS:
			status = "Pass";
			break;
		case ITestResult.FAILURE:
			status = "Failed";
			break;
		case ITestResult.SKIP:
			status = "Skipped";
		}
		LoggerUtils.info("Test Status: " + status);

	}

	/**
     *  This Method to get current time instance
     */
	public static String getCurrentTimeInstance() {
		Calendar calendar = Calendar.getInstance();
		String currentTimeInstance = "-" + calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.HOUR_OF_DAY) + "-"
				+ calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + "-"
				+ calendar.get(Calendar.MILLISECOND);
		return currentTimeInstance;

	}

	private Date getTime(long millis) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return calendar.getTime();
	}

	/***
	 *   Method to capture screenshot
	 */
	public String takeScreenShot(Object currentClass, ITestResult result) {
		InitDriver init = new InitDriver();
		AppiumDriver<MobileElement> driver = init.getDriver();
		String timeStamp = getCurrentTimeInstance();
		String filename = System.getProperty("user.dir") + "/screenshots/"  + result.getMethod().getMethodName()
				+ timeStamp;
		File f = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(f, new File(filename + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String imageName = filename + ".png";
		return imageName;
	}
	
	/***
	 *   Method to log results
	 */
	public void logTestResults(ITestResult testResult) {
		switch (testResult.getStatus()) {
		case ITestResult.SUCCESS:
			logTestResultsForPassed(testResult);
			break;
		case ITestResult.FAILURE:
			logTestResultsForFail(testResult);
			break;
		case ITestResult.SKIP:
			LoggerUtils.info("Skipped called in logTestResults");
			logTestResultsForSkip(testResult);
			break;
		}
	}
	
	/***
	 *   Method to log results for pass
	 */
	public void logTestResultsForPassed(ITestResult testResult) {
		extentTest.setEndedTime(getTime(testResult.getEndMillis()));
		extentTest.log(LogStatus.PASS, testResult.getName());
		extentReports.endTest(extentTest);
	}
	
	/***
	 *   Method to log results for fail
	 */
	public void logTestResultsForFail(ITestResult testResult) {
		extentTest.setEndedTime(getTime(testResult.getEndMillis()));
		extentTest.log(LogStatus.FAIL, testResult.getName(), testResult.getThrowable());
		extentTest.log(LogStatus.FAIL, testResult.getName());
		LoggerUtils.error("Test case :" + testResult.getName() + " got failed... Failure Reason:"
				+ testResult.getThrowable());
		extentReports.endTest(extentTest);
	}

	/***
	 *   Method to log results for skip
	 */
	public void logTestResultsForSkip(ITestResult testResult) {
		extentTest.setEndedTime(getTime(testResult.getEndMillis()));
		extentTest.log(LogStatus.SKIP, testResult.getName(), testResult.getThrowable());
		extentTest.setEndedTime(getTime(testResult.getEndMillis()));
		LoggerUtils.error("Test case :" + testResult.getName() + " got skipped... skipped Reason:"
				+ testResult.getThrowable());
		extentReports.endTest(extentTest);
	}

	public String[] getGroupsName(ITestResult testResult) {
		return testResult.getMethod().getGroups();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}
}
