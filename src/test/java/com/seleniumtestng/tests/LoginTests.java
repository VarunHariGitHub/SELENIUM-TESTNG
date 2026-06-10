package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.pages.LoggedInPage;
import com.seleniumtestng.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        loginPage = new LoginPage(driver);
        driver.get(ConfigReader.getBaseUrl() + "/practice-test-login/");
    }

    @Test(groups = "smoke")
    public void testPositiveLogin() {
        loginPage.login("student", "Password123");
        LoggedInPage loggedInPage = new LoggedInPage(driver);
        Assert.assertTrue(driver.getCurrentUrl().contains("logged-in-successfully"),
                "URL should contain 'logged-in-successfully'");
        String msg = loggedInPage.getSuccessMessage();
        Assert.assertTrue(msg.contains("Congratulations") || msg.contains("successfully logged in"),
                "Success message should contain congratulations text");
        Assert.assertTrue(loggedInPage.isLogoutButtonDisplayed(), "Logout button should be displayed");
    }

    @Test
    public void testNegativeUsername() {
        loginPage.login("incorrectUser", "Password123");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertEquals(loginPage.getErrorMessage(), "Your username is invalid!");
    }

    @Test
    public void testNegativePassword() {
        loginPage.login("student", "incorrectPassword");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
        Assert.assertEquals(loginPage.getErrorMessage(), "Your password is invalid!");
    }

    @Test
    public void testLogout() {
        loginPage.login("student", "Password123");
        LoggedInPage loggedInPage = new LoggedInPage(driver);
        loggedInPage.clickLogout();
        Assert.assertTrue(driver.getCurrentUrl().contains("practice-test-login"),
                "Should return to login page after logout");
    }

    @Test
    public void testEmptyFields() {
        loginPage.clickSubmit();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("practice-test-login"),
                "Should stay on login page when fields are empty");
    }
}
