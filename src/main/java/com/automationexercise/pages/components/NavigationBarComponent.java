package com.automationexercise.pages.components;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.*;
import com.automationexercise.utils.dataReader.PropertyReader;
import com.automationexercise.utils.logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    private final GUIDriver driver;
    public NavigationBarComponent(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By homeButton = By.xpath("//a[.=' Home']");
    private final By productsButton = By.cssSelector("a[href='/products']");
    private final By cartButton =By.xpath("//a[.=' Cart']");
    private final By signUpLoginButton = By.cssSelector("a[href='/login']");
    private final By logOutButton = By.cssSelector("a[href='/logout']");
    private final By deleteAccountButton = By.cssSelector("a[href='/delete_account']");
    private final By testCasaButton = By.xpath("//a[.=' Test Cases']");
    private final By contactUsButton = By.cssSelector("a[href='/contact_us']");
    private final By apiTestingButton = By.xpath("//a[.=' API Testing']");
    private final By videoTutorialsButton = By.xpath("//a[.=' Video Tutorials']");
    private final By homePageLabel = By.cssSelector("h1 > span");
    private final By loggedInAsLabel = By.xpath("//a[contains(text(),'Logged in as')]");

    //actions
    @Step("Navigate to Automation Exercise home page")
    public NavigationBarComponent navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb"));
        return this;
    }

    @Step("Click on Home button")
    public NavigationBarComponent clickOnHomeButton() {
        driver.element().click(homeButton);
        return this;
    }

    @Step("Click on Products button")
    public ProductsPage clickOnProductsButton() {
        driver.element().click(productsButton);
        return new ProductsPage(driver);
    }

    @Step("Click on Cart button")
    public CartPage clickOnCartButton() {
        driver.element().click(cartButton);
        return new CartPage(driver);
    }

    @Step("Click on Sign Up/Login button")
    public SignupLoginPage clickOnSignUpLoginButton() {
        driver.element().click(signUpLoginButton);
        return new SignupLoginPage(driver);
    }

    @Step("Click on Log Out button")
    public SignupLoginPage clickOnLogOutButton() {
        driver.element().click(logOutButton);
        return new SignupLoginPage(driver);
    }

    @Step("Click on Delete Account button")
    public DeleteAccountPage clickOnDeleteAccountButton() {
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }

    @Step("Click on Test Cases button")
    public TestCasesPage clickOnTestCasesButton() {
        driver.element().click(testCasaButton);
        return new TestCasesPage(driver);
    }

    @Step("Click on Contact Us button")
    public ContactUsPage clickOnContactUsButton() {
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }


    //validations
    @Step("Verify Home Page Label")
    public NavigationBarComponent verifyHomePageLabel() {
        driver.verify().isElementVisible(homePageLabel);
        return this;
    }

    @Step("Verify Logged in as {username} label")
    public NavigationBarComponent verifyLoggedInAsLabel(String expectedName) {
        String actualMessage = driver.element().getText(loggedInAsLabel);
        String expectedMessage = "Logged in as " + expectedName;
        LogsManager.info("Verifying logged in as label. Expected: " + expectedMessage + ", Actual: " + actualMessage);
        driver.verify().Equals(actualMessage, expectedMessage, "Logged in as label does not match. Expected: " + expectedMessage + ", but got: " + actualMessage);
        return this;
    }



}
