package com.seleniumtestng.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class TablePage extends BasePage {

    @FindBy(id = "resetFilters")
    private WebElement resetButton;

    @FindBy(id = "noData")
    private WebElement noDataMessage;

    @FindBy(id = "sortBy")
    private WebElement sortBySelect;

    @FindBy(id = "enrollDropdown")
    private WebElement enrollDropdown;

    @FindBy(css = "#courses_table tbody tr")
    private List<WebElement> tableRows;

    public TablePage(WebDriver driver) {
        super(driver);
    }

    public void selectLanguage(String language) {
        String xpath = "//input[@name='lang' and @value='" + language + "']";
        driver.findElement(By.xpath(xpath)).click();
    }

    public void setLevelCheckbox(String level, boolean checked) {
        String xpath = "//input[@name='level' and @value='" + level + "']";
        WebElement checkbox = driver.findElement(By.xpath(xpath));
        if (checkbox.isSelected() != checked) {
            checkbox.click();
        }
    }

    public void setMinEnrollments(String value) {
        enrollDropdown.click();
        String xpath = "//div[@id='enrollDropdown']//li[@data-value='" + value + "']";
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
    }

    public void sortBy(String optionValue) {
        Select select = new Select(sortBySelect);
        select.selectByValue(optionValue);
    }

    public void clickReset() {
        resetButton.click();
    }

    public boolean isResetButtonDisplayed() {
        return resetButton.isDisplayed();
    }

    public boolean isNoDataMessageDisplayed() {
        try {
            return noDataMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getNoDataMessageText() {
        return noDataMessage.getText();
    }

    public int getRowCount() {
        return tableRows.size();
    }

    public List<String> getColumnValues(String dataCol) {
        List<String> values = new ArrayList<>();
        String xpath = "//table[@id='courses_table']//td[@data-col='" + dataCol + "' and not(ancestor::tr[contains(@style,'display: none')])]";
        List<WebElement> cells = driver.findElements(By.xpath(xpath));
        for (WebElement cell : cells) {
            if (cell.isDisplayed()) {
                values.add(cell.getText().trim());
            }
        }
        return values;
    }

    public List<String> getCourseNames() {
        return getColumnValues("course");
    }

    public List<String> getLanguages() {
        return getColumnValues("language");
    }

    public List<String> getLevels() {
        return getColumnValues("level");
    }

    public List<Integer> getEnrollments() {
        List<Integer> enrollments = new ArrayList<>();
        for (String val : getColumnValues("enrollments")) {
            enrollments.add(Integer.parseInt(val.replace(",", "")));
        }
        return enrollments;
    }

    public void clickCourseLink(String courseName) {
        String xpath = "//td[@data-col='course'][text()='" + courseName + "']/following-sibling::td//a";
        driver.findElement(By.xpath(xpath)).click();
    }

    public boolean isDefaultState() {
        try {
            WebElement anyRadio = driver.findElement(By.xpath("//input[@name='lang' and @value='Any']"));
            boolean langOk = anyRadio.isSelected();

            WebElement beginner = driver.findElement(By.xpath("//input[@name='level' and @value='Beginner']"));
            WebElement intermediate = driver.findElement(By.xpath("//input[@name='level' and @value='Intermediate']"));
            WebElement advanced = driver.findElement(By.xpath("//input[@name='level' and @value='Advanced']"));
            boolean levelsOk = beginner.isSelected() && intermediate.isSelected() && advanced.isSelected();

            String enrollLabel = enrollDropdown.findElement(By.cssSelector(".dropdown-label")).getText();
            boolean enrollOk = enrollLabel.equals("Any");

            return langOk && levelsOk && enrollOk;
        } catch (Exception e) {
            return false;
        }
    }
}
