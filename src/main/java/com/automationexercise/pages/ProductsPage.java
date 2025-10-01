package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductsPage {
    private final GUIDriver driver;
    public NavigationBarComponent navigationBar;
    private String productsPageUrl = "/products";
    public ProductsPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //locators
    private final By searchField = By.id("search_product");
    private final By searchButton = By.id("submit_search");
    private final By productAddedLabel = By.cssSelector(".modal-body p");
    private final By viewCartButton = By.cssSelector("p > [href='/view_cart']");   //.modal-body p.text-center a
    private final By continueShoppingButton = By.cssSelector(".modal-footer button");
    //dynamic locators
    private By productByName(String productName)
    {
        return By.xpath("//div[@class='overlay-content'] /p[.='"+productName+"']");
    }

    private By priceByName(String productName)
    {
        return By.xpath("//div[@class='overlay-content'] /p[.='"+productName+"'] //preceding-sibling::h2");
    }

    private By hoverOverProductByName(String productName) {
        return By.xpath("//div[@class='productinfo text-center'] /p[.='" + productName + "']");
    }

    private By addToCartButtonByName(String productName) {
        return By.xpath("//div[@class='overlay-content'] /p[.='" + productName + "'] //following-sibling::a");
    }

    private By viewProductButtonByName(String productName)
    {
        return By.xpath("//p[.='"+productName+"'] //following::div[@class='choose'][1]");
    }

    //actions
    @Step("Navigate to Products Page")
    public ProductsPage navigate()
    {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + productsPageUrl);
        return this;
    }

    @Step("Search for product: {productName}")
    public ProductsPage searchProduct(String productName)
    {
        driver.element().type(searchField, productName)
                .click(searchButton);
        return this;
    }

    @Step("Click on Add product to cart: {productName}")
    public ProductsPage addProductToCart(String productName) {
        driver.element().hover(hoverOverProductByName(productName))
                .click(addToCartButtonByName(productName));
        return this;
    }

    @Step("Click on View product details for product: {productName}")
    public ProductDetailsPage viewProductDetails(String productName) {
        driver.element().click(viewProductButtonByName(productName));
        return new ProductDetailsPage(driver);
    }

    @Step("Click on View Cart button")
    public CartPage viewCart() {
        driver.element().click(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Click on Continue Shopping button")
    public ProductsPage continueShopping() {
        driver.element().click(continueShoppingButton);
        return this;
    }

    //validations
    @Step("Verify product details for product: {productName} with price: {price} are displayed" )
    public ProductsPage validateProductDetails(String productName, String price) {
        String actualProductName = driver.element().hover(productByName(productName)).getText(productByName(productName));
        String actualPrice = driver.element().hover(productByName(productName)).getText(priceByName(productName));
        LogsManager.info("Validating product details for : " + actualProductName, " with price: " + actualPrice);
        driver.validate().Equals(actualProductName, productName, "Product name does not match");
        driver.validate().Equals(actualPrice, price, "Product price does not match");
        return this;
    }

    @Step("Verify product is added to cart with message: {expectedMessage}")
    public ProductsPage validateProductAddedToCart(String expectedMessage) {
        String actualMessage = driver.element().getText(productAddedLabel);
        LogsManager.info("Validating product added to cart message: " + actualMessage);
        driver.verify().Equals(actualMessage, expectedMessage, "Product added to cart message does not match");
        return this;
    }

}
