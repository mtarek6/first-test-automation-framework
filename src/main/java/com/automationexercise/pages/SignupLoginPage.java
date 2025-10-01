package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    public NavigationBarComponent navigationBar;
    private final GUIDriver driver;
    private final String signUpLoginEndpoint = "/login";
    public SignupLoginPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    //locators
    private final By loginEmail = By.cssSelector("input[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("input[data-qa='login-password']");
    private final By loginButton = By.cssSelector("button[data-qa='login-button']");
    private final By signupName = By.cssSelector("input[data-qa='signup-name']");
    private final By signupEmail = By.cssSelector("input[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("button[data-qa='signup-button']");
    private final By signupLabel = By.cssSelector(".signup-form > h2");
    private final By loginError = By.cssSelector(".login-form p");
    private final By signupError = By.cssSelector(".signup-form p");
    //actions
    @Step("Navigate to Signup/Login page")
    public SignupLoginPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + signUpLoginEndpoint);
        return this;
    }
    @Step("Enter login email: {email}")
    public SignupLoginPage enterLoginEmail(String email) {
        driver.element().type(loginEmail, email);
        return this;
    }

    @Step("Enter login password: {password}")
    public SignupLoginPage enterLoginPassword(String password) {
        driver.element().type(loginPassword, password);
        return this;
    }

    @Step("Click on login button")
    public SignupLoginPage clickOnLoginButton() {
        driver.element().click(loginButton);
        return new SignupLoginPage(driver);
    }

    @Step("Enter signup name: {name}")
    public SignupLoginPage enterSignupName(String name) {
        driver.element().type(signupName, name);
        return this;
    }

    @Step("Enter signup email: {email}")
    public SignupLoginPage enterSignupEmail(String email) {
        driver.element().type(signupEmail, email);
        return this;
    }

    @Step("Click on signup button")
    public SignupLoginPage clickOnSignupButton() {
        driver.element().click(signupButton);
        return new SignupLoginPage(driver);
    }

    //validations
    @Step("Verify signup label")
    public SignupLoginPage verifyNewUserSignupLabelVisible() {
        driver.verify().isElementVisible(signupLabel);
        return this;
    }

    @Step("Verify login error message: {errorExpected}")
    public SignupLoginPage verifyLoginErrorMsg(String errorExpected) {
        String errorActual = driver.element().getText(loginError);
        driver.verify().Equals(errorActual, errorExpected, "Login error message is not as expected!");
        return this;
    }

    @Step("Verify signup error message: {errorExpected}")
    public SignupLoginPage verifySignupErrorMsg(String errorExpected) {
        String errorActual = driver.element().getText(signupError);
        driver.verify().Equals(errorActual, errorExpected, "Signup error message is not as expected!");
        return this;
    }
}
