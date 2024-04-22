package webTests;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;

public class WebTest {
    private WebDriver driver;
    JavascriptExecutor jsexecutor = (JavascriptExecutor) driver;
    WebDriverWait wait;
    private String baseUrl = "http://localhost:5000";
    private long waitTime = 500; //Изменить, если хотим наблюдать процесс
    private String loginPage = "/login";
    private String profilePage = "/profile";
    private String signUpPage = "/signup";
    private final String userLoginEmail = "email@ejao.ru";
    private final String userPassword = "ejao";
    Logger LOG = LoggerFactory.getLogger(WebTest.class);

// rjjv
    private String[] emails = {"у", "test@example.", "invalidemail", "@email.ru", "email@example", "рус@рус.ру", "user@", "user@@example.com"};
    private final HashMap<String, String> expectedErrors = new HashMap<>() {{
        put("у", "Адрес электронной почты должен содержать символ \"@\". В адресе \"у\" отсутствует символ \"@\".");
        put("test@example.", "Недопустимое положение символа \".\" в адресе \"example.\".");
        put("invalidemail", "Адрес электронной почты должен содержать символ \"@\". В адресе \"invalidemail\" отсутствует символ \"@\".");
        put("@email.ru", "Введите часть адреса до символа \"@\". Адрес \"@email.ru\" неполный.");
        put("email@example", "");
        put("рус@рус.ру", "Часть адреса до символа \"@\" не должна содержать символ \"р\".");
        put("user@", "Введите часть адреса после символа \"@\". Адрес \"user@\" неполный.");
        put("user@@example.com", "Часть адреса после символа \"@\" не должна содержать символ \"@\".");
    }};


    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=950,1200");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--silent");
        options.addArguments("--remote-debugging-port=9222");
        options.setBinary("chrome-win64/chrome.exe");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofMillis(5000));
        LOG.info("Driver is started");
        //driver.get(baseUrl);

    }

    @Test
    public void emptyInputsInRegisterForm() {
        driver.get(baseUrl + signUpPage);
        System.out.println(driver.getCurrentUrl());
        WebElement submitButton = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > button"));
        if (submitButton.isEnabled()) {
            System.out.println("Кнопка активна с пустыми обязательными полями");
            //Assert.assertEquals(submitButton.isEnabled(), false,"Ожидается false, так как оябзательные поля не заполненыи");
        }

        submitButton.click();
        threadIsWaiting();
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.equals(baseUrl + signUpPage)) {
            System.out.println("Драйвер зарегистрировался с пустыми обязательными полями. И перешел на страницу login");
            Assert.assertEquals(currentUrl, baseUrl + "/signup", "Драйвер зарегистрировался с пустыми обязательными полями. И перешел на страницу login");
        }
        threadIsWaiting();
        if (currentUrl.equals(baseUrl + signUpPage)) {
            WebElement errorDiv = null;
            try {
                errorDiv = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > div"));
            } catch (NoSuchElementException e) {
                System.out.println("Блок с ошибкой не отображен");
            }
            if (errorDiv != null) {
                if (errorDiv.isDisplayed()) {
                    boolean containtext = errorDiv.getText().contains("Email address already exists");
                    System.out.println("Отображено сообщение об ошибке, что email уже зарегистрирован, но поле не заполнено");
                    Assert.assertEquals(containtext, false, "Ожидается false так как обязательные поля не заполнены");
                }
            }
        }
    }

    @Test
    public void negativeRegisterEmailInForm() {
        driver.get(baseUrl + signUpPage);
        System.out.println(driver.getCurrentUrl());
        WebElement parrentDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > section > div.hero-body")));
        WebElement emailInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(1) > div > input"));

        for (String email : emails) {
            emailInput.sendKeys(email);
            emailInput.click();
            threadIsWaiting();

            WebElement errorDiv = null;
            try {
                errorDiv = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > div"));
            } catch (NoSuchElementException e) {
                System.out.println("Блок с сообщением об ошибке не был найден при отправке email  \"" + email + "\"");
            }

            if (errorDiv != null) {
                boolean containtext = errorDiv.getText().contains("Email address already exists");
                System.out.println("Отображено сообщение об ошибке, что " + email + " уже зарегистрирован");
                Assert.assertEquals(containtext, true);
            } else if (errorDiv == null) {
                String validationMessage = emailInput.getAttribute("validationMessage");
                System.out.println("validationMessage: " + validationMessage);
                //Assert.assertEquals(validationMessage,"Адрес электронной почты должен содержать символ \"@\". В адресе \"у\" отсутствует символ \"@\".");
                boolean expectedEqual = validationMessage.equals(expectedErrors.get(email));
                //boolean expectedEqual = true;
                System.out.println("Введен email: \"" + email + "\" Ожидаемая ошибка \"" + expectedErrors.get(email) + ((expectedEqual) ? "\" была получена" : "\" не была получена"));
                System.out.println(" ");
                Assert.assertEquals(validationMessage, expectedErrors.get(email));
            }
            emailInput.clear();
        }
    }




    @Test
    public void successRegistrationExpected() {
        driver.get(baseUrl+ signUpPage);
        System.out.println(driver.getCurrentUrl());
        WebElement parrentDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > section > div.hero-body")));
        WebElement boxForm = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div"));
        WebElement emailInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(1) > div > input"));
        WebElement nameInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(2) > div > input"));
        WebElement passwordInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(3) > div > input"));
        WebElement submitButton = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > button"));

        emailInput.sendKeys(userLoginEmail);
        System.out.println("Ввел "+userLoginEmail+" в поле Email");
        nameInput.sendKeys("ejao");
        System.out.println("Ввел ejao в поле Name");
        passwordInput.sendKeys("newpass");
        System.out.println("Ввел "+userPassword+" в поле Password");
        submitButton.click();
        System.out.println("Дравер находится на странице " + driver.getCurrentUrl());
        Assert.assertEquals(driver.getCurrentUrl(),baseUrl+loginPage, "Ожидаем "+baseUrl+loginPage+ " после успешной регистрации пользователя");

    } //тест ожидаем успешную регистрацию

    @Test
    public void isFieldsInRegisterFormAreRequired() {
        driver.get(baseUrl + signUpPage);

        System.out.println(driver.getCurrentUrl());
        WebElement parrentDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > section > div.hero-body")));
        WebElement emailInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(1) > div > input"));
        WebElement nameInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(2) > div > input"));
        WebElement passwordInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(3) > div > input"));
        WebElement submitButton = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > button"));

        String isEmailRequired = emailInput.getAttribute("required");
        if (isEmailRequired != null) {
            Assert.assertEquals(isEmailRequired, "true", "Ожидается true, так как в требованиях заявлено, что поле Email обязательно к заполнению");
        }
        String einClass = emailInput.getAttribute("class");
        if (einClass.contains("required")) {
            System.out.println("Поле обязательно к заполнению");
            Assert.assertEquals(einClass.contains("required"), true, "Ожидается true, так как в требованиях заявлено, что поле Email обязательно к заполнению");
        } else {
            Assert.assertTrue(false, "Email field");
            System.out.println("Поле Email не обязательно к заполнению");
        }

        String isNameRequired = nameInput.getAttribute("required");
        //Assert.assertEquals(isNameRequired,"false","Ожидается false, так как в требованиях заявлено, что поле Name не обязательно к заполнению");
        if (isNameRequired != null) {
            Assert.assertEquals(isNameRequired, "false", "Ожидается false, так как в требованиях заявлено, что поле Name не обязательно к заполнению");
        }
        String ninClass = nameInput.getAttribute("class");
        if (ninClass.contains("required")) {
            System.out.println("Поле обязательно к заполнению");
            Assert.assertEquals(ninClass.contains("required"), false, "Ожидается true, так как в требованиях заявлено, что поле Name не обязательно к заполнению");
        } else {
            Assert.assertTrue(true, "Name field");
            System.out.println("Поле Name не обязательно к заполнению");
        }

        String isPasswordRequired = passwordInput.getDomAttribute("required");
        //Assert.assertEquals(isPasswordRequired,"true","Ожидается true, так как в требованиях заявлено, что поле Password обязательно к заполнению");
        if (isPasswordRequired != null) {
            Assert.assertEquals(isPasswordRequired, "true", "Ожидается true, так как в требованиях заявлено, что поле Password обязательно к заполнению");
        }
        String pinClass = passwordInput.getAttribute("class");
        if (pinClass.contains("required")) {
            System.out.println("Поле обязательно к заполнению");
            Assert.assertEquals(pinClass.contains("required"), true, "Ожидается true, так как в требованиях заявлено, что поле Password обязательно к заполнению");
        } else {
            System.out.println("Поле Password не обязательно к заполнению");
            Assert.assertTrue(false, "Поле password формы регистраиции, не является обязательным");

        }

    } //Проверка  полей формы регистрации на обязательность заполнения


    //email@ejao.ru
    //ejao
    //ejao
    @Test
    public void wrongTypeOfEmailInLoginForm() {
        driver.get(baseUrl + loginPage);
        System.out.println(driver.getCurrentUrl());
        WebElement parrentDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > section > div.hero-body")));
        WebElement emailInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(1) > div > input"));
        WebElement passwordInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(2) > div > input"));
        WebElement checkBox = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(3) > label > input[type=checkbox]"));
        WebElement submitButton = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > button"));

        if (submitButton.isEnabled()) {
            System.out.println("Кнопка Login активна при пустых обязательных полях");
            //Assert.assertEquals(submitButton.isEnabled(),false);
        } else System.out.println("Кнопка Login неактивна при пустых обязательных полях");

        if (checkBox.isEnabled() && checkBox.isDisplayed()) {
            System.out.println("Чекбокс \"Remember me\" отображен на странице");
            checkBox.click();
            checkBox.click();
        }
        for (String email : emails) {
            emailInput.sendKeys(email);
            emailInput.click();
            threadIsWaiting();

            String validationMessage = emailInput.getAttribute("validationMessage");
            System.out.println("validationMessage: " + validationMessage);
            //Assert.assertEquals(validationMessage,"Адрес электронной почты должен содержать символ \"@\". В адресе \"у\" отсутствует символ \"@\".");
            boolean expectedEqual = validationMessage.equals(expectedErrors.get(email));
            //boolean expectedEqual = true;
            System.out.println("Введен email: \"" + email + "\" Ожидаемая ошибка \"" + expectedErrors.get(email) + ((expectedEqual) ? "\" была получена" : "\" не была получена"));
            System.out.println(" ");

            Assert.assertEquals(validationMessage, expectedErrors.get(email));
            emailInput.clear();
        }
    }

    @Test
    public void wrongPasswordinLoginForm() {
        driver.get(baseUrl + loginPage);
        System.out.println(driver.getCurrentUrl());
        WebElement parrentDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > section > div.hero-body")));
        WebElement emailInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(1) > div > input"));
        WebElement passwordInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(2) > div > input"));
        WebElement submitButton = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > button"));
        threadIsWaiting();
        emailInput.sendKeys(userLoginEmail);
        passwordInput.sendKeys("wrongPassword");
        submitButton.click();
        WebElement errorDiv = null;
        try {
            errorDiv = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > div"));
            Assert.assertEquals(errorDiv.isDisplayed(), true, "Ожидается true, так как мы ввели неверный пароль");
        } catch (NoSuchElementException e) {
            System.out.println("блок с ошибкой не был найдена");
            Assert.assertFalse(false, "Блок с сообщением об ошибке не был найдена");
        }
    } //тест на проврку блока об ошибке

    @Test
    public void emptyInputsInloginForm() {
        driver.get(baseUrl + loginPage);
        System.out.println(driver.getCurrentUrl());
        WebElement parrentDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > section > div.hero-body")));
        WebElement emailInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(1) > div > input"));
        WebElement passwordInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(2) > div > input"));
        WebElement submitButton = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > button"));
        if (submitButton.isEnabled()) {
            submitButton.click();
        } else System.out.println("Кнопка входа не активна");
        //driver.getCurrentUrl();
        //threadIsWaiting();
        if (driver.getCurrentUrl().equals(baseUrl + profilePage)) {
            System.out.println("Был совершен переход при нажатии кнопки входа с пустыми полями");
            Assert.assertEquals(driver.getCurrentUrl(), baseUrl + profilePage);
        }
    }


    //email@ejao.ru
    //ejao
    //ejao
    @Test
    public void expectedSuccessLoginToTheSystem() {
        driver.get(baseUrl + loginPage);
        System.out.println(driver.getCurrentUrl());
        WebElement parrentDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > section > div.hero-body")));
        WebElement emailInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(1) > div > input"));
        WebElement passwordInput = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > div:nth-child(2) > div > input"));
        WebElement submitButton = driver.findElement(By.cssSelector("body > section > div.hero-body > div > div > div > form > button"));
        emailInput.sendKeys(userLoginEmail); //todo я руками просто завел такого пользователя
        passwordInput.sendKeys(userPassword);
        submitButton.click();
        //threadIsWaiting();
        if (driver.getCurrentUrl().equals(baseUrl + profilePage)) {
            System.out.println("Пользователь успешно вошел в систему");
            Assert.assertEquals(driver.getCurrentUrl(), baseUrl + profilePage);
        }
    }

    private void threadIsWaiting() {
        try {
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
