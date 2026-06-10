package com.seleniumtestng.base;

import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {
    protected WebDriver driver;

    @BeforeSuite
    public void setUpSuite() {
        System.out.println("=== Test Suite Started ===");
    }

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        driver = DriverManager.getDriver();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverManager.quitDriver();
    }

    @AfterSuite
    public void tearDownSuite() {
        System.out.println("=== Test Suite Completed ===");
    }
}
