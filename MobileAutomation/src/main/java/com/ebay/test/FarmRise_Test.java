package com.ebay.test;

import org.openqa.selenium.Dimension;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.ebay.pagobjects.Farm_Rise;
import com.loylty.automationframework.common.TestClassUtil;
import com.loylty.automationframework.util.APP;
import com.loylty.automationframework.util.impl.DefaultDriverManager;
import com.loylty.businessobject.TestCase;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;


public class FarmRise_Test extends TestClassUtil
{
	final static Logger log=Logger.getLogger(FarmRise_Test.class);
	DefaultDriverManager drivermanager = null;
	AppiumDriver driver = null;
	
	
	
	
	@Test  (dataProvider = "testObjDP")
	public void checkWeatherTime(TestCase testcase) throws IOException, SQLException, InterruptedException
	{
		String testCasename = new Object(){}.getClass().getEnclosingMethod().getName();
		test = extent.createTest(testCasename);
		driver = new DefaultDriverManager().getMobileDriver(APP.FARMRISE);
		test.log(Status.INFO, "Farmrise APP launched");
		
		Farm_Rise testpo=new Farm_Rise(driver);
		
		testpo.click_on_Language(driver);
		test.log(Status.INFO,"Clicked On Language");
		
		testpo.click_On_proceedBtn(driver);
		test.log(Status.INFO,"Clicked On Proceed Btn");
		
		testpo.clickon_agree_continueBtn(driver);
		test.log(Status.INFO,"Clicked On agree Continue Btn");
		
		testpo.clickon_okBtn(driver);
		
		
		testpo.clickon_WeatherTab(driver);
		test.log(Status.INFO,"Clicked On Weather Tab");
		
		
	    Dimension dSize = driver.manage().window().getSize();
		int starty = dSize.height / 2;
		
		int startx = (int) (dSize.width * 0.10);
		int endx=(int) (dSize.width * 0.60);
		driver.swipe(startx, 415, endx, starty, 3000);
		
		Assert.assertEquals(testpo.get_HourlyValue(driver), "11 PM");
		
		
		
	
		
		
	}
	
	@Test  (dataProvider = "testObjDP")
	public void checkGovtSchemes(TestCase testcase) throws IOException, SQLException, InterruptedException
	{
		String testCasename = new Object(){}.getClass().getEnclosingMethod().getName();
		test = extent.createTest(testCasename);
		driver = new DefaultDriverManager().getMobileDriver(APP.FARMRISE);
		test.log(Status.INFO, "Farmrise APP launched");
		
		Farm_Rise testpo=new Farm_Rise(driver);
		
		testpo.click_on_Language(driver);
		test.log(Status.INFO,"Clicked On Language");
		
		testpo.click_On_proceedBtn(driver);
		test.log(Status.INFO,"Clicked On Proceed Btn");
		
		testpo.clickon_agree_continueBtn(driver);
		test.log(Status.INFO,"Clicked On agree Continue Btn");
		
		testpo.clickon_okBtn(driver);
		test.log(Status.INFO,"Clicked On ok Btn");
		
		testpo.moreTab();
		test.log(Status.INFO,"Clicked On more Btn");
		
		testpo.govt_SchemeTab(driver);
		test.log(Status.INFO,"Clicked On govt scheme Tab");
		

		Assert.assertEquals(testpo.get_Title(driver), "Government Schemes");
		
	
		
		
		
	
		
		
	}
	
	
	
	
	@AfterMethod
	public void teardown()
	{
		driver.quit();
	}
	

}
