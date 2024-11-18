package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {

    private ExtentSparkReporter sparkReporter;
    private ExtentReports extent;
    private ExtentTest test;

    private static String reportPath = System.getProperty("user.dir") + "/reports/";

    @Override
    public void onStart(ITestContext context) {
        // Set the report file name based on timestamp
        String reportName = "ExtentReport_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".html";
        sparkReporter = new ExtentSparkReporter(reportPath + reportName);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("User Module");
        sparkReporter.config().setTheme(Theme.STANDARD);

        // Initialize ExtentReports and attach the reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Host Name", "Localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Automation Tester");
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a node and categorize the test
        test = extent.createTest(result.getMethod().getMethodName()).assignCategory("User Module Tests");
        
        // Check if the method has parameters and handle accordingly
        if (result.getParameters().length > 0) {
            test.createNode(result.getMethod().getMethodName() + " - " + result.getParameters()[0].toString());
        } else {
            test.createNode(result.getMethod().getMethodName());
        }
    }


    @Override
    public void onTestSuccess(ITestResult result) {
        // Log success
        test.log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
        test.assignCategory(result.getMethod().getGroups());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log failure and add the exception
        test.log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        test.log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log skipped test
        test.log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
        test.log(Status.SKIP, result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the report
        extent.flush();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used in this context
    }
}
