# Test Script Review Report

**Date:** 2026-06-10
**Reviewer:** Code Review Agent

## Files Reviewed

### Page Objects (8 files)
- NavigationPage.java, HomePage.java, LoginPage.java, LoggedInPage.java
- ExceptionsPage.java, TablePage.java, ContactPage.java, PracticePage.java

### Test Classes (9 files)
- NavigationTests.java, HomeTests.java, LoginTests.java, ExceptionTests.java
- TableTests.java, ContactTests.java, CoursesTests.java, BlogTests.java
- PracticeTests.java

### Support Files (2 files)
- BaseTest.java, BasePage.java

---

## Issues Found

### ERROR — ExceptionsPage.waitForRow2WithTimeout mutates shared state
**File:** `src/main/java/com/seleniumtestng/pages/ExceptionsPage.java:86-94`
**Issue:** `wait.withTimeout(Duration.ofSeconds(seconds))` permanently changes the timeout of the class-level `wait` instance. After calling `waitForRow2WithTimeout(3)`, all subsequent `wait.until(...)` calls in the same test only wait 3 seconds instead of 15.
**Fix:** Use `new WebDriverWait(driver, Duration.ofSeconds(seconds))` instead of mutating `this.wait`.

---

### WARNING — getRow1Input / getRow2Input use raw findElement without wait
**Files:** `ExceptionsPage.java:44-46`, `ExceptionsPage.java:58-60`
**Issue:** These methods use `driver.findElement()` directly. If the element is not immediately present (e.g. newly added row), the call throws `NoSuchElementException` instead of waiting.
**Fix:** Wrap in `wait.until(ExpectedConditions.visibilityOfElementLocated(...))`.

---

### WARNING — NavigationPage.clickNavLink lacks explicit wait
**File:** `src/main/java/com/seleniumtestng/pages/NavigationPage.java:20-23`
**Issue:** Uses `driver.findElement(By.xpath(...)).click()` with no wait. If the nav menu hasn't rendered, the test fails with `NoSuchElementException`.
**Fix:** Use `wait.until(ExpectedConditions.elementToBeClickable(By.xpath(...))).click()`.

---

### WARNING — Hardcoded base URL across all test classes
**Files:** All 9 test classes (e.g. `NavigationTests.java:16`, `LoginTests.java:17`, `ExceptionTests.java:16`, etc.)
**Issue:** Every `driver.get("https://practicetestautomation.com/...")` is hardcoded. Changing the target URL requires editing every test file.
**Fix:** Use `ConfigReader.getBaseUrl()` + relative path, e.g. `driver.get(ConfigReader.getBaseUrl() + "/practice-test-login/")`.

---

### WARNING — CoursesTests / BlogTests bypass page objects
**Files:** `CourseTests.java:21`, `BlogTests.java:21`
**Issue:** Raw `By` selectors used directly in test classes instead of page object methods. Violates the POM pattern used everywhere else.
**Fix:** Create `CoursesPage.java` and `BlogPage.java` page objects and move selectors there.

---

### WARNING — ExceptionTests.testInvalidElementStateScenario weak assertion
**File:** `ExceptionTests.java:44`
**Issue:** `Assert.assertTrue(confirmation.contains("Row 1") || !confirmation.isEmpty())` — the `!confirmation.isEmpty()` clause is always true if any text is present, masking false passes.
**Fix:** Use `Assert.assertTrue(confirmation.contains("Row 1"), "Expected confirmation about Row 1 but got: " + confirmation)`.

---

### SUGGESTION — PracticeTests combos 3 navigations in one test
**File:** `PracticeTests.java:19-34`
**Issue:** Single `@Test` method calls `driver.get()` twice mid-test to reset state. If the first assertion fails, the remaining 2 link checks are skipped.
**Fix:** Split into 3 separate test methods or use `@DataProvider`.

---

### SUGGESTION — NavigationTests.testNavigationLinks tests 5 links in one loop
**File:** `NavigationTests.java:19-33`
**Issue:** First failing link aborts the whole test; remaining 4 links go unverified.
**Fix:** Use `@DataProvider` for individual test methods per link.

---

### SUGGESTION — No `@DataProvider` usage despite framework capability
**Files:** All test classes
**Issue:** No data-driven tests. Login credentials, filter options, nav links could use `@DataProvider` for better coverage with less boilerplate.
**Fix:** Add `@DataProvider` for Positive Login (multiple valid users), Table Filters (multiple combinations), etc.

---

### SUGGESTION — ContactTests empty/invalid email assertions are weak
**Files:** `ContactTests.java:29-43`
**Issue:** Only checks URL stays on `/contact/`. Does not validate inline validation errors, field highlighting, or server-side error messages.
**Fix:** Add explicit error message or validation state assertions.

---

## Summary

| Severity | Count |
|----------|-------|
| ERROR    | 1     |
| WARNING  | 6     |
| SUGGESTION | 4   |

**Total issues:** 11

**Most critical:** `waitForRow2WithTimeout` permanently changes the class wait timeout — will cause flaky failures in tests after it runs.
