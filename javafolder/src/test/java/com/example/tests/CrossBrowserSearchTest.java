package com.example.tests;

import com.example.BrowserType;
import com.example.WebDriverFactory;
import com.example.pages.GoogleSearchPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Cross-browser parametrized tests using JUnit 5
 * Tests run across multiple browsers: Chrome, Firefox, Edge, Safari
 */
@DisplayName("Cross-Browser Search Tests")
public class CrossBrowserSearchTest {
    private static final Logger logger = LoggerFactory.getLogger(CrossBrowserSearchTest.class);
    private WebDriver driver;
    private GoogleSearchPage searchPage;

    @BeforeEach
    void setUp(BrowserType browserType) {
        logger.info("========== Setting up test for browser: {} ==========", browserType.getName());
        driver = WebDriverFactory.createDriver(browserType);
        searchPage = new GoogleSearchPage(driver);
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDown() {
        logger.info("========== Tearing down test ==========");
        WebDriverFactory.quitDriver(driver);
    }

    /**
     * Parametrized test: Verify Google Search page loads correctly across all browsers
     */
    @ParameterizedTest(name = "Browser: {0}")
    @EnumSource(BrowserType.class)
    @DisplayName("Google Search page should load successfully")
    void testGoogleSearchPageLoads(BrowserType browserType) {
        logger.info("Test: Google Search page loads in {}", browserType.getName());
        
        searchPage.navigateTo();
        
        assertTrue(searchPage.isSearchBoxDisplayed(), 
            "Search box should be displayed on Google Search page");
        
        assertEquals("Google", driver.getTitle(), 
            "Page title should be 'Google'");
        
        logger.info("✓ Test passed for {}", browserType.getName());
    }

    /**
     * Parametrized test: Perform search and verify results across all browsers
     */
    @ParameterizedTest(name = "Browser: {0}")
    @EnumSource(BrowserType.class)
    @DisplayName("Search functionality should work correctly")
    void testSearchFunctionality(BrowserType browserType) {
        logger.info("Test: Search functionality in {}", browserType.getName());
        
        searchPage.navigateTo();
        searchPage.searchFor("Selenium WebDriver");
        searchPage.clickSearch();
        
        // Wait for results page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String title = driver.getTitle();
        assertNotNull(title, "Page title should not be null");
        assertTrue(title.contains("Selenium WebDriver"), 
            "Page title should contain search query: " + title);
        
        logger.info("✓ Test passed for {}", browserType.getName());
    }

    /**
     * Parametrized test: Verify multiple search queries across all browsers
     */
    @ParameterizedTest(name = "Browser: {0}")
    @EnumSource(BrowserType.class)
    @DisplayName("Multiple search queries should work")
    void testMultipleSearchQueries(BrowserType browserType) {
        logger.info("Test: Multiple search queries in {}", browserType.getName());
        
        String[] searchQueries = {"Java", "Selenium", "Testing"};
        
        for (String query : searchQueries) {
            logger.info("Searching for: {}", query);
            searchPage.navigateTo();
            searchPage.searchFor(query);
            searchPage.clickSearch();
            
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            assertTrue(driver.getTitle().contains(query), 
                "Title should contain search query: " + query);
        }
        
        logger.info("✓ Test passed for {}", browserType.getName());
    }
}
