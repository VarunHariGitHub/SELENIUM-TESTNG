package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.pages.ExceptionsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ExceptionTests extends BaseTest {

    private ExceptionsPage exceptionsPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        exceptionsPage = new ExceptionsPage(driver);
        driver.get(ConfigReader.getBaseUrl() + "/practice-test-exceptions/");
    }

    @Test(groups = "smoke")
    public void testNoSuchElementScenario() {
        exceptionsPage.clickAdd();
        Assert.assertTrue(exceptionsPage.isRow2InputDisplayed(),
                "Row 2 input field should appear after clicking Add");
    }

    @Test
    public void testElementNotInteractableScenario() {
        exceptionsPage.clickAdd();
        exceptionsPage.waitForLoadingToDisappear();
        Assert.assertTrue(exceptionsPage.isRow2InputDisplayed(),
                "Row 2 input should appear");
        exceptionsPage.typeIntoRow2Input("Pizza");
        exceptionsPage.clickRow2Save();
        String confirmation = exceptionsPage.getConfirmationText();
        Assert.assertTrue(confirmation.contains("Row 2"),
                "Confirmation should indicate Row 2 was added");
    }

    @Test
    public void testInvalidElementStateScenario() {
        exceptionsPage.clickEdit();
        exceptionsPage.typeIntoRow1Input("Pasta");
        exceptionsPage.clickSave();
        exceptionsPage.waitForLoadingToDisappear();
        String confirmation = exceptionsPage.getConfirmationText();
        Assert.assertTrue(confirmation.contains("Row 1"),
                "Expected confirmation about Row 1 but got: " + confirmation);
    }

    @Test
    public void testDisabledElementScenario() {
        try {
            exceptionsPage.typeIntoRow1Input("Pasta");
            Assert.fail("Should have thrown InvalidElementStateException on disabled input");
        } catch (org.openqa.selenium.InvalidElementStateException e) {
            Assert.assertTrue(true, "Disabled input throws InvalidElementStateException as expected");
        }
    }

    @Test
    public void testConfirmationAfterAdd() {
        exceptionsPage.clickAdd();
        exceptionsPage.waitForLoadingToDisappear();
        Assert.assertTrue(exceptionsPage.isRow2InputDisplayed(), "Row 2 input should appear after Add");
        String confirmation = exceptionsPage.getConfirmationText();
        Assert.assertTrue(confirmation.contains("Row 2"),
                "Confirmation should appear after row 2 is added");
    }
}
