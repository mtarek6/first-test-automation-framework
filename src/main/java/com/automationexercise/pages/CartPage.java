package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    private final GUIDriver driver;
    public NavigationBarComponent navigationBar;
    private String cartPageEndpoint = "/view_cart";
    public CartPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //locators
    private final By checkoutButton = By.className("check_out");

    //dynamic locators
    private By productName(String productName)
    {
        return By.xpath("//h4 /a[.='" + productName + "']");
    }

    private By productPrice(String productName)
    {
        return By.xpath("//h4 /a[.='" + productName + "'] //following::td[@class='cart_price'] /p [1]");
    }

    private By productQuantity(String productName)
    {
        return By.xpath("(//h4 /a[.='" + productName + "'] //following::td[@class='cart_quantity'] /button)[1]");
    }

    private By productTotal(String productName)
    {
        return By.xpath("(//h4 /a[.='" + productName + "'] //following::td[@class='cart_total'] /p)[1]");
    }

    private By productDelete(String productName)
    {
        return By.xpath("(//h4 /a[.='" + productName + "'] //following::td[@class='cart_delete'])[1]");
    }

    //actions
    @Step("Navigate to Cart page")
    public CartPage navigate()
    {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + cartPageEndpoint);
        return this;
    }

    @Step("Click on Checkout button")
    public CheckoutPage clickCheckoutButton()
    {
        driver.element().click(checkoutButton);
        return new CheckoutPage(driver);
    }

    @Step("Delete product '{productName}' from cart")
    public CartPage clickProductDelete(String productName)
    {
        driver.element().click(productDelete(productName));
        return this;
    }

    //validations
    @Step("Verify product details in cart for '{productName}'")
    public CartPage verifyProductDetails(String productName, String price, String quantity, String total)
    {
        String actualProductName = driver.element().getText(productName(productName));
        String actualProductPrice = driver.element().getText(productPrice(productName));
        String actualProductQuantity = driver.element().getText(productQuantity(productName));
        String actualProductTotal = driver.element().getText(productTotal(productName));
        driver.validate().Equals(actualProductName, productName, "Product name in cart is not correct")
                .Equals(actualProductPrice, price, "Product price in cart is not correct")
                .Equals(actualProductQuantity, quantity, "Product quantity in cart is not correct")
                .Equals(actualProductTotal, total, "Product total in cart is not correct");
        return this;
    }
}
