package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.pages.ContactPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ContactTests extends BaseTest {

    private ContactPage contactPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        contactPage = new ContactPage(driver);
        String url = ConfigReader.getBaseUrl() + "/contact/";
        for (int i = 0; i < 2; i++) {
            try {
                driver.get(url);
                return;
            } catch (Exception e) {
                if (i == 1) throw e;
            }
        }
    }

    @Test(groups = "smoke")
    public void testContactFormDisplayed() {
        Assert.assertTrue(contactPage.isFirstNameDisplayed(), "First name field should be displayed");
        Assert.assertTrue(contactPage.isLastNameDisplayed(), "Last name field should be displayed");
        Assert.assertTrue(contactPage.isEmailDisplayed(), "Email field should be displayed");
        Assert.assertTrue(contactPage.isMessageDisplayed(), "Message field should be displayed");
        Assert.assertTrue(contactPage.isSubmitDisplayed(), "Submit button should be displayed");
        Assert.assertTrue(contactPage.isSubmitEnabled(), "Submit button should be enabled");
    }

    @Test
    public void testSubmitEmptyForm() {
        contactPage.clickSubmit();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("contact"),
                "Should stay on contact page after submitting empty form");
    }

    @Test
    public void testSubmitInvalidEmail() {
        contactPage.fillForm("Test", "User", "invalid-email", "Test message");
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("contact"),
                "Should stay on contact page when email is invalid");
    }
}
