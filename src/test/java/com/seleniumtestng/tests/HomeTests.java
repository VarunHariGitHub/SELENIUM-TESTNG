package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomeTests extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        homePage = new HomePage(driver);
        driver.get(ConfigReader.getBaseUrl());
    }

    @Test(groups = "smoke")
    public void testHomePageHeroSection() {
        Assert.assertTrue(homePage.isHeadingDisplayed(), "Main heading should be displayed");
        String heading = homePage.getHeadingText();
        Assert.assertTrue(heading.contains("Hello"),
                "Heading should contain hello message but was: " + heading);
        Assert.assertTrue(homePage.isAvatarDisplayed(), "Instructor avatar should be displayed");
    }

    @Test
    public void testNewsletterSignupSection() {
        Assert.assertTrue(homePage.isNewsletterSectionDisplayed(),
                "Newsletter signup section should be displayed");
    }
}
