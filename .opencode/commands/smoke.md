---
description: Run smoke tests and show summary
---
Run `mvn clean test -Dgroups=smoke`. After it finishes, read `target/surefire-reports/testng-results.xml` and give me a table showing each test class, which tests passed/failed/skipped, and the total duration.
