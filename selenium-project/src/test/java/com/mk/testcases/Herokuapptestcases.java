package com.mk.testcases;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.mk.base.BaseTest;
import com.mk.pages.HerokuappPage;
import com.mk.utils.ExtentUtil;

public class Herokuapptestcases extends BaseTest {
	
	private HerokuappPage happ;
	private static final Logger log = LogManager.getLogger(Herokuapptestcases.class);

	@BeforeClass
	public void setup() throws IOException {
		log.info("BeforeClass setup of herokuapptestcases");
		
		initializeDriver();
		log.info("BeforeClass initializeDriver of herokuapptestcases");
		
		// Initializing the page object
		happ = new HerokuappPage(getDriver());
		log.info("HerokuappPage object initialized.");

		// Initializing the Extent Report
		ExtentUtil.initReport(getDriver());
		log.info("Extent Report initialized successfully.");
	}

	@AfterClass
	public void tearDown() {
		log.info("AfterClass tearDown");
		
		tearDownDriver();
		log.info("AfterClass tearDown ended");
	}

	@Test(groups = { "Smoke" })
	public void addRemoveElements() {
		ExtentUtil.createTest("Add/Remove Elements Test", "Smoke");
		log.info("Test started: Verifying the addition and deletion of 'Delete' buttons.");

		// Navigate to the Add/Remove Elements page
		getDriver().get("https://the-internet.herokuapp.com/add_remove_elements/");
		ExtentUtil.logInfo("Navigated to https://the-internet.herokuapp.com/add_remove_elements/");
		log.info("Page loaded: https://the-internet.herokuapp.com/add_remove_elements/");

		// Add elements
		log.info("Attempting to add 'Delete' elements to the page...");
		int elementsToAdd = 5;
		int addDeleteElementsCount = happ.addclickElementMultipleTimes(elementsToAdd);
		ExtentUtil.logInfo("Added" + addDeleteElementsCount + " Delete elements to the page.");
		log.info("Added 'Delete' elements count: " + addDeleteElementsCount);

		 Assert.assertEquals(addDeleteElementsCount, elementsToAdd, "Number of 'Delete' buttons added is incorrect.");
		    log.info("Successfully added " + elementsToAdd + " 'Delete' buttons.");
		    ExtentUtil.logInfo("Successfully added " + elementsToAdd + " 'Delete' buttons.");

		// Delete elements
		log.info("Attempting to delete 3 'Delete' elements");
		int elementsToDelete = 3;
		int delno = happ.deleteElements(elementsToDelete);
		int remainingDeleteCount = addDeleteElementsCount - delno;

		Assert.assertEquals(delno, elementsToDelete, "Number of 'Delete' buttons deleted is incorrect.");
	    log.info("Successfully deleted " + elementsToDelete + " 'Delete' buttons.");
	    ExtentUtil.logInfo("Successfully deleted " + elementsToDelete + " 'Delete' buttons.");


	    try {
	        Assert.assertEquals(remainingDeleteCount, delno, "Number of 'Delete' buttons remaining is incorrect.");
	        log.info("Number of 'Delete' buttons remaining: " + delno);
	        ExtentUtil.logPass("Number of 'Delete' buttons remaining: " + delno);
	    } catch (AssertionError e) {
	        String exceptionMessage = e.getMessage();
	        log.info("Number of 'Delete' buttons remaining: " + delno);
			ExtentUtil.logFail(exceptionMessage + "<< Purposefully Failed");
	        throw e; 
	    }
	}

	@Test(groups = { "Smoke" })
	public void validateBrokenImages() {
		ExtentUtil.createTest("Validate Broken Images", "Smoke");
		log.info("Test started: Validating broken images.");

		getDriver().get("https://the-internet.herokuapp.com/broken_images");
		log.info("Navigated to broken images page.");
		ExtentUtil.logInfo("Navigated to: https://the-internet.herokuapp.com/broken_images");

		int brokenImageCount = happ.countBrokenImage();
		log.info("Total broken images count: " + brokenImageCount);
		ExtentUtil.logInfo("Actual Broken images count: " + brokenImageCount);

		int expectedBrokenImages = 2;
		try {
			log.info("Expected broken images count: " + expectedBrokenImages);
			ExtentUtil.logInfo("Expected broken images count: " + expectedBrokenImages);
			Assert.assertEquals(brokenImageCount, expectedBrokenImages, "The number of broken images is incorrect.");
			log.info("Test passed: Broken images count matched expected value.");
			ExtentUtil.logPass("Passed : Broken images count matched expected value: " + brokenImageCount);
		} catch (AssertionError e) {
			ExtentUtil.logFail("Broken images count mismatched. Expected " + expectedBrokenImages + " but found "
					+ brokenImageCount);
			log.error("Test failed: Broken image count mismatch. Expected: " + expectedBrokenImages + ", Found: "
					+ brokenImageCount, e);
			Assert.fail("Test failed due to broken image count mismatch.");
			throw e; // Rethrow exception for proper test reporting
		}

		log.info("Test completed: Broken images validation.");
	}

