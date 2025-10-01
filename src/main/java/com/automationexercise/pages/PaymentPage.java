package com.automationexercise.pages;

import com.automationexercise.drivers.GUIDriver;
import com.automationexercise.pages.components.NavigationBarComponent;
import com.automationexercise.utils.dataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {
    private final GUIDriver driver;
    private final NavigationBarComponent navigationBar;
    private String paymentPageEndpoint = "/payment";

    public PaymentPage(GUIDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }
        //locators
        private final By nameOnCardField = By.name("name_on_card");
        private final By cardNumberField = By.name("card_number");
        private final By cvcField = By.name("cvc");
        private final By expirationMonthField = By.name("expiry_month");
        private final By expirationYearField = By.name("expiry_year");
        private final By payAndConfirmOrderButton = By.id("submit");
        private final By successMessage = By.cssSelector("h2[data-qa='order-placed'] + p");
        private final By downloadInvoiceButton = By.xpath("//a[.='Download Invoice']");

        //actions
        @Step("Navigate to Payment Page")
        public PaymentPage navigate () {
            driver.browser().navigateTo(PropertyReader.getProperty("baseUrlWeb") + paymentPageEndpoint);
            return this;
        }

        @Step("Enter payment details: Name on Card: {nameOnCard}, Card Number: {cardNumber}, CVC: {cvc}, Expiration Month: {expMonth}, Expiration Year: {expYear}")
        public PaymentPage enterPaymentDetails (String nameOnCard, String cardNumber, String cvc, String
        expMonth, String expYear){
            driver.element().type(nameOnCardField, nameOnCard)
                    .type(cardNumberField, cardNumber)
                    .type(cvcField, cvc)
                    .type(expirationMonthField, expMonth)
                    .type(expirationYearField, expYear);
            return this;
        }

        @Step("Click on 'Pay and Confirm Order' button")
        public PaymentPage clickPayAndConfirmOrder () {
            driver.element().click(payAndConfirmOrderButton);
            return this;
        }

        @Step("Click on 'Download Invoice' button")
        public PaymentPage clickDownloadInvoice () {
            driver.element().click(downloadInvoiceButton);
            return this;
        }

        //validations
        @Step("Verify success message after order placement")
        public PaymentPage verifySuccessMessage (String expectedMessage) {
            String actualMessage = driver.element().getText(successMessage);
            driver.verify().Equals(actualMessage, expectedMessage, "Success message does not match!");
            return this;
        }
        @Step("Verify invoice downloaded: {file}")
        public PaymentPage verifyInvoiceDownloaded(String invoiceName) {
            driver.verify().assertFileExists(invoiceName, "Invoice file was not downloaded: " + invoiceName);
            return this;
        }
}
