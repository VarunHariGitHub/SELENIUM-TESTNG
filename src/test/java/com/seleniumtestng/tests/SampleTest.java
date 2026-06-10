package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest extends BaseTest {

    @Test
    public void verifyPageTitle() {
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title should not be null");
        System.out.println("Page title: " + title);
    }
}
