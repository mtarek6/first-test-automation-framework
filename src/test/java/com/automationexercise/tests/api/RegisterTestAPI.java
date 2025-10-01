package com.automationexercise.tests.api;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.SignupPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RegisterTestAPI extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();

    @Test
    public void registerUserTC() {
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
    }

    //Configurations

    @BeforeClass
    public void setUp() {
        testData = new JsonReader("register-data");
    }

}
