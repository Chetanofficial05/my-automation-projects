package com.mk.testcases;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mk.base.BaseTest;
import com.mk.pages.DashboardPage;
import com.mk.pages.LoginPage;
import com.mk.utils.ExtentUtil;

public class DashboardTest extends BaseTest {

	private static final Logger log = LogManager.getLogger(DashboardTest.class);
	private LoginPage loginPage;
	private DashboardPage dashboardPage;

	@BeforeClass
	public void setUp() throws IOException, InterruptedException {
		log.info("BeforeClass of DashboardTest");

		loadProperties("src/test/resources/config.properties");
		log.info("loadProperties");

		initializeDriver();
		log.info("initializeDriver");

		loginPage = new LoginPage(getDriver());
		log.info("LoginPage object created");

		dashboardPage = new DashboardPage(getDriver());
		log.info("DashboardPage object created");

		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		log.info("username and password stored in String");

		navigateTo(properties.getProperty("url"));
		log.info("navigateTo url");

		loginPage.login(username, password);
		log.info("login");

		ExtentUtil.initReport(getDriver());
		log.info("Extent Report initialized successfully.");
	}

	@AfterMethod
	public void navigateToDashboard() {
		navigateTo(properties.getProperty("dashboardurl"));
		log.info("AfterMethod : navigateToDashboard");
	}

	@AfterClass
	public void tearDown() {
		log.info("AfterClass : tearDown");

		tearDownDriver();
		log.info("AfterClass : tearDownDriver");
	}

	@Test(groups = { "Regression" })
	public void TC_1() {
		log.info("Starting test case: Validate Dashboard Header");
		ExtentUtil.createTest("Validate Dashboard Header", "Regression");

		log.info("Checking if dashboard header is displayed...");
		boolean headerDisplayed = dashboardPage.dashboardHeaderdisplay();
		log.info("Dashboard header display status: {}", headerDisplayed);

		if (headerDisplayed) {
			log.info("Dashboard header is displayed correctly.");
			ExtentUtil.logPass("Dashboard header is displayed as expected.");
		} else {
			log.warn("Dashboard header is not displayed.");
			ExtentUtil.logFail("Dashboard header is not displayed.");
		}

		Assert.assertTrue(headerDisplayed, "Dashboard header should be displayed.");
	}

	@Test(groups = { "Smoke" })
	public void TC_2() {
		log.info("Starting test case - Validate User Profile Icon Visibility");
		ExtentUtil.createTest("Validate User Profile Icon Visibility", "Smoke");

		boolean isIconVisible = dashboardPage.isUserProfileIconVisible();
		ExtentUtil.logInfo("User profile Icon visibility: " + isIconVisible);
		log.info("User profile Icon visibility: {}", isIconVisible);

		try {
			Assert.assertTrue(isIconVisible, "User profile dropdown should be visible.");
			ExtentUtil.logPass("User Profile Icon is visible as expected.");
			log.info("Test case  passed: User profile Icon is visible.");
		} catch (AssertionError e) {
			String exceptionMessage = e.getMessage(); // Capture only the exception message
			ExtentUtil.logFail("User profile Icon visibility check failed: " + exceptionMessage);
			log.error("Test case failed: ", e); // Log stack trace only in logs
			throw e; // Rethrow exception to mark the test as failed
		}
	}

	@Test(groups = { "Smoke" })
	public void TC_3() {
		log.info("Starting test case Validate Quick Launch Widget Visibility");
		ExtentUtil.createTest("Validate Quick Launch Widget Visibility", "Smoke");

		boolean widgetDisplayed = dashboardPage.quickLaunchWidgetVisibility();
		ExtentUtil.logInfo("Quick Launch Widget visibility: " + widgetDisplayed);
		log.info("Quick Launch Widget visibility: {}", widgetDisplayed);

		try {
			Assert.assertTrue(widgetDisplayed, "Quick launch widget should be visible.");
			ExtentUtil.logPass("Quick launch widget is visible as expected.");
			log.info("Test case passed: Quick launch widget is visible.");
		} catch (AssertionError e) {
			String exceptionMessage = e.getMessage(); // Capture only the exception message
			ExtentUtil.logFail("Quick Launch Widget visibility check failed: " + exceptionMessage);
			log.error("Test case failed: ", e); // Log stack trace only in logs
			throw e; // Rethrow exception to mark the test as failed
		}
	}