	@Test(groups = { "Regression" })
	public void checkFirstRowData() {
	    ExtentUtil.createTest("Check First Row Data", "Regression");
	    log.info("Test started: Check First Row Data");

	    // Navigate to the page
	    getDriver().get("https://the-internet.herokuapp.com/challenging_dom");
	    log.info("Navigated to: https://the-internet.herokuapp.com/challenging_dom");
	    ExtentUtil.logInfo("Navigated to: https://the-internet.herokuapp.com/challenging_dom");

	    // Get the first row column headers
	    List<String> headerText = happ.getheaders();
        List<String> expectedHeaders = Arrays.asList("Lorem", "Ipsum", "Dolor", "Sit", "Amet", "Diceret");

	    // Validate the retrieved data
	    if (headerText != null && !headerText.isEmpty()) {
	        log.info("Validating Headers Text");
	        ExtentUtil.logListAsHtml("Headers Text", headerText);
	        // Validate the headers
	        try {
	            Assert.assertEquals(headerText, expectedHeaders, "Actual headers do not match the expected headers.");
	            log.info("Actual headers match the expected headers."+headerText);
	            ExtentUtil.logPass("Actual headers match the expected headers."+headerText);
	        } catch (AssertionError e) {
	            log.error("Mismatch in headers: " + e.getMessage());
	            ExtentUtil.logFail("Mismatch in headers. Expected: " + expectedHeaders + ", Actual: " + headerText);
	            throw e; // Ensure test fails if validation fails
	        }
	    } else {
	        log.error("Failed to retrieve headers expected :"+expectedHeaders);
	        ExtentUtil.logFail("Failed to retrieve headers expected :"+expectedHeaders);
	        Assert.fail("Failed to retrieve headers expected :"+expectedHeaders);
	    }

	    log.info("Test completed: Checked first row column headers.");
	}


	@Test(groups = { "Regression" })
	public void validateCheckboxes() {
		// Start test and log
		ExtentUtil.createTest("Validate Checkboxes", "Regression");
		log.info("Test started: Validating checkboxes.");

		getDriver().get("https://the-internet.herokuapp.com/checkboxes");
		log.info("Navigated to: https://the-internet.herokuapp.com/checkboxes");
		ExtentUtil.logInfo("Navigated to https://the-internet.herokuapp.com/checkboxes");

		// Check if both checkboxes are selected
		log.info("Checking both checkboxes.");
		ExtentUtil.logInfo("Attempting to check both checkboxes.");
		boolean checkboxesChecked = happ.checkCheckBox();
		if (checkboxesChecked) {
			log.info("Both checkboxes successfully checked: {}", checkboxesChecked);
			ExtentUtil.logInfo("Both checkboxes are successfully checked: " + checkboxesChecked);
		} else {
			log.error("Both checkboxes are not checked: {}", checkboxesChecked);
			ExtentUtil.logFail("Both checkboxes should be checked.");
			Assert.fail("Both checkboxes should be checked.");
		}

		// Uncheck the checkboxes
		ExtentUtil.logInfo("Attempting to uncheck both checkboxes.");
		boolean checkboxesUnchecked = happ.uncheckCheckboxes();
		if (checkboxesUnchecked) {
			ExtentUtil.logInfo("Both checkboxes are successfully unchecked: " + checkboxesUnchecked);
			Assert.assertTrue(checkboxesUnchecked, "Both checkboxes should be unchecked.");
		} else {
			ExtentUtil.logInfo("Both checkboxes are not unchecked: " + checkboxesUnchecked);
			Assert.fail("Both checkboxes should be unchecked.");
		}

		// Final result
		if (checkboxesChecked && checkboxesUnchecked) {
			log.info("Checkbox validation passed.");
			ExtentUtil.logPass("Both checkboxes were successfully checked and unchecked.");
		} else {
			log.error("Checkbox validation failed.");
			ExtentUtil.logFail("Checkbox validation failed. One or both actions did not complete as expected.");
		}

		log.info("Test completed: Checkbox validation.");
	}

