package com.phptravels.pages;

import com.phptravels.utilities.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private final WebDriver driver;

    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By loginButton = By.id("submitBTN");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterEmail(String email) {
        WaitUtils.waitForVisible(driver, emailInput).clear();
        WaitUtils.waitForVisible(driver, emailInput).sendKeys(email);
    }

    public void enterPassword(String password) {
        WaitUtils.waitForVisible(driver, passwordInput).clear();
        WaitUtils.waitForVisible(driver, passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        WaitUtils.waitForClickable(driver, loginButton).click();
    }
}
