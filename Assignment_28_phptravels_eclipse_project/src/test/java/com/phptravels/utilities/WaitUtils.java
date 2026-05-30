package com.phptravels.utilities;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);

    private WaitUtils() {
    }

    public static WebElement waitForClickable(WebDriver driver, By locator) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement waitForVisible(WebDriver driver, By locator) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement waitForPresence(WebDriver driver, By locator) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static Alert waitForAlert(WebDriver driver) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.alertIsPresent());
    }

    public static WebDriver waitForFrame(WebDriver driver, By locator) {
        return new WebDriverWait(driver, DEFAULT_TIMEOUT).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }
}
