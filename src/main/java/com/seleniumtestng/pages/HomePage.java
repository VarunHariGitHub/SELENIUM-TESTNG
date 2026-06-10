package com.seleniumtestng.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(tagName = "h1")
    private WebElement mainHeading;

    @FindBy(css = "img[alt*='Dmitry Shyshkin']")
    private WebElement avatarImage;

    @FindBy(xpath = "//*[contains(text(),'FREE XPath')]")
    private WebElement newsletterText;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHeadingDisplayed() {
        return mainHeading.isDisplayed();
    }

    public String getHeadingText() {
        return mainHeading.getText();
    }

    public boolean isAvatarDisplayed() {
        return avatarImage.isDisplayed();
    }

    public boolean isNewsletterSectionDisplayed() {
        return newsletterText.isDisplayed();
    }
}
