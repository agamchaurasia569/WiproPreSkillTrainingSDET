# Parametrization Comparison: JUnit 5 vs TestNG

## Overview

Both JUnit 5 and TestNG support parametrized tests for cross-browser testing. This document explains the differences and when to use each.

## JUnit 5 Approach (Recommended)

### Setup
- Framework: JUnit 5
- Annotation: `@ParameterizedTest`
- Parameter Provider: `@EnumSource`, `@ValueSource`, `@CsvSource`, `@MethodSource`

### Example
```java
@ParameterizedTest(name = "Browser: {0}")
@EnumSource(BrowserType.class)
void testGoogleSearchPageLoads(BrowserType browserType) {
    // Test runs once per browser
    // Receives different BrowserType value each time
}
```

### Advantages
✅ Modern, built-in to JUnit 5
✅ Clean, readable syntax
✅ Multiple parameter provider options
✅ Excellent IDE support
✅ Easy to combine multiple parameters
✅ Better integration with modern tools
✅ Lower learning curve

### Running Tests
```bash
# Run all parametrized tests
mvn test

# Run specific parametrized test
mvn test -Dtest=CrossBrowserSearchTest
```

### Parameter Providers

#### 1. EnumSource (Recommended for browsers)
```java
@ParameterizedTest
@EnumSource(BrowserType.class)
void test(BrowserType browser) { }
// Runs 4 times (one per enum value)
```

#### 2. ValueSource (Simple values)
```java
@ParameterizedTest
@ValueSource(strings = { "Chrome", "Firefox", "Edge" })
void test(String browser) { }
// Runs 3 times
```

#### 3. CsvSource (Multiple parameters)
```java
@ParameterizedTest
@CsvSource({
    "Chrome, 1920",
    "Firefox, 1366",
    "Edge, 768"
})
void test(String browser, int width) { }
// Runs 3 times with different parameter combinations
```

#### 4. MethodSource (Complex data)
```java
@ParameterizedTest
@MethodSource("provideTestData")
void test(BrowserType browser, String testData) { }

static Stream<Arguments> provideTestData() {
    return Stream.of(
        Arguments.of(BrowserType.CHROME, "data1"),
        Arguments.of(BrowserType.FIREFOX, "data2")
    );
}
// Runs 2 times with custom combinations
```

#### 5. Combining Multiple Providers
```java
@ParameterizedTest(name = "Browser: {0}, Language: {1}")
@EnumSource(BrowserType.class)
@CsvSource({
    "Java",
    "Python",
    "JavaScript"
})
void test(BrowserType browser, String language) { }
// Runs 12 times (4 browsers × 3 languages)
```

## TestNG Approach

### Setup
- Framework: TestNG
- Annotation: `@DataProvider`
- Method: Define 2D array of parameters

### Example
```java
@DataProvider(name = "browsers")
public static Object[][] browsers() {
    return new Object[][] {
        { BrowserType.CHROME },
        { BrowserType.FIREFOX },
        { BrowserType.EDGE },
        { BrowserType.SAFARI }
    };
}

@Test(dataProvider = "browsers")
public void testGoogleSearchPageLoads(BrowserType browserType) {
    // Test runs once per browser
}
```

### Advantages
✅ Powerful reporting features
✅ Supports test grouping
✅ Parallel execution support
✅ Test dependencies
✅ More flexible assertion methods
✅ Better for enterprise projects

### Disadvantages
❌ More verbose syntax
❌ Requires 2D array definition
❌ Less integrated with modern IDEs
❌ Steeper learning curve
❌ More boilerplate code

### Running Tests
```bash
# Run all tests
mvn test

# Run with TestNG configuration
mvn test -Dsuite=testng-cross-browser.xml

# Run specific test method
mvn -Dtest=TestNGCrossBrowserTest#testGoogleSearchPageLoads test
```

## Comparison Table

