# Selenium TestNG Framework

## Framework

- **Java 11**, Selenium 4.30, TestNG 7.11, WebDriverManager 5.9, Log4j 2.24
- TestNG dependency has **no `<scope>test`** — `BaseTest` lives in `src/main/java` and needs TestNG at compile time
- `@BeforeTest(alwaysRun = true)` / `@AfterTest(alwaysRun = true)` mandatory — group filtering (`-Dgroups=smoke`) skips them otherwise
- `@BeforeMethod(alwaysRun = true)` same requirement for same reason
- `BasePage` constructor calls `PageFactory.initElements(driver, this)` and creates `WebDriverWait(driver, Duration.ofSeconds(15))` — protected field `wait` available to all page objects
- Never `Thread.sleep` — use `BasePage.wait.until(...)` always
- `DriverManager`: `ThreadLocal<WebDriver>`, auto-manages Chrome/Firefox/Edge via `config.properties`
- `ConfigReader`: static access to `config.properties` from classpath

## Target site

- `baseUrl = https://practicetestautomation.com` in `src/main/resources/config.properties`

### Site quirks (verified against live DOM)

- **Home page** `<h1>` text is `"Hello"`, not `"Welcome"`
- **Exceptions page** structure: `#rows > #row1`, `#rows > #loading`, `#rows > #row2` — row2 selector is `#row2 .input-field`, NOT `#rows .row:nth-child(2) .input-field`
- **Exceptions page** has **duplicate IDs** `save_btn` and `edit_btn` (one per row). For row2 save: use `#row2 button[name='Save']` instead
- **Exceptions page row2** has inline `style="disaplay: none;"` (CSS typo — element is fully visible)
- **Table page** `data-col` values: `"id"`, `"course"`, `"language"`, `"level"`, `"enrollments"` (plural!), `"link"`
- **Table page** filtering is JS-based — hidden rows return empty `getText()`; filter them out with `ancestor::tr[not(contains(@style,'display: none'))]`
- **Table page** course links use `target="_blank"` — must switch window handles (`driver.getWindowHandles()`), close new tab, switch back
- **Contact page** IDs: `wpforms-161-field_0` (first), `wpforms-161-field_0-last` (last), `wpforms-161-field_1` (email), `wpforms-161-field_2` (message), `wpforms-submit-161`
- **Logged-in page**: `p.has-text-align-center strong` (success), `a.wp-block-button__link` (logout)
- **Practice page**: nav links `#practice-page a[href*="login"]`, `[href*="exceptions"]`, `[href*="table"]`
- **Navigation**: logo is `.wp-block-site-logo a`, footer is `#colophon`, nav links match by text in `.wp-block-navigation-item a`

## Test files

- 9 test classes in `src/test/java/com/seleniumtestng/tests/` — 30 test methods total, 10 marked `groups = "smoke"`
- 8 page objects in `src/main/java/com/seleniumtestng/pages/`
- `CoursesTests` and `BlogTests` use raw `By` selectors (no Page Object yet)

## Commands

```
mvn clean test                                   # full suite (30 tests)
mvn clean test -Dgroups=smoke                    # smoke group only (10 tests)
mvn clean test -Dtest=ExceptionTests             # single test class
mvn clean test -Dsurefire.suiteXmlFiles=testng-smoke.xml   # alternate suite
```

Reports: `target/surefire-reports/`

## Known pitfalls

- Disabled `<input>`: calling `.clear()` throws `InvalidElementStateException` — catch that, not `ElementNotInteractableException`
- `ExceptionsPage.waitForRow2WithTimeout(int)` must use `new WebDriverWait(...)`, never mutate `this.wait` with `.withTimeout()`
- After clicking `edit_btn`, `add_btn`, or `save_btn`, the `#loading` element appears — always call `waitForLoadingToDisappear()` before interacting with results
- `@FindBy(id = "save_btn")` finds the *first* element in DOM (row1) — row2 needs explicit CSS `#row2 button[name='Save']`

## Agents

6 subagents in `.opencode/agents/` registered in `opencode.jsonc`:

| Agent | edit | Purpose |
|---|---|---|
| `test-plan-creator` | allow | Analyze website → `TestPlan/test-strategy.md` + `test-cases.md` |
| `test-script-creator` | allow | Test cases → page objects + TestNG classes |
| `test-script-reviewer` | **deny** | Code review → `TestPlan/review-report.md` |
| `test-script-healer` | allow | Fix broken locators/waits/assertions |
| `test-script-executor` | **deny** | `mvn clean test` → `TestPlan/execution-report.md` |
| `orchestrator` | allow | Pipeline: plan → create → review → heal (loop ≤3) → execute |

Invoke via `/task <agent-name> <prompt>`. Orchestrator delegates via `/task`.
