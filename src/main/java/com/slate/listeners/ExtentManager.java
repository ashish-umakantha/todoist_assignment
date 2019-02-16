package com.slate.listeners;

import org.apache.http.util.TextUtils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;
import com.slate.dataaccess.ConfigReader;


public class ExtentManager {
	private static ExtentReports extent;
	public static final String CONFIG_KEY_REPORT_REPLACE = "reportReplaceExisting";
	public static ConfigReader reportProperty = new ConfigReader();

	/**
     *  Method to get the report details
     *  @param filePath      -  file pathe name
     */
	public synchronized static ExtentReports getReporter(String filePath) {
		if (extent == null) {
			if (TextUtils.isEmpty(getReplaceExistingValue())) {
				extent = new ExtentReports(filePath, true, NetworkMode.ONLINE);
			} else {
				extent = new ExtentReports(filePath, Boolean.valueOf(getReplaceExistingValue()), NetworkMode.ONLINE);
			}

			extent.config().documentTitle("Todoist Automation Report").reportName("Automation Report");

		}

		return extent;
	}

	/**
     *  Method to replace existing / previous report after reading the reportReplaceExisting from config file  
     */
	private synchronized static String getReplaceExistingValue() {
		try {
			String reportReplaceExisting = ConfigReader.getProperty(CONFIG_KEY_REPORT_REPLACE);
			return reportReplaceExisting;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;

	}
}