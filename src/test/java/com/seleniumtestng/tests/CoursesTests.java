package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.Duration;

public class CoursesTests extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        driver.get(ConfigReader.getBaseUrl() + "/courses/");
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.titleContains("Course"));
    }

    @Test(groups = "smoke")
    public void testCoursesPageLoads() {
        String title = driver.getTitle();
        Assert.assertTrue(title.toLowerCase().contains("course"),
                "Page title should mention courses");
        boolean hasCourses = driver.findElements(By.cssSelector(".post, article, .course, .wp-block-column"))
                .size() > 0;
        Assert.assertTrue(hasCourses, "Course listings should be present");
    }
}
