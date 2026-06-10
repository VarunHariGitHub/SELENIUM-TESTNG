package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.pages.PracticePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PracticeTests extends BaseTest {

    private PracticePage practicePage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        practicePage = new PracticePage(driver);
        driver.get(ConfigReader.getBaseUrl() + "/practice/");
    }

    @Test(groups = "smoke")
    public void testPracticeLinks() {
        practicePage.clickTestLoginLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("practice-test-login"),
                "Test Login link should navigate to login page");

        driver.get(ConfigReader.getBaseUrl() + "/practice/");
        practicePage.clickTestExceptionsLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("practice-test-exceptions"),
                "Test Exceptions link should navigate to exceptions page");

        driver.get(ConfigReader.getBaseUrl() + "/practice/");
        practicePage.clickTestTableLink();
        Assert.assertTrue(driver.getCurrentUrl().contains("practice-test-table"),
                "Test Table link should navigate to table page");
    }
}
