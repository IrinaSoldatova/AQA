package com.example.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы
    private static final By SERVICE_BUTTON = By.xpath("//button[contains(@class, 'select__header')]");
    private static final By SERVICE_OPTION = By.xpath("//p[contains(text(), 'Услуги связи')]");
    private static final By PHONE_NUMBER_FIELD = By.xpath("//input[@id='connection-phone']");
    private static final By AMOUNT_FIELD = By.xpath("//input[@id='connection-sum']");
    private static final By CONTINUE_BUTTON = By.xpath("//button[text()='Продолжить']");
    private static final By PAYMENT_IFRAME = By.xpath("//iframe[contains(@class, 'bepaid-iframe')]");
    private static final By PAYMENT_HEADER = By.xpath("//span[contains(text(), 'BYN')]");
    private static final By CLOSE_BUTTON = By.xpath("//div[@class='header__close-button']");
    private static final By BLOCK_CARD = By.xpath("//input[@id='cc-number']");
    private static final By CARD_NUMBER_LABEL = By.xpath("//label[text()='Номер карты']");
    private static final By EXPIRY_DATE_FIELD = By.xpath("//label[text()='Срок действия']");
    private static final By CVC_FIELD = By.xpath("//label[text()='CVC']");
    private static final By CARD_HOLDER_FIELD = By.xpath("//label[text()='Имя держателя (как на карте)']");
    private static final By PAYMENT_ICONS = By.xpath("//div[contains(@class, 'cards-brands')]/img");

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void clickServiceButton() {
        waitForElementToBeClickable(SERVICE_BUTTON).click();
    }

    public void selectServiceOption() {
        waitForElementToBeClickable(SERVICE_OPTION).click();
    }

    public void enterPhoneNumber(String phoneNumber) {
        waitForElement(PHONE_NUMBER_FIELD).sendKeys(phoneNumber);
    }

    public void enterAmount(String amount) {
        waitForElement(AMOUNT_FIELD).sendKeys(amount);
    }

    public void clickContinueButton() {
        waitForElementToBeClickable(CONTINUE_BUTTON).click();
    }

    public void switchToPaymentFrame() {
        driver.switchTo().frame(waitForElement(PAYMENT_IFRAME));
    }

    public String getPaymentHeaderText() {
        return waitForElement(PAYMENT_HEADER).getText();
    }

    public void closePaymentWindow() {
        waitForElementToBeClickable(CLOSE_BUTTON).click();
        driver.switchTo().defaultContent();
    }

    public void checkPlaceholders() {
        WebElement blockCard = waitForElement(BLOCK_CARD);
        assert blockCard.isDisplayed() : "Виртуальная карта не отображается";

        assert waitForElement(CARD_NUMBER_LABEL).getText().equals("Номер карты") : "Текст метки для номера карты неверен";
        assert waitForElement(EXPIRY_DATE_FIELD).getText().equals("Срок действия") : "Плейсхолдер для срока действия неверен";
        assert waitForElement(CVC_FIELD).getText().equals("CVC") : "Плейсхолдер для CVC неверен";
        assert waitForElement(CARD_HOLDER_FIELD).getText().equals("Имя держателя (как на карте)") : "Плейсхолдер для имени держателя неверен";

        List<WebElement> paymentIcons = driver.findElements(PAYMENT_ICONS);
        assert !paymentIcons.isEmpty() : "Иконки платёжных систем отсутствуют";
    }

    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}