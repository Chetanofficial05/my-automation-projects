package com.mk.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;

import com.mk.utils.ExtentUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	// ThreadLocal for WebDriver to ensure thread safety in parallel execution.
	private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
	protected static Properties properties;
	private static final Logger log = LogManager.getLogger(BaseTest.class);

	@AfterSuite
	public void aftersuite() {
		ExtentUtil.endReport();
		log.info("ExtentUtil endReport");
	}

	/**
	 * Load the properties file from the specified file path.
	 * 
	 * @param filePath path to the properties file
	 */
	public void loadProperties(String filePath) {
		properties = new Properties();
		log.info("Properties Object initialized");
		try (FileInputStream fis = new FileInputStream(filePath)) {
			properties.load(fis);
			log.info("Properties loaded successfully from: {}", filePath);
		} catch (IOException e) {
			log.error("Failed to load properties file: {}", filePath, e);
			throw new RuntimeException("Could not load configuration file.");
		}
		log.info("loadProperties section closed");
	}

	/**
	 * Initialize the WebDriver and set it in ThreadLocal.
	 */
	public void initializeDriver() {
		log.info("Initializing WebDriver...");

		// Check if the WebDriver instance is already set in ThreadLocal.
		if (driverThreadLocal.get() == null) {
			WebDriverManager.chromedriver().setup();
			log.info("WebDriverManager setup");

			WebDriver driver = new ChromeDriver();
			log.info("ChromeDriver object created");

			driver.manage().window().maximize();
			log.info("Chrome Browser maximized");

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
			log.info("implicitlyWait set");

			driverThreadLocal.set(driver);
			log.info("WebDriver initialized successfully.");
		} else {
			log.info("WebDriver already initialized in ThreadLocal.");
		}
		log.info("initializeDriver Closed");
	}

	/**
	 * Get the WebDriver instance (Singleton).
	 * 
	 * @return WebDriver instance
	 */
	public static WebDriver getDriver() {
		log.info("In getDriver section");
		return driverThreadLocal.get();
	}

	/**
	 * Navigate to the given URL.
	 * 
	 * @param url URL to navigate to
	 */
	public void navigateTo(String url) {
		WebDriver driver = getDriver(); // Get the WebDriver from ThreadLocal
		log.info("Get the WebDriver from ThreadLocal");

		if (url == null || url.isEmpty()) {
			log.error("URL is null or empty. Cannot navigate.");
			throw new IllegalArgumentException("Invalid URL.");
		}

		driver.get(url);
		log.info("Navigating to URL: {}", url);
	}

	/**
	 * Teardown and quit the WebDriver instance.
	 */
	public void tearDownDriver() {
		WebDriver driver = driverThreadLocal.get();
		log.info("tearDownDriver section");

		if (driver != null) {
			driver.quit();
			log.info("driver quit");

			driverThreadLocal.remove(); // Clean up ThreadLocal
			log.info("WebDriver closed successfully.");

		} else
		{
			log.warn("WebDriver is already null, skipping quit.");
		}
	}

	/**
	 * Cleanup WebDriver after all tests are finished.
	 */
	public static void clearDriver() {
		log.info("clearDriver section");

		if (driverThreadLocal.get() != null) {

			driverThreadLocal.get().quit();
			log.info("driverThreadLocal get and quit");

			driverThreadLocal.remove(); // Clean up WebDriver from ThreadLocal
			log.info("Cleaned WebDriver from ThreadLocal");
		}

		else {
			log.warn("WebDriver is already null, skipping quit.");
		}
	}
}
