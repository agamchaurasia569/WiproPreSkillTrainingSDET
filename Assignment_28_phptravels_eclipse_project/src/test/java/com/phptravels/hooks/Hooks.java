package com.phptravels.hooks;

import com.phptravels.driver.DriverFactory;
import com.phptravels.utilities.ScreenshotUtils;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void beforeScenario() {
        // Driver is initialized in BaseTest or runner workflow.
    }

    @After
    public void afterScenario(Scenario scenario) {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null && scenario.isFailed()) {
            byte[] screenshot = ScreenshotUtils.getScreenshot(driver);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }
    }
}
