package com.example.tests;

import com.example.BrowserType;
import com.example.WebDriverFactory;
import com.example.pages.GoogleSearchPage;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

/**
 * TestNG Implementation of Cross-Browser Tests
 * Alternative to JUnit 5 for parametrized cross-browser testing
 * 
 * To use TestNG instead of JUnit 5:
 * 1. Add dependency in pom.xml:
 *    <dependency>
 *        <groupId>org.testng</groupId>
 *        <artifactId>testng</artifactId>
 *        <version>7.8.1</version>
 *        <scope>test</scope>
 *    </dependency>
 * 
 * 2. Run: mvn test -Dsuite=testng-cross-browser.xml
 */
@Test
public class TestNGCrossBrowserTest {
    private static final Logger logger = LoggerFactory.getLogger(TestNGCrossBrowserTest.class);
    private WebDriver driver;
    private GoogleSearchPage searchPage;

    @DataProvider(name = "browsers")
    public static Object[][] browsers() {
        return new Object[][] {
            { BrowserType.CHROME },
            { BrowserType.FIREFOX },
            { BrowserType.EDGE },
            { BrowserType.SAFARI }
        };
    }

    @DataProvider(name = "searchQueries")
    public static Object[][] searchQueries() {
        return new Object[][] {
            { BrowserType.CHROME, "Java" },
            { BrowserType.FIREFOX, "Python" },
            { BrowserType.EDGE, "JavaScript" },
            { BrowserType.SAFARI, "C++" }
        };
    }

    @BeforeMethod
    public void setUp(Object[] params) {
        BrowserType browserType = (BrowserType) params[0];
        logger.info("========== Setting up test for browser: {} ==========", browserType.getName());
        driver = WebDriverFactory.createDriver(browserType);
        searchPage = new GoogleSearchPage(driver);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        logger.info("========== Tearing down test ==========");
        WebDriverFactory.quitDriver(driver);
    }

    @Test(dataProvider = "browsers", 
          description = "Google Search page should load successfully")
    public void testGoogleSearchPageLoads(BrowserType browserType) {
        logger.info("Test: Google Search page loads in {}", browserType.getName());
        
        searchPage.navigateTo();
        
        Assert.assertTrue(searchPage.isSearchBoxDisplayed(), 
            "Search box should be displayed on Google Search page");
        Assert.assertEquals(driver.getTitle(), "Google", 
            "Page title should be 'Google'");
        
        logger.info("✓ Test passed for {}", browserType.getName());
    }

    @Test(dataProvider = "browsers",
          description = "Search functionality should work correctly")
    public void testSearchFunctionality(BrowserType browserType) {
        logger.info("Test: Search functionality in {}", browserType.getName());
        
        searchPage.navigateTo();
        searchPage.searchFor("Selenium WebDriver");
        searchPage.clickSearch();
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String title = driver.getTitle();
        Assert.assertNotNull(title, "Page title should not be null");
        Assert.assertTrue(title.contains("Selenium WebDriver"), 
            "Page title should contain search query: " + title);
        
        logger.info("✓ Test passed for {}", browserType.getName());
    }

    @Test(dataProvider = "searchQueries",
          description = "Search with different queries across browsers")
    public void testSearchWithQueries(BrowserType browserType, String query) {
        logger.info("Test: Searching for '{}' in {}", query, browserType.getName());
        
        searchPage.navigateTo();
        searchPage.searchFor(query);
        searchPage.clickSearch();
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String title = driver.getTitle();
        Assert.assertTrue(title.contains(query), 
            "Title should contain search query: " + query);
        
        logger.info("✓ Test passed for {}", browserType.getName());
    }
}
