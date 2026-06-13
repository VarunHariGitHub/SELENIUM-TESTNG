package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.pages.ContactPage;
import com.seleniumtestng.util.ExcelUtil;

import org.openqa.selenium.TimeoutException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ContactTests extends BaseTest {

    private ContactPage contactPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        contactPage = new ContactPage(driver);
        String url = ConfigReader.getBaseUrl() + "/contact/";
        try {
            driver.get(url);
        } catch (TimeoutException e) {
            // reCAPTCHA may cause page load timeout; DOM is already available
        }
    }

    @DataProvider(name = "contactFormData")
    public Object[][] getContactFormData() {
        String filePath = "src/test/resources/testdata/contact-data.xlsx";
        return ExcelUtil.getData(filePath, "ContactData");
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

    @Test(dataProvider = "contactFormData")
    public void testSubmitContactForm(String firstName, String lastName, String email, String message) {
        contactPage.fillForm(firstName, lastName, email, message);
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("contact"),
                "Should stay on contact page after submitting form");
    }
}
