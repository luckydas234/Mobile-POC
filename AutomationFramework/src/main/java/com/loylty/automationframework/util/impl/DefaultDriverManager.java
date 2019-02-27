package com.loylty.automationframework.util.impl;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;


import com.loylty.automationframework.util.APP;

import com.loylty.automationframework.util.common.ConfigReader;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;



public class DefaultDriverManager 
{
	private static String sPropFileName = "config.properties";


    
   
    public static AppiumDriver getMobileDriver( APP app) throws IOException 
    {
    	AppiumDriver driver = null;
		// String platform="Android";
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability("noReset", "False");
		cap.setCapability("fullReset", "False");
		cap.setCapability("automatioNname", ConfigReader.getProperty(sPropFileName, "AUTOMATIONNAME"));
		cap.setCapability("platformName", ConfigReader.getProperty(sPropFileName, "PLATFORMNAME"));
		cap.setCapability("platformversion", ConfigReader.getProperty(sPropFileName, "PLATFORMVERSION"));
		cap.setCapability("deviceName", ConfigReader.getProperty(sPropFileName, "DEVICENAME"));
		cap.setCapability("newCommandTimeout", 10000);

		String appProperties = ConfigReader.getProperty(sPropFileName, app.toString());
		String[] propertiesArray = appProperties.split("\\|");

		System.out.println(propertiesArray[0]);
		cap.setCapability("app", propertiesArray[0]);
		cap.setCapability("apppackage", propertiesArray[1]);
		cap.setCapability("appactivity", propertiesArray[2]);

		String appiumServerUrl = System.getProperty("APPIUM_SERVER_URL");
		if (appiumServerUrl == null) {
			appiumServerUrl = ConfigReader.getProperty(sPropFileName, "APPIUM_SERVER_URL");
		}

		driver = new AndroidDriver(new URL(appiumServerUrl), cap);

		driver.manage().timeouts().implicitlyWait(30000, TimeUnit.MILLISECONDS);

		return driver;
	}
	

}
