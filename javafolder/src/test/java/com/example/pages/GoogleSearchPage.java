package com.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Page Object for Google Search page
 */
public class GoogleSearchPage {
    private static final Logger logger = LoggerFactory.getLogger(GoogleSearchPage.class);
    private static final String URL = "https://www.google.com";
    private static final By SEARCH_BOX = By.name("q");
    private static final By SEARCH_BUTTON = By.name("btnK");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Navigate to Google Search page
     */
    public void navigateTo() {
        logger.info("Navigating to Google Search page");
        driver.navigate().to(URL);
    }

    /**
     * Enter search query
     *
     * @param searchQuery the query to search
     */
    public void searchFor(String searchQuery) {
        logger.info("Searching for: {}", searchQuery);
        WebElement searchBox = wait.until(ExpectedConditions.presenceOfElementLocated(SEARCH_BOX));
        searchBox.clear();
        searchBox.sendKeys(searchQuery);
    }

    /**
     * Click search button
     */
    public void clickSearch() {
        logger.info("Clicking search button");
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(SEARCH_BUTTON));
        searchButton.click();
    }

    /**
     * Get the page title
     *
     * @return page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }

    /**
     * Check if search box is displayed
     *
     * @return true if search box is visible
     */
    public boolean isSearchBoxDisplayed() {
        try {
            return driver.findElement(SEARCH_BOX).isDisplayed();
        } catch (Exception e) {
            logger.warn("Search box not displayed");
            return false;
        }
    }
}
