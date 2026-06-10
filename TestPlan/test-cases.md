# Test Cases — Practice Test Automation

---

## Module 1: Navigation & Layout

### TC-NAV-001: Verify navigation bar links
| Field | Value |
|---|---|
| **ID** | TC-NAV-001 |
| **Title** | Verify all navigation bar links navigate to correct pages |
| **Priority** | High |
| **Module** | Navigation |
| **Type** | Functional |
| **Preconditions** | Browser opened on home page |
| **Steps** | 1. Locate nav bar with links: Home, Practice, Courses, Blog, Contact<br>2. Click each link sequentially<br>3. Verify URL and page heading for each destination |
| **Expected** | Each link navigates to the correct page; page heading matches link text |
| **Test Data** | None |

### TC-NAV-002: Verify header logo links to home
| Field | Value |
|---|---|
| **ID** | TC-NAV-002 |
| **Title** | Verify clicking the logo navigates to home page |
| **Priority** | Medium |
| **Module** | Navigation |
| **Type** | UI |
| **Preconditions** | Browser on any internal page |
| **Steps** | 1. Navigate to Practice page<br>2. Click the site logo |
| **Expected** | URL returns to https://practicetestautomation.com/ |

### TC-NAV-003: Verify footer is present and contains copyright
| Field | Value |
|---|---|
| **ID** | TC-NAV-003 |
| **Title** | Verify footer displays copyright text |
| **Priority** | Low |
| **Module** | Navigation |
| **Type** | UI |
| **Preconditions** | Browser on any page |
| **Steps** | 1. Scroll to page bottom<br>2. Locate footer element<br>3. Verify copyright text contains "Practice Test Automation" and current year |
| **Expected** | Footer is visible; copyright text is present |

---

## Module 2: Home Page

### TC-HOME-001: Verify home page hero section
| Field | Value |
|---|---|
| **ID** | TC-HOME-001 |
| **Title** | Verify home page displays welcome message and avatar |
| **Priority** | Medium |
| **Module** | Home |
| **Type** | UI |
| **Preconditions** | Browser on home page |
| **Steps** | 1. Verify "Welcome to Practice Test Automation" heading is visible<br>2. Verify Dmitry Shyshkin avatar image is displayed<br>3. Verify instructor description text is present |
| **Expected** | Hero section renders with heading, avatar, and description |

### TC-HOME-002: Verify newsletter signup section
| Field | Value |
|---|---|
| **ID** | TC-HOME-002 |
| **Title** | Verify XPath cheat sheet newsletter signup form is present |
| **Priority** | Medium |
| **Module** | Home |
| **Type** | Functional |
| **Preconditions** | Browser on home page |
| **Steps** | 1. Scroll to newsletter signup section<br>2. Verify "Get a FREE XPath cheat sheet" text is displayed<br>3. Verify email input field is present<br>4. (Optional) Submit empty form and verify behavior |
| **Expected** | Newsletter form is visible with email field |

---

## Module 3: Test Login

### TC-LOGIN-001: Positive login — valid credentials
| Field | Value |
|---|---|
| **ID** | TC-LOGIN-001 |
| **Title** | Successful login with valid username and password |
| **Priority** | Critical |
| **Module** | Login |
| **Type** | Functional |
| **Preconditions** | Browser on https://practicetestautomation.com/practice-test-login/ |
| **Steps** | 1. Enter username: `student`<br>2. Enter password: `Password123`<br>3. Click Submit button<br>4. Verify URL contains `logged-in-successfully`<br>5. Verify page contains "Congratulations" or "successfully logged in"<br>6. Verify "Log out" button is displayed |
| **Expected** | User is redirected to the logged-in success page; congratulations text and log out button visible |
| **Test Data** | username=student, password=Password123 |

### TC-LOGIN-002: Negative login — invalid username
| Field | Value |
|---|---|
| **ID** | TC-LOGIN-002 |
| **Title** | Login fails with incorrect username |
| **Priority** | Critical |
| **Module** | Login |
| **Type** | Negative |
| **Preconditions** | Browser on login page |
| **Steps** | 1. Enter username: `incorrectUser`<br>2. Enter password: `Password123`<br>3. Click Submit button<br>4. Verify error message is displayed<br>5. Verify error text is "Your username is invalid!" |
| **Expected** | Error message appears with text "Your username is invalid!" |
| **Test Data** | username=incorrectUser, password=Password123 |