	@Test(groups = { "Regression" })
	public void validateDragAndDrop() {
		// Log and report the test start
		log.info("Starting test: Validate Drag and Drop.");
		ExtentUtil.createTest("Validate Drag and Drop", "Regression");

		// Navigate to the page
		getDriver().get("https://the-internet.herokuapp.com/drag_and_drop");
		log.info("Navigated to: https://the-internet.herokuapp.com/drag_and_drop");
		ExtentUtil.logInfo("Navigated to https://the-internet.herokuapp.com/drag_and_drop");

		// Perform drag and drop
		log.info("Performing drag and drop action.");
		boolean isDragAndDropSuccessful = happ.performDragAndDrop();

		// Validate the result
		if (isDragAndDropSuccessful) {
			log.info("Drag and drop was successful.");
			ExtentUtil.logPass("Drag and drop was successful.");
			Assert.assertTrue(isDragAndDropSuccessful, "Drag and drop test passed.");
		} else {
			log.error("Drag and drop was unsuccessful.");
			ExtentUtil.logFail("Drag and drop was unsuccessful.");
			Assert.fail("Drag and drop was unsuccessful, target did not change as expected.");
		}

		log.info("Test completed: Validate Drag and Drop.");
	}

	@Test(groups = { "Smoke" })
	public void validateDropdown() {
		log.info("Starting test: Validate Dropdown.");
		ExtentUtil.createTest("Validate Dropdown", "Smoke");

		// Navigate to the page
		getDriver().get("https://the-internet.herokuapp.com/dropdown");
		log.info("Navigated to: https://the-internet.herokuapp.com/dropdown");
		ExtentUtil.logInfo("Navigated to https://the-internet.herokuapp.com/dropdown");

		// Select "Option 1" and validate
		String optionToSelect = "Option 1";
		log.info("Selecting dropdown option: {}", optionToSelect);
		happ.selectDropdown(optionToSelect);

		String selectedOption1 = happ.getSelectedDropdown();
		log.info("Selected option: {}", selectedOption1);

		if (optionToSelect.equals(selectedOption1)) {
			log.info("Dropdown validation passed. Selected option: {}", selectedOption1);
			ExtentUtil.logPass("Option 1 is selected.");
			Assert.assertTrue(true, "Option 1 selection passed.");
		} else {
			log.error("Dropdown validation failed. Expected: {}, but found: {}", optionToSelect, selectedOption1);
			ExtentUtil.logFail("Failed to select Option 1.");
			Assert.fail("Option 1 selection failed.");
		}

		log.info("Test completed: Validate Dropdown.");
	}

	@Test(groups = { "Regression" })
	public void testCheckboxRemoveAndAdd() {
		log.info("Starting test: Verify Checkbox Remove and Add.");
		ExtentUtil.createTest("Verify Checkbox Remove and Add", "Regression");

		// Navigate to the Dynamic Controls page
		getDriver().get("https://the-internet.herokuapp.com/dynamic_controls");
		log.info("Navigated to: https://the-internet.herokuapp.com/dynamic_controls");
		ExtentUtil.logInfo("Navigated to https://the-internet.herokuapp.com/dynamic_controls");

		// Remove Checkbox
		log.info("Attempting to remove the checkbox.");
		WebElement removedMessageElement = happ.removeCheckbox();
		String actualRemovedMessage = removedMessageElement.getText();
		String expectedRemovedMessage = "It's gone!";
		if (actualRemovedMessage.equals(expectedRemovedMessage)) {
			log.info("Checkbox removed successfully. Message displayed: {}", actualRemovedMessage);
			ExtentUtil.logInfo("Checkbox removed successfully with message: " + actualRemovedMessage);
		} else {
			log.error("Checkbox removal failed. Actual message: {}", actualRemovedMessage);
			ExtentUtil.logFail("Checkbox removal failed. Expected message: " + expectedRemovedMessage);
			Assert.fail("Expected removal message not displayed.");
		}

		// Re-add Checkbox
		log.info("Attempting to re-add the checkbox.");
		WebElement reAddMessageElement = happ.addCheckbox();
		String actualReAddMessage = reAddMessageElement.getText();
		String expectedReAddMessage = "It's back!";
		if (actualReAddMessage.equals(expectedReAddMessage)) {
			log.info("Checkbox re-added successfully. Message displayed: {}", actualReAddMessage);
			ExtentUtil.logPass("Checkbox re-added successfully with message: " + actualReAddMessage);
		} else {
			log.error("Checkbox re-addition failed. Actual message: {}", actualReAddMessage);
			ExtentUtil.logFail("Checkbox re-addition failed. Expected message: " + expectedReAddMessage);
			Assert.fail("Expected re-addition message not displayed.");
		}

		log.info("Test completed: Verify Checkbox Remove and Add.");
	}

