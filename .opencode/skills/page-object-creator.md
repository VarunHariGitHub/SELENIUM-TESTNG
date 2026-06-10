# Page Object Creator

Conventions for creating new page objects in this project.

## Template

```java
package com.seleniumtestng.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class XyzPage extends BasePage {

    @FindBy(id = "...")
    private WebElement someField;

    public XyzPage(WebDriver driver) {
        super(driver);
    }

    // Interaction methods: void
    public void doSomething() { ... }

    // Query methods: return state or value
    public String getSomething() {
        wait.until(ExpectedConditions.visibilityOf(someField));
        return someField.getText();
    }

    public boolean isSomethingDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(someField));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
```

## Rules

1. **Extend `BasePage`** — constructor takes `WebDriver`, calls `super(driver)`
2. **Use `@FindBy`** for stable elements — locator order: `id` > CSS > XPath
3. **Use `wait.until`** (inherited `WebDriverWait` with 15s timeout) — never `Thread.sleep`
4. **Never mutate `this.wait`** — create `new WebDriverWait(...)` if you need a different timeout
5. **Await `.clear()` + `.sendKeys()` sequentially** — disabled inputs throw `InvalidElementStateException`
6. **Expose `waitForLoadingToDisappear()`** — if the page has a `#loading` element after interactions
7. **Boolean query methods** — wrap in try/catch and return false, don't throw
8. **Use `By.cssSelector`** for dynamic elements found within methods, not `@FindBy`

## When to use `@FindBy` vs `By` directly

| Case | Use |
|---|---|
| Stable element with unique ID | `@FindBy(id = "...")` |
| Element appears after an action (e.g. row2) | `wait.until(visibilityOfElementLocated(By.cssSelector("...")))` |
| Duplicate IDs exist | `By.cssSelector()` or `By.xpath()` in a method |
| List of similar elements | `@FindBy(css = "...") private List<WebElement> items;` |

## Duplicate ID workaround

The exceptions page has duplicate `save_btn` and `edit_btn` IDs (one per row).
- Row1 save: `@FindBy(id = "save_btn")` works (first in DOM)
- Row2 save: use `By.cssSelector("#row2 button[name='Save']")` in a method