### TC-LOGIN-003: Negative login — invalid password
| Field | Value |
|---|---|
| **ID** | TC-LOGIN-003 |
| **Title** | Login fails with correct username but wrong password |
| **Priority** | Critical |
| **Module** | Login |
| **Type** | Negative |
| **Preconditions** | Browser on login page |
| **Steps** | 1. Enter username: `student`<br>2. Enter password: `incorrectPassword`<br>3. Click Submit button<br>4. Verify error message is displayed<br>5. Verify error text is "Your password is invalid!" |
| **Expected** | Error message appears with text "Your password is invalid!" |
| **Test Data** | username=student, password=incorrectPassword |

### TC-LOGIN-004: Logout from success page
| Field | Value |
|---|---|
| **ID** | TC-LOGIN-004 |
| **Title** | Log out button returns to login page |
| **Priority** | High |
| **Module** | Login |
| **Type** | Functional |
| **Preconditions** | User is logged in (on logged-in-successfully page) |
| **Steps** | 1. Click "Log out" button<br>2. Verify URL returns to login page |
| **Expected** | User is redirected back to the login page |

### TC-LOGIN-005: Empty fields validation
| Field | Value |
|---|---|
| **ID** | TC-LOGIN-005 |
| **Title** | Login with empty username and password |
| **Priority** | Medium |
| **Module** | Login |
| **Type** | Negative |
| **Preconditions** | Browser on login page |
| **Steps** | 1. Leave username field empty<br>2. Leave password field empty<br>3. Click Submit button |
| **Expected** | HTML5 validation or error message is triggered (browser behavior may vary); no redirect occurs |

---

## Module 4: Test Exceptions

### TC-EXC-001: NoSuchElementException scenario
| Field | Value |
|---|---|
| **ID** | TC-EXC-001 |
| **Title** | Wait for dynamically added row 2 input field |
| **Priority** | High |
| **Module** | Exceptions |
| **Type** | Functional |
| **Preconditions** | Browser on https://practicetestautomation.com/practice-test-exceptions/ |
| **Steps** | 1. Click "Add" button<br>2. Wait for Row 2 input field to appear (use explicit wait)<br>3. Verify Row 2 input field is displayed |
| **Expected** | Row 2 input field becomes visible after clicking Add (with proper wait) |

### TC-EXC-002: ElementNotInteractableException scenario
| Field | Value |
|---|---|
| **ID** | TC-EXC-002 |
| **Title** | Click correct Save button when two elements share the same name |
| **Priority** | High |
| **Module** | Exceptions |
| **Type** | Functional |
| **Preconditions** | Browser on exceptions page |
| **Steps** | 1. Click "Add" button<br>2. Wait for Row 2 input field to appear<br>3. Type text into Row 2 input field<br>4. Click Save button using By.name("Save")<br>5. Verify text was saved successfully |
| **Expected** | Text is saved; verify by checking saved confirmation text |

### TC-EXC-003: InvalidElementStateException scenario
| Field | Value |
|---|---|
| **ID** | TC-EXC-003 |
| **Title** | Edit a disabled input field |
| **Priority** | High |
| **Module** | Exceptions |
| **Type** | Functional |
| **Preconditions** | Browser on exceptions page |
| **Steps** | 1. Click "Edit" button to enable the first row's input field<br>2. Clear the input field<br>3. Type new text into the input field<br>4. Verify text changed to the new value |
| **Expected** | Input field is enabled after clicking Edit; text can be modified and saved |

### TC-EXC-004: StaleElementReferenceException scenario
| Field | Value |
|---|---|
| **ID** | TC-EXC-004 |
| **Title** | Verify instructions text is removed after adding a row |
| **Priority** | High |
| **Module** | Exceptions |
| **Type** | Functional |
| **Preconditions** | Browser on exceptions page |
| **Steps** | 1. Locate the instructions text element<br>2. Click "Add" button<br>3. Verify the instructions text element is no longer displayed |
| **Expected** | Instructions element is removed from DOM and no longer displayed |

### TC-EXC-005: TimeoutException scenario
| Field | Value |
|---|---|
| **ID** | TC-EXC-005 |
| **Title** | Verify timeout when waiting with insufficient wait time |
| **Priority** | Medium |
| **Module** | Exceptions |
| **Type** | Negative |
| **Preconditions** | Browser on exceptions page |
| **Steps** | 1. Click "Add" button<br>2. Wait for only 3 seconds for Row 2 input field<br>3. Attempt to verify Row 2 input field is displayed |
| **Expected** | TimeoutException is thrown because Row 2 appears after ~5 seconds |

