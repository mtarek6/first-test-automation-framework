package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.SignupLoginPage;
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
@Story("Login")
@Owner("Mostafa")
@Severity(SeverityLevel.CRITICAL)
@UITest
public class LoginTest extends BaseTest {

    String timestamp = TimeManager.getSimpleTimeStamp();

    @Description("Verify that user can login with valid data")
    @Test
    public void validLoginTC() {
        new UserManagementAPI().createAccount(
                testData.getJsonData("name"),
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password"),
                testData.getJsonData("first_name"),
                testData.getJsonData("last_name"))
                .verifyAccountCreated();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton()
                .navigationBar
                .verifyLoggedInAsLabel(testData.getJsonData("name"));

        new UserManagementAPI().deleteAccount(
                testData.getJsonData("email") + timestamp + "@gmail.com",
                testData.getJsonData("password"))
                .verifyAccountDeleted();
    }

    @Description("Verify that user cannot login with invalid email")
    @Test
    public void invalidLoginUsingInvalidEmailTC() {
        new UserManagementAPI().createAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("first_name"),
                        testData.getJsonData("last_name"))
                .verifyAccountCreated();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email")  + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton()
                .verifyLoginErrorMsg(testData.getJsonData("messages.VerifyLoginFailed"));

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyAccountDeleted();
    }

    @Description("Verify that user cannot login with invalid password")
    @Test
    public void invalidLoginUsingInvalidPasswordTC() {
        new UserManagementAPI().createAccount(
                        testData.getJsonData("name"),
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"),
                        testData.getJsonData("first_name"),
                        testData.getJsonData("last_name"))
                .verifyAccountCreated();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password" ) + timestamp )
                .clickOnLoginButton()
                .verifyLoginErrorMsg(testData.getJsonData("messages.VerifyLoginFailed"));

        new UserManagementAPI().deleteAccount(
                        testData.getJsonData("email") + timestamp + "@gmail.com",
                        testData.getJsonData("password"))
                .verifyAccountDeleted();
    }

    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("login-data");
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
