package com.seleniumtestng.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoggedInPage extends BasePage {

    @FindBy(css = "p.has-text-align-center strong")
    private WebElement successMessage;

    @FindBy(css = "a.wp-block-button__link")
    private WebElement logoutButton;

    public LoggedInPage(WebDriver driver) {
        super(driver);
    }

    public String getSuccessMessage() {
        return successMessage.getText();
    }

    public boolean isLogoutButtonDisplayed() {
        return logoutButton.isDisplayed();
    }

    public void clickLogout() {
        logoutButton.click();
    }
}