---

## Module 5: Test Table

### TC-TABLE-001: Filter by language — Java
| Field | Value |
|---|---|
| **ID** | TC-TABLE-001 |
| **Title** | Filter courses by Java language |
| **Priority** | High |
| **Module** | Table |
| **Type** | Functional |
| **Preconditions** | Browser on https://practicetestautomation.com/practice-test-table/ |
| **Steps** | 1. Select Language = Java<br>2. Verify only courses with "Java" in Language column are visible |
| **Expected** | Only Java courses displayed |

### TC-TABLE-002: Filter by level — Beginner
| Field | Value |
|---|---|
| **ID** | TC-TABLE-002 |
| **Title** | Filter courses by Beginner level |
| **Priority** | High |
| **Module** | Table |
| **Type** | Functional |
| **Preconditions** | Browser on table page |
| **Steps** | 1. Uncheck Intermediate and Advanced checkboxes<br>2. Verify only Beginner courses are visible |
| **Expected** | Only Beginner level courses displayed |

### TC-TABLE-003: Filter by minimum enrollments
| Field | Value |
|---|---|
| **ID** | TC-TABLE-003 |
| **Title** | Filter courses by minimum 10,000 enrollments |
| **Priority** | High |
| **Module** | Table |
| **Type** | Data Validation |
| **Preconditions** | Browser on table page |
| **Steps** | 1. Open "Min enrollments" dropdown<br>2. Select "10,000+"<br>3. Verify every visible row has enrollments >= 10,000 |
| **Expected** | All courses shown have 10,000+ enrollments (numeric comparison) |

### TC-TABLE-004: Combined filters
| Field | Value |
|---|---|
| **ID** | TC-TABLE-004 |
| **Title** | Combine Language (Python) + Level (Beginner) + Min Enrollments (10,000+) |
| **Priority** | High |
| **Module** | Table |
| **Type** | Functional |
| **Preconditions** | Browser on table page |
| **Steps** | 1. Select Language = Python<br>2. Uncheck Intermediate and Advanced<br>3. Set Min enrollments = 10,000+<br>4. Verify only Python Beginner courses with >= 10,000 enrollments are visible |
| **Expected** | Only matching courses displayed (e.g., Selenium with Python) |

### TC-TABLE-005: No results state
| Field | Value |
|---|---|
| **ID** | TC-TABLE-005 |
| **Title** | Verify "No matching courses" message appears for unfilled filter |
| **Priority** | Medium |
| **Module** | Table |
| **Type** | UI |
| **Preconditions** | Browser on table page |
| **Steps** | 1. Select a filter combination that matches no courses (e.g., Language=Java + Level=Beginner should still return results; try an impossible combination)<br>2. Verify "No matching courses" text is displayed |
| **Expected** | Empty state message is shown when filters match no courses |

### TC-TABLE-006: Reset button behavior
| Field | Value |
|---|---|
| **ID** | TC-TABLE-006 |
| **Title** | Verify Reset button restores default filters and hides itself |
| **Priority** | High |
| **Module** | Table |
| **Type** | Functional |
| **Preconditions** | Browser on table page |
| **Steps** | 1. Apply any filter change<br>2. Verify Reset button becomes visible<br>3. Click Reset button<br>4. Verify Language = Any, all Levels checked, Min enrollments = Any<br>5. Verify Reset button is hidden<br>6. Verify all courses are visible again |
| **Expected** | Filters reset to defaults; Reset button disappears; full table shown |

### TC-TABLE-007: Sort by Enrollments (ascending)
| Field | Value |
|---|---|
| **ID** | TC-TABLE-007 |
| **Title** | Verify numeric sort by Enrollments column |
| **Priority** | High |
| **Module** | Table |
| **Type** | Data Validation |
| **Preconditions** | Browser on table page |
| **Steps** | 1. Set Sort by = Enrollments<br>2. Read all enrollment values from visible rows<br>3. Verify values are in ascending numeric order |
| **Expected** | Courses sorted from lowest to highest enrollment (numeric, not lexicographic) |

