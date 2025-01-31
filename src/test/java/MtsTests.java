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
import java.util.concurrent.TimeUnit;


public class MtsTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private PaymentPage paymentPage;
    private ServicePage servicePage;
    private static final String MTS_URL = "https://www.mts.by/";
    private static final String SERVICE_URL = "https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/";
    private static final int DEFAULT_TIMEOUT_SECONDS = 20;

    private static final By COOKIE_ACCEPT_BUTTON = By.xpath("//*[@id='cookie-agree']");
    private static final By BLOCK_TITLE = By.xpath("//h2[text()='Онлайн пополнение ' and contains(., 'без комиссии')]");
    private static final By LOGOS_CONTAINER = By.xpath("//div[contains(@class, 'pay__partners')]//ul");
    private static final By DETAILS_LINK = By.xpath("//a[contains(text(), 'Подробнее о сервисе')]");
    private static final By EXPECTED_CONTENT = By.xpath("//*[contains(text(), 'Оплата банковской картой')]");
    private static final By SERVICE_BUTTON = By.xpath("//button[contains(@class, 'select__header')]");
    private static final By SERVICE_SELECT_LIST = By.xpath("//ul[contains(@class, 'select__list')]");

    //Проверка Pop-up c cookie
    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.get(MTS_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
        paymentPage = new PaymentPage(driver);
        servicePage = new ServicePage(driver);
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

    // Проверка полей ввода и кнопки "Продолжить", корректность отображения суммы и полей карты
    @Test
    public void testContinueButtonForServices() {
        String amount = "2.00";
        String phoneNumber = "297777777";

        paymentPage.clickServiceButton();
        paymentPage.selectServiceOption();
        paymentPage.enterPhoneNumber(phoneNumber);
        paymentPage.enterAmount(amount);
        paymentPage.clickContinueButton();

        paymentPage.switchToPaymentFrame();

        Assert.assertTrue(paymentPage.getPaymentHeaderText().contains(amount + " BYN"), "Сумма в заголовке неверна");

        WebElement payButton = driver.findElement(By.xpath("//button[contains(text(), 'Оплатить') and contains(text(), '" + amount + " BYN')]"));
        Assert.assertTrue(payButton.getText().contains("Оплатить " + amount + " BYN"), "Текст на кнопке оплаты неверен");

        paymentPage.checkPlaceholders();
        paymentPage.closePaymentWindow();
    }

    // Проверка плейсхолдеров
    private void checkInputFieldsLabelsForService(String serviceName) {
        waitForElementToBeClickable(SERVICE_BUTTON).click();
        waitForElement(SERVICE_SELECT_LIST);
        waitForElementToBeClickable(By.xpath("//li[contains(@class, 'select__item')]//p[contains(text(), '" + serviceName + "')]")).click();
    }

    @Test
    public void testPhoneServiceInputFieldsLabels() {
        checkInputFieldsLabelsForService("Услуги связи");
        servicePage.checkPhoneServiceInputFieldsLabels();
    }

    @Test
    public void testHomeInternetInputFieldsLabels() {
        checkInputFieldsLabelsForService("Домашний интернет");
        servicePage.checkHomeInternetInputFieldsLabels();
    }

    @Test
    public void testInstallmentInputFieldsLabels() {
        checkInputFieldsLabelsForService("Рассрочка");
        servicePage.checkInstallmentInputFieldsLabels();
    }

    @Test
    public void testDebtInputFieldsLabels() {
        checkInputFieldsLabelsForService("Задолженность");
        servicePage.checkDebtInputFieldsLabels();
    }
}