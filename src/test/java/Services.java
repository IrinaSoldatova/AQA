import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class Services {
    private WebDriver driver;
    private WebDriverWait wait;

    // Локаторы для полей ввода
    private static final By AMOUNT_FIELD = By.xpath("//input[@id='connection-sum']");
    private static final By EMAIL_FIELD = By.xpath("//input[@class='email']");
    private static final By PHONE_NUMBER_ABONENT_FIELD = By.xpath("//input[@id='internet-phone']");
    private static final By AMOUNT_INTERNET_FIELD = By.xpath("//input[@id='internet-sum']");
    private static final By EMAIL_INTERNET_FIELD = By.xpath("//input[@id='internet-email']");
    private static final By ACCOUNT_NUMBER_FIELD = By.xpath("//input[@id='score-instalment']");
    private static final By AMOUNT_ACCOUNT_FIELD = By.xpath("//input[@id='instalment-sum']");
    private static final By EMAIL_ACCOUNT_FIELD = By.xpath("//input[@id='instalment-email']");
    private static final By NUMBER_ACC_FIELD = By.xpath("//input[@id='score-arrears']");
    private static final By AMOUNT_ACC_FIELD = By.xpath("//input[@id='arrears-sum']");
    private static final By EMAIL_ACC_FIELD = By.xpath("//input[@id='arrears-email']");
    private static final By PHONE_NUMBER_FIELD = By.xpath("//input[@id='connection-phone']");

    public Services(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void checkPhoneServiceInputFieldsLabels() {
        Assert.assertEquals(waitForElement(PHONE_NUMBER_FIELD).getAttribute(
                "placeholder"), "Номер телефона", "Название плейсхолдера для номера телефона неверен");
        Assert.assertEquals(waitForElement(AMOUNT_FIELD).getAttribute("placeholder"),
                "Сумма", "Название плейсхолдера для суммы неверен");
        Assert.assertEquals(waitForElement(EMAIL_FIELD).getAttribute("placeholder"),
                "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }

    public void checkHomeInternetInputFieldsLabels() {
        Assert.assertEquals(waitForElement(PHONE_NUMBER_ABONENT_FIELD).getAttribute("placeholder"),
                "Номер абонента", "Название плейсхолдера для номера абонента неверен");
        Assert.assertEquals(waitForElement(AMOUNT_INTERNET_FIELD).getAttribute("placeholder"),
                "Сумма", "Название плейсхолдера для суммы неверен");
        Assert.assertEquals(waitForElement(EMAIL_INTERNET_FIELD).getAttribute("placeholder"),
                "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }

    public void checkInstallmentInputFieldsLabels() {
        Assert.assertEquals(waitForElement(ACCOUNT_NUMBER_FIELD).getAttribute("placeholder"),
                "Номер счета на 44", "Название плейсхолдера для номера счета неверен");
        Assert.assertEquals(waitForElement(AMOUNT_ACCOUNT_FIELD).getAttribute("placeholder"),
                "Сумма", "Название плейсхолдера для суммы неверен");
        Assert.assertEquals(waitForElement(EMAIL_ACCOUNT_FIELD).getAttribute("placeholder"),
                "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }

    public void checkDebtInputFieldsLabels() {
        Assert.assertEquals(waitForElement(NUMBER_ACC_FIELD).getAttribute("placeholder"),
                "Номер счета на 2073", "Название плейсхолдера для номера счета неверен");
        Assert.assertEquals(waitForElement(AMOUNT_ACC_FIELD).getAttribute("placeholder"),
                "Сумма", "Название плейсхолдера для суммы неверен");
        Assert.assertEquals(waitForElement(EMAIL_ACC_FIELD).getAttribute("placeholder"),
                "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }

    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}