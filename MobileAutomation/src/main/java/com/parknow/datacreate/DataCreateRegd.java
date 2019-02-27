package com.parknow.datacreate;

import java.util.Map;

import com.ebay.bo.Bo_Registration;
import com.loylty.businessobject.TestCase;

public class DataCreateRegd {
	
	
	public static Bo_Registration getDefaultDbAttributes(TestCase testcase) 
	{
		//String databasePropertiesFileName="retail_properties" + File.separator +bank.toString() + ".properties";
		Map<String, String> testData = testcase.getTestData();
		
		Bo_Registration regd=new  Bo_Registration();
		
		
		regd.setMobileno(testData.get("Mobileno"));
		regd.setEmail(testData.get("Emailid"));
		
		
		return regd;
		
	}

}
