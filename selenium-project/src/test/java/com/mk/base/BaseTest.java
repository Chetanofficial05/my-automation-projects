package com.mk.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
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
	 * Initialize the WebDriver and set it in ThreadLocal based on the browser value
	 * from the properties file.
	 */
	public void initializeDriver() {
		log.info("Initializing WebDriver...");
		
		// Get the browser name from properties file (e.g., "chrome", "edge"). Default
		// to "chrome" if not specified.
		String browser = properties.getProperty("browser", "chrome").trim();
		
		log.info("Browser selected from properties: {}", browser);

		// Check if the WebDriver instance is already set in ThreadLocal.
		if (driverThreadLocal.get() == null) {
			try {
				switch (browser.toLowerCase()) {
				case "chrome":
					WebDriverManager.chromedriver().setup();
					driverThreadLocal.set(new ChromeDriver());
					log.info("ChromeDriver initialized");
					break;
				case "edge":
					WebDriverManager.edgedriver().setup();
					driverThreadLocal.set(new EdgeDriver());
					log.info("EdgeDriver initialized");
					break;
				default:
					log.error("Unsupported browser: {}. Defaulting to Chrome.", browser);
					WebDriverManager.chromedriver().setup(); // Fallback to ChromeDriver
					driverThreadLocal.set(new ChromeDriver());
					log.info("ChromeDriver initialized as fallback");
					break;
				}

				WebDriver driver = driverThreadLocal.get();
				driver.manage().window().maximize();
				log.info("Browser window maximized");
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
				log.info("Implicit wait set");

				log.info("WebDriver initialized successfully.");
			} catch (Exception e) {
				log.error("Error while initializing WebDriver: {}", e.getMessage(), e);
				throw new RuntimeException("WebDriver initialization failed.");
			}
		} else {
			log.info("WebDriver already initialized in ThreadLocal.");
		}
		log.info("initializeDriver method closed");
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

		} else {
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