	@Test(groups = { "Smoke" })
	public void validateContextMenuAlert() {
		log.info("Starting test: Validate Context Menu Alert.");
		ExtentUtil.createTest("Validate Context Menu Alert", "Smoke");

		// Navigate to the context menu page
		getDriver().get("https://the-internet.herokuapp.com/context_menu");
		log.info("Navigated to: https://the-internet.herokuapp.com/context_menu");
		ExtentUtil.logInfo("Navigated to URL https://the-internet.herokuapp.com/context_menu");

		try {
			log.info("Attempting to perform right-click action.");
			happ.rightClick();
			log.info("Alert displayed and accepted successfully.");
			ExtentUtil.logPass("Alert displayed and accepted successfully.");
			Assert.assertTrue(true, "Alert accepted.");
		} catch (Exception e) {
			log.error("Failed to display or accept the alert. Exception: {}", e.getMessage(), e);
			ExtentUtil.logFail("Alert display or acceptance failed.");
			Assert.fail("Alert not accepted.");
		}

		log.info("Test completed: Validate Context Menu Alert.");
	}

	@Test(groups = { "Regression" })
	public void validateFileUpload() {
		log.info("Starting test: Validate File Upload.");
		ExtentUtil.createTest("Validate File Upload", "Regression");

		// Navigate to the File Upload page
		getDriver().get("https://the-internet.herokuapp.com/upload");
		log.info("Navigated to: https://the-internet.herokuapp.com/upload");
		ExtentUtil.logInfo("Navigated to URL https://the-internet.herokuapp.com/upload");

		try {
			log.info("Attempting to upload the file.");
			WebElement successMessageElement = happ.uploadFile();
			log.info("File upload initiated. Validating success message.");

			String actualMessage = successMessageElement.getText();
			Assert.assertEquals(actualMessage, "File Uploaded!", "File upload success message mismatch");

			log.info("File uploaded successfully with message: {}", actualMessage);
			ExtentUtil.logPass("File uploaded successfully with correct message: " + actualMessage);

		} catch (AssertionError e) {
			log.error("File upload validation failed. Expected message mismatch. {}", e.getMessage(), e);
			ExtentUtil
					.logFail("File upload validation failed. Expected: 'File Uploaded!', but found: " + e.getMessage());
			Assert.fail("File upload failed. " + e.getMessage());
		} catch (Exception e) {
			log.error("An unexpected error occurred during file upload validation. {}", e.getMessage(), e);
			ExtentUtil.logFail("An unexpected error occurred during file upload validation: " + e.getMessage());
			Assert.fail("Unexpected error during file upload validation: " + e.getMessage());
		}

		log.info("Test completed: Validate File Upload.");
	}

	@Test(groups = { "Regression" })
	public void validateTinyMceEditor() {
		log.info("Starting test: Validate TinyMCE WYSIWYG Editor Page.");
		ExtentUtil.createTest("Validate TinyMCE WYSIWYG Editor Page", "Regression");

		getDriver().get("https://the-internet.herokuapp.com/iframe");
		log.info("Navigated to TinyMCE WYSIWYG Editor page.");
		ExtentUtil.logInfo("Navigated to URL https://the-internet.herokuapp.com/iframe");

		try {
			// Close the alert if present
			log.info("Attempting to close alert.");
			boolean closed = happ.closeAlert();
			Assert.assertTrue(closed, "Failed to close the alert.");
			log.info("Alert closed successfully.");
			ExtentUtil.logInfo("Alert closed");

			// Get and validate editor content
			log.info("Attempting to retrieve content from TinyMCE editor.");
			String editorContent = happ.getEditorContent();
			String expectedContent = "Your content goes here.";
			Assert.assertEquals(editorContent, expectedContent, "Editor content does not match the expected content.");
			log.info("Editor content validated successfully: {}", editorContent);
			ExtentUtil.logPass("TinyMCE WYSIWYG Editor page validated successfully.");

		} catch (AssertionError e) {
			log.error("Editor content validation failed. Expected: 'Your content goes here.' but found: {}",
					e.getMessage());
			ExtentUtil.logFail("TinyMCE WYSIWYG Editor page validation failed. Expected content mismatch.");
			Assert.fail("Test failed due to an assertion error: " + e.getMessage());
		} catch (Exception e) {
			log.error("Test failed due to an unexpected exception: {}", e.getMessage(), e);
			ExtentUtil.logFail("TinyMCE WYSIWYG Editor page validation failed due to an exception.");
			Assert.fail("Test failed due to an exception: " + e.getMessage());
		}

		log.info("Test completed: Validate TinyMCE WYSIWYG Editor Page.");
	}

