package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    private final GUIDriver driver;
    public SignupPage(GUIDriver driver) {
        this.driver = driver;
    }

    //locators
    private final By nameInput = By.id("name");
    private final By emailInput = By.id("email");
    private final By passwordInput = By.id("password");
    private final By daysDropdown = By.id("days");
    private final By monthsDropdown = By.id("months");
    private final By yearsDropdown = By.id("years");
    private final By newsletterCheckbox = By.id("newsletter");
    private final By offersCheckbox = By.id("optin");
    private final By firstNameInput = By.id("first_name");
    private final By lastNameInput = By.id("last_name");
    private final By companyInput = By.id("company");
    private final By address1Input = By.id("address1");
    private final By address2Input = By.id("address2");
    private final By countryDropdown = By.id("country");
    private final By stateInput = By.id("state");
    private final By cityInput = By.id("city");
    private final By zipCodeInput = By.id("zipcode");
    private final By mobileNumberInput = By.id("mobile_number");
    private final By createAccountButton = By.cssSelector("button[data-qa='create-account']");
    private final By accountCreatedLabel = By.cssSelector("h2 > b");
    private final By continueButton = By.cssSelector("a[data-qa='continue-button']");


    //actions
    @Step("Choose title: {title}") //Mr or Mrs
    private SignupPage chooseTitle(String title) {
        By titleLocator = By.xpath("//input[@value='" + title + "']");
        driver.element().click(titleLocator);
        return this;
    }

    @Step("Fill Registration Form")
    public SignupPage fillRegistrationForm( String title, String password, String day, String month, String year,
                                         String firstName, String lastName, String company, String address1,
                                         String address2, String country, String state, String city,
                                         String zipCode, String mobileNumber ) {
        chooseTitle(title);
        driver.element().type(passwordInput, password);
        driver.element().selectFromDropdown(daysDropdown, day);
        driver.element().selectFromDropdown(monthsDropdown, month);
        driver.element().selectFromDropdown(yearsDropdown, year);
        driver.element().click(newsletterCheckbox);
        driver.element().click(offersCheckbox);
        driver.element().type(firstNameInput, firstName);
        driver.element().type(lastNameInput, lastName);
        driver.element().type(companyInput, company);
        driver.element().type(address1Input, address1);
        driver.element().type(address2Input, address2);
        driver.element().selectFromDropdown(countryDropdown, country);
        driver.element().type(stateInput, state);
        driver.element().type(cityInput, city);
        driver.element().type(zipCodeInput, zipCode);
        driver.element().type(mobileNumberInput, mobileNumber);
        return this;
    }

    @Step("Click on Create Account button")
    public SignupPage clickOnCreateAccountButton() {
        driver.element().click(this.createAccountButton);
        return this;
    }

    @Step("Click on Continue button")
    public NavigationBarComponent clickOnContinueButton() {
        driver.element().click(this.continueButton);
        return new NavigationBarComponent(driver);
    }

    //validations
    @Step("Verify that account is created successfully")
    public SignupPage verifyAccountCreated() {
        driver.verify().isElementVisible(accountCreatedLabel);
        return this;
    }
}
