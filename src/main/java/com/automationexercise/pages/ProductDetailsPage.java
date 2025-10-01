package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    private final GUIDriver driver;
    public NavigationBarComponent navigationBar;
    private String productDetailsPageEndpoint = "/product_details/2";
    public ProductDetailsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }
    //locators
    private final By productName = By.cssSelector(".product-information h2");
    private final By productPrice = By.cssSelector(".product-information span span");
    private final By productQuantity = By.id("quantity");
    private final By addToCartButton = By.cssSelector(".cart");
    private final By viewCartButton = By.cssSelector(".text-center [href='/view_cart']");
    private final By continueShoppingButton = By.cssSelector(".modal-footer button");
    private final By reviewNameField = By.id("name");
    private final By reviewEmailField = By.id("email");
    private final By reviewTextField = By.id("review");
    private final By submitReviewButton = By.id("button-review");
    private final By reviewSuccessMessage = By.cssSelector(".alert-success span");

    //actions
    @Step("Navigate to Product Details Page")
    public ProductDetailsPage navigate()
    {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb")+productDetailsPageEndpoint);
        return this;
    }

    @Step("Add review")
    public ProductDetailsPage addReview(String name, String email, String reviewText)
    {
        driver.element().type(reviewNameField, name);
        driver.element().type(reviewEmailField, email);
        driver.element().type(reviewTextField, reviewText);
        driver.element().click(submitReviewButton);
        return this;
    }

    //validations
    @Step("Validate product details")
    public ProductDetailsPage validateProductDetails(String expectedName, String expectedPrice)
    {
        String actualName = driver.element().getText(productName);
        String actualPrice = driver.element().getText(productPrice);
        LogsManager.info("Actual name: "+actualName+" | Actual price: "+expectedPrice);
        driver.validate().Equals(actualName, expectedName, "Product name does not match");
        driver.validate().Equals(actualPrice, expectedPrice, "Product price does not match");
        return this;
    }

    @Step("Validate review submission success message")
    public ProductDetailsPage validateReviewMsg(String expectedMessage)
    {
        String actualMessage = driver.element().getText(reviewSuccessMessage);
        LogsManager.info("Actual message: "+actualMessage);
        driver.verify().Equals(actualMessage, expectedMessage, "Review success message does not match");
        return this;
    }

}