	@Test(groups = { "Regression" })
	public void TC_4() throws InterruptedException, IOException {
		log.info("Starting test case- Validate Assign Leave Button Clickability");
		ExtentUtil.createTest("Validate Assign Leave Button Clickability", "Regression");

		dashboardPage.clickAssignLeaveBtn();
		log.info("Assign Leave button clicked successfully.");
		ExtentUtil.logInfo("Assign Leave button clicked successfully.");
		String actual = dashboardPage.assignLeaveHeader().getText();
		String expected = "Assign Leav";
		log.info("Actual Assign Leave Header: {}", actual);
		ExtentUtil.logInfo("Actual Assign Leave Header: " + actual);

		try {
			Assert.assertEquals(actual, expected, "Assign Leave header text should be same.");
			log.info("Testcase passed: Assign Leave header matches as expected.");
			ExtentUtil.logPass("Assign Leave header matches as expected.");

		} catch (AssertionError e) {
			String exceptionMessage = e.getMessage(); // Capture only the exception message
			log.error("Testcase failed: {}", e); // Log stack trace only in logs
			ExtentUtil.logFail(
					"Validation failed for Assign Leave header: " + exceptionMessage + "<< Purposefully Failed");

			throw e; // Rethrow exception to mark the test as failed
		}
	}

	@Test(groups = { "Smoke" })
	public void TC_5() throws InterruptedException, IOException {
		log.info("Starting test case - Validate Leave List Button Clickability");
		ExtentUtil.createTest("Validate Leave List Button Clickability", "Smoke");

		dashboardPage.leaveListBtnclick();
		ExtentUtil.logInfo("Leave List button clicked.");

		WebElement leaveListHeader = dashboardPage.leaveListHeader();
		String actualHeaderText = leaveListHeader.getText();
		String expectedHeaderText = "Leave List";

		log.info("Actual Leave List header: {}", actualHeaderText);
		ExtentUtil.logInfo("Actual Leave List header: " + actualHeaderText);
		try {
			Assert.assertEquals(actualHeaderText, expectedHeaderText, "Leave List header should match.");
			log.info("Test case passed: Leave List button functionality and header validation successful.");
			ExtentUtil.logPass("Navigated to the Leave List page and validated the header.");

		} catch (AssertionError e) {
			String exceptionMessage = e.getMessage();
			log.error("Test case failed: {}", e);
			ExtentUtil.logFail("Leave List header validation failed: " + exceptionMessage);

			throw e;
		}
	}

	@Test(groups = { "Smoke" })
	public void TC_6() throws InterruptedException, IOException {
		log.info("Starting test case - Validate Timesheet Button Clickability");
		ExtentUtil.createTest("Validate Timesheet Button Clickability", "Smoke");

		dashboardPage.timeSheetBtnclick();
		ExtentUtil.logInfo("Timesheet button clicked successfully.");

		WebElement timesheetHeader = dashboardPage.timeTimesheetHeader();
		String actualHeaderText = timesheetHeader.getText();
		String expectedHeaderText = "Timesheet";

		log.info("Actual Timesheet header: {}", actualHeaderText);
		ExtentUtil.logInfo("Actual Timesheet header: " + actualHeaderText);
		try {
			Assert.assertEquals(actualHeaderText, expectedHeaderText, "Timesheet header text should match.");
			log.info("Test case passed: Timesheet button functionality and header validation successful.");
			ExtentUtil.logPass("Navigated to the Timesheet page and validated the header.");

		} catch (AssertionError | Exception e) {
			log.error("Test case  failed.", e);
			ExtentUtil.logFail("Timesheet header validation failed: " + e.getMessage() + "<< Purposefully Failed");
			throw e;
		}
	}

