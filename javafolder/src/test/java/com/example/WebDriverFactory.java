package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory class to create WebDriver instances for different browsers
 */
public class WebDriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);

    /**
     * Create a WebDriver instance based on BrowserType
     *
     * @param browserType the type of browser to create
     * @return WebDriver instance
     */
    public static WebDriver createDriver(BrowserType browserType) {
        logger.info("Creating WebDriver for browser: {}", browserType.getName());

        switch (browserType) {
            case CHROME:
                return createChromeDriver();
            case FIREFOX:
                return createFirefoxDriver();
            case EDGE:
                return createEdgeDriver();
            case SAFARI:
                return createSafariDriver();
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
    }

    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Optional: Add headless mode for CI/CD environments
        // options.addArguments("--headless");
        // options.addArguments("--no-sandbox");
        // options.addArguments("--disable-dev-shm-usage");
        logger.debug("Chrome driver created with options: {}", options);
        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        // Optional: Add headless mode
        // options.addArguments("--headless");
        logger.debug("Firefox driver created with options: {}", options);
        return new FirefoxDriver(options);
    }

    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        // Optional: Add headless mode
        // options.addArguments("--headless");
        logger.debug("Edge driver created with options: {}", options);
        return new EdgeDriver(options);
    }

    private static WebDriver createSafariDriver() {
        logger.info("Safari driver requires manual setup - ensure Safari is available");
        return new SafariDriver();
    }

    /**
     * Quit the WebDriver and clean up resources
     *
     * @param driver the WebDriver instance to quit
     */
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            logger.info("Quitting WebDriver");
            driver.quit();
        }
    }
}
