package com.seleniumtestng.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExceptionsPage extends BasePage {

    @FindBy(id = "add_btn")
    private WebElement addButton;

    @FindBy(id = "edit_btn")
    private WebElement editButton;

    @FindBy(id = "save_btn")
    private WebElement saveButton;

    @FindBy(id = "confirmation")
    private WebElement confirmation;

    @FindBy(id = "loading")
    private WebElement loading;

    public ExceptionsPage(WebDriver driver) {
        super(driver);
    }

    public void clickAdd() {
        addButton.click();
    }

    public void clickEdit() {
        editButton.click();
    }

    public void clickSave() {
        saveButton.click();
    }

    public WebElement getRow1Input() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#row1 .input-field")));
    }

    public boolean isRow2InputDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("#row2 .input-field")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public WebElement getRow2Input() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#row2 .input-field")));
    }

    public void typeIntoRow2Input(String text) {
        WebElement input = getRow2Input();
        input.clear();
        input.sendKeys(text);
    }

    public void typeIntoRow1Input(String text) {
        WebElement input = getRow1Input();
        input.clear();
        input.sendKeys(text);
    }

    public void clickRow2Save() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#row2 button[name='Save']"))).click();
    }

    public String getConfirmationText() {
        wait.until(ExpectedConditions.visibilityOf(confirmation));
        return confirmation.getText();
    }

    public boolean isInstructionsDisplayed() {
        try {
            return driver.findElement(By.xpath("//*[contains(text(),'Create list of your favorite foods')]")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean waitForRow2WithTimeout(int seconds) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#row2 .input-field")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void waitForLoadingToDisappear() {
        wait.until(ExpectedConditions.invisibilityOf(loading));
    }
}
