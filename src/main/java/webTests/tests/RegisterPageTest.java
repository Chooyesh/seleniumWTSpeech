package webTests.tests;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import webTests.config.Configuration;
import webTests.config.WebDriverInitializer;
import webTests.pageObjects.RegisterPage;
import webTests.pageObjects.TestData;

public class RegisterPageTest {
    private final WebDriverInitializer webDriverInitializer = new WebDriverInitializer();
    private final Configuration config = new Configuration();
    private WebDriver driver;
    private RegisterPage registerPage;
    private final String baseUrl = config.getBaseUrl(); //стартовый урл
    private final String registerPageURL = baseUrl + "/signup"; // signup урл
    private final String loginPageURL = baseUrl + "/login"; // signup урл

    @BeforeClass
    public void setUp() {
        driver = webDriverInitializer.initWebDriver();

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test(dataProvider = "emailsAndExcepts", dataProviderClass = TestData.class)
    public void testWrongEmailInputs(String userLoginEmail, String validMessage) {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        registerPage.enterEmail(userLoginEmail);
        Assert.assertEquals(registerPage.getEmailValidationMessage(), validMessage, "Был отправлен email: " + userLoginEmail);
    }

    @Test
    public void isEmailInputRequired() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.getEmailRequiredAttribute());
        Assert.assertEquals(registerPage.getEmailRequiredAttribute(), "true", "Ожидаем true, так как по условиям поле должно быть required");
    }

    @Test
    public void isUserNameInputRequired() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.getUserNameRequiredAttribute());
        Assert.assertEquals(registerPage.getUserNameRequiredAttribute(), "false", "Ожидаем false, так как по условиям поле не должно быть required");
    }

    @Test
    public void isPasswordInputRequired() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.getPasswordRequiredAttribute());
        Assert.assertEquals(registerPage.getPasswordRequiredAttribute(), "true", "Ожидаем true, так как по условиям поле должно быть required");
    }

    @Test
    public void isButtonEnabled() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.isSignUpButtonEnabled());
        Assert.assertEquals(registerPage.isSignUpButtonEnabled(), false, "Ожидаем false так как поля ввода данных не заполнены и кнопка должна быть выключена");
    }

    @Test
    public void isEmailInputEnabled() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.isEmailInputAccessible());
        Assert.assertEquals(registerPage.isEmailInputAccessible(), true, "Ожидаем true, так как поле Email должно быть доступно для ввода данных");
    }

    @Test
    public void isPasswordInputEnabled() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.isPasswordInputAccessible());
        Assert.assertEquals(registerPage.isPasswordInputAccessible(), true, "Ожидаем true, так как поле Password должно быть доступно для ввода данных");

    }

    @Test
    public void isNameInputEnabled() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.isNameInputAccessible());
        Assert.assertEquals(registerPage.isNameInputAccessible(), true, "Ожидаем true, так как поле Name должно быть доступно для ввода данных");
    }

    @Test(dataProvider = "invalidUsernames", dataProviderClass = TestData.class)
    public void testInvalidUsernames(String userName) { // нет валидации
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        registerPage.enterUserName(userName);
        System.out.println(registerPage.getUserNameValidationMessage());
        Assert.assertNotEquals(registerPage.getUserNameValidationMessage(), "", "Ожидаемое значение не должно быть пустым");
    }

    @Test(dataProvider = "invalidPasswords", dataProviderClass = TestData.class)
    public void testInvalidPasswords(String password) { //Нет валидации некорректных паролей
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        registerPage.enterPassword(password);
        System.out.println(registerPage.getPasswordValidationMessage());
        Assert.assertNotEquals(registerPage.getPasswordValidationMessage(), "", "Ожидаемое значение не должно быть пустым");
    }

    @Test
    public void tryToRegisterWithEmptyInputs() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        registerPage.clickLoginButton();
        Assert.assertEquals(driver.getCurrentUrl(), registerPageURL, "Ожидаем что драйвер останется на странице " + registerPageURL + " так как поля не заполнены");
    }

    @Test(dataProvider = "goodDataToSignUp", dataProviderClass = TestData.class)
    public void expectedSuccessfulSignUp(String email, String name, String password) {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        registerPage.enterEmail(email);
        registerPage.enterUserName(name);
        registerPage.enterPassword(password);
        registerPage.clickLoginButton();
        Assert.assertEquals(driver.getCurrentUrl(), loginPageURL, "Ожидаем, что дравер перейдет на страницу " + loginPageURL + " после успешной регистрации"); //todo
    }


    //нет данных о том, что в БД есть пустышка для проверки, пгоэтому использую тестовые данные использованные для успешной регистрации, насколько я знаю так делать не реклмендуется
    @Test(dataProvider = "alreadySignUp", dataProviderClass = TestData.class)
    //нет данных о том, что в БД есть пустышка для проверки, пгоэтому использую тестовые данные использованные для успешной регистрации
    public void zExpectedErrorAlreadyRegistered(String email, String name, String password) {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        registerPage.enterEmail(email);
        registerPage.enterUserName(name);
        registerPage.enterPassword(password);
        registerPage.clickLoginButton();
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Ожидаем true, так как пользователь уже зарегистрирован");
    }


}
