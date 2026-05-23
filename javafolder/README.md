# Cross-Browser Testing with Parametrization

A comprehensive example of cross-browser testing using Selenium WebDriver with JUnit 5 parametrized tests.

## Project Structure

```
src/
├── test/
│   ├── java/com/example/
│   │   ├── BrowserType.java                 # Enum for supported browsers
│   │   ├── WebDriverFactory.java            # Factory for creating WebDriver instances
│   │   ├── pages/
│   │   │   └── GoogleSearchPage.java        # Page Object Model for Google Search
│   │   └── tests/
│   │       ├── CrossBrowserSearchTest.java  # Basic parametrized tests
│   │       └── AdvancedCrossBrowserTest.java # Advanced examples
│   └── resources/
│       └── logback.xml                      # Logging configuration
└── pom.xml                                  # Maven configuration
```

## Key Features

### 1. **Parametrized Testing with `@ParameterizedTest`**
   - Tests run once per browser type
   - Uses `@EnumSource(BrowserType.class)` to provide browser parameters
   - Display names show which browser is being tested

### 2. **Browser Support**
   - Chrome
   - Firefox
   - Edge
   - Safari

### 3. **WebDriver Management**
   - Automatic driver setup using WebDriverManager
   - Factory pattern for clean driver instantiation
   - Proper cleanup in `@AfterEach`

### 4. **Page Object Model**
   - `GoogleSearchPage` demonstrates best practices
   - Encapsulates page elements and interactions
   - Reusable across all browser tests

### 5. **Advanced Parametrization**
   - Combine multiple `@ParameterizedTest` providers
   - Example: test each browser with different search queries
   - Example: test responsive design across resolutions

## Running the Tests

### Prerequisites
```bash
# Ensure you have Java 11+ and Maven installed
java -version
mvn -version
```

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=CrossBrowserSearchTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=CrossBrowserSearchTest#testGoogleSearchPageLoads
```

### Run with Headless Mode (CI/CD)
Edit `WebDriverFactory.java` and uncomment headless options:
```java
options.addArguments("--headless");
options.addArguments("--no-sandbox");
```

## Test Examples

### Example 1: Basic Parametrized Test
```java
@ParameterizedTest(name = "Browser: {0}")
@EnumSource(BrowserType.class)
void testGoogleSearchPageLoads(BrowserType browserType) {
    // Test runs 4 times (once per browser)
    searchPage.navigateTo();
    assertTrue(searchPage.isSearchBoxDisplayed());
}
```
**Output**: Test runs 4 times with different browsers

### Example 2: Combining Parameters
```java
@ParameterizedTest(name = "Browser: {0}, Query: {1}")
@EnumSource(BrowserType.class)
@CsvSource({
    "Java",
    "Python"
})
void testSearchProgrammingLanguages(BrowserType browserType, String language) {
    // Test runs 8 times (4 browsers × 2 languages)
}
```
**Output**: 8 test executions total

### Example 3: Responsive Design Testing
```java
@ParameterizedTest(name = "Browser: {0}, Size: {1}x{2}")
@EnumSource(BrowserType.class)
@CsvSource({
    "1920, 1080",
    "768, 1024"
})
void testResponsiveDesign(BrowserType browserType, int width, int height) {
    // Test runs 8 times (4 browsers × 2 resolutions)
}
```

## Configuration

### WebDriver Options

#### Chrome (Recommended)
- Fast execution
- Good compatibility
- Extensive options support

#### Firefox
- Alternative browser
- ESR (Extended Support Release) versions available

#### Edge
- Chromium-based
- Similar to Chrome behavior

#### Safari
- Requires macOS
- Requires Safari to be installed
- WebDriverManager may not auto-download

### Headless Mode
For CI/CD pipelines, enable headless mode in `WebDriverFactory.java`:
```java
options.addArguments("--headless=new");  // Chrome/Edge
options.addArguments("--headless");      // Firefox
```

## Logging

Logs are configured via `logback.xml`:
- **Console Output**: Real-time test execution details
- **File Output**: Logs saved to `logs/test.log`

Log Levels:
- INFO: Test flow and browser operations
- DEBUG: WebDriver configuration details
- WARN: Selenium component warnings

## Best Practices

### 1. Test Independence
- Each test should be independent
- Use `@BeforeEach` to set up fresh driver per test
- Use `@AfterEach` to clean up resources

### 2. Wait Strategies
- Use explicit waits instead of `Thread.sleep()`
- Example in `GoogleSearchPage.java`:
```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_BOX));
```

### 3. Page Object Model
- Encapsulate page elements and actions
- Keep tests focused on behavior, not implementation
- Reuse page objects across tests

### 4. Browser Compatibility
- Test critical paths on all browsers
- Use browser-specific options only when necessary
- Handle platform differences (e.g., Safari on macOS)

## Extending the Project

### Add More Test Cases
1. Create new page objects in `src/test/java/com/example/pages/`
2. Create test classes extending parametrized pattern
3. Run with different browser combinations

### Add Custom Parameters
```java
@ParameterizedTest
@MethodSource("provideTestData")
void customTest(BrowserType browser, String testData) {
    // Custom test with specific data
}

static Stream<Arguments> provideTestData() {
    return Stream.of(
        Arguments.of(BrowserType.CHROME, "data1"),
        Arguments.of(BrowserType.FIREFOX, "data2")
    );
}
```

### CI/CD Integration
Add to your CI pipeline (GitHub Actions, Jenkins, etc.):
```yaml
- name: Run Cross-Browser Tests
  run: mvn test
```

## Troubleshooting

### Browser Driver Not Found
- WebDriverManager should auto-download drivers
- If issues persist, download manually or set system PATH
- Check logs for error messages

### Test Timeout
- Increase WebDriverWait timeout in page objects
- Check internet connectivity (for Google Search tests)
- Verify browser processes aren't hanging

### Safari Issues on Windows
- Safari driver only works on macOS
- Remove Safari from browsers for Windows CI/CD
- Use Chrome/Firefox as alternatives

## Dependencies

| Dependency | Version | Purpose |
|-----------|---------|---------|
| Selenium WebDriver | 4.15.0 | Browser automation |
| JUnit 5 | 5.10.0 | Testing framework |
| WebDriverManager | 5.6.3 | Automatic driver management |
| SLF4J | 2.0.9 | Logging API |
| Logback | 1.4.14 | Logging implementation |

## Further Reading

- [Selenium Documentation](https://www.selenium.dev/documentation/)
- [JUnit 5 Parametrized Tests](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)
- [Page Object Model Pattern](https://www.selenium.dev/documentation/test_practices/encouraged/page_object_models/)
- [WebDriverManager](https://github.com/bonigarcia/webdrivermanager)

## License

This project is provided as-is for educational purposes.
