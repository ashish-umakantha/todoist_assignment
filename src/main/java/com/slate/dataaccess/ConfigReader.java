package com.slate.dataaccess;

import com.slate.utils.LoggerUtils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

	private static Properties properties = new Properties();
	private final String propertyFilePath= "config/config.properties";
	
	/**
     * Constructor to load the config file
     */
	public ConfigReader(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	/**
     * This Method is used to get value from config file.
     * @param propertyKey      -  property name
     * 
     */
	public static String getProperty(String propertyKey) {
		String propertyValue = properties.getProperty(propertyKey.trim());
		if (propertyValue == null || propertyValue.trim().length() == 0) {
			// Log error message
			LoggerUtils.error(propertyKey + " Not available in Config File");
		}
		return propertyValue;
	}
	
	/**
     * This Method is used to set key value in config file.
     * @param propertyKey      -  key name
     * @param value      -  value
     */
	public static void setProperty(String propertyKey, String value) {
		properties.setProperty(propertyKey, value);
	}

}
