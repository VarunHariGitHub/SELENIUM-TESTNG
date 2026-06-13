package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.pages.LoggedInPage;
import com.seleniumtestng.pages.LoginPage;
import com.seleniumtestng.util.ExcelUtil;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        loginPage = new LoginPage(driver);
        driver.get(ConfigReader.getBaseUrl() + "/practice-test-login/");
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() {
        String filePath = "src/test/resources/testdata/login-data.xlsx";
        return ExcelUtil.getData(filePath, "LoginData");
    }

    @Test(groups = "smoke", dataProvider = "loginData")
    public void testLogin(String username, String password, String expectedResult) {
        loginPage.login(username, password);

        switch (expectedResult) {
            case "positive":
                LoggedInPage loggedInPage = new LoggedInPage(driver);
                Assert.assertTrue(driver.getCurrentUrl().contains("logged-in-successfully"),
                        "URL should contain 'logged-in-successfully'");
                String msg = loggedInPage.getSuccessMessage();
                Assert.assertTrue(msg.contains("Congratulations") || msg.contains("successfully logged in"),
                        "Success message should contain congratulations text");
                Assert.assertTrue(loggedInPage.isLogoutButtonDisplayed(), "Logout button should be displayed");
                break;

            case "negative-username":
                Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
                Assert.assertEquals(loginPage.getErrorMessage(), "Your username is invalid!");
                break;

            case "negative-password":
                Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be displayed");
                Assert.assertEquals(loginPage.getErrorMessage(), "Your password is invalid!");
                break;

            default:
                throw new IllegalArgumentException("Unknown expectedResult: " + expectedResult);
        }
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
