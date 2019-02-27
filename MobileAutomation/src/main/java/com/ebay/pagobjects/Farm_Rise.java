package com.ebay.pagobjects;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;

public class Farm_Rise 
{
	AppiumDriver driver = null;
	
	
	final static Logger log=Logger.getLogger(Farm_Rise.class);
	

	public Farm_Rise(AppiumDriver driver) 
	{
		// TODO Auto-generated constructor stub
		this.driver = driver;
		PageFactory.initElements(driver, this);
		

	}

	@FindBy(id= "com.climate.farmrise:id/txt_alphabet")
	private  WebElement SelectLanguage;

	public  WebElement SelectLanguage() 
	{
		return SelectLanguage;
	}

	@FindBy(id = "com.climate.farmrise:id/btn_Proceed")
	private static WebElement proceedBtn;

	public   WebElement proceedBtn() {
		return proceedBtn;
	}

	@FindBy(id = "com.climate.farmrise:id/btn_agree")
	private   WebElement agree_continue;

	public  WebElement agree_continuet() 
	{
		return agree_continue;
	}

	@FindBy(id = "com.climate.farmrise:id/tv_dismiss" )
	private static WebElement okBtn;

	public static  WebElement okBtn() 
	{
		return okBtn;
	}

	@FindBy(id = "com.climate.farmrise:id/checkWeatherView")
	private static WebElement checkWeatherTab;

	public static WebElement checkWeatherTab() {
		return checkWeatherTab;
	}
	
	@FindBy(id = "com.climate.farmrise:id/icon")
	private static WebElement moreTab;

	public static WebElement moreTab() {
		return  moreTab;
	}
	
	@FindBy(id = "com.climate.farmrise:id/more_govtSchemes")
	private static WebElement govtScheme;

	public static WebElement  govtScheme() {
		return   govtScheme;
	}
	
	
	@FindBy(id = "com.climate.farmrise:id/toolBar_Title")
	private static WebElement getTitle;

	public static WebElement  getTitle() {
		return   getTitle;
	}
	
	@FindBy(id = "com.climate.farmrise:id/hourlyValue")
	private static WebElement gethourlyValue;
	public static  WebElement getHourlyValue()
	{
		return gethourlyValue;
	}
	
	

	
	public  void click_on_Language(AppiumDriver driver)
	{
		
		try {
	
			SelectLanguage().click();
			

		} catch (Exception e) 
		{
			
			log.info("Unable to Click on language");
			Assert.fail("Unable to Click on language");
			
			
		}
		
	}

	
	public  void click_On_proceedBtn(AppiumDriver driver)
	{
		
		try {
			proceedBtn().click();

		} catch (Exception e) 
		{
			
			log.info("Unable to locate proceedBtn");
			Assert.fail("Unable to locate proceedBtn");
			
		}
		
	}
	
	
	public  void  clickon_agree_continueBtn(AppiumDriver driver)
	{
		boolean flag = false;
		try {
			agree_continuet().click();

		} catch (Exception e) 
		{
			
			log.info("agreeContinue  not displayed ");
			Assert.fail("agreeContinue  not displayed ");
			
		}
	}
	
	
	public  void  clickon_okBtn(AppiumDriver driver)
	{
		
		try {
			for(int i=0;i<5;i++)
			{
			okBtn().click();
			}

		} catch (Exception e) 
		{
			
			log.info("ok  button not displayed");
			Assert.fail("ok  button not displayed");
			
		}
	}
	
	

	public  void  clickon_WeatherTab(AppiumDriver driver)
	{
		
		try {
			
			checkWeatherTab().click();
			

		} catch (Exception e) 
		{
			
			log.info("checkWeatherTab not displayed");
			Assert.fail("checkWeatherTab not displayed");
			
			
		}
	}

	public  void  clickon_MoreTab(AppiumDriver driver)
	{
		
		try {
			
			moreTab().click();
			

		} catch (Exception e) 
		{
			
			log.info("more Tab not displayed");
			Assert.fail("more Tab not displayed");
			
			
		}
	}
	public  void  govt_SchemeTab(AppiumDriver driver)
	{
		
		try {
			
			govtScheme().click();
			

		} catch (Exception e) 
		{
			
			log.info("govtSchemeTab not displayed");
			Assert.fail("govtSchemeTab not displayed");
			
		}
	}
	
	public  String  get_Title(AppiumDriver driver)
	{
		
		try {
			
			return getTitle().getText();
			

		} catch (Exception e) 
		
		{
			log.info("govtSchemeTab not displayed");
			return e.toString();
			
			
			
			
		}
	}
	
	public  String  get_HourlyValue(AppiumDriver driver)
	{
		
		try {
			
			return getHourlyValue().getText();
			

		} catch (Exception e) 
		
		{
			log.info("govt hourly value not displayed");
			return e.toString();
			
			
			
			
		}
	}


}
