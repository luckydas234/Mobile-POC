package com.loylty.automationframework;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlInclude;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

import com.loylty.automationframework.util.common.ConfigReader;

/**
 * This class will select the test to run and gives the result of a test.
 *
 * @author Biswaranjan.Das
 * 
 * 
 *
 */
public class TestRunner {

	private static String packageNames = null;
	private static String testClassesPackageName = null;
	private static String testName = null;
	private static String testSuiteName = null;
	private static List<String> testCaseExcelFileNames = new ArrayList<String>();
	private static List<String> banks = new  ArrayList<String>();
	public static String appname = null;
	private static List<String> testCasesToRun = new ArrayList<String>();
	private static List<String> testCasesToSkip = new ArrayList<String>();
	private static String to_run = ConfigReader.getProperty("config.properties", "BANK");

	public static Map<String, Object[][]> testData = new HashMap<String, Object[][]>();
	public static Map<String, Map<String, String>> keywordDetails = new HashMap<String, Map<String, String>>();

	public static void main(String[] args) throws IOException 
	{

		/*
		 * This method will initialize the package as well as the excel sheet flag
		 * where  testdata is written. 
		 */
		initialize();

		System.out.println("Starting Server");

		TestNG testNG = new TestNG();
		
		XmlSuite suite = new XmlSuite();
		suite.setName(testSuiteName);

		XmlTest test = new XmlTest(suite);
		

	

		// part 1 : read excel file and generate the testcases to run
				for (String eachXlFile : testCaseExcelFileNames)
				{
					readMethodDetailsFromExcel(eachXlFile);
				}

		List<XmlClass> classes = new ArrayList<XmlClass>();
		// part 2 : get all test methods from the package and check if a method
		// is
		// present in the toRunTestCases list, if present then add it to the
		// class in testng.xml file
		Map<String, List<String>> testMap = prepareTestNGClassMethodMap();

		String className = null;
		List<String> methodsOfClass = null;
		XmlClass eachXMLClass = null;
		List<XmlInclude> includeList = null;
		for (Entry<String, List<String>> eachClassMethodEntry : testMap.entrySet()) {

			className = eachClassMethodEntry.getKey();
			methodsOfClass = eachClassMethodEntry.getValue();

			eachXMLClass = new XmlClass(className);
			includeList = new ArrayList<XmlInclude>();
			for (String eachMethodOfClass : methodsOfClass) {
				includeList.add(new XmlInclude(eachMethodOfClass));
			}
			eachXMLClass.setIncludedMethods(includeList);
			classes.add(eachXMLClass);
		}

		// add the classes list to the xml test
		test.setXmlClasses(classes);

		// map the test class and methods to testng format

		List<XmlSuite> suites = new ArrayList<XmlSuite>();
		suites.add(suite);
		testNG.setXmlSuites(suites);

		testNG.setUseDefaultListeners(true);
		testNG.setOutputDirectory("output");

		testNG.run();

	}

