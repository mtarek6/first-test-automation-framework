package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.ProductsPage;
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
@Feature("UI Product Management")
@Story("Product Management")
@Owner("Mostafa")
@Severity(SeverityLevel.CRITICAL)
@UITest
public class ProductsTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();

    //tests
    @Test
    @Description("Verify that user can search for products and its details")
    public void searchProductsTC(){
        new ProductsPage(driver)
                .navigate()
                .searchProduct(testData.getJsonData("searchedProduct.name"))
                .validateProductDetails(
                        testData.getJsonData("searchedProduct.name"),
                        testData.getJsonData("searchedProduct.price"));
    }

    @Test
    @Description("Add a product to cart without logging in")
    public void addProductToCartWithoutLoginTC(){
        new ProductsPage(driver)
                .navigate()
                .addProductToCart(testData.getJsonData("product1.name"))
                .validateProductAddedToCart(
                        testData.getJsonData("messages.addedToCart")
                );
    }
    //Configurations

    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("products-data");
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
