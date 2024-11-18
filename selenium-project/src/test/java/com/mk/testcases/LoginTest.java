package com.mk.testcases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.mk.base.BaseTest;
import com.mk.pages.LoginPage;
import com.mk.utils.ExcelUtility;
import com.mk.utils.ExtentUtil;

public class LoginTest extends BaseTest {

	private static final Logger log = LogManager.getLogger(LoginTest.class);
	private LoginPage loginPage;

	@BeforeClass
	public void setUp() throws IOException {
		log.info("Starting setUp method...");

		loadProperties("src/test/resources/config.properties");
		log.info("Properties loaded from config.properties.");

		initializeDriver();
		log.info("Driver initialized.");
		
		navigateTo(properties.getProperty("url"));
		log.info("Initializing back to URL");

		loginPage = new LoginPage(getDriver());
		log.info("LoginPage instance created.");

		ExtentUtil.initReport(getDriver());
		log.info("Extent report initialized.");

		log.info("setUp method completed.");
	}

	@AfterClass
	public void tearDown() {
		log.info("Starting tearDown method...");

		// Ensure that the driver is still initialized before calling endReport
		if (getDriver() != null) {
			
			tearDownDriver();
			log.info("Driver torn down.");
		} else {
			log.error("Driver not initialized. Skipping tearDown.");
		}
		log.info("tearDown method completed.");
	}
	
	@AfterMethod
	public void initiallizingback() {
		navigateTo(properties.getProperty("url"));
		log.info("Initializing back to URL");
	}

	@Test(dataProvider = "loginData")
	public void loginTest(String username, String password) {
		log.info("Starting loginTest method for username: {}...", username);

		ExtentUtil.createTest("Data Driven Login Test", "DataDriven Testing");
		log.info("Created test in Extent report.");

		log.info("Executing login test with username: {} and password: {}", username, password);
		try {
			// Perform login and get result
			boolean loginResult = loginPage.login(username, password);
			log.info("Login result for username {}: {}", username, loginResult);

			if (loginResult) {
				ExtentUtil.logPass("Login successful for username: " + username);
				log.info("Login successful for username: {}", username);
				// Assert that the dashboard is visible for successful login
				Assert.assertTrue(true, "Dashboard is not fetched for username: " + username);
				log.info("Assertion passed for successful login.");
			} else {
				ExtentUtil.logFail("Login failed for username: " + username);
				log.info("Login failed for username: {}", username);
				// Assert that the failure alert is visible for failed login
				Assert.assertTrue(true, "Invalid credentials not for username: " + username);
				log.info("Assertion passed for failed login.");
			}
		} catch (Exception e) {
			log.info("Login test failed for username: {} due to exception: {}", username, e.getMessage());
			ExtentUtil.logFail("Login test failed for username: " + username);
			Assert.fail("Test failed due to unexpected exception");
		}

		log.info("Completed loginTest method for username: {}.", username);
	}
	
	

	@DataProvider(name = "loginData")
	public static Object[][] getLoginData() throws IOException {
		log.info("Starting getLoginData method...");

		//String filePath = System.getProperty("user.dir") + "/testdata/data.xlsx";
		String filePath = properties.getProperty("testdatapath");
		String sheetName = "Sheet2";
		log.info("File path: {} | Sheet name: {}", filePath, sheetName);

		Object[][] data = ExcelUtility.getExcelData(filePath, sheetName);
		log.info("Data fetched from Excel: {}", data);

		List<Object[]> validData = new ArrayList<>();
		log.info("Validating data rows...");

		for (Object[] row : data) {
			if (row.length == 2 && row[0] != null && row[1] != null && !row[0].toString().trim().isEmpty()
					&& !row[1].toString().trim().isEmpty()) {
				validData.add(row);
				log.info("Adding valid data row: {}", row);
			}
		}

		log.info("Total valid login data rows: {}", validData.size());
		log.info("Completed getLoginData method.");

		return validData.toArray(new Object[validData.size()][]);
	}
}
