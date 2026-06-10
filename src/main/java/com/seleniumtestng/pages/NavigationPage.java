package com.seleniumtestng.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigationPage extends BasePage {

    @FindBy(css = ".custom-logo-link")
    private WebElement logoLink;

    @FindBy(className = "site-footer")
    private WebElement footer;

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public void clickNavLink(String linkText) {
        String xpath = "//ul[@id='menu-primary-items']//a[text()='" + linkText + "']";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    public void clickLogo() {
        logoLink.click();
    }

    public boolean isFooterVisible() {
        return footer.isDisplayed();
    }

    public String getFooterText() {
        return footer.getText();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }
}