	private static Map<String, List<String>> prepareTestNGClassMethodMap() {

		Map<String, List<String>> classMethodMap = new HashMap<String, List<String>>();

		List<ClassLoader> classLoadersList = new LinkedList<ClassLoader>();
		classLoadersList.add(ClasspathHelper.contextClassLoader());
		classLoadersList.add(ClasspathHelper.staticClassLoader());

		Reflections reflections = new Reflections(new ConfigurationBuilder()
				.setScanners(
						new SubTypesScanner(
								false /* don't exclude Object.class */),
						new ResourcesScanner(), new MethodAnnotationsScanner())
				.setUrls(ClasspathHelper.forClassLoader(classLoadersList.toArray(new ClassLoader[0])))
				.filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(testClassesPackageName))));

		String eachMethodName = null;
		String eachClassName = null;
		List<String> classMethodsAdded = null;
		for (Method methodRef : reflections.getMethodsAnnotatedWith(org.testng.annotations.Test.class)) {
			eachMethodName = methodRef.getName();
			eachClassName = methodRef.getDeclaringClass().getName();

			if (testCasesToRun.contains(eachMethodName)) {
				classMethodsAdded = classMethodMap.get(eachClassName);
				if (classMethodsAdded == null) { // if this class was not added
													// to map earlier,
													// initialize
					classMethodsAdded = new ArrayList<String>();
				}
				classMethodsAdded.add(eachMethodName);
				classMethodMap.put(eachClassName, classMethodsAdded);
			}
		}

		return classMethodMap;
	}

	// method to initialize system related things eg :include system.properties
	private static void initialize() 
	{

		// set system properties for chrome and ie drivers
		Map<String, String> systemProperties = ConfigReader.getAllProperties("system.properties");
		for (Entry<String, String> eachProperty : systemProperties.entrySet()) {
			System.setProperty(eachProperty.getKey(), eachProperty.getValue());
		}

		// initialize other properties
		packageNames = ConfigReader.getProperty("config.properties", "TEST_PACKAGES");
		testClassesPackageName = ConfigReader.getProperty("config.properties", "TESTCLASS_PACKAGE");
		testName = ConfigReader.getProperty("config.properties", "TEST_NAME");
		testSuiteName = ConfigReader.getProperty("config.properties", "TEST_SUITE_NAME");
		String runTCBanks = ConfigReader.getProperty("config.properties", "RUN_TC_BANKS");
		if (runTCBanks.equals("ALL")) 
		{
			String[] banksArray = runTCBanks.split(",");
			for (String eachBank : banksArray) 
			{
				banks.add(eachBank.toUpperCase());
			}
			
		} 
		else
		{
			String[] banksArray = runTCBanks.split(",");
			for (String eachBank : banksArray) 
			{
				banks.add(eachBank.toUpperCase());
			}
		}
		for (String bank : banks) {
			String ExcelDir = System.getProperty("user.dir") + "/"
					+ ConfigReader.getProperty("config.properties", "TESTCASE_EXCEL_FILE");
			File folder = new File(ExcelDir);
			File[] listOfFilesInDirectory = folder.listFiles();

			for (File filetoadd : listOfFilesInDirectory) 
			{
				String files = filetoadd.getName();
				
				if(files.endsWith(".xlsx") &&bank.equalsIgnoreCase("ALL"))
				{
					System.out.println("ALL");
					testCaseExcelFileNames.add(System.getProperty("user.dir") + "/input/" + files);
					
					
				}
				
				else if (files.endsWith(".xlsx") && files.contains(bank)) 
				{
					System.out.println(files);
					testCaseExcelFileNames.add(System.getProperty("user.dir") + "/input/" + files);
				}
			}
		}
		}
	

	private static void readMethodDetailsFromExcel(String excelFileName) throws IOException {

		File myFile = new File(excelFileName);
		FileInputStream fis = new FileInputStream(myFile);
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		Iterator<Row> rowIterator = mySheet.iterator();
		rowIterator.next();// skipping the first row as it is a header
		String methodName = null;
		String run = null;
		Row row = null;
		Cell cell = null;
		String rowTestData = null;
		String rowKeywordFileNames;
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			cell = row.getCell(0);
			methodName = cell.getStringCellValue().trim();
            
			cell = row.getCell(1);
			run = cell.getStringCellValue().trim();
			if (methodName != null && methodName.length() > 0 && run != null && run.length() > 0) {
				if ("YES".equalsIgnoreCase(run)) 
				{
					//logic to add only specific banks depending upon the value of RUN_TC_BANKS config
					Cell bankNameCell = row.getCell(4);
					String testCaseBankName = bankNameCell.getStringCellValue();
					if(banks.get(0).equals("ALL")  || banks.indexOf(testCaseBankName) >= 0) {
						testCasesToRun.add(methodName);
					} else  {
						continue;						
					}

					// read test data from excel at row 3
					cell = row.getCell(2);
					rowTestData = cell.getStringCellValue();

					if (rowTestData != null && rowTestData.trim().length() > 0) {
						//test cases to run with multiple testdata

						String[] allTestData = rowTestData.split("\\|");
						// for multiple data set
						Object[][] testDataList = new Object[allTestData.length][];
						int tdIndex = 0;
						for (String eachTestData : allTestData) {
							//test data for a single flow separated with comma
							String[] allData = eachTestData.split(",");
							Object[] mapDataArray = new Object[allData.length];
							for (int index = 0; index < allData.length; index++) {
								mapDataArray[index] = allData[index].trim();
							}
							testDataList[tdIndex++] = mapDataArray;
						}
						testData.put(methodName, testDataList);
					}

					// read keyword file names from excel at row 4 and match it with config file
					cell = row.getCell(3);
					rowKeywordFileNames = cell.getStringCellValue();

					if (rowKeywordFileNames != null && rowKeywordFileNames.trim().length() > 0) {
						String[] allFiles = rowKeywordFileNames.split(",");
						Map<String, String> fileKeywords = new HashMap<String, String>();

						for (String eachFileName : allFiles) {
							fileKeywords.putAll(ConfigReader.getAllProperties(eachFileName));
						}
						keywordDetails.put(methodName, fileKeywords);
					}

				} else if ("NO".equalsIgnoreCase(run)) {
					testCasesToSkip.add(methodName);
				}
			}
		}

		myWorkBook.close();
	}

}
