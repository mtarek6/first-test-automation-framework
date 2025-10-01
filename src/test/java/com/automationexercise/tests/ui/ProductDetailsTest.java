package com.automationexercise.tests.ui;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.drivers.UITest;
import com.automationexercise.pages.ProductDetailsPage;
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
public class ProductDetailsTest extends BaseTest {
    String timestamp = TimeManager.getSimpleTimeStamp();

    //tests
    @Test
    @Description("Verify that user can view product details")
    public void viewProductDetailsWithoutLoginTC() {
        new ProductsPage(driver)
                .viewProductDetails(testData.getJsonData("product.name"))
                .validateProductDetails(
                        testData.getJsonData("product.name"),
                        testData.getJsonData("product.price"));

    }

    @Test
    @Description("Verify that the user can add a review to a product")
    public void addReviewWithoutLoginTC() {
        new ProductsPage(driver)
                .viewProductDetails(testData.getJsonData("product.name"))
                .addReview(
                        testData.getJsonData("review.name") ,
                        testData.getJsonData("review.email").replace("@", "+" + timestamp + "@"),
                        testData.getJsonData("review.text"))
                .validateReviewMsg(testData.getJsonData("messages.reviewSubmitted"));
    }

    //Configurations
    @BeforeClass
    protected void preCondition() {
        testData = new JsonReader("productdetails-data");
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