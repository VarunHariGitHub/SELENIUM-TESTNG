# Smoke Triage

Deciding which tests deserve `groups = "smoke"` and how to verify.

## Criteria

A smoke test should be:
- **Critical** — validates a core user-facing feature
- **Fast** — completes in under 30 seconds (no heavy waits or multi-step workflows)
- **Independent** — doesn't depend on other tests passing
- **Stable** — not flaky (no race conditions, no slow-load features)

## Current selection (10 smoke tests out of 30)

| Class | Smoke test | Why |
|---|---|---|
| NavigationTests | `testNavigationLinks`, `testLogoNavigatesToHome` | Site navigation must work |
| HomeTests | `testHomePageHeroSection` | Home page is the landing page |
| LoginTests | `testPositiveLogin` | Login is the primary auth path |
| ExceptionTests | `testNoSuchElementScenario` | Core exception handling flow |
| TableTests | `testFilterByLanguageJava` | Table filtering is a main feature |
| ContactTests | `testContactFormDisplayed` | Contact form is a key page |
| CoursesTests | `testCoursesPageLoads` | Courses page must render |
| BlogTests | `testBlogPageLoads` | Blog page must render |
| PracticeTests | `testPracticeLinks` | Practice page links must work |

## Marking a test as smoke

```java
@Test(groups = "smoke")
public void testMyCriticalFeature() { ... }
```

## Requirement

All `@BeforeTest`, `@AfterTest`, and `@BeforeMethod` methods **must** have `alwaysRun = true` or they will be skipped when filtering by `-Dgroups=smoke`.

```java
@BeforeMethod(alwaysRun = true)
public void initPage() { ... }
```

## Verification

```powershell
mvn clean test -Dgroups=smoke
```

Expect 10 tests, 0 failures, under 2 minutes.

To run alongside full suite without issues, use `testng-smoke.xml`:
```powershell
mvn clean test -Dsurefire.suiteXmlFiles=testng-smoke.xml
```
