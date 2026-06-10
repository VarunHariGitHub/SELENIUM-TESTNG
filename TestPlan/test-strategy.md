# Test Strategy — Practice Test Automation

## 1. Scope & Objectives

**Objective:** Automate functional UI testing of https://practicetestautomation.com/ to validate core user workflows across navigation, authentication, form interactions, dynamic content, and data display.

**In-Scope:**
- Home page layout and navigation bar
- Practice page (hub linking to test pages)
- Test Login — positive and negative login flows
- Logged-In Successfully page — post-login verification
- Test Exceptions — all 5 exception-based test scenarios
- Test Table — filtering, sorting, and no-results states
- Contact form — validation and submission
- Courses page — content verification
- Blog page — content verification
- Responsive header/footer across all pages

**Out-of-Scope:**
- Performance/load testing
- Security testing (SQL injection, XSS)
- Mobile native app testing
- API-level testing (not exposed publicly)
- Third-party integrations (YouTube, Udemy embeds)

## 2. Test Levels & Types

| Level | Type | Description |
|---|---|---|
| System | Functional | End-to-end user workflows |
| System | UI | Layout, visibility, element states |
| System | Data Validation | Table filters, sort order, enrollment numbers |
| Regression | Re-run | Full suite against each new build |

## 3. Browser & Device Coverage

| Browser | OS | Priority |
|---|---|---|
| Chrome (latest) | Windows 10/11 | Critical |
| Firefox (latest) | Windows 10/11 | High |
| Edge (latest) | Windows 10/11 | High |
| Chrome (mobile viewport) | Windows (emulated) | Medium |

All tests execute in headless mode for CI; headed mode for local debugging.

## 4. Automation Framework

- **Language:** Java 17
- **Framework:** TestNG 7.11
- **Driver Mgmt:** WebDriverManager 5.9 (auto-manages browser drivers)
- **Pattern:** Page Object Model with `BasePage` providing `WebDriverWait`
- **Base Test:** `BaseTest` with `@BeforeSuite`/`@AfterSuite` lifecycle
- **Config:** `config.properties` for browser, URL, timeouts
- **Runner:** Maven Surefire with `testng.xml` suite

## 5. Test Data Management

| Data Type | Source |
|---|---|
| Login credentials | Hardcoded in test (student / Password123) |
| Invalid credentials | Hardcoded (incorrectUser, incorrectPassword) |
| Table filter selections | Test code constants / DataProvider |
| Contact form data | DataProvider |
| Exception test inputs | Inline in test methods |

## 6. Risk Assessment

| Risk | Impact | Likelihood | Mitigation |
|---|---|---|---|
| Dynamic element loading | High | Medium | Use explicit waits (WebDriverWait) |
| Table sort/filter changes | Medium | Low | Test against specific data attributes |
| Stale elements | High | High | Re-locate elements before interaction |
| Page layout changes | Medium | Medium | Use stable locators (IDs, data attributes) |
| Third-party content blocks | Low | Low | Mock or skip affected tests |

## 7. Pass/Fail Criteria

- **Pass:** All assertions pass; no unhandled exceptions; suite completes within 5 minutes.
- **Fail:** Any assertion fails; timeout or unexpected exception; suite > 5 minutes.

## 8. Entry & Exit Criteria

**Entry:** Test environment ready, browser driver available, target URL reachable.

**Exit:** All planned tests executed, report generated, no critical/blocker failures.

## 9. Deliverables

- `TestPlan/test-strategy.md` — this document
- `TestPlan/test-cases.md` — detailed test cases
- `testng.xml` — TestNG suite definition
- Page Object classes under `src/main/java/com/seleniumtestng/pages/`
- Test classes under `src/test/java/com/seleniumtestng/tests/`
- Execution report (Surefire HTML + XML)