	@Test(groups = { "Regression" })
	public void verifyTextInFrames() {
		log.info("Starting test: Verify Text in Nested Frames.");
		ExtentUtil.createTest("Validate nested frames", "Regression");

		getDriver().get("https://the-internet.herokuapp.com/nested_frames");
		ExtentUtil.logInfo("Navigated to URL https://the-internet.herokuapp.com/nested_frames");

		// Check if the frames are displayed and get the text
		String leftText = happ.getFrameText("left");
		String middleText = happ.getFrameText("middle");
		String rightText = happ.getFrameText("right");
		String bottomText = happ.getFrameText("bottom");

		boolean allFramesDisplayed = true; // To track if all frames are displayed correctly

		// Log and check left frame
		if (leftText.contains("LEFT")) {
			log.info("Left frame is displayed with correct text: {}", leftText);
			ExtentUtil.logInfo("Left frame is displayed with correct text: " + leftText);
		} else {
			log.warn("Left frame is not displayed correctly or text is incorrect. Found: {}", leftText);
			ExtentUtil.logInfo("Left frame is not displayed correctly or text is incorrect.");
			allFramesDisplayed = false; // Mark failure if the frame is not correct
		}

		// Log and check middle frame
		if (middleText.contains("MIDDLE")) {
			log.info("Middle frame is displayed with correct text: {}", middleText);
			ExtentUtil.logInfo("Middle frame is displayed with correct text: " + middleText);
		} else {
			log.warn("Middle frame is not displayed correctly or text is incorrect. Found: {}", middleText);
			ExtentUtil.logInfo("Middle frame is not displayed correctly or text is incorrect.");
			allFramesDisplayed = false; // Mark failure if the frame is not correct
		}

		// Log and check right frame
		if (rightText.contains("RIGHT")) {
			log.info("Right frame is displayed with correct text: {}", rightText);
			ExtentUtil.logInfo("Right frame is displayed with correct text: " + rightText);
		} else {
			log.warn("Right frame is not displayed correctly or text is incorrect. Found: {}", rightText);
			ExtentUtil.logInfo("Right frame is not displayed correctly or text is incorrect.");
			allFramesDisplayed = false; // Mark failure if the frame is not correct
		}

		// Log and check bottom frame
		if (bottomText.contains("BOTTOM")) {
			log.info("Bottom frame is displayed with correct text: {}", bottomText);
			ExtentUtil.logInfo("Bottom frame is displayed with correct text: " + bottomText);
		} else {
			log.warn("Bottom frame is not displayed correctly or text is incorrect. Found: {}", bottomText);
			ExtentUtil.logInfo("Bottom frame is not displayed correctly or text is incorrect.");
			allFramesDisplayed = false; // Mark failure if the frame is not correct
		}

		// If all frames are displayed correctly, pass the test
		if (allFramesDisplayed) {
			log.info("All frames are displayed correctly with the expected text.");
			ExtentUtil.logPass("All frames are displayed correctly with the expected text.");
		} else {
			log.error("One or more frames failed to display the correct text.");
			ExtentUtil.logFail("One or more frames failed to display the correct text.");
		}

		// Final assertion to verify all text content in the frames
		Assert.assertTrue(allFramesDisplayed, "Not all frames were displayed correctly.");
		log.info("Test completed: Verify Text in Nested Frames.");
	}

