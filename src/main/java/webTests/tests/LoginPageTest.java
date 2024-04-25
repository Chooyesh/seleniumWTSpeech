package webTests.tests;

import org.openqa.selenium.WebDriver;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import webTests.config.Configuration;
import webTests.config.WebDriverInitializer;
import webTests.pageObjects.LoginPage;
import webTests.pageObjects.TestData;

public class LoginPageTest {
    private final WebDriverInitializer webDriverInitializer = new WebDriverInitializer();
    private final Configuration config = new Configuration();
    private WebDriver driver;
    private LoginPage loginPage;
    private final String baseUrl = config.getBaseUrl(); //стартовый урл
    private final String loginPageURL = baseUrl + "/login"; //логин урл
    private final String profilePageURl = baseUrl + "/profile"; //логин урл

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
        driver.get(loginPageURL);
        loginPage = new LoginPage(driver);
        loginPage.enterEmail(userLoginEmail);
        Assert.assertEquals(loginPage.getEmailValidationMessage(), validMessage, "Был отправлен email: " + userLoginEmail);
    }

    @Test(dataProvider = "loginData", dataProviderClass = TestData.class)
    public void expectedSuccessLogin(String userLoginEmail, String password) {
        driver.get(loginPageURL);
        loginPage = new LoginPage(driver);
        loginPage.enterEmail(userLoginEmail);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        Assert.assertEquals(driver.getCurrentUrl(), profilePageURl, "Был отправлен email: " + userLoginEmail + " Пароль " + password);
    }

    @Test(dataProvider = "wrongLoginData", dataProviderClass = TestData.class)
    public void expectedWrongLogin(String userLoginEmail, String password) {
        driver.get(loginPageURL);
        loginPage = new LoginPage(driver);
        loginPage.enterEmail(userLoginEmail);
        loginPage.enterPassword(password);
        loginPage.clickLoginButton();
        Assert.assertEquals(driver.getCurrentUrl(), loginPageURL, "Был отправлен email: " + userLoginEmail + " Пароль " + password);
    }


    @Test
    public void isEmailInputEnabled(){
        driver.get(loginPageURL);
        loginPage = new LoginPage(driver);
        System.out.println(loginPage.isEmailInputAccessible());
        Assert.assertTrue(loginPage.isEmailInputAccessible(), "Ожидаем true, так как поле Email должно быть доступно для ввода данных");
    }
    @Test
    public void isPasswordInputEnabled(){
        driver.get(loginPageURL);
        loginPage = new LoginPage(driver);
        System.out.println(loginPage.isPasswordInputAccessible());
        Assert.assertTrue(loginPage.isPasswordInputAccessible(), "Ожидаем true, так как поле Password должно быть доступно для ввода данных");
    }

    @Test
    public void isLoginButtonEnabled() {
        driver.get(loginPageURL);
        loginPage = new LoginPage(driver);
        System.out.println(loginPage.isLoginButtonEnable());
        Assert.assertFalse(loginPage.isLoginButtonEnable(), "Ожидаем false, так как поле Password и email не были заполнены");
    }
}
