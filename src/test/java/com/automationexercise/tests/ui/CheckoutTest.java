package com.automationexercise.tests.ui;

import com.automationexercise.apis.UserManagementAPI;
import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.CheckoutPage;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.SignupLoginPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.TimeManager;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.*;

@Epic("Automation Exercise")
@Feature("UI Checkout Management")
@Story("Checkout Management")
@Owner("Mostafa")
@Severity(SeverityLevel.CRITICAL)
@UITest
public class CheckoutTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();
    @Test
    public void registerNewAccountTC()
    {
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

    @Test(dependsOnMethods = "registerNewAccountTC")
    public void loginToAccountTC()
    {
        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("email") + timestamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("password"))
                .clickOnLoginButton()
                .navigationBar
                .verifyLoggedInAsLabel(testData.getJsonData("name"));
    }

    @Test(dependsOnMethods = {"loginToAccountTC", "registerNewAccountTC"})
    public void addProductsToCartTC()
    {
        new ProductsPage(driver)
                .navigate()
                .addProductToCart(testData.getJsonData("product.name"))
                .validateProductAddedToCart(
                        testData.getJsonData("messages.addedToCart")
                )
                .viewCart()
                .verifyProductDetails(
                        testData.getJsonData("product.name"),
                        testData.getJsonData("product.price"),
                        testData.getJsonData("product.quantity"),
                        testData.getJsonData("product.total")
                );
    }

    @Test(dependsOnMethods = {"addProductsToCartTC", "loginToAccountTC", "registerNewAccountTC"})
    public void checkoutTC()
    {
        new CartPage(driver)
                .clickCheckoutButton()
                .verifyDeliveryAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("first_name"),
                        testData.getJsonData("last_name"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobile_number")
                ).verifyBillingAddress(
                        testData.getJsonData("titleMale"),
                        testData.getJsonData("first_name"),
                        testData.getJsonData("last_name"),
                        testData.getJsonData("company"),
                        testData.getJsonData("address1"),
                        testData.getJsonData("address2"),
                        testData.getJsonData("city"),
                        testData.getJsonData("state"),
                        testData.getJsonData("zipcode"),
                        testData.getJsonData("country"),
                        testData.getJsonData("mobile_number")
                );
    }

    @Test(dependsOnMethods = {"checkoutTC", "addProductsToCartTC", "loginToAccountTC", "registerNewAccountTC"})
    public void deleteAccountAsCleanup()
    {
        new UserManagementAPI()
                .deleteAccount(testData.getJsonData("email") + timestamp + "@gmail.com", testData.getJsonData("password"))
                .verifyAccountDeleted();
    }

    //Configurations

    @BeforeClass
    public void setUp() {
        testData = new JsonReader("checkout-data");
        driver = new GUIDriver();
        new NavigationBarComponent(driver).navigate();
        //driver.browser().closeExtensionTab();
    }

    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
