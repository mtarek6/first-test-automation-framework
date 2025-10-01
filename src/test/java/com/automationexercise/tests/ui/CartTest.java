package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.ProductsPage;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.dataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise")
@Feature("UI Cart Management")
@Story("Cart Management")
@Owner("Mostafa")
@Severity(SeverityLevel.CRITICAL)
@UITest
public class CartTest extends BaseTest {

    //tests
    @Test
    public void verifyProductDetailsInCartWithoutLoginTC() {
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
    //Configurations

    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("cart-data");
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