| Feature | JUnit 5 | TestNG |
|---------|---------|--------|
| **Syntax** | Annotations + methods | Annotations + 2D arrays |
| **Parameter Sources** | Multiple (Enum, CSV, Value, Method) | Single (@DataProvider) |
| **Readability** | Excellent | Good |
| **Setup Complexity** | Simple | Moderate |
| **IDE Support** | Excellent | Good |
| **Learning Curve** | Gentle | Moderate |
| **Parallel Execution** | Experimental | Built-in |
| **Report Generation** | Maven Surefire | Native HTML reports |
| **Test Grouping** | Groups via tags | Built-in grouping |
| **Assertions** | Standard | Extended (`Assert.*`) |

## Choosing Between JUnit 5 and TestNG

### Use JUnit 5 if:
- ✅ Starting a new project
- ✅ Team is familiar with JUnit
- ✅ Simple to moderate test complexity
- ✅ Want modern framework
- ✅ Need easy parametrization
- ✅ Using latest CI/CD tools
- **Recommendation: Most teams should choose this**

### Use TestNG if:
- ✅ Need advanced reporting
- ✅ Require test dependencies
- ✅ Need parallel execution
- ✅ Enterprise environment
- ✅ Team already uses TestNG
- ✅ Need complex grouping
- **Recommendation: Large enterprise projects**

## Practical Example: Cross-Browser + Responsive Testing

### JUnit 5
```java
@ParameterizedTest(name = "{0} at {1}x{2}")
@EnumSource(BrowserType.class)
@CsvSource({
    "1920, 1080",
    "768, 1024"
})
void testResponsive(BrowserType browser, int width, int height) {
    // 8 executions: 4 browsers × 2 resolutions
}
```

### TestNG
```java
@DataProvider(name = "browserResolutions")
public Object[][] browserResolutions() {
    return new Object[][] {
        { BrowserType.CHROME, 1920, 1080 },
        { BrowserType.CHROME, 768, 1024 },
        { BrowserType.FIREFOX, 1920, 1080 },
        { BrowserType.FIREFOX, 768, 1024 },
        // ... and so on
    };
}

@Test(dataProvider = "browserResolutions")
public void testResponsive(BrowserType browser, int width, int height) {
    // 8 executions
}
```

## Configuration in pom.xml

### JUnit 5 (Current Project)
```xml
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-params</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>
```

### TestNG
```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.8.1</version>
    <scope>test</scope>
</dependency>
```

## Migration Path

Both implementations are provided in this project:
- **JUnit 5**: `CrossBrowserSearchTest.java`, `AdvancedCrossBrowserTest.java`
- **TestNG**: `TestNGCrossBrowserTest.java`

To switch frameworks:
1. Add TestNG dependency to `pom.xml`
2. Run: `mvn test` (will run both JUnit 5 and TestNG)
3. Comment out or remove JUnit 5 tests if needed

## Best Practices

### For JUnit 5
```java
// Good: Clear parameter source
@ParameterizedTest(name = "Browser: {0}")
@EnumSource(BrowserType.class)
void test(BrowserType browser) { }

// Avoid: Unclear test name
@ParameterizedTest
@EnumSource(BrowserType.class)
void test(BrowserType browser) { }
```

### For TestNG
```java
// Good: Descriptive DataProvider name
@DataProvider(name = "crossBrowsers")
public Object[][] crossBrowsers() { }

// Avoid: Generic name
@DataProvider
public Object[][] data() { }
```

## Summary

| Aspect | JUnit 5 | TestNG |
|--------|---------|--------|
| **Ease** | Easy | Moderate |
| **Flexibility** | Good | Excellent |
| **For Beginners** | Recommended | Not recommended |
| **For Enterprise** | Growing | Established |
| **Modern Projects** | Recommended | Optional |

**Conclusion**: For most new projects, **JUnit 5 with @ParameterizedTest is the recommended choice**. It provides excellent parametrization support with clean syntax and modern features.
