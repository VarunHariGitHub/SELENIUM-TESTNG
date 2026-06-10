package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.pages.NavigationPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NavigationTests extends BaseTest {

    private NavigationPage navPage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        navPage = new NavigationPage(driver);
        driver.get(ConfigReader.getBaseUrl());
    }

    @Test(groups = "smoke")
    public void testNavigationLinks() {
        String[][] navItems = {
                {"Home", ConfigReader.getBaseUrl() + "/"},
                {"Practice", "practice"},
                {"Courses", "courses"},
                {"Blog", "blog"},
                {"Contact", "contact"}
        };
        for (String[] item : navItems) {
            navPage.clickNavLink(item[0]);
            String url = navPage.getCurrentUrl();
            Assert.assertTrue(url.contains(item[1]),
                    "Link '" + item[0] + "' should navigate to URL containing: " + item[1]);
        }
    }

    @Test(groups = "smoke")
    public void testLogoNavigatesToHome() {
        driver.get(ConfigReader.getBaseUrl() + "/practice/");
        navPage.clickLogo();
        Assert.assertEquals(navPage.getCurrentUrl(), ConfigReader.getBaseUrl() + "/");
    }

    @Test
    public void testFooterCopyright() {
        Assert.assertTrue(navPage.isFooterVisible(), "Footer should be visible");
        String footerText = navPage.getFooterText();
        Assert.assertTrue(footerText.contains("Practice Test Automation"),
                "Footer should contain 'Practice Test Automation'");
    }
}
