# Test Healing

Pattern for diagnosing and fixing test failures in this project.

## Triage flow

```
Failure → classify error type → inspect → fix → re-run
```

## Error classification

| Error type | Likely cause | Tool |
|---|---|---|
| `NoSuchElementException` | Locator changed or element not yet rendered | site-inspection to verify locator |
| `TimeoutException` on wait | Element never appears, or wrong selector | snapshot the page, check for `#loading` blocking |
| `StaleElementReferenceException` | DOM changed after element was found (page re-rendered) | re-find element after action, or use `wait.until` |
| `InvalidElementStateException` | element is `disabled` — `.clear()` or `.sendKeys()` fails | catch this, not `ElementNotInteractableException` |
| `AssertionError` | Locator returns wrong text or wrong number of results | check `data-*` attrs, hidden row filtering |
| `NullPointerException` on `driver` | `@BeforeTest`/`@BeforeMethod` lacks `alwaysRun = true` | add `alwaysRun = true` |
| `NullPointerException` on page object | `@BeforeMethod` didn't run — same cause as above | add `alwaysRun = true` |

## Fix checklist

1. **Read the error message** — the line number tells you exactly which interaction failed
2. **Inspect the live site** — navigate to the page and verify the element exists with the expected attributes
3. **Check for duplicate IDs** — `@FindBy(id = "foo")` returns the first match
4. **Check `#loading`** — if the page shows a loading indicator after an action, always call `waitForLoadingToDisappear()` before the next interaction
5. **Check table filtering** — JS-filtered rows are still in the DOM but hidden; filter them with XPath `ancestor::tr[not(contains(@style,'display: none'))]`
6. **Check `target="_blank"`** — course links open new tabs; must switch window handles
7. **Run the single test** to verify before running the full suite

## Verification

```powershell
mvn clean test -Dtest=ExceptionTests
mvn clean test -Dgroups=smoke   # if smoke test
```
