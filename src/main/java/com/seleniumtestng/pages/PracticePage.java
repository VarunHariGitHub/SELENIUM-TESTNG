package com.seleniumtestng.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PracticePage extends BasePage {

    public PracticePage(WebDriver driver) {
        super(driver);
    }

    public void clickTestLoginLink() {
        driver.findElement(By.linkText("Test Login Page")).click();
    }

    public void clickTestExceptionsLink() {
        driver.findElement(By.linkText("Test Exceptions")).click();
    }

    public void clickTestTableLink() {
        driver.findElement(By.linkText("Test Table")).click();
    }
}
