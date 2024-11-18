package com.mk.pages;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mk.utils.ExtentUtil;
import com.mk.utils.WebUtils;

public class DashboardPage {
	private static final Logger log = LogManager.getLogger(LoginPage.class);
	private WebDriver driver;
	private WebUtils webutils;

	private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
	private By userProfileIcon = By.className("oxd-userdropdown-img");
	private By quicklaunchWidget = By.xpath("//div[@class='oxd-grid-3 orangehrm-quick-launch']");
	private By assignLeaveBtn = By.xpath("//button[@title='Assign Leave']");
	private By assignLeaveHeader = By.xpath("//h6[text()='Assign Leave']");
	private By leaveListBtn = By.xpath("//button[@title='Leave List']");
	private By leaveListHeader = By.xpath("//h5[text()='Leave List']");
	private By timesheetBtn = By.xpath("//button[@title='Timesheets']");
	private By timeHeader = By.xpath("//h6[text()='Time']");
	private By timesheetHeader = By.xpath("//h6[text()='Timesheets']");
	private By applyLeaveBtn = By.xpath("//button[@title='Apply Leave']");
	private By applyLeaveHeader = By.xpath("//h6[text()='Apply Leave']");
	private By myLeaveBtn = By.xpath("//button[@title='My Leave']");
	private By myLeaveHeader = By.xpath("//h5[text()='My Leave List']");
	private By myTimeSheetBtn = By.xpath("//button[@title='My Timesheet']");
	private By myTimesheetHeader = By.xpath("//h6[text()='My Timesheet']");
	private By employeeDistribution = By.xpath("//p[text()='Employee Distribution by Sub Unit']");

	public DashboardPage(WebDriver driver) {
		this.driver = driver;
		this.webutils = new WebUtils(driver);
	}

	public boolean dashboardHeaderdisplay() {
	    log.info("Checking if the dashboard header is displayed.");
	    try {
	        WebElement header = webutils.waitForVisibility(dashboardHeader);
	        boolean isDisplayed = header.isDisplayed();
	        ExtentUtil.logInfo("Dashboard header visibility status: " + isDisplayed);
	        return isDisplayed;
	    } catch (Exception e) {
	        ExtentUtil.logInfo("Failed to verify dashboard header visibility. Error: " + e.getMessage());
	        log.error("Error while checking dashboard header visibility.", e);
	        return false;
	    }
	}

	public boolean isButtonVisible(By locator) {
	    log.info("Checking if the button with locator {} is visible.", locator);
	    try {
	        WebElement button = webutils.waitForVisibility(locator);
	        boolean isDisplayed = button.isDisplayed();
	        ExtentUtil.logInfo("Button visibility status: " + isDisplayed);
	        return isDisplayed;
	    } catch (Exception e) {
	        ExtentUtil.logFail("Failed to verify button visibility for locator: " + locator + ". Error: " + e.getMessage());
	        log.error("Error while checking button visibility for locator: {}", locator, e);
	        return false;
	    }
	}

	public String getDashboardHeaderText() {
	    log.info("Fetching the text of the dashboard header.");
	    try {
	        WebElement header = webutils.waitForVisibility(dashboardHeader);
	        String headerText = header.getText();
	        ExtentUtil.logInfo("Dashboard header text: " + headerText);
	        return headerText;
	    } catch (Exception e) {
	        ExtentUtil.logFail("Failed to fetch dashboard header text. Error: " + e.getMessage());
	        log.error("Error while fetching dashboard header text.", e);
	        return null;
	    }
	}

