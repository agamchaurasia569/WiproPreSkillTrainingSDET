package com.example.tests;

import com.example.BrowserType;
import com.example.WebDriverFactory;
import com.example.pages.GoogleSearchPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Advanced cross-browser tests with multiple parameter sources
 * Demonstrates combining browser type with other test data
 */
@DisplayName("Advanced Cross-Browser Tests")
public class AdvancedCrossBrowserTest {
    private static final Logger logger = LoggerFactory.getLogger(AdvancedCrossBrowserTest.class);
    private WebDriver driver;
    private GoogleSearchPage searchPage;

    @BeforeEach
    void setUp(BrowserType browserType) {
        logger.info("Setting up test for browser: {}", browserType.getName());
        driver = WebDriverFactory.createDriver(browserType);
        searchPage = new GoogleSearchPage(driver);
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        WebDriverFactory.quitDriver(driver);
    }

    /**
     * Parametrized test combining browser type and search queries
     * Tests each browser with multiple search queries
     */
    @ParameterizedTest(name = "Browser: {0}, Query: {1}")
    @EnumSource(BrowserType.class)
    @CsvSource({
        "Java",
        "Python",
        "JavaScript",
        "C++"
    })
    @DisplayName("Search should work for different programming languages")
    void testSearchProgrammingLanguages(BrowserType browserType, String language) {
        logger.info("Testing search for '{}' in {}", language, browserType.getName());
        
        searchPage.navigateTo();
        assertTrue(searchPage.isSearchBoxDisplayed(), "Search box should be visible");
        
        searchPage.searchFor(language);
        searchPage.clickSearch();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String title = driver.getTitle();
        assertNotNull(title, "Page title should not be null");
        logger.info("✓ Successfully searched for {} in {}", language, browserType.getName());
    }

    /**
     * Parametrized test for window sizes across browsers
     */
    @ParameterizedTest(name = "Browser: {0}, Width: {1}, Height: {2}")
    @EnumSource(BrowserType.class)
    @CsvSource({
        "1920, 1080",
        "1366, 768",
        "768, 1024"
    })
    @DisplayName("Search page should be responsive across different window sizes")
    void testResponsiveDesign(BrowserType browserType, int width, int height) {
        logger.info("Testing responsive design for {} - {}x{}", browserType.getName(), width, height);
        
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(width, height));
        searchPage.navigateTo();
        
        boolean isSearchBoxVisible = searchPage.isSearchBoxDisplayed();
        assertTrue(isSearchBoxVisible, 
            String.format("Search box should be visible at %dx%d resolution", width, height));
        
        logger.info("✓ Search box visible at {}x{} in {}", width, height, browserType.getName());
    }
}
