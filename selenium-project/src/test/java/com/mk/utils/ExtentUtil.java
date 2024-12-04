package com.mk.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.mk.base.BaseTest;

public class ExtentUtil {
	private static ExtentReports extent; // Static instance of ExtentReports to hold the report
	private static boolean isReportInitialized = false; // Flag to track if the report has been initialized
	private static ExtentTest test; // Static instance of ExtentTest to hold each individual test
	private static WebDriver driver; // WebDriver instance to capture screenshots
	private static String reportPath; // Path where the report will be saved
	private static final Logger log = LogManager.getLogger(ExtentUtil.class); // Logger for logging

	// Method to initialize the ExtentReports
	public static ExtentReports initReport(WebDriver webDriver) throws IOException {
		driver = webDriver;
		log.info("Initializing the ExtentReports object."); // Log the initialization attempt

		if (extent == null) { // Check if the ExtentReports object is not initialized
			log.info("Creating a new ExtentReports instance.");
			extent = new ExtentReports(); // Initialize the ExtentReports object

			String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()); // Generate a timestamp for
			// the report filename

			// Get the browser name from the WebDriver instance
			String browserName = getBrowserName(driver).toLowerCase(); // Example: "chrome", "edge", "firefox"

			log.info("Generated timestamp: {}", timeStamp);

			reportPath = System.getProperty("user.dir") + "/Reports/ExtentReport_" + timeStamp + "_" + browserName
					+ ".html"; // Set the report path
			log.info("Report will be saved at: {}", reportPath);

			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath); // Create a reporter for
																						// ExtentReports
			extent.attachReporter(sparkReporter); // Attach the reporter to the ExtentReports object
			log.info("Spark reporter attached to the extent report.");

			// Set system info only once
			if (!isReportInitialized) { // If the report hasn't been initialized yet
				log.info("In isReportInitialized block");

				extent.setSystemInfo("Environment", "QA"); // Add environment info to the report
				log.info("setSystemInfo : Environment");

				extent.setSystemInfo("Browser", getBrowserName(driver)); // Add browser info to the report
				log.info("setSystemInfo : Browser");

				isReportInitialized = true; // Mark the report as initialized
				log.info("isReportInitialized set to true and exit the block");
			}
		} else {
			log.info("ExtentReports instance already initialized."); // If it's already initialized
		}
		return extent; // Return the ExtentReports instance
	}

	// Method to create a new test in the report
	public static void createTest(String testName, String testtype) {
		log.info("Creating test in the Extent report with name: {} and type: {}", testName, testtype);
		test = extent.createTest(testName) // Create a new test in the report
				.assignCategory(testtype) // Assign category to the test
				.assignDevice(getBrowserName(driver)); // Assign device information to the test
		log.info("Test created with author: Chetan K, category: {}, device: {}.", testtype, getBrowserName(driver));
	}

	// Method to log a "Pass" status in the report
	public static void logPass(String message) {
		log.info("Logging pass message: {}", message);
		String base64Screenshot = getBase64Screenshot(); // Capture a screenshot in base64 format
		log.info("Captured screenshot for pass status.");

		// Create a media entity with the screenshot
		Media media = MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build();
		log.info("Created media entity for pass status.");

		if (test != null) { // If the test is initialized
			test.pass(message, media); // Log the message with the screenshot in the report
			log.info("Pass message logged in the report.");
		} else {
			log.warn("Test is not initialized, unable to log pass message.");
		}
	}

	// Method to log a "Fail" status in the report
	public static void logFail(String message) {
		log.info("Logging fail message: {}", message);
		String base64Screenshot = getBase64Screenshot(); // Capture a screenshot in base64 format
		log.info("Captured screenshot for fail status.");

		// Create a media entity with the screenshot
		Media media = MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build();
		log.info("Created media entity for fail status.");

		if (test != null) { // If the test is initialized
			test.fail(message, media); // Log the failure message with the screenshot in the report
			log.info("Fail message logged in the report.");
		} else { // If the test is not initialized
			log.error("Test is not initialized, unable to log fail message.");
		}
	}

	// Method to log "Info" status in the report
	public static void logInfo(String message) {
		log.info("Logging info message: {}", message);
		String base64Screenshot = getBase64Screenshot(); // Capture a screenshot in base64 format
		log.info("Captured screenshot for info status.");

		Media media = MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build(); // Create a
																										// media entity
																										// // screenshot
		log.info("Created media entity for info status.");

		if (test != null) { // If the test is initialized
			test.info(message, media); // Log the info message with the screenshot in the report
			log.info("Info message logged in the report.");
		} else { // If the test is not initialized
			log.error("Test is not initialized, unable to log info message.");
		}
	}

	// Method to log an error message in the report
	public static void logError(String message) {
		log.error("Logging error message: {}", message);
		test.fail(MarkupHelper.createLabel(message, ExtentColor.RED)); // Log the error message with red color in the
																		// report
		log.info("Error message logged in the report with red color.");
	}

	public static String getBase64Screenshot() {
		WebDriver driver = BaseTest.getDriver(); // Now you can call it statically
		if (driver == null) {
			log.error("WebDriver is not initialized.");
			return null; // Return null if driver is not available
		}

		log.debug("Capturing screenshot.");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64); // Capture screenshot and return as base64
	}

	// Method to finalize the report and save it
	public static void endReport() {
		log.info("Finalizing the report.");
		if (extent != null) { // If the ExtentReports object is not null
			extent.flush(); // Write all data to the report file
			log.info("Report flushed to file.");
			openReportInBrowser(); // Open the report in the browser
		} else {
			log.error("ExtentReports object is null, unable to finalize report.");
		}
	}

	// Method to open the generated report automatically after test execution
	private static void openReportInBrowser() {
		try {
			log.info("Attempting to open the report in browser.");
			File reportFile = new File(reportPath); // Create a file object for the report
			if (Desktop.isDesktopSupported()) { // Check if Desktop API is supported
				Desktop.getDesktop().browse(reportFile.toURI()); // Open the report in the default browser
				log.info("Report opened in browser successfully.");
			} else { // If Desktop API is not supported
				log.warn("Desktop is not supported. Please manually open the report: {}", reportPath);
			}
		} catch (IOException e) { // Catch any IO exceptions
			log.error("Error opening the report in the browser: {}", e.getMessage()); // Print error message to log
		}
	}

	// Method to log a list of items in HTML format in the report
	public static void logListAsHtml(String title, List<String> items) {
		log.info("Logging list as HTML with title: {}", title);
		if (items == null || items.isEmpty()) {
			test.info(title + ": <b>No data available</b>");
			log.info("List is empty or null, logged as 'No data available'.");
		} else {
			StringBuilder htmlList = new StringBuilder("<ul>");
			for (String item : items) {
				htmlList.append("<li>").append(item).append("</li>");
			}
			htmlList.append("</ul>");
			test.info(title + ":<br>" + htmlList);
			log.info("List logged in HTML format in the report.");
		}
	}

	/**
	 * Get the browser name dynamically from the WebDriver instance.
	 */
	private static String getBrowserName(WebDriver driver) {
		if (driver instanceof ChromeDriver) {
			// If it's Chrome, return Chrome as the browser name
			return "Chrome";
		}

		else if (driver instanceof EdgeDriver) {
			// If it's Chrome, return Chrome as the browser name
			return "Edge";
		}
		// Extend this check for other browsers if necessary
		return "Unknown Browser"; // Default to unknown if it's not Chrome
	}
}