	public boolean quickLaunchWidgetVisibility() {
	    log.info("Checking if the Quick Launch widget is visible.");
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	        WebElement widget = wait.until(ExpectedConditions.presenceOfElementLocated(quicklaunchWidget));
	        Actions actions = new Actions(driver);
	        actions.scrollToElement(widget).perform();
	        boolean isDisplayed = widget.isDisplayed();
	        ExtentUtil.logInfo("Quick Launch widget visibility status: " + isDisplayed);
	        return isDisplayed;
	    } catch (Exception e) {
	        ExtentUtil.logFail("Failed to verify Quick Launch widget visibility. Error: " + e.getMessage());
	        log.error("Error while checking Quick Launch widget visibility.", e);
	        return false;
	    }
	}

	public boolean isUserProfileIconVisible() {
	    log.info("Checking if the User Profile icon is visible.");
	    try {
	        WebElement userIcon = webutils.waitForVisibility(userProfileIcon);
	        boolean isDisplayed = userIcon.isDisplayed();
	        ExtentUtil.logInfo("User Profile icon visibility status: " + isDisplayed);
	        return isDisplayed;
	    } catch (Exception e) {
	        ExtentUtil.logInfo("Failed to verify User Profile icon visibility. Error: " + e.getMessage());
	        log.error("Error while checking User Profile icon visibility.", e);
	        return false;
	    }
	}

	public DashboardPage clickButton(By locator) {
	    log.info("Clicking on button with locator: {}", locator);
	    try {
	        webutils.scrollToElementJS(locator);
	        webutils.waitForClickabilityAndClick(locator); // Use the instance method here
	        ExtentUtil.logInfo("Successfully clicked on the button with locator: " + locator);
	        return new DashboardPage(driver);
	    } catch (Exception e) {
	        ExtentUtil.logInfo("Failed to click button with locator: " + locator + ". Error: " + e.getMessage());
	        log.error("Error while clicking button with locator: {}", locator, e);
	        throw e;
	    }
	}

	public DashboardPage clickAssignLeaveBtn() {
	    try {
	        ExtentUtil.logInfo("Attempting to click the Assign Leave button.");
	        log.info("Attempting to click the Assign Leave button.");
	        return clickButton(assignLeaveBtn);
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to click the Assign Leave button: " + e.getMessage());
	        log.error("Failed to click the Assign Leave button.", e);
	        throw e; // Rethrow to handle the exception at the test level
	    }
	}

	public WebElement assignLeaveHeader() {
	    try {
	        log.info("Attempting to locate the Assign Leave header.");
	        ExtentUtil.logInfo("Attempting to locate the Assign Leave header.");

	        WebElement assignLeaveText = driver.findElement(assignLeaveHeader);
	        log.info("Assign Leave header located successfully.");
	        ExtentUtil.logInfo("Assign Leave header located successfully.");

	        return assignLeaveText;
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to locate the Assign Leave header: " + e.getMessage());
	        log.error("Failed to locate the Assign Leave header.", e);
	        throw e; // Rethrow to handle the exception at the test level
	    }
	}


	public DashboardPage leaveListBtnclick() throws InterruptedException, IOException {
	    try {
	        ExtentUtil.logInfo("Attempting to click the Leave List button.");
	        log.info("Attempting to click the Leave List button.");
	        return clickButton(leaveListBtn);
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to click the Leave List button: " + e.getMessage());
	        log.error("Failed to click the Leave List button.", e);
	        throw e; // Rethrow to mark the test as failed
	    }
	}

	public WebElement leaveListHeader() {
	    try {
	        ExtentUtil.logInfo("Attempting to locate the Leave List header.");
	        log.info("Attempting to locate the Leave List header.");
	        WebElement llheader = driver.findElement(leaveListHeader);
	        ExtentUtil.logInfo("Leave List header located successfully.");
	        log.info("Leave List header located successfully.");
	        return llheader;
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to locate the Leave List header: " + e.getMessage());
	        log.error("Failed to locate the Leave List header.", e);
	        throw e; // Rethrow to mark the test as failed
	    }
	}

	

	public DashboardPage timeSheetBtnclick() throws InterruptedException, IOException {
	    try {
	        ExtentUtil.logInfo("Attempting to click the Timesheet button.");
	        log.info("Attempting to click the Timesheet button.");
	        return clickButton(timesheetBtn);
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to click the Timesheet button: " + e.getMessage());
	        log.error("Failed to click the Timesheet button.", e);
	        throw e; // Rethrow to mark the test as failed
	    }
	}

	
	public WebElement timeTimesheetHeader() {
	    try {
	        ExtentUtil.logInfo("Attempting to locate the Timesheet header.");
	        log.info("Attempting to locate the Timesheet header.");

	        WebElement time = driver.findElement(timeHeader);
	        WebElement timesheet = driver.findElement(timesheetHeader);

	        if (time.isDisplayed() && timesheet.isDisplayed()) {
	            ExtentUtil.logInfo("Timesheet headers located successfully.");
	            log.info("Timesheet headers located successfully.");
	            return timesheet; // Assuming timesheet is the primary header to validate
	        } else {
	            String errorMsg = "Timesheet headers are not visible.";
	            ExtentUtil.logError(errorMsg);
	            log.error(errorMsg);
	            throw new NoSuchElementException(errorMsg);
	        }
	    } catch (NoSuchElementException e) {
	        ExtentUtil.logError("Timesheet headers could not be located: " + e.getMessage());
	        log.error("Timesheet headers could not be located.", e);
	        throw e;
	    } catch (Exception e) {
	        ExtentUtil.logError("An unexpected error occurred while locating the Timesheet headers: " + e.getMessage());
	        log.error("Unexpected error during Timesheet header location.", e);
	        throw e;
	    }
	}



	public DashboardPage applyLeaveBtnclick() throws InterruptedException, IOException {
	    try {
	        ExtentUtil.logInfo("Attempting to click the Apply Leave button.");
	        log.info("Attempting to click the Apply Leave button.");
	        return clickButton(applyLeaveBtn);
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to click the Apply Leave button: " + e.getMessage());
	        log.error("Exception while clicking the Apply Leave button.", e);
	        throw e;
	    }
	}

	public WebElement applyLeaveHeader() {
	    try {
	        ExtentUtil.logInfo("Locating the Apply Leave page header.");
	        log.info("Locating the Apply Leave page header.");
	        WebElement applyLeave = driver.findElement(applyLeaveHeader);
	        ExtentUtil.logInfo("Apply Leave page header located successfully.");
	        log.info("Apply Leave page header located successfully.");
	        return applyLeave;
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to locate the Apply Leave page header: " + e.getMessage());
	        log.error("Exception while locating the Apply Leave page header.", e);
	        throw e;
	    }
	}

	public DashboardPage myLeaveBtnclick() throws InterruptedException, IOException {
	    try {
	        ExtentUtil.logInfo("Attempting to click the My Leave button.");
	        log.info("Attempting to click the My Leave button.");
	        return clickButton(myLeaveBtn);
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to click the My Leave button: " + e.getMessage());
	        log.error("Exception while clicking the My Leave button.", e);
	        throw e;
	    }
	}

	public WebElement myLeaveHeader() {
	    try {
	        ExtentUtil.logInfo("Locating the My Leave page header.");
	        log.info("Locating the My Leave page header.");
	        WebElement myLeave = driver.findElement(myLeaveHeader);
	        ExtentUtil.logInfo("My Leave page header located successfully.");
	        log.info("My Leave page header located successfully.");
	        return myLeave;
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to locate the My Leave page header: " + e.getMessage());
	        log.error("Exception while locating the My Leave page header.", e);
	        throw e;
	    }
	}

	public DashboardPage myTimeSheetBtnclick() throws InterruptedException, IOException {
	    try {
	        ExtentUtil.logInfo("Attempting to click the My Timesheet button.");
	        log.info("Attempting to click the My Timesheet button.");
	        return clickButton(myTimeSheetBtn);
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to click the My Timesheet button: " + e.getMessage());
	        log.error("Exception while clicking the My Timesheet button.", e);
	        throw e;
	    }
	}

	public WebElement myTimesheetheader() {
	    try {
	        ExtentUtil.logInfo("Locating the My Timesheet page header.");
	        log.info("Locating the My Timesheet page header.");
	        WebElement myTimesheet = driver.findElement(myTimesheetHeader);
	        ExtentUtil.logInfo("My Timesheet page header located successfully.");
	        log.info("My Timesheet page header located successfully.");
	        return myTimesheet;
	    } catch (Exception e) {
	        ExtentUtil.logError("Failed to locate the My Timesheet page header: " + e.getMessage());
	        log.error("Exception while locating the My Timesheet page header.", e);
	        throw e;
	    }
	}

	
	public boolean employeeDistributionSubUnitD() throws InterruptedException {
	    
	        ExtentUtil.logInfo("Checking visibility of Employee Distribution Subunit section.");
	        log.info("Checking visibility of Employee Distribution Subunit section.");
	        webutils.scrollToElementJS(employeeDistribution);
	        WebElement distributionElement = webutils.waitForVisibility(employeeDistribution);

	        boolean isVisible = distributionElement.isDisplayed();
	        ExtentUtil.logInfo("Employee Distribution Subunit visibility: " + isVisible);
	        log.info("Employee Distribution Subunit visibility: {}", isVisible);
	        return isVisible;
	      
	}
}