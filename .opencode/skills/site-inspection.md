# Site Inspection

Inspect the live target site to verify locators and discover DOM structure before creating or fixing page objects.

## Workflow

1. **Start browser** → navigate to the page URL
2. **Snapshot the page** → use `playwright_browser_snapshot` or `selenium_execute_script` to dump DOM
3. **Verify key locators** — check IDs, CSS classes, data attributes match assumptions
4. **Test interactions** — click buttons, type into inputs, verify behavior
5. **Update page object** → fix locators, add missing waits, handle quirks

## What to check

- Element IDs (`id` attribute) — most stable
- CSS classes that are unique enough for `By.cssSelector`
- `data-*` attribute values (e.g. table `data-col` values)
- `name` attributes on buttons/inputs when IDs are duplicated
- Hidden/show behavior — does `#loading` appear? is the element really invisible via `display: none` or `visibility: hidden`?
- `target="_blank"` on links — need window handle management

## Quick JS snippet for bulk element dump

```javascript
// Dump all table column attributes
JSON.stringify(Array.from(document.querySelectorAll('#courses_table td')).map(td => ({ text: td.textContent.trim(), 'data-col': td.getAttribute('data-col') })))
```

```javascript
// Find all unique IDs on the page (catches duplicates)
Array.from(document.querySelectorAll('[id]')).map(el => el.id).filter((id, i, arr) => arr.indexOf(id) !== i)
```

## Common pitfalls

- `@FindBy` returns the **first** element in the DOM when IDs are duplicated
- `By.cssSelector` is faster than `By.xpath` — prefer it
- Hidden filtered table rows still return empty `getText()` — check `display` style on ancestor `tr`
- `style="disaplay: none;"` is a CSS typo — the element is fully visible despite the intent
