
package com.mk.pages;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HerokuappPage {
	private WebDriver driver;
	private static final Logger log = LogManager.getLogger(LoginPage.class);

	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	// Locators for Add/Remove Elements
	private By addremoveheader = By.xpath("//div[@id='content']/h3");
	private By addElementButton = By.xpath("//button[text()='Add Element']");
	private By deleteButton = By.xpath("//div[@id='elements']/button[@class='added-manually']");

	// Locators for Broken Images
	private By brokenimages = By.xpath("//div[@class='example']/img");

	// Locators for Challenging DOM
	private By tableheaders = By.xpath("(//table/thead/tr[1]/th)[position()<=6]");

	// Locators for Checkboxes
	private By checkbox1 = By.xpath("//input[@type='checkbox'][1]");
	private By checkbox2 = By.xpath("//input[@type='checkbox'][2]");

	// Locators for Drag and Drop
	private By draggable = By.id("column-a");
	private By droppable = By.id("column-b");

	// Locators for Dropdown
	private By dropdown = By.id("dropdown");

	// Locators for the Dynamic Controls page
	public By removeButton = By.xpath("//button[text()='Remove']");
	public By addButton = By.xpath("//button[@type='button'][text()='Add']");
	public By message = By.id("message");
	public By checkbox = By.id("checkbox");

	private By contextMenuArea = By.id("hot-spot");

	private By closeButton = By.xpath("//div[@class='tox-icon' and @aria-label='Close']");
	private By iframeLocator = By.id("mce_0_ifr");
	// Locators for the top frame and its child frames
	private By topFrame = By.xpath("//frame[@name='frame-top']");
	private By leftFrame = By.xpath("//frame[@name='frame-left']");
	private By middleFrame = By.xpath("//frame[@name='frame-middle']");
	private By rightFrame = By.xpath("//frame[@name='frame-right']");

	// Locator for the bottom frame (which has no nested frames)
	private By bottomFrame = By.xpath("//frame[@name='frame-bottom']");

	// Locators
	private By jsAlertButton = By.xpath("//button[text()='Click for JS Alert']");
	private By resultText = By.id("result");
	private By jsConfirmButton = By.xpath("//button[text()='Click for JS Confirm']");
	private By jsPromptButton = By.xpath("//button[text()='Click for JS Prompt']");

	private By newwindowlink = By.tagName("Click Here");

	// Locators for the table headers
	private By table1HeaderRow = By.xpath("//table[@id='table1']/thead/tr");
	private By table1HeaderCells = By.xpath("//table[@id='table1']/thead/tr/th");

	// Locators
	By lastNameHeader = By.xpath("//table[@id='table1']//th/span");
	By lastNameCells = By.xpath("//table[@id='table1']/tbody/tr/td[1]");

	private By emailCells = By.xpath("//table[@id='table1']/tbody/tr/td[3]");

	public HerokuappPage(WebDriver driver) {
		log.info("HerokuappPage Constructor started");
		
		this.driver = driver;
		log.info("HerokuappPage Constructor this driver");
		
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		log.info("HerokuappPage Constructor this wait");
	}

	public void switchToFrame(String frameName) {
		switch (frameName.toLowerCase()) {
		case "top":
			driver.switchTo().frame(driver.findElement(topFrame));
			break;
		case "left":
			driver.switchTo().frame(driver.findElement(topFrame));
			driver.switchTo().frame(driver.findElement(leftFrame));
			break;
		case "middle":
			driver.switchTo().frame(driver.findElement(topFrame));
			driver.switchTo().frame(driver.findElement(middleFrame));
			break;
		case "right":
			driver.switchTo().frame(driver.findElement(topFrame));
			driver.switchTo().frame(driver.findElement(rightFrame));
			break;
		case "bottom":
			driver.switchTo().frame(driver.findElement(bottomFrame));
			break;
		default:
			System.out.println("Invalid frame name provided: " + frameName);
		}
	}

	public String getFrameText(String frameName) {
		log.info("Attempting to retrieve text from frame: {}", frameName);
		try {
			switchToFrame(frameName);
			WebElement frameBody = driver.findElement(By.tagName("body"));
			String text = frameBody.getText();

			log.info("Text retrieved from frame '{}': {}", frameName, text);
			return text;
		} catch (Exception e) {
			log.error("Failed to retrieve text from frame '{}'. Error: {}", frameName, e.getMessage(), e);
			return "";
		} finally {
			driver.switchTo().defaultContent();
		}
	}

	public boolean isFrameDisplayed(String frameName) {
		try {
			log.info("Checking if frame '{}' is displayed.", frameName);
			switchToFrame(frameName);
			WebElement frame = driver.findElement(By.tagName("body"));
			boolean isDisplayed = frame.isDisplayed();

			if (isDisplayed) {
				log.info("Frame '{}' is displayed correctly.", frameName);
			} else {
				log.warn("Frame '{}' is not displayed.", frameName);
			}

			return isDisplayed;
		} catch (Exception e) {
			log.error("Frame '{}' is not found or failed to load. Error: {}", frameName, e.getMessage(), e);
			return false;
		} finally {
			driver.switchTo().defaultContent();
		}
	}

	public List<String> getEmailColumnValues() {
		log.info("Fetching email column values from the table.");
		List<WebElement> emailElements = driver.findElements(emailCells);
		log.info("Captured " + emailElements.size() + " email elements.");

		List<String> emails = new ArrayList<>();
		for (WebElement cell : emailElements) {
			String email = cell.getText();
			emails.add(email);
			log.info("Captured email: " + email);
		}

		log.info("Completed fetching email values: " + emails);
		return emails;
	}

	public boolean AddRemoveHeader() {
		try {
			log.info("Checking if the Add/Remove Header is displayed.");
			boolean isDisplayed = driver.findElement(addremoveheader).isDisplayed();
			log.info("Add/Remove Header display status: " + isDisplayed);
			return isDisplayed;
		} catch (Exception e) {
			log.error("Failed to check Add/Remove Header visibility: " + e.getMessage(), e);
			throw e;
		}
	}

	public String getAddRemoveHeaderText() {
		try {
			log.info("Fetching text from the Add/Remove Header.");
			String headerText = driver.findElement(addremoveheader).getText();
			log.info("Text retrieved from Add/Remove Header: " + headerText);
			return headerText;
		} catch (Exception e) {
			log.error("Failed to retrieve text from Add/Remove Header: " + e.getMessage(), e);
			throw e;
		}
	}

	public int addclickElementMultipleTimes(int times) {
		log.info("addclickElementMultipleTimes method started");
		
		WebDriverWait wait = new  WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement addElementButtonvisible = wait.until(ExpectedConditions.visibilityOfElementLocated(addElementButton));
		
		log.info("Clicking 'Add Element' button " + times + " times.");
		
		for (int i = 0; i < times; i++) {
			addElementButtonvisible.click();
			log.info("addElementButtonvisible clicked");
		}
		log.info("Clicked 'Add Element' button " + times + " times successfully.");
		return times;
	}

	public int deleteElements(int count) {
		log.info("Attempting to delete " + count + " 'Delete' buttons.");
		List<WebElement> deleteButtons = driver.findElements(deleteButton);

		int initialCount = deleteButtons.size();
		log.info("Initial number of 'Delete' buttons: " + initialCount);

		for (int i = 0; i < count && !deleteButtons.isEmpty(); i++) {
			deleteButtons.get(0).click();
			log.info("Clicked 'Delete' button at index 0. Remaining buttons will be refreshed.");
			deleteButtons = driver.findElements(deleteButton);
		}

		int remainingCount = deleteButtons.size();
		int deletedCount = initialCount - remainingCount;
		log.info("Number of 'Delete' buttons deleted: " + deletedCount);
		log.info("Number of 'Delete' buttons remaining: " + remainingCount);

		return deletedCount;
	}

	public int countBrokenImage() {
		int brokenImageCount = 0;
		List<WebElement> images = driver.findElements(brokenimages);

		log.info("Total images found for checking: " + images.size());
	

		for (WebElement img : images) {
			try {
				String imgUrl = img.getAttribute("src");
				log.info("Checking image with URL: " + imgUrl);

				HttpURLConnection connection = (HttpURLConnection) URI.create(imgUrl).toURL().openConnection();
				connection.setRequestMethod("GET");
				connection.connect();

				int responseCode = connection.getResponseCode();
				if (responseCode >= 400) {
					brokenImageCount++;
					log.warn("Broken image found: " + imgUrl + " with response code: " + responseCode);
					
				}
				connection.disconnect();
			} catch (IOException e) {
				log.error("Exception while verifying image: " + e.getMessage(), e);
				
			}
		}

		log.info("Total broken images found: " + brokenImageCount);
		
		return brokenImageCount;
	}

	public List<String> getheaders() {
	    List<String> headerData = new ArrayList<>();
	    try {
	        log.info("Attempting to retrieve the first row column headers.");

	        // Find all cells in the header row (assuming the header row is the first row in the table)
	        List<WebElement> headerCells = driver.findElements(tableheaders);

	        if (headerCells.isEmpty()) {
	            log.warn("No table headers");
	        } else {
	            for (WebElement cell : headerCells) {
	                String cellText = cell.getText().trim(); // Trim to clean up spaces
	                headerData.add(cellText);
	                log.info("Header cell data: " + cellText);
	            }
	        }
	        log.info("First row column header retrieval completed with " + headerData.size() + " cells.");
	    } catch (Exception e) {
	        log.error("Exception while retrieving first row column headers: " + e.getMessage(), e);
	    }
	    return headerData;
	}


	public boolean checkCheckBox() {
		try {
			log.info("Attempting to check both checkboxes.");

			WebElement checkbox1Element = driver.findElement(checkbox1);
			WebElement checkbox2Element = driver.findElement(checkbox2);

			if (!checkbox1Element.isSelected()) {
				log.info("Checkbox 1 is not selected, clicking it.");

				checkbox1Element.click();
			} else {
				log.info("Checkbox 1 is already selected.");

			}

			if (!checkbox2Element.isSelected()) {
				log.info("Checkbox 2 is not selected, clicking it.");
				
				checkbox2Element.click();
			} else {
				log.info("Checkbox 2 is already selected.");
				
			}

			boolean result = checkbox1Element.isSelected() && checkbox2Element.isSelected();
			log.info("Checkbox check result: {}", result);
			
			return result;
		} catch (Exception e) {
			log.error("Exception while checking checkboxes: {}", e.getMessage());
			
			return false;
		}
	}

	public boolean uncheckCheckboxes() {
		try {
			log.info("Attempting to uncheck both checkboxes.");
			

			WebElement checkbox1Element = driver.findElement(checkbox1);
			WebElement checkbox2Element = driver.findElement(checkbox2);

			if (checkbox1Element.isSelected()) {
				log.info("Checkbox 1 is selected, clicking to uncheck.");
				
				checkbox1Element.click();
			} else {
				log.info("Checkbox 1 is already unchecked.");
				
			}

			if (checkbox2Element.isSelected()) {
				log.info("Checkbox 2 is selected, clicking to uncheck.");
				
				checkbox2Element.click();
			} else {
				log.info("Checkbox 2 is already unchecked.");
				
			}

			boolean result = !checkbox1Element.isSelected() && !checkbox2Element.isSelected();
			log.info("Checkbox uncheck result: {}", result);
			
			return result;
		} catch (Exception e) {
			log.error("Exception while unchecking checkboxes: {}", e.getMessage());
			
			return false;
		}
	}

	public boolean performDragAndDrop() {
		try {
			log.info("Locating source and target elements for drag and drop.");
			WebElement source = driver.findElement(draggable);
			WebElement target = driver.findElement(droppable);

			log.info("Performing drag and drop action.");
			Actions actions = new Actions(driver);
			actions.dragAndDrop(source, target).perform();

			// Validate if the drop was successful
			log.info("Validating drag and drop action result.");
			String targetText = target.getText();
			boolean result = "A".equals(targetText);

			log.info("Drag and drop validation result: {}", result);
			return result;
		} catch (Exception e) {
			log.error("Exception during drag and drop action: {}", e.getMessage(), e);
			return false;
		}
	}

	public void selectDropdown(String optionText) {
		try {
			log.info("Waiting for the dropdown element to be visible.");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));
			log.info("Dropdown element is visible. Selecting option: {}", optionText);

			Select select = new Select(dropdownElement);
			select.selectByVisibleText(optionText);

			log.info("Option '{}' selected successfully.", optionText);
		} catch (Exception e) {
			log.error("Error while selecting dropdown option '{}': {}", optionText, e.getMessage(), e);
			throw e;
		}
	}

	public String getSelectedDropdown() {
		try {
			log.info("Waiting for the dropdown element to be visible to get the selected option.");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement dropdownElement = wait.until(ExpectedConditions.visibilityOfElementLocated(dropdown));

			Select dropdown = new Select(dropdownElement);
			String selectedOption = dropdown.getFirstSelectedOption().getText();
			log.info("Currently selected option: {}", selectedOption);

			return selectedOption;
		} catch (Exception e) {
			log.error("Error while getting the selected dropdown option: {}", e.getMessage(), e);
			throw e;
		}
	}

	public WebElement removeCheckbox() {
		try {
			log.info("Clicking the 'Remove' button to remove the checkbox.");
			driver.findElement(removeButton).click();
			log.info("Waiting for the removal confirmation message.");
			WebElement goneMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(message));
			log.info("Checkbox removal confirmation message displayed: {}", goneMessage.getText());
			return goneMessage;
		} catch (Exception e) {
			log.error("Exception occurred while removing the checkbox: {}", e.getMessage(), e);
			throw e;
		}
	}

	public WebElement addCheckbox() {
		try {
			log.info("Clicking the 'Add' button to re-add the checkbox.");
			driver.findElement(addButton).click();
			log.info("Waiting for the re-add confirmation message.");
			WebElement backMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(message));
			log.info("Checkbox re-addition confirmation message displayed: {}", backMessage.getText());
			return backMessage;
		} catch (Exception e) {
			log.error("Exception occurred while re-adding the checkbox: {}", e.getMessage(), e);
			throw e;
		}
	}

	public boolean isCheckboxDisplayed() {
		try {
			log.info("Checking if the checkbox is displayed.");
			boolean isDisplayed = driver.findElement(checkbox).isDisplayed();
			log.info("Checkbox display status: {}", isDisplayed);
			return isDisplayed;
		} catch (NoSuchElementException e) {
			log.warn("Checkbox not displayed. NoSuchElementException caught.");
			return false;
		}
	}

	public void rightClick() {
		try {
			log.info("Locating the context menu area.");
			WebElement element = driver.findElement(contextMenuArea);
			log.info("Performing right-click on the context menu area.");
			Actions actions = new Actions(driver);
			actions.contextClick(element).perform();
			log.info("Right-click performed successfully. Checking for alert.");
			acceptAlertIfPresent();
		} catch (Exception e) {
			log.error("Exception occurred during right-click action: {}", e.getMessage(), e);
			throw e;
		}
	}

	public void acceptAlertIfPresent() {
		try {
			log.info("Checking for the presence of an alert.");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
			if (wait.until(ExpectedConditions.alertIsPresent()) != null) {
				log.info("Alert is present. Switching to alert.");
				Alert alert = driver.switchTo().alert();
				log.info("Accepting the alert with text: {}", alert.getText());
				alert.accept();
				log.info("Alert accepted successfully.");
			} else {
				log.warn("No alert was present.");
			}
		} catch (Exception e) {
			log.error("Exception occurred while handling the alert: {}", e.getMessage(), e);
			throw e;
		}
	}

	public WebElement uploadFile() {
		try {
			log.info("Locating the file upload input element.");
			WebElement uploadBtn = driver.findElement(By.id("file-upload"));

			String filePath = System.getProperty("user.dir") + "/FileUpload/Uploadfile.txt";
			log.info("Uploading file from path: {}", filePath);
			uploadBtn.sendKeys(filePath); // File path provided to input

			log.info("Clicking the submit button to upload the file.");
			driver.findElement(By.id("file-submit")).click();

			log.info("Waiting for the success message after file upload.");
			WebElement success = driver.findElement(By.xpath("//h3[text()='File Uploaded!']"));
			log.info("File uploaded successfully. Success message located.");
			return success;
		} catch (Exception e) {
			log.error("Exception occurred during file upload. {}", e.getMessage(), e);
			throw e;
		}
	}

	public boolean closeAlert() {
		try {
			driver.findElement(closeButton).click();
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	public void switchToEditorFrame() {
		try {
			log.info("Waiting for the iframe to be available and switching to it.");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(iframeLocator));
			log.info("Switched to TinyMCE iframe successfully.");
		} catch (Exception e) {
			log.error("Failed to switch to TinyMCE iframe. {}", e.getMessage(), e);
			throw e;
		}
	}

	public String getEditorContent() {
		try {
			log.info("Switching to the editor frame.");
			switchToEditorFrame();

			WebElement editorBody = driver.findElement(By.xpath("//body[@id='tinymce']"));
			String content = editorBody.getText();
			log.info("Retrieved content from editor: {}", content);

			driver.switchTo().defaultContent();
			return content;
		} catch (Exception e) {
			log.error("Error retrieving editor content: {}", e.getMessage(), e);
			throw e;
		}
	}

	public String clickJSAlertButton() {
		try {
			log.info("Clicking the JS Alert button.");
			driver.findElement(jsAlertButton).click();
			if (isAlertPresent()) {
				Alert alert = driver.switchTo().alert();
				String alertText = alert.getText();
				log.info("Alert text retrieved: {}", alertText);
				alert.accept();
				return alertText;
			} else {
				log.warn("No alert present after clicking the button.");
				return null;
			}
		} catch (Exception e) {
			log.error("Error while clicking the JS Alert button or handling alert.", e);
			return null;
		}
	}

	public boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			log.info("Alert is present.");
			return true;
		} catch (NoAlertPresentException e) {
			log.warn("No alert present.");
			return false;
		}
	}

	public String AlertSuccessMessage() {
		try {
			String successMessage = driver.findElement(resultText).getText();
			log.info("Success message retrieved: {}", successMessage);
			return successMessage;
		} catch (Exception e) {
			log.error("Failed to retrieve the success message.", e);
			return "";
		}
	}

	public String clickJSConfirmButton() {
		driver.findElement(jsConfirmButton).click();
		if (isAlertPresent()) {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			log.info("Alert text is: " + alertText);
			alert.accept();
			return alertText;
		} else {
			log.warn("No alert present when clicking JS Confirm button.");
			return null;
		}
	}

	public String clickJSPromptButton(boolean accept) {
		driver.findElement(jsPromptButton).click();
		Alert promptAlert = driver.switchTo().alert();

		String textToEnter = "Hello, this is a test!";

		try {
			promptAlert.sendKeys("");
			promptAlert.sendKeys(textToEnter);
			log.info("Entered text into the JS prompt: " + textToEnter);
		} catch (Exception e) {
			log.error("Failed to enter text into the JS prompt: " + e.getMessage());
			throw e;
		}

		if (accept) {
			promptAlert.accept();
			log.info("Accepted the JS prompt.");
		} else {
			promptAlert.dismiss();
			log.info("Dismissed the JS prompt.");
		}

		return textToEnter;
	}

	public String clickJSPromptButtonDismiss() {
		driver.findElement(jsPromptButton).click();
		Alert promptAlert = driver.switchTo().alert(); // Switch to the alert

		String textToEnter = "";
		promptAlert.sendKeys(textToEnter);
		log.info("Entered text into the JS prompt: " + textToEnter);

		promptAlert.dismiss();
		log.info("Dismissed the JS prompt without accepting.");

		return textToEnter;
	}

	public void multipleWindow() {
		log.info("Attempting to click the 'new window' link to open a new window");
		driver.findElement(newwindowlink).click();
		log.info("Clicked on the 'new window' link.");

	}

	public String newWindow() {
	    String originalWindow = driver.getWindowHandle();
	    log.info("Original window handle: " + originalWindow);

	    driver.findElement(By.linkText("Click Here")).click();
	    log.info("Clicked on 'Click Here' link to open a new window.");


	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	    wait.until(ExpectedConditions.numberOfWindowsToBe(2));

	    for (String windowHandle : driver.getWindowHandles()) {
	        if (!windowHandle.equals(originalWindow)) {
	            driver.switchTo().window(windowHandle);
	            log.info("Switched to new window with handle: " + windowHandle);
	            break;
	        }
	    }

	    WebElement newWindowText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h3")));
	    String text = newWindowText.getText();
	    log.info("Text found in the new window: " + text);

	    driver.close(); // Close the new window
	//    driver.switchTo().window(originalWindow);
	//    log.info("Switched back to the original window with handle: " + originalWindow);

	    return text;
	}


	public void switchBackToOriginalWindow() {
		driver.switchTo().defaultContent();
		log.info("Switched back to the original window.");
	}

	public List<WebElement> getTableHeaders() {
		try {
			log.info("Waiting for the table headers to become visible.");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.visibilityOfElementLocated(table1HeaderRow));
			log.info("Table headers are visible. Fetching header elements.");

			List<WebElement> headers = driver.findElements(table1HeaderCells);
			log.info("Successfully retrieved " + headers.size() + " table header(s).");

			return headers;
		} catch (Exception e) {
			log.error("Failed to retrieve table headers: " + e.getMessage(), e);
			throw e;
		}
	}

	public boolean validateTableHeaders(List<String> expectedHeaders) {
		log.info("Validating table headers...");
		List<WebElement> headers = getTableHeaders();

		for (int i = 0; i < expectedHeaders.size(); i++) {
			String actualHeader = headers.get(i).getText().trim();
			log.info("Validating header " + (i + 1) + ": Expected: '" + expectedHeaders.get(i) + "', Found: '"
					+ actualHeader + "'");
			if (!actualHeader.equals(expectedHeaders.get(i))) {
				log.error("Header mismatch at index " + (i + 1) + ". Expected: '" + expectedHeaders.get(i)
						+ "', Found: '" + actualHeader + "'");
				return false;
			}
		}

		log.info("All headers validated successfully.");
		return true;
	}

	public void clickLastNameHeader() {
		log.info("Locating 'Last Name' header element.");
		WebElement lastNameHeaderElement = driver.findElement(lastNameHeader);
		log.info("'Last Name' header element located successfully.");
		lastNameHeaderElement.click();
		log.info("Clicked on 'Last Name' header element.");
	}

	public List<String> getLastNames() {
		log.info("Fetching last name cells from the table.");
		List<WebElement> lastNameElements = driver.findElements(lastNameCells);
		log.info("Captured " + lastNameElements.size() + " last name cells.");

		List<String> lastNames = new ArrayList<>();
		for (WebElement cell : lastNameElements) {
			String cellText = cell.getText();
			lastNames.add(cellText);
			log.info("Captured last name: " + cellText);
		}

		log.info("Completed fetching last names: " + lastNames);
		return lastNames;
	}

	public String getTextFromShadowDom() {
		log.info("Attempting to retrieve text from Shadow DOM element.");

		String domTextScript = "return document.querySelector(\"span[slot='my-text']\")";
		WebElement domElement = null;
		try {
			domElement = (WebElement) ((JavascriptExecutor) driver).executeScript(domTextScript);
			log.info("Shadow DOM element retrieved successfully.");
		} catch (Exception e) {
			log.error("Failed to retrieve Shadow DOM element: " + e.getMessage());
			throw e;
		}

		String text = domElement.getText();
		log.info("Text from Shadow DOM element: " + text);
		return text;
	}

}
