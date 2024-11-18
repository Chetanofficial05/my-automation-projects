package com.mk.pages;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mk.utils.ExtentUtil;
import com.mk.utils.WebUtils;

public class LoginPage {

	private WebDriver driver; // Declare driver
	private WebUtils webutils;

	private static final Logger log = LogManager.getLogger(LoginPage.class);

	// Locators
	private By usernameField = By.xpath("//input[@name='username']");
	private By passwordField = By.xpath("//input[@name='password']");
	private By loginButton = By.xpath("//button[@type='submit']");
	private By failureAlertElement = By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']");
	private By dashboardHeader = By.xpath("//h6[text()='Dashboard']");

	// Constructor
	public LoginPage(WebDriver driver) throws IOException {

		if (driver == null) {
			throw new IllegalStateException("Driver is not initialized.");
		}

		this.driver = driver; // Initialize the driver
		this.webutils = new WebUtils(driver); // Pass driver to WebUtils
		ExtentUtil.initReport(driver); // Use driver to initialize reports
	}

	// Method to enter username
	public void enterUsername(String username) throws InterruptedException {
		log.info("Entering username: {}", username);
		try {
			webutils.waitForVisibilityAndSendKeys(usernameField, username);
			log.debug("Username entered successfully.");
		} catch (Exception e) {
			log.error("Failed to enter username: {}", username, e);
			throw e;
		}
	}

	// Method to enter password
	public void enterPassword(String password) throws InterruptedException {
		log.info("Entering password.");
		try {
			webutils.waitForVisibilityAndSendKeys(passwordField, password);
			log.debug("Password entered successfully.");
		} catch (Exception e) {
			log.error("Failed to enter password.", e);
			throw e;
		}
	}

	// Method to click the login button
	public void clickLogin() {
		log.info("Clicking the login button.");
		try {
			webutils.waitForClickabilityAndClick(loginButton);
			log.debug("Login button clicked successfully.");
		} catch (Exception e) {
			log.error("Failed to click the login button.", e);
			throw e;
		}
	}

	// Main login method that logs in and returns a status
	public boolean login(String username, String password) throws InterruptedException {
		log.info("Starting login process for username: {}", username);
		try {
			enterUsername(username);
			enterPassword(password);
			clickLogin();

			// First check for failure alert
			try {
				WebElement failureAlert = webutils.waitForVisibility(failureAlertElement);
				String textmessage = failureAlert.getText();
				log.info("Failure Alert Present: {}", textmessage);
				return false; // Login failed if failure alert is present
			} catch (TimeoutException e) {
				log.info("Failure alert not found, proceeding to success check.");
			}

			// Check for the dashboard if failure alert is not found
			try {
				WebElement dashboard = webutils.waitForVisibility(dashboardHeader);
				String textmessage = dashboard.getText();
				log.info("Dashboard Present: {}", textmessage);
				return true; // Login successful if dashboard is present
			} catch (TimeoutException e) {
				log.error("Dashboard not found after login attempt for username: {}", username);
				return false; // Dashboard not found, login is not successful
			}

		} catch (Exception e) {
			log.error("Login process encountered an exception for username: {}", username, e);
			throw e;
		}
	}

}