	@Test(groups = { "Regression" })
	public void validateJSAlert() {
		Logger log = LogManager.getLogger(this.getClass());
		ExtentUtil.createTest("Validate JavaScript Alert", "Regression");

		// Navigate to the JavaScript Alerts page
		getDriver().get("https://the-internet.herokuapp.com/javascript_alerts");
		log.info("Navigated to JavaScript Alerts page.");
		ExtentUtil.logInfo("Navigated to URL https://the-internet.herokuapp.com/javascript_alerts");

		String actualText = happ.clickJSAlertButton();
		String expectedAlertText = "I am a JS Alert";

		// Validate alert text
		if (actualText != null && actualText.equals(expectedAlertText)) {
			log.info("Alert text is correct: {}", actualText);
			ExtentUtil.logInfo("Alert text is correct: " + actualText);
			Assert.assertTrue(true, "Alert text is correct");
		} else {
			log.error("Expected alert text: '{}' but found: '{}'", expectedAlertText, actualText);
			ExtentUtil.logInfo("No such alert, expected: " + expectedAlertText + ", but found: " + actualText);
			Assert.fail("No such alert");
		}

		// Validate the success message after accepting the alert
		String successMessage = happ.AlertSuccessMessage();
		String expectedSuccessMessage = "You successfully clicked an alert";

		if (successMessage.equals(expectedSuccessMessage)) {
			log.info("Alert accepted, success message is displayed: {}", successMessage);
			ExtentUtil.logPass("Alert accepted, success message is displayed as: " + successMessage);
			Assert.assertEquals(successMessage, expectedSuccessMessage, "Success message is displayed");
		} else {
			log.error("Expected success message: '{}' but found: '{}'", expectedSuccessMessage, successMessage);
			ExtentUtil.logFail(
					"Alert not accepted. Expected: '" + expectedSuccessMessage + "', but found: " + successMessage);
			Assert.fail("Alert not accepted");
		}
	}

	@Test(groups = { "Regression" })
	public void validateJSConfirm() {
		ExtentUtil.createTest("Validate JavaScript Confirm", "Regression");

		// Navigate to the JavaScript Alerts page
		getDriver().get("https://the-internet.herokuapp.com/javascript_alerts");
		log.info("Navigated to JavaScript Alerts page");
		ExtentUtil.logInfo("Navigated to URL https://the-internet.herokuapp.com/javascript_alerts");

		String actualText = happ.clickJSConfirmButton();
		String expectedAlertText = "I am a JS Confirm";

		// Validate alert text
		if (actualText.equals(expectedAlertText)) {
			log.info("Alert text is correct: " + actualText);
			ExtentUtil.logInfo("Alert text is correct: " + actualText);
			Assert.assertTrue(true, "Alert text is correct");
		} else {
			log.info("No such alert " + expectedAlertText + ", but found: " + actualText);
			ExtentUtil.logInfo("No such alert " + expectedAlertText + ", but found: " + actualText);
			Assert.fail("No such alert ");
		}

		String successMessage = happ.AlertSuccessMessage();
		String expectedSuccessMessage = "You clicked: Ok";
		String expectedFailMessage = "You clicked: Cancel";

		if (successMessage.equals(expectedSuccessMessage)) {
			log.info("Alert accepted, success message is displayed as: " + successMessage);
			ExtentUtil.logPass("Alert accepted, success message is displayed as: " + successMessage);
			Assert.assertEquals(successMessage, expectedSuccessMessage, "Success message is displayed");
		} else if (successMessage.equals(expectedFailMessage)) {
			log.error("Alert not accepted. Expected: '" + expectedFailMessage + "', but found: " + successMessage);
			ExtentUtil.logFail(
					"Alert not accepted. Expected: '" + expectedFailMessage + "', but found: " + successMessage);
			Assert.fail("Alert not accepted");
		}
	}

	@Test(groups = { "Regression" })
	public void validateJSPrompt() {
		ExtentUtil.createTest("Validate JavaScript Prompt", "Regression");

		getDriver().get("https://the-internet.herokuapp.com/javascript_alerts");
		log.info("Navigated to JavaScript Alerts page");
		ExtentUtil.logInfo("Navigated to URL https://the-internet.herokuapp.com/javascript_alerts");

		String enteredText = happ.clickJSPromptButton(true);
		String expectedEnteredText = "Hello, this is a test!";

		if (enteredText.equals(expectedEnteredText)) {
			log.info("Entered text in the prompt is correct: " + enteredText);
			ExtentUtil.logPass("Entered text in the prompt is correct: " + enteredText);
			Assert.assertTrue(true, "Entered text in the prompt is correct");
		} else {
			log.error("Entered text in the prompt is incorrect. Expected: '" + expectedEnteredText + "', but found: '"
					+ enteredText + "'");
			ExtentUtil.logFail("Entered text in the prompt is incorrect. Expected: '" + expectedEnteredText
					+ "', but found: '" + enteredText + "'");
			Assert.fail("Entered text in the prompt is incorrect");
		}

		// Validate success message after accepting the prompt
		String successMessage = happ.AlertSuccessMessage();
		String expectedSuccessMessage = "You entered: " + expectedEnteredText;

		try {
			Assert.assertEquals(successMessage,expectedSuccessMessage); 
			log.info("Text accepted in the prompt. Success message: " + successMessage);
			ExtentUtil.logPass("Text accepted in the prompt. Success message: " + successMessage);
			Assert.assertEquals(successMessage, expectedSuccessMessage, "Success message is displayed");
		} catch(AssertionError e) {
			String exceptionMessage = e.getMessage();
			log.error("Success message not displayed" + exceptionMessage);
			ExtentUtil.logFail("Success message not displayed "+ exceptionMessage);
			Assert.fail("Text not accepted in the prompt");
			throw e;
		}

	}

