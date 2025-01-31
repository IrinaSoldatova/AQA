import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class MtsTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final String MTS_URL = "https://www.mts.by/";
    private static final String SERVICE_URL = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
    private static final int DEFAULT_TIMEOUT_SECONDS = 20;

    private static final By COOKIE_ACCEPT_BUTTON = By.xpath("//*[@id='cookie-agree']");
    private static final By BLOCK_TITLE = By.xpath("//h2[text()='Онлайн пополнение ' and contains(., 'без комиссии')]");
    private static final By LOGOS_CONTAINER = By.xpath("//div[contains(@class, 'pay__partners')]//ul");
    private static final By DETAILS_LINK = By.xpath("//a[contains(text(), 'Подробнее о сервисе')]");
    private static final By EXPECTED_CONTENT = By.xpath("//*[contains(text(), 'Оплата банковской картой')]");
    private static final By SERVICE_BUTTON = By.xpath("//button[contains(@class, 'select__header')]");
    private static final By SERVICE_OPTION = By.xpath("//p[contains(text(), 'Услуги связи')]");
    private static final By PHONE_NUMBER_FIELD = By.xpath("//input[@id='connection-phone']");
    private static final By AMOUNT_FIELD = By.xpath("//input[@id='connection-sum']");
    private static final By CONTINUE_BUTTON = By.xpath("//button[text()='Продолжить']");
    private static final By CLOSE_BUTTON = By.xpath("//div[@class='header__close-button']");
    private static final By PAYMENT_IFRAME = By.xpath("//iframe[contains(@class, 'bepaid-iframe')]");
    private static final By PAYMENT_HEADER = By.xpath("//span[contains(text(), 'BYN')]");
    private static final By BLOCK_CARD = By.xpath("//input[@id='cc-number']");
    private static final By CARD_NUMBER_LABEL = By.xpath("//label[text()='Номер карты']");
    private static final By EXPIRY_DATE_FIELD = By.xpath("//label[text()='Срок действия']");
    private static final By CVC_FIELD = By.xpath("//label[text()='CVC']");
    private static final By CARD_HOLDER_FIELD = By.xpath("//label[text()='Имя держателя (как на карте)']");
    private static final By PAYMENT_ICONS = By.xpath("//div[contains(@class, 'cards-brands')]/img");
    private static final By SERVICE_SELECT_LIST = By.xpath("//ul[contains(@class, 'select__list')]");
    private static final By EMAIL_FIELD = By.xpath("//input[@class='email']");
    private static final By PHONEUMBER_ABONENT_FIELD = By.xpath("//input[@id='internet-phone']");
    private static final By AMOUNT_INTERNET_FIELD = By.xpath("//input[@id='internet-sum']");
    private static final By EMAIL_INTERNET_FIELD = By.xpath("//input[@id='internet-email']");
    private static final By ACCOUNT_NUMBER_FIELD = By.xpath("//input[@id='score-instalment']");
    private static final By AMOUNT_ACCOUNT_FIELD = By.xpath("//input[@id='instalment-sum']");
    private static final By EMAIL_ACCOUNT_FIELD = By.xpath("//input[@id='instalment-email']");
    private static final By NUMBER_ACC_FIELD = By.xpath("//input[@id='score-arrears']");
    private static final By AMOUNT_ACC_FIELD = By.xpath("//input[@id='arrears-sum']");
    private static final By EMAIL_ACC_FIELD = By.xpath("//input[@id='arrears-email']");

    //Проверка Pop-up c cookie
    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS);
        driver.get(MTS_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
        acceptCookies();
        waitForPageLoad();
    }

    private void acceptCookies() {
            try {
                WebElement acceptCookiesButton = wait.until(ExpectedConditions.visibilityOfElementLocated(COOKIE_ACCEPT_BUTTON));
                acceptCookiesButton.click();
            } catch (TimeoutException e) {
                System.out.println("Pop-up c cookie не обнаружен");
            }
    }

    private void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS))
                .until(webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete"));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement waitForElementToBeClickable(By locator) {
        return  wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Проверка наличия и названия блока "Онлайн пополнение без комиссии"
    @Test
    public void testBlockTitle() {
        WebElement blockTitle = waitForElement(BLOCK_TITLE);
        Assert.assertEquals(blockTitle.getText().replace("\n", " "), "Онлайн пополнение без комиссии", "Название блока не соответствует");
    }

    // Проверка логотипов платежных систем
    @Test
    public void testPaymentSystemLogos() {
        WebElement logosContainer = waitForElement(LOGOS_CONTAINER);
        Assert.assertTrue(logosContainer.isDisplayed(), "Логотипы платежных систем отсутсвуют.");

        String[] expectedLogos = {
                "Visa",
                "Verified By Visa",
                "MasterCard",
                "MasterCard Secure Code",
                "Белкарт"
        };

        for (String expectedLogo : expectedLogos) {
            WebElement logo = waitForElement(By.xpath("//img[@alt='" + expectedLogo + "']"));
            Assert.assertTrue(logo.isDisplayed(), "Логотип " + expectedLogo + " отсутствует.");
            Assert.assertEquals(logo.getAttribute("alt"), expectedLogo, "Неверный alt у лого " + expectedLogo);
        }
    }

    // Проверка ссылки "Подробнее о сервисе"
    @Test
    public void testServiceDetailsLink() {
        WebElement detailsLink = waitForElementToBeClickable(DETAILS_LINK);
        Assert.assertTrue(detailsLink.isDisplayed(), "Ссылка 'Подробнее о сервисе' не найдена.");

        String originalUrl = driver.getCurrentUrl();
        detailsLink.click();

        try {
            wait.until(ExpectedConditions.urlToBe(SERVICE_URL));
        } catch (TimeoutException e) {
            System.out.println("URL не загружен в течение 20 секунд: " + e.getMessage());
            Assert.fail("Не удалось загрузить ожидаемый URL.");
        }

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, originalUrl, "Ссылка 'Подробнее о сервисе' не перенаправляет на другую страницу.");

        WebElement expectedContent = waitForElement(EXPECTED_CONTENT);
        Assert.assertTrue(expectedContent.isDisplayed(), "Ожидаемый контент не найден на новой странице.");
    }

    // Проверка полей ввода и кнопки "Продолжить" + корректность отображения суммы
    @Test
    public void testContinueButtonForServices() {
        String amount = "2.00";
        String phoneNumber = "297777777";

        WebElement serviceButton = waitForElementToBeClickable(SERVICE_BUTTON);
        serviceButton.click();

        WebElement serviceOption = waitForElementToBeClickable(SERVICE_OPTION);
        serviceOption.click();

        WebElement phoneNumberField = waitForElement(PHONE_NUMBER_FIELD);
        phoneNumberField.sendKeys(phoneNumber);

        WebElement amountField = waitForElement(AMOUNT_FIELD);
        amountField.sendKeys(amount);

        WebElement continueButton = waitForElementToBeClickable(CONTINUE_BUTTON);
        continueButton.click();

        driver.switchTo().frame(waitForElement(PAYMENT_IFRAME));
        amountValid(amount);

        checkPlaceholders();
        WebElement closeButton = waitForElementToBeClickable(CLOSE_BUTTON);
        closeButton.click();
        driver.switchTo().defaultContent();
    }
    //Проверка суммы
    public void amountValid(String amount) {
        Assert.assertTrue(waitForElement(PAYMENT_HEADER).getText().contains(amount + " BYN"), "Сумма в заголовке неверна");

        WebElement payButton = waitForElement(By.xpath("//button[contains(text(), 'Оплатить') and contains(text(), '" + amount + " BYN')]"));
        Assert.assertTrue(payButton.getText().contains("Оплатить " + amount + " BYN"), "Текст на кнопке оплаты неверен");
    }

    // Метод полей карты
    private void checkPlaceholders() {
        WebElement blockCard = waitForElement(BLOCK_CARD);
        Assert.assertTrue(blockCard.isDisplayed(), "Виртуальная карта не отображается");

        Assert.assertEquals(waitForElement(CARD_NUMBER_LABEL).getText(), "Номер карты", "Текст метки для номера карты неверен");

        Assert.assertEquals(waitForElement(EXPIRY_DATE_FIELD).getText(), "Срок действия", "Плейсхолдер для срока действия неверен");

        Assert.assertEquals(waitForElement(CVC_FIELD).getText(), "CVC", "Плейсхолдер для CVC неверен");

        Assert.assertEquals(waitForElement(CARD_HOLDER_FIELD).getText(), "Имя держателя (как на карте)", "Плейсхолдер для имени держателя неверен");

        List<WebElement> paymentIcons = driver.findElements(PAYMENT_ICONS);
        Assert.assertFalse(paymentIcons.isEmpty(), "Иконки платёжных систем отсутствуют");
    }

    // Проверка плейсхолдеров
    private void checkInputFieldsLabelsForService(String serviceName) {
        final By serviceOptionXPath = By.xpath(
                "//li[contains(@class, 'select__item')]//p[contains(text(), '" + serviceName + "')]");
        WebElement serviceButton = waitForElementToBeClickable(SERVICE_BUTTON);
        serviceButton.click();

        waitForElement(SERVICE_SELECT_LIST);

        WebElement serviceOption = waitForElementToBeClickable(serviceOptionXPath);
        serviceOption.click();
    }

    @Test
    public void testPhoneServiceInputFieldsLabels() {
        checkInputFieldsLabelsForService("Услуги связи");
        Assert.assertEquals(waitForElement(PHONE_NUMBER_FIELD).getAttribute(
                "placeholder"), "Номер телефона", "Название плейсхолдера для номера телефона неверен");

        Assert.assertEquals(waitForElement(AMOUNT_FIELD).getAttribute(
                "placeholder"), "Сумма", "Название плейсхолдера для суммы неверен");

        Assert.assertEquals(waitForElement(EMAIL_FIELD).getAttribute(
                "placeholder"), "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }

    @Test
    public void testHomeInternetInputFieldsLabels() {
        checkInputFieldsLabelsForService("Домашний интернет");
        Assert.assertEquals(waitForElement(PHONEUMBER_ABONENT_FIELD).getAttribute(
                "placeholder"), "Номер абонента", "Название плейсхолдера для номера абонента неверен");

        Assert.assertEquals(waitForElement(AMOUNT_INTERNET_FIELD).getAttribute(
                "placeholder"), "Сумма", "Название плейсхолдера для суммы неверен");

        Assert.assertEquals(waitForElement(EMAIL_INTERNET_FIELD).getAttribute(
                "placeholder"), "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }

    @Test
    public void testInstallmentInputFieldsLabels() {
        checkInputFieldsLabelsForService("Рассрочка");
        Assert.assertEquals(waitForElement(ACCOUNT_NUMBER_FIELD).getAttribute(
                "placeholder"), "Номер счета на 44", "Название плейсхолдера для номера счета неверен");

        Assert.assertEquals(waitForElement(AMOUNT_ACCOUNT_FIELD).getAttribute(
                "placeholder"), "Сумма", "Название плейсхолдера для суммы неверен");

        Assert.assertEquals(waitForElement(EMAIL_ACCOUNT_FIELD).getAttribute(
                "placeholder"), "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }

    @Test
    public void testDebtInputFieldsLabels() {
        checkInputFieldsLabelsForService("Задолженность");
        Assert.assertEquals(waitForElement(NUMBER_ACC_FIELD).getAttribute(
                "placeholder"), "Номер счета на 2073", "Название плейсхолдера для номера счета неверен");

        Assert.assertEquals(waitForElement(AMOUNT_ACC_FIELD).getAttribute(
                "placeholder"), "Сумма", "Название плейсхолдера для суммы неверен");

        Assert.assertEquals(waitForElement(EMAIL_ACC_FIELD).getAttribute(
                "placeholder"), "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }
}