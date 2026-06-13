package com.seleniumtestng.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import com.seleniumtestng.driver.DriverManager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtentReportListener implements ITestListener {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThread = new ThreadLocal<>();
    private static final String REPORT_DIR = "target/extent-reports";
    private static final String SCREENSHOT_DIR = REPORT_DIR + "/screenshots";

    @Override
    public void onStart(ITestContext context) {
        if (extent != null) return;

        try {
            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
        } catch (IOException e) {
            throw new RuntimeException("Failed to create report directories", e);
        }

        ExtentSparkReporter spark = new ExtentSparkReporter(REPORT_DIR + "/index.html");
        spark.config().setTheme(Theme.DARK);
        spark.config().setDocumentTitle("Selenium TestNG - Extent Report");
        spark.config().setReportName("Test Execution Report");
        spark.config().setEncoding("UTF-8");

        extent = new ExtentReports();
        extent.attachReporter(spark);

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Environment", "QA");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(
                result.getTestClass().getName() + " :: " + result.getMethod().getMethodName(),
                result.getMethod().getDescription()
        );
        test.assignCategory(result.getTestClass().getRealClass().getSimpleName());
        testThread.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testThread.get().log(Status.PASS, "Test passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = testThread.get();
        test.log(Status.FAIL, result.getThrowable());

        String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
        if (screenshotPath != null) {
            test.fail("Screenshot: ").addScreenCaptureFromPath(screenshotPath);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testThread.get().log(Status.SKIP, "Test skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        if (extent != null) {
            extent.flush();
        }
    }

    private String captureScreenshot(String methodName) {
        try {
            WebDriver driver = DriverManager.getDriver();
            if (driver == null) return null;

            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = methodName + "_" + timestamp + ".png";
            Path dest = Paths.get(SCREENSHOT_DIR, fileName);
            Files.copy(src.toPath(), dest);

            return "../screenshots/" + fileName;
        } catch (Exception e) {
            return null;
        }
    }
}