	@Test(groups = { "Regression" })
	public void testNewWindowText() {
	    ExtentUtil.createTest("Verify New Window Text", "Regression");
	    log.info("Test: Verify New Window Text");

	    getDriver().get("https://the-internet.herokuapp.com/windows");
	    log.info("Navigated to the Windows page");

	    ExtentUtil.logInfo("Navigated to URL https://the-internet.herokuapp.com/windows");

	    // Verify text in the new window and switch back to the original window
	    String actualText = happ.newWindow();
	    String expectedText = "New Window";

	    if (actualText.equals(expectedText)) {
	        log.info("New window opened with text: " + actualText);
	        ExtentUtil.logPass("New window opened with text: " + actualText);
	        Assert.assertEquals(actualText, expectedText, "New window text matches expected.");
	        log.info("Validation passed.");
	    } else {
	        log.error("New window text does not match expected. Found: " + actualText);
	        ExtentUtil.logFail("New window text does not match expected. Found: " + actualText);
	        Assert.fail("New window text does not match expected.");
	    }

	    log.info("Switched back to the original window.");
	}


	@Test(groups = { "Regression" })
	public void testShadowDom() {
		ExtentUtil.createTest("Verify Shadow DOM Text", "Regression");
		log.info("Starting test: Verify Shadow DOM Text");

		getDriver().get("https://the-internet.herokuapp.com/shadowdom");
		log.info("Navigated to Shadow DOM page: https://the-internet.herokuapp.com/shadowdom");
		ExtentUtil.logInfo("Navigated to URL https://the-internet.herokuapp.com/shadowdom");

		String text = happ.getTextFromShadowDom();
		log.info("Text retrieved from Shadow DOM: " + text);

		if (text.equals("Let's have some different text!")) {
			log.info("Test passed: Text inside Shadow DOM is correct.");
			ExtentUtil.logPass("Text inside Shadow DOM found: " + text);
			Assert.assertTrue(text.equals("Let's have some different text!"),
					"Text inside Shadow DOM matches the expected value.");
		} else {
			log.error("Test failed: Expected text 'Let's have some different text!', but found: " + text);
			ExtentUtil.logFail("Text inside Shadow DOM not found: " + text);
			Assert.fail("Test Failed: Text inside Shadow DOM not found.");
		}
	}

	@Test(groups = { "Regression" })
	public void testTableHeaders() {
		log.info("Starting test: Validate Table Headers");
		ExtentUtil.createTest("Validate Table Headers", "Regression");

		getDriver().get("https://the-internet.herokuapp.com/tables");
		log.info("Navigated to URL: https://the-internet.herokuapp.com/tables");
		ExtentUtil.logInfo("Navigated to URL: https://the-internet.herokuapp.com/tables");

		List<String> expectedHeaders = Arrays.asList("Last Name", "First Name", "Email", "Due", "Web Site", "Action");
		log.info("Expected headers: " + expectedHeaders);
		ExtentUtil.logInfo("Expected headers: " + expectedHeaders.toString());

		boolean isHeadersValid = happ.validateTableHeaders(expectedHeaders);

		// Log and Assert based on validation
		if (isHeadersValid) {
			log.info("Validation passed: Table headers match expected values.");
			ExtentUtil.logPass("All headers are present and match the expected values.");
			Assert.assertTrue(isHeadersValid, "Headers are present and valid.");
		} else {
			log.error("Validation failed: Headers mismatch detected. Expected headers: " + expectedHeaders);
			ExtentUtil.logFail("Headers do not match the expected values.");
			Assert.fail("Headers mismatch detected.");
		}
	}