### TC-TABLE-008: Sort by Course Name (alphabetical)
| Field | Value |
|---|---|
| **ID** | TC-TABLE-008 |
| **Title** | Verify alphabetical sort by Course Name |
| **Priority** | Medium |
| **Module** | Table |
| **Type** | Data Validation |
| **Preconditions** | Browser on table page |
| **Steps** | 1. Set Sort by = Course Name<br>2. Read all course names<br>3. Verify names are in A-Z order |
| **Expected** | Courses sorted alphabetically by name |

### TC-TABLE-009: Course link navigates correctly
| Field | Value |
|---|---|
| **ID** | TC-TABLE-009 |
| **Title** | Verify clicking a course "View" link opens correct page |
| **Priority** | Medium |
| **Module** | Table |
| **Type** | Functional |
| **Preconditions** | Browser on table page |
| **Steps** | 1. Click the "View" link for "Selenium with Java"<br>2. Verify the new page URL contains the course slug |
| **Expected** | Correct course page opens in the same or new tab |

---

## Module 6: Contact Form

### TC-CONTACT-001: Verify contact form is displayed
| Field | Value |
|---|---|
| **ID** | TC-CONTACT-001 |
| **Title** | Verify contact form fields are present |
| **Priority** | Medium |
| **Module** | Contact |
| **Type** | UI |
| **Preconditions** | Browser on https://practicetestautomation.com/contact/ |
| **Steps** | 1. Verify First Name field is present<br>2. Verify Last Name field is present<br>3. Verify Email field is present<br>4. Verify Message/textarea field is present<br>5. Verify Submit button is present |
| **Expected** | All form fields are visible |

### TC-CONTACT-002: Submit with empty fields
| Field | Value |
|---|---|
| **ID** | TC-CONTACT-002 |
| **Title** | Attempt to submit empty contact form |
| **Priority** | Medium |
| **Module** | Contact |
| **Type** | Negative |
| **Preconditions** | Browser on contact page |
| **Steps** | 1. Click Submit without filling any fields<br>2. Verify HTML5 validation messages appear on required fields |
| **Expected** | Browser validation prevents submission; form not submitted |

### TC-CONTACT-003: Submit with invalid email
| Field | Value |
|---|---|
| **ID** | TC-CONTACT-003 |
| **Title** | Submit with invalid email format |
| **Priority** | Medium |
| **Module** | Contact |
| **Type** | Negative |
| **Preconditions** | Browser on contact page |
| **Steps** | 1. Enter First Name: Test<br>2. Enter Last Name: User<br>3. Enter Email: invalid-email<br>4. Enter Message: Test message<br>5. Click Submit |
| **Expected** | Email validation prevents submission; form not submitted |

---

## Module 7: Courses Page

### TC-COURSE-001: Verify courses page displays course list
| Field | Value |
|---|---|
| **ID** | TC-COURSE-001 |
| **Title** | Verify courses page loads and shows course offerings |
| **Priority** | Medium |
| **Module** | Courses |
| **Type** | UI |
| **Preconditions** | Browser on https://practicetestautomation.com/courses/ |
| **Steps** | 1. Verify page heading mentions "Courses"<br>2. Verify at least one course card or listing is present |
| **Expected** | Courses page loads with course listings |

---

## Module 8: Blog Page

### TC-BLOG-001: Verify blog page loads with articles
| Field | Value |
|---|---|
| **ID** | TC-BLOG-001 |
| **Title** | Verify blog page shows article list |
| **Priority** | Low |
| **Module** | Blog |
| **Type** | UI |
| **Preconditions** | Browser on https://practicetestautomation.com/blog/ |
| **Steps** | 1. Verify page heading mentions "Blog"<br>2. Verify at least one article or post preview is present |
| **Expected** | Blog page loads with article list |

---

## Module 9: Practice Page

### TC-PRACTICE-001: Verify practice test links
| Field | Value |
|---|---|
| **ID** | TC-PRACTICE-001 |
| **Title** | Verify all practice sub-page links navigate correctly |
| **Priority** | High |
| **Module** | Practice |
| **Type** | Functional |
| **Preconditions** | Browser on https://practicetestautomation.com/practice/ |
| **Steps** | 1. Verify "Test Login Page" link navigates to `/practice-test-login/`<br>2. Verify "Test Exceptions" link navigates to `/practice-test-exceptions/`<br>3. Verify "Test Table" link navigates to `/practice-test-table/` |
| **Expected** | Each link opens the respective practice page |

---

## Test Summary

| Priority | Count |
|---|---|
| Critical | 3 |
| High | 12 |
| Medium | 10 |
| Low | 2 |
| **Total** | **27** |
