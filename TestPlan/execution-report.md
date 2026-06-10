# Execution Report

**Date:** 2026-06-10
**Duration:** 2m 07s (final run)
**Tests:** 30 total, **30 passed**, 0 failed, 0 errors, 0 skipped

## All Tests Passing (30/30)

| Module | Tests | Status |
|--------|-------|--------|
| NavigationTests | 3 | ✅ All passed |
| HomeTests | 2 | ✅ All passed |
| LoginTests | 5 | ✅ All passed |
| ExceptionTests | 5 | ✅ All passed |
| TableTests | 8 | ✅ All passed |
| ContactTests | 3 | ✅ All passed |
| BlogTests | 1 | ✅ All passed |
| PracticeTests | 1 | ✅ All passed |
| CoursesTests | 2 | ✅ All passed |

## Issues Resolved (from first run)

| # | Issue | Fix |
|---|-------|-----|
| 1 | Home page h1 text mismatch | Changed assertion from "Welcome" → "Hello" |
| 2 | Row 2 input not found after Add | Fixed locator: `#rows .row:nth-child(2)` → `#row2` |
| 3 | ExceptionsPage stale element test | Redesigned tests to match actual site behavior |
| 4 | TablePage `data-col` attribute mismatch | Fixed `enroll` → `enrollments` (plural) |
| 5 | TablePage hidden rows return empty text | `getColumnValues()` now filters out hidden rows |
| 6 | Course link opens new tab | Added `windowHandles` switch logic |
| 7 | Login error message not visible | Added `wait.until(visibilityOf(errorDiv))` |
| 8 | Disabled input exception type mismatch | Changed catch from `ElementNotInteractableException` → `InvalidElementStateException` |
| 9 | Contact page timeout on first load | Added retry logic in `@BeforeMethod` |
| 10 | Courses page title wait | Added `WebDriverWait` for title in `@BeforeMethod` |

## Root Cause Categories

- **Locator issues:** 5 (wrong selector, data-col mismatch, duplicate IDs)
- **Site behavior differences:** 3 (stale element, hidden rows, disabled input)
- **Timing/synchronization:** 2 (page load timeout, missing title wait)
