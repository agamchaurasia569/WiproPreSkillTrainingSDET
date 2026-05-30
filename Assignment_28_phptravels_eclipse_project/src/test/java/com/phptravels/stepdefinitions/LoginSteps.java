package com.phptravels.stepdefinitions;

import com.phptravels.driver.DriverFactory;
import com.phptravels.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class LoginSteps {

    private final WebDriver driver = DriverFactory.getDriver();
    private final LoginPage loginPage = new LoginPage(driver);

    @Given("user launches browser")
    public void user_launches_browser() {
        Assert.assertNotNull(driver, "Driver should be initialized.");
    }

    @When("user enters {string} and {string}")
    public void user_enters_and(String username, String password) {
        loginPage.enterEmail(username);
        loginPage.enterPassword(password);
    }

    @When("clicks on login button")
    public void clicks_on_login_button() {
        loginPage.clickLogin();
    }

    @Then("validate login result")
    public void validate_login_result() {
        Assert.assertTrue(driver.getCurrentUrl().contains("phptravels"), "Login result should be validated here.");
    }
}
