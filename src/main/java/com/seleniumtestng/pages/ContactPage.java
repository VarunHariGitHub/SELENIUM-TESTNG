package com.seleniumtestng.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ContactPage extends BasePage {

    @FindBy(id = "wpforms-161-field_0")
    private WebElement firstNameField;

    @FindBy(id = "wpforms-161-field_0-last")
    private WebElement lastNameField;

    @FindBy(id = "wpforms-161-field_1")
    private WebElement emailField;

    @FindBy(id = "wpforms-161-field_2")
    private WebElement messageField;

    @FindBy(id = "wpforms-submit-161")
    private WebElement submitButton;

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    public boolean isFirstNameDisplayed() {
        return firstNameField.isDisplayed();
    }

    public boolean isLastNameDisplayed() {
        return lastNameField.isDisplayed();
    }

    public boolean isEmailDisplayed() {
        return emailField.isDisplayed();
    }

    public boolean isMessageDisplayed() {
        return messageField.isDisplayed();
    }

    public boolean isSubmitDisplayed() {
        return submitButton.isDisplayed();
    }

    public boolean isSubmitEnabled() {
        return submitButton.isEnabled();
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void fillForm(String firstName, String lastName, String email, String message) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        emailField.sendKeys(email);
        messageField.sendKeys(message);
        clickSubmit();
    }
}
