package com.automationexercise.tests;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.WebDriverProvider;
import com.automationexercise.utils.dataReader.JsonReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;

public class BaseTest implements WebDriverProvider {
    protected GUIDriver driver;
    protected JsonReader  testData;


    @Override
    public WebDriver getWebDriver() {
        return driver.get();
    }
}
