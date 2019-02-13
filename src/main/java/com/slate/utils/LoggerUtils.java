package com.slate.utils;	

import java.text.SimpleDateFormat;
import org.testng.Reporter;
import com.slate.listeners.ListenerUtil;

public class LoggerUtils extends ListenerUtil{
	
	/**
     * This Method is used to log Info. Used for logging test intent, and actions.
     * @param text      -  string Text to log
     */
	public static void info(String text) {
			java.util.Date date = new java.util.Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss SSS");
			String timestamp = sdf.format(date);
			text = String.format("INFO: [%s] %s", timestamp, text);
			System.out.println("----> " + text);
			utilList.add(text);
			Reporter.log(String.format("<div style=\"color:green\">%s</div>", text), false);
	}

	/**
     * This Method is used to log debug information, used to determine why a test is failing.
     * @param text      -  string Text to log
     */
	public static void debug(String text) {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss SSS");
		String timestamp = sdf.format(date);
		text = String.format("DEBUG: [%s] %s", timestamp, text);
		System.out.println("----> " + text);
		utilList.add(text);
		Reporter.log(String.format("<div>%s</div>", text), false);
	}

	/**
     * This Method is used to log A Warning. Use this when something went wrong, 
     * but may not be critical for test success. Will show up yellow in the report.
     * @param text      -  string Text to log
     */
	public static void warning(String text) {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss SSS");
		String timestamp = sdf.format(date);
		text = String.format("WARNING: [%s] %s", timestamp, text);
		System.out.println("----> " + text);
		utilList.add(text);
		Reporter.log(String.format("<div style=\"background-color:yellow\">%s</div>", text), false);
	}

	/**
     * This Method is used to log error.
     * @param text      -  string Text to log
     */
	public static void error(String text) {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss SSS");
		String timestamp = sdf.format(date);
		text = String.format("ERROR: [%s] %s", timestamp, text);
		System.out.println("!---- " + text);
		utilList.add(text);
		Reporter.log(String.format("<div style=\"background-color:red; color:white\">%s</div>", text), false);
	}

}