	@Test(groups = { "Regression" })
	public void validateLastNameSorting() {
		ExtentUtil.createTest("Validate Sort Last Name Column", "Regression");
		log.info("Starting test: Validate Sort Last Name Column");

		// Navigate to the page with the table
		getDriver().get("https://the-internet.herokuapp.com/tables");
		ExtentUtil.logInfo("Navigated to URL: https://the-internet.herokuapp.com/tables");
		log.info("Navigated to URL: https://the-internet.herokuapp.com/tables");

		// Click on the "Last Name" header to sort the table by last name
		happ.clickLastNameHeader();
		ExtentUtil.logInfo("Clicked on 'Last Name' header to sort.");
		log.info("Clicked on 'Last Name' header to trigger sorting.");

		// Get the list of last names after sorting
		List<String> lastNames = happ.getLastNames();

		// Log the captured last names for reference
		ExtentUtil.logInfo("Captured Last Name values: " + lastNames);
		log.info("Captured Last Name values: " + lastNames);

		// Check if the last names are sorted in ascending order
		boolean isSorted = true;
		for (int i = 1; i < lastNames.size(); i++) {
			if (lastNames.get(i - 1).compareTo(lastNames.get(i)) > 0) {
				log.error("Sorting mismatch: " + lastNames.get(i - 1) + " > " + lastNames.get(i));
				isSorted = false;
				break;
			}
		}

		// Log and assert based on validation
		if (isSorted) {
			ExtentUtil.logPass("Last Name column is sorted in ascending order.");
			log.info("Validation passed: Last Name column is sorted in ascending order.");
			Assert.assertTrue(true, "Last Name column is sorted.");
		} else {
			ExtentUtil.logFail("Last Name column is not sorted.");
			log.error("Validation failed: Last Name column is not sorted.");
			Assert.fail("Last Name column is not sorted.");
		}
	}

	@Test(groups = { "Regression" })
	public void validateEmailColumnFormat() {
		ExtentUtil.createTest("Validate Email Column Format", "Regression");
		log.info("Starting test: Validate Email Column Format");

		// Navigate to the page with the table
		getDriver().get("https://the-internet.herokuapp.com/tables");
		ExtentUtil.logInfo("Navigated to URL: https://the-internet.herokuapp.com/tables");
		log.info("Navigated to URL: https://the-internet.herokuapp.com/tables");

		// Fetch email values from the table
		List<String> emails = happ.getEmailColumnValues();
		ExtentUtil.logInfo("Captured email values: " + emails);
		log.info("Captured email values: " + emails);

		// Regular expression for validating email format
		String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
		log.info("Using regex pattern for email validation: " + emailPattern);

		boolean isValid = true;

		// Check if each email matches the email format
		for (String email : emails) {
			if (!Pattern.matches(emailPattern, email)) {
				log.error("Invalid email found: " + email);
				ExtentUtil.logFail("Invalid email found: " + email);
				isValid = false;
				break; // Exit the loop if an invalid email is found
			}
			log.info("Validated email: " + email);
		}

		// Log and Assert based on validation
		if (isValid) {
			ExtentUtil.logPass("All emails in the column are in a valid format.");
			log.info("Validation passed: All emails are in a valid format.");
			Assert.assertTrue(true, "All emails are valid.");
		} else {
			ExtentUtil.logFail("One or more emails are invalid.");
			log.error("Validation failed: One or more emails are invalid.");
			Assert.fail("One or more emails are invalid.");
		}
	}

	@Test(groups = { "Smoke" })
	public void getextfromShadowDOM() {
		log.info("Starting test: Validate text from Shadow DOM");
		ExtentUtil.createTest("Validate text from Shadow DOM", "Smoke");

		// Navigate to the Shadow DOM page
		getDriver().get("https://the-internet.herokuapp.com/shadowdom");
		log.info("Navigated to URL: https://the-internet.herokuapp.com/shadowdom");
		ExtentUtil.logInfo("Navigated to URL: https://the-internet.herokuapp.com/shadowdom");

		// Retrieve text from Shadow DOM
		String textfromDOM = happ.getTextFromShadowDom();
		log.info("Retrieved text from Shadow DOM: " + textfromDOM);

		String expected = "Let's have some different text!";

		// Validate the retrieved text
		if (textfromDOM.equals(expected)) {
			log.info("Shadow DOM text validation passed. Text: " + textfromDOM);
			ExtentUtil.logPass("Shadow DOM text contains: " + textfromDOM);
			Assert.assertTrue(true, "Shadow DOM text contains: " + textfromDOM);
		} else {
			// Capture only the exception message
			log.error("Shadow DOM text validation failed. Expected: " + expected + ", Found: " + textfromDOM);
			ExtentUtil.logFail("Shadow DOM text is not as expected. Text found: " + textfromDOM);
			Assert.fail("Shadow DOM text mismatch. Expected: " + expected + ", Found: " + textfromDOM);
		}
	}

}
