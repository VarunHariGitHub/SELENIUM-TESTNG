package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BlogTests extends BaseTest {

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        driver.get(ConfigReader.getBaseUrl() + "/blog/");
    }

    @Test(groups = "smoke")
    public void testBlogPageLoads() {
        String title = driver.getTitle();
        Assert.assertTrue(title.toLowerCase().contains("blog") || title.toLowerCase().contains("test automation"),
                "Page title should contain blog-related text");
        boolean hasArticles = driver.findElements(By.cssSelector("article, .post, .entry")).size() > 0;
        Assert.assertTrue(hasArticles, "Blog articles should be present");
    }
}
