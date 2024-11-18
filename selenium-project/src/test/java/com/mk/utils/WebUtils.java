package com.mk.utils;

import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebUtils { // explicit wait reading from webUtils

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger log = LogManager.getLogger(WebUtils.class); // Logger instance

    // Constructor to initialize WebDriver and set timeout
    public WebUtils(WebDriver driver) {
        log.info("Starting WebUtils constructor...");
        
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized.");
        }

        this.driver = driver;
        log.info("WebDriver initialized: {}", driver);
        
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        log.info("WebDriverWait initialized with timeout of 10 seconds");

        log.info("WebUtils constructor completed.");
    }

    // Wait for element visibility
    public WebElement waitForVisibility(By quickLaunchLocator) {
        log.info("Starting waitForVisibility method for locator: {}", quickLaunchLocator);

        log.info("Waiting for visibility of element located by: {}", quickLaunchLocator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(quickLaunchLocator));
        log.info("Element is now visible: {}", quickLaunchLocator);

        log.info("Completed waitForVisibility method for locator: {}", quickLaunchLocator);
        return element;
    }

    // Wait for element clickability
    public WebElement waitForClickability(By locator) {
        log.info("Starting waitForClickability method for locator: {}", locator);

        log.info("Waiting for element to be clickable: {}", locator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        log.info("Element is clickable: {}", locator);

        log.info("Completed waitForClickability method for locator: {}", locator);
        return element;
    }

    // Wait for visibility and send keys to the element
    public void waitForVisibilityAndSendKeys(By locator, String text) throws InterruptedException {
        log.info("Starting waitForVisibilityAndSendKeys method for locator: {} with text: {}", locator, text);

        log.info("Waiting for visibility of element located by: {}", locator);
        WebElement element = waitForVisibility(locator);
        log.info("Clearing existing text in the element: {}", locator);
        element.clear(); // Clear existing text
        log.info("Existing text cleared in element: {}", locator);

        log.info("Sending text '{}' to the element: {}", text, locator);
        element.sendKeys(text); // Send the specified text
        log.info("Text '{}' sent to element: {}", text, locator);

        log.info("Completed waitForVisibilityAndSendKeys method for locator: {}", locator);
    }

    // Wait for clickability and click on the element
    public WebElement waitForClickabilityAndClick(By locator) {
        log.info("Starting waitForClickabilityAndClick method for locator: {}", locator);

        log.info("Waiting for element to be clickable: {}", locator);
        WebElement element = waitForClickability(locator);
        log.info("Element is clickable: {}", locator);

        log.info("Clicking on the element: {}", locator);
        element.click(); // Click the element
        log.info("Element clicked: {}", locator);

        log.info("Completed waitForClickabilityAndClick method for locator: {}", locator);
        return element;
    }

    // Scroll to the element using JavaScript Executor with exception handling
    public void scrollToElementJS(By locator) {
        log.info("Starting scrollToElementJS method for locator: {}", locator);

        log.info("Waiting for visibility of element located by: {}", locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        log.info("Element is visible: {}", locator);

        log.info("Scrolling to the element located by: {}", locator);
        String jsScript = "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});"; 
        // Scroll to the center of the viewport
        ((JavascriptExecutor) driver).executeScript(jsScript, element);
        log.info("Successfully scrolled to the element: {}", locator);

        log.info("Completed scrollToElementJS method for locator: {}", locator);
    }

    // Click on the element using JavaScript Executor
    public void clickJS(By locator) {
        log.info("Starting clickJS method for locator: {}", locator);

        log.info("Finding element located by: {}", locator);
        WebElement element = driver.findElement(locator);
        log.info("Element found: {}", locator);

        log.info("Clicking on the element using JavaScript: {}", locator);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element); // Execute script to click the element
        log.info("Element clicked using JavaScript: {}", locator);

        log.info("Completed clickJS method for locator: {}", locator);
    }
}
