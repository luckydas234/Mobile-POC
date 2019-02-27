package com.loylty.automationframework.common;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import com.loylty.automationframework.TestRunner;
import com.loylty.automationframework.util.common.ConfigReader;
import com.loylty.businessobject.TestCase;


public class TestClassUtil {
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;

	 @BeforeSuite
		public void setUp() {
			String path = System.getProperty("user.dir") + "/test-output/Test_Execution_report3.html";
			PropertyConfigurator.configure("log4j.properties");

			
			htmlReporter = new ExtentHtmlReporter(path);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);

			extent.setSystemInfo("OS", "WINDOWS 10");
			extent.setSystemInfo("Host Name", "Biswaranjan");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("User Name", "Biswaranjan");
			htmlReporter.config().setReportName("WebApp -Automation Execution Report");
			
			htmlReporter.config().setChartVisibilityOnOpen(true);
			htmlReporter.config().setDocumentTitle("WebApp_Automation Execution Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTheme(Theme.STANDARD);
		}
	
	

	 
		@DataProvider(name = "testObjDP")
		public static Object[][] getTestDataObject(Method method) {

			String methodName = method.getName();
			Object[][] allDataSet = TestRunner.testData.get(methodName);

			Object[][] finalDataSet = new Object[allDataSet.length][1];
			int index = 0;

			for (Object[] eachDataSet : allDataSet) {
				Map<String, String> eachTestData = new HashMap<String, String>();
				TestCase testcase = new TestCase();
				testcase.setTestMethodName(methodName);
				for (Object eachData : eachDataSet) {
					String dataKeyVal = eachData.toString();
					String[] data = dataKeyVal.split("=");
					eachTestData.put(data[0], data[1]);
				}
				testcase.setTestData(eachTestData);
				finalDataSet[index++] = new Object[] { testcase };
			}

			return finalDataSet;
		}
	 
	
	@DataProvider(name = "defaultDP")
	public static Object[][] getTestData(Method method) 
	{

		String methodName = method.getName();
		Object[][] allDataSet = TestRunner.testData.get(methodName);

		if (allDataSet == null) {
			return new Object[][] { {} };
		}

		Object[][] convertedDataSet = new Object[allDataSet.length][];

		int convertedSetIndex = 0;
		for (Object[] singleDataSet : allDataSet) {// Lky, 26
			int typeIndex = 0;
			Object[] finalSingleDataSet = new Object[singleDataSet.length];

			Class[] types = method.getParameterTypes();
			for (Class eachType : types) {// types : string, int
				String elementType = eachType.getName();
				switch (elementType) {
				case "boolean":
					boolean booleanData = Boolean.valueOf(singleDataSet[typeIndex].toString());
					finalSingleDataSet[typeIndex++] = booleanData;
					break;
				case "char":
					char charData = singleDataSet[typeIndex].toString().charAt(0);
					finalSingleDataSet[typeIndex++] = charData;
					break;
				case "double":

					break;
				case "float":

					break;
				case "int":
					int intData = Integer.parseInt(singleDataSet[typeIndex].toString());
					finalSingleDataSet[typeIndex++] = intData;
					break;
				case "java.lang.String":
					String strData = singleDataSet[typeIndex].toString().split("=")[1];
					finalSingleDataSet[typeIndex++] = strData;
					break;
				case "long":

					break;
				default:

				}
			}
			convertedDataSet[convertedSetIndex++] = finalSingleDataSet;
		}
		return convertedDataSet;

	}
	 @AfterMethod
	public void getResult(ITestResult result) throws IOException {
		
		
		System.out.println(result.getStatus() + "RESULT");
		String testName = result.getMethod().getMethodName();

		if (result.getStatus() == ITestResult.FAILURE) 
		{
		
			SimpleDateFormat sdfDate = new SimpleDateFormat("dd_MM_yyyy");// dd/MM/yyyy
		    Date now = new Date();
		   String strDate = sdfDate.format(now);
			
		//	 String screenShotPath =System.getProperty("user.dir") + "//Screenshot//fail//"+strDate+ File.separator+testName + strDate + ".png";
	    	 String screenShotPath ="./../Screenshot//fail//"+strDate+ File.separator+testName + strDate + ".png";

		   test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:",
					ExtentColor.RED));
			
			test.fail(result.getThrowable());
			test.fail("Snapshot below: "+ test.addScreenCaptureFromPath(screenShotPath));

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));

		} else {
			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.BLUE));
			test.skip(result.getThrowable());
		}

		extent.flush();
	}

}