	@Test(groups = { "Smoke" })
	public void TC_7() throws Exception {
		log.info("Starting test case - Validate Apply Leave button Clickability");
		ExtentUtil.createTest("Validate Apply Leave button Clickability", "Smoke");

		dashboardPage.applyLeaveBtnclick();
		ExtentUtil.logInfo("Apply Leave button clicked successfully.");

		WebElement applyLeaveHeader = dashboardPage.applyLeaveHeader();
		String actualHeaderText = applyLeaveHeader.getText();
		String expectedHeaderText = "Apply Leave";

		log.info("Actual Apply Leave page header: {}", actualHeaderText);
		ExtentUtil.logInfo("Actual Apply Leave page header: " + actualHeaderText);
		try {
			Assert.assertEquals(actualHeaderText, expectedHeaderText, "Apply Leave page header text should match.");
			log.info("Test case passed.");
			ExtentUtil.logPass("Navigated to the Apply Leave page and validated the header.");

		} catch (AssertionError | Exception e) {
			log.error("Test case failed.", e);
			ExtentUtil.logFail("Apply Leave button functionality or header validation failed: " + e.getMessage());

			throw e;
		}
	}

	@Test(groups = { "Regression" })
	public void TC_8() throws InterruptedException, IOException {
		log.info("Starting test case - Validate My Leave button Clickability");
		ExtentUtil.createTest("Validate My Leave button Clickability", "Regression");

		dashboardPage.myLeaveBtnclick();
		ExtentUtil.logInfo("My Leave button clicked successfully.");

		WebElement myLeaveHeader = dashboardPage.myLeaveHeader();
		String actualHeaderText = myLeaveHeader.getText();
		String expectedHeaderText = "My Leave Lis";

		log.info("Actual My Leave List header: {}", actualHeaderText);
		ExtentUtil.logInfo("Actual My Leave List header: " + actualHeaderText);
		try {
			Assert.assertEquals(actualHeaderText, expectedHeaderText, "My Leave List header text should match.");
			log.info("Test case passed.");
			ExtentUtil.logPass("Navigated to My Leave page and validated the header.");

		} catch (AssertionError | Exception e) {
			log.error("Test case failed.", e);
			ExtentUtil.logFail("My Leave button functionality or header validation failed: " + e.getMessage()
					+ "<< Purposefully Failed");
			throw e;
		}
	}

	@Test(groups = { "Regression" })
	public void TC_9() throws InterruptedException, IOException {
		log.info("Starting test case - Validate My Timesheet button Clickability");
		ExtentUtil.createTest("Validate My Timesheet button Clickability", "Regression");

		dashboardPage.myTimeSheetBtnclick();
		ExtentUtil.logInfo("My Timesheet button clicked successfully.");

		WebElement myTimesheetHeader = dashboardPage.myTimesheetheader();
		String actualHeaderText = myTimesheetHeader.getText();
		String expectedHeaderText = "My Timesheet";

		log.info("Actual My Timesheet header: {}", actualHeaderText);
		ExtentUtil.logInfo("Actual My Timesheet header: " + actualHeaderText);
		try {
			Assert.assertEquals(actualHeaderText, expectedHeaderText, "My Timesheet header text should match.");
			log.info("Test case passed.");
			ExtentUtil.logPass("Navigated to My Timesheet page and validated the header.");

		} catch (AssertionError | Exception e) {
			log.error("Test case failed.", e);
			ExtentUtil.logFail("My Timesheet button functionality or header validation failed: " + e.getMessage());

			throw e;
		}
	}

	@Test(groups = { "Regression" })
	public void TC_10() throws InterruptedException {
		log.info("Starting test case - Validate Employee Distribution Subunit section visibility");
		ExtentUtil.createTest("Validate Employee Distribution Subunit section visibility", "Regression");

		boolean isDisplayed = dashboardPage.employeeDistributionSubUnitD();
		log.info("Employee Distribution Subunit section visibility: {}", isDisplayed);
		ExtentUtil.logInfo("Checked Employee Distribution Subunit section visibility: " + isDisplayed);
		try {
			Assert.assertTrue(isDisplayed, "Employee Distribution Subunit section should be visible.");
			log.info("Test case passed.");
			ExtentUtil.logPass("Employee Distribution Subunit section is displayed.");

		} catch (AssertionError e) {
			log.error("Test case failed.", e);
			ExtentUtil.logFail("Employee Distribution Subunit section visibility check failed: " + e.getMessage());

			throw e;
		}
	}

}
