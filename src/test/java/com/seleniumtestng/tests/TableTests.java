package com.seleniumtestng.tests;

import com.seleniumtestng.base.BaseTest;
import com.seleniumtestng.config.ConfigReader;
import com.seleniumtestng.pages.TablePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class TableTests extends BaseTest {

    private TablePage tablePage;

    @BeforeMethod(alwaysRun = true)
    public void initPage() {
        tablePage = new TablePage(driver);
        driver.get(ConfigReader.getBaseUrl() + "/practice-test-table/");
    }

    @Test(groups = "smoke")
    public void testFilterByLanguageJava() {
        tablePage.selectLanguage("Java");
        List<String> languages = tablePage.getLanguages();
        Assert.assertTrue(languages.size() > 0, "Should return courses");
        for (String lang : languages) {
            Assert.assertEquals(lang, "Java", "All courses should be Java");
        }
    }

    @Test
    public void testFilterByLevelBeginner() {
        tablePage.setLevelCheckbox("Intermediate", false);
        tablePage.setLevelCheckbox("Advanced", false);
        List<String> levels = tablePage.getLevels();
        Assert.assertTrue(levels.size() > 0, "Should return courses");
        for (String level : levels) {
            Assert.assertEquals(level, "Beginner", "All courses should be Beginner");
        }
    }

    @Test
    public void testFilterByMinEnrollments() {
        tablePage.setMinEnrollments("10000");
        List<Integer> enrollments = tablePage.getEnrollments();
        Assert.assertTrue(enrollments.size() > 0, "Should return courses");
        for (int enrollment : enrollments) {
            Assert.assertTrue(enrollment >= 10000,
                    "Enrollment " + enrollment + " should be >= 10000");
        }
    }

    @Test
    public void testCombinedFilters() {
        tablePage.selectLanguage("Python");
        tablePage.setLevelCheckbox("Intermediate", false);
        tablePage.setLevelCheckbox("Advanced", false);
        List<String> languages = tablePage.getLanguages();
        List<String> levels = tablePage.getLevels();
        Assert.assertTrue(languages.size() > 0, "Should return courses");
        for (int i = 0; i < languages.size(); i++) {
            Assert.assertEquals(languages.get(i), "Python", "All courses should be Python");
            Assert.assertEquals(levels.get(i), "Beginner", "All courses should be Beginner");
        }
    }

    @Test
    public void testNoResultsState() {
        tablePage.selectLanguage("Java");
        tablePage.setLevelCheckbox("Beginner", false);
        tablePage.setLevelCheckbox("Advanced", false);
        if (tablePage.isNoDataMessageDisplayed()) {
            Assert.assertTrue(tablePage.getNoDataMessageText().contains("No matching courses"));
        }
    }

    @Test
    public void testResetButton() {
        tablePage.selectLanguage("Java");
        Assert.assertTrue(tablePage.isResetButtonDisplayed(), "Reset button should appear after filter change");
        tablePage.clickReset();
        Assert.assertTrue(tablePage.isDefaultState(), "Filters should return to default after reset");
        Assert.assertFalse(tablePage.isResetButtonDisplayed(), "Reset button should hide after reset");
    }

    @Test
    public void testSortByEnrollments() {
        tablePage.sortBy("col_enroll");
        List<Integer> enrollments = tablePage.getEnrollments();
        Assert.assertTrue(enrollments.size() >= 2, "Need at least 2 courses for sort validation");
        for (int i = 1; i < enrollments.size(); i++) {
            Assert.assertTrue(enrollments.get(i) >= enrollments.get(i - 1),
                    "Enrollments should be sorted ascending");
        }
    }

    @Test
    public void testSortByCourseName() {
        tablePage.sortBy("col_course");
        List<String> names = tablePage.getCourseNames();
        Assert.assertTrue(names.size() >= 2, "Need at least 2 courses for sort validation");
        for (int i = 1; i < names.size(); i++) {
            Assert.assertTrue(names.get(i).compareToIgnoreCase(names.get(i - 1)) >= 0,
                    "Course names should be sorted alphabetically");
        }
    }

    @Test
    public void testCourseLinkNavigates() {
        String currentHandle = driver.getWindowHandle();
        tablePage.clickCourseLink("Selenium Framework");
        for (String handle : driver.getWindowHandles()) {
            if (!handle.equals(currentHandle)) {
                driver.switchTo().window(handle);
                break;
            }
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("selenium"),
                "Course link should navigate to a selenium-related URL");
        driver.close();
        driver.switchTo().window(currentHandle);
    }
}
