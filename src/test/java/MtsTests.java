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
    private static final String MTS_URL = "https://www.mts.by/";

    //Проверка Pop-up c cookie
    @BeforeClass
    public void setupDriver() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(MTS_URL);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='cookie-agree']")));
            acceptCookiesButton.click();
        } catch (TimeoutException e) {
            System.out.println("Pop-up c cookie не обнаружен");
        }


        new WebDriverWait(driver, Duration.ofSeconds(10))
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

    // Проверка наличия и названия блока "Онлайн пополнение без комиссии"
    @Test
    public void testBlockTitle() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement block = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div")));
        Assert.assertTrue(block.isDisplayed(), "Блок \"Онлайн пополнение без комиссии\" не отображается");

        WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2")));
        Assert.assertEquals(blockTitle.getText().replace("\n", " "), "Онлайн пополнение без комиссии", "Название блока не соответствует");
    }

    // Проверка логотипов платежных систем
    @Test
    public void testPaymentSystemLogos() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement logosContainer = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul")));
        Assert.assertTrue(logosContainer.isDisplayed(), "Логотипы платежных систем отсутсвуют.");

        String[] expectedLogos = {
                "Visa",
                "Verified By Visa",
                "MasterCard",
                "MasterCard Secure Code",
                "Белкарт"
        };

        String[] expectedXPaths = {
                "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[1]/img",
                "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[2]/img",
                "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[3]/img",
                "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[4]/img",
                "//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[5]/img"
        };

        for (int i = 0; i < expectedLogos.length; i++) {
            WebElement logo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(expectedXPaths[i])));
            Assert.assertTrue(logo.isDisplayed(), "Логотип " + expectedLogos[i] + " не отображается");
            Assert.assertTrue(logo.getAttribute("alt").contains(expectedLogos[i]),"Неверный alt у лого " + expectedLogos[i]);
        }
    }

    // Проверка ссылки "Подробнее о сервисе"
    @Test
    public void testServiceDetailsLink() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement detailsLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/a")));
        Assert.assertTrue(detailsLink.isDisplayed(), "Ссылка 'Подробнее о сервисе' не найдена.");

        String originalUrl = driver.getCurrentUrl();
        detailsLink.click();

        try {
            wait.until(ExpectedConditions.urlToBe("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/"));
        } catch (TimeoutException e) {
            System.out.println("URL не загружен в течение 20 секунд: " + e.getMessage());
            Assert.fail("Не удалось загрузить ожидаемый URL.");
        }

        String currentUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, originalUrl, "Ссылка 'Подробнее о сервисе' не перенаправляет на другую страницу.");
    }

    // Проверка полей ввода и кнопки "Продолжить" + корректность отображения суммы
    @Test
    public void testContinueButtonForServices() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        WebElement serviceButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[1]/div[1]/div[2]/button")));
        serviceButton.click();

        WebElement serviceOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[1]/div[1]/div[2]/button/span[1]")));
        serviceOption.click();

        WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"connection-phone\"]")));
        phoneNumberField.sendKeys("297777777");

        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"connection-sum\"]")));
        String amount = "2.00";
        amountField.sendKeys(amount);

        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pay-connection\"]/button")));
        continueButton.click();

        WebElement paymentIframe = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[contains(@class, 'bepaid-iframe')]")));

        driver.switchTo().frame(paymentIframe);

        WebElement paymentHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(), 'BYN')]")));
        Assert.assertTrue(paymentHeader.getText().contains(amount + " BYN"), "Сумма в заголовке неверна");

        WebElement payButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(), 'Оплатить') and contains(text(), '" + amount + " BYN')]")));
        Assert.assertTrue(payButton.getText().contains("Оплатить " + amount + " BYN"), "Текст на кнопке оплаты неверен");

        checkPlaceholders();
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/div/div/app-header/header/div/app-back-navigation/div/div")));
        closeButton.click();
        driver.switchTo().defaultContent();
    }
    // Метод полей карты
    private void checkPlaceholders() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement blockCard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='cc-number']")));
        Assert.assertTrue(blockCard.isDisplayed(), "Виртуальная карта не отображается");

        // Проверяем плейсхолдеры на соответствие
        WebElement cardNumberLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Номер карты']")));
        Assert.assertEquals(cardNumberLabel.getText(), "Номер карты", "Текст метки для номера карты неверен");

        WebElement expiryDateField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Срок действия']")));
        Assert.assertEquals(expiryDateField.getText(), "Срок действия", "Плейсхолдер для срока действия неверен");

        WebElement cvcField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='CVC']")));
        Assert.assertEquals(cvcField.getText(), "CVC", "Плейсхолдер для CVC неверен");

        WebElement cardHolderField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[text()='Имя держателя (как на карте)']")));
        Assert.assertEquals(cardHolderField.getText(), "Имя держателя (как на карте)", "Плейсхолдер для имени держателя неверен");

        List<WebElement> paymentIcons = driver.findElements(By.xpath("//div[contains(@class, 'cards-brands')]/img"));
        Assert.assertFalse(paymentIcons.isEmpty(), "Иконки платёжных систем отсутствуют");
    }

    // Проверка плейсхолдеров
    private void checkInputFieldsLabelsForService(String serviceName) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement serviceButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id='pay-section']/div/div/div[2]/section/div/div[1]/div[1]/div[2]/button")));
        serviceButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[contains(@class, 'select__list')]")));

        WebElement serviceOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[contains(@class, 'select__item')]//p[contains(text(), '" + serviceName + "')]")));
        serviceOption.click();
    }

    @Test
    public void testPhoneServiceInputFieldsLabels() {
        checkInputFieldsLabelsForService("Услуги связи");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Проверяем плейсхолдеры для полей ввода
        WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='phone']")));
        Assert.assertEquals(phoneNumberField.getAttribute("placeholder"), "Номер телефона", "Название плейсхолдера для номера телефона неверен");

        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='total_rub']")));
        Assert.assertEquals(amountField.getAttribute("placeholder"), "Сумма", "Название плейсхолдера для суммы неверен");

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='email']")));
        Assert.assertEquals(emailField.getAttribute("placeholder"), "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }

    @Test
    public void testHomeInternetInputFieldsLabels() {
        checkInputFieldsLabelsForService("Домашний интернет");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Проверяем плейсхолдеры для полей ввода
        WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"internet-phone\"]")));
        Assert.assertEquals(phoneNumberField.getAttribute("placeholder"), "Номер абонента", "Название плейсхолдера для номера абонента неверен");

        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"internet-sum\"]")));
        Assert.assertEquals(amountField.getAttribute("placeholder"), "Сумма", "Название плейсхолдера для суммы неверен");

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"internet-email\"]")));
        Assert.assertEquals(emailField.getAttribute("placeholder"), "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }

    @Test
    public void testInstallmentInputFieldsLabels() {
        checkInputFieldsLabelsForService("Рассрочка");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Проверяем плейсхолдеры для полей ввода
        WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"score-instalment\"]")));
        Assert.assertEquals(phoneNumberField.getAttribute("placeholder"), "Номер счета на 44", "Название плейсхолдера для номера счета неверен");

        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"instalment-sum\"]")));
        Assert.assertEquals(amountField.getAttribute("placeholder"), "Сумма", "Название плейсхолдера для суммы неверен");

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"instalment-email\"]")));
        Assert.assertEquals(emailField.getAttribute("placeholder"), "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }
    @Test
    public void testDebtInputFieldsLabels() {
        checkInputFieldsLabelsForService("Задолженность");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Проверяем плейсхолдеры для полей ввода
        WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"score-arrears\"]")));
        Assert.assertEquals(phoneNumberField.getAttribute("placeholder"), "Номер счета на 2073", "Название плейсхолдера для номера счета неверен");

        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"arrears-sum\"]")));
        Assert.assertEquals(amountField.getAttribute("placeholder"), "Сумма", "Название плейсхолдера для суммы неверен");

        WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"arrears-email\"]")));
        Assert.assertEquals(emailField.getAttribute("placeholder"), "E-mail для отправки чека", "Название плейсхолдера для email неверен");
    }
}
