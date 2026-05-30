package com.phptravels.base;

import com.phptravels.driver.DriverFactory;
import com.phptravels.utilities.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        String browser = ConfigReader.getProperty("browser");
        driver = DriverFactory.initializeDriver(browser);
        driver.get(ConfigReader.getProperty("baseUrl"));
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
