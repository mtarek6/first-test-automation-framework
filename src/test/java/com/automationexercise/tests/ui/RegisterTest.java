package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.SignupPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI User Management")
@Story("User Registration")
@Owner("Mostafa")
@Severity(SeverityLevel.CRITICAL)
@UITest
public class RegisterTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimeStamp();

    //Tests
    @Description("Verify that user can register with valid data")
    @Test
    public void validSignupTC()
    {
        new SignupLoginPage(driver).navigate()
                .enterSignupName(testData.getJsonData("name"))
                .enterSignupEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .clickOnSignupButton();
                new SignupPage(driver)
                .fillRegistrationForm(testData.getJsonData("titleMale"),
                        testData.getJsonData("password"),
                        testData.getJsonData("day"),
                        testData.getJsonData("month"),
                        testData.getJsonData("year"),
                        testData.getJsonData("first_name"),
                        testData.getJsonData("last_name"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("country"),
                        testData.getJsonData("state"),
                        testData.getJsonData("city"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("mobile_number"))
                .clickOnCreateAccountButton()
                .verifyAccountCreated();

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyAccountDeleted();
    }

    @Description("Verify that user cannot register with invalid data")
    @Test
    public void verifyErrorMessageWhenAccountCreatedBefore()
    {
        //precondition: create an account
        new UserManagementAPI().createAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("titleMale"),
                testData.getJsonData("day"),
                testData.getJsonData("month"),
                testData.getJsonData("year"),
                testData.getJsonData("first_name"),
                testData.getJsonData("last_name"),
                testData.getJsonData("company"),
                testData.getJsonData("address1"),
                testData.getJsonData("address2"),
                testData.getJsonData("country"),
                testData.getJsonData("state"),
                testData.getJsonData("city"),
                testData.getJsonData("zipcode"),
                testData.getJsonData("mobile_number")
        ).verifyAccountCreated();

        new SignupLoginPage(driver).navigate()
                .enterSignupName(testData.getJsonData("name"))
                .enterSignupEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .clickOnSignupButton()
                .verifySignupErrorMsg(testData.getJsonData("messages.verifyEmailAlreadyExist"));

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyAccountDeleted();

    }

    //Configurations

    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("register-data");
    }

    @BeforeMethod
    public void setUp() {
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        //driver.browser().closeExtensionTab();
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
