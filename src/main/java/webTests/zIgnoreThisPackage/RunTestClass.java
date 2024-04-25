package webTests.zIgnoreThisPackage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import webTests.config.WebDriverInitializer;
import webTests.pageObjects.HomePage;

public class RunTestClass {

    private WebDriver driver;
    JavascriptExecutor jsexecutor = (JavascriptExecutor) driver;
    WebDriverWait wait;
    //private String baseUrl = "http://localhost:5000"; //todo раскомментировать
    private String baseUrl = "http://213.171.5.82/"; //урл сервака
    private long waitTime = 500; //Изменить, если хотим наблюдать процесс
    private String loginPage = "/login";
    private String profilePage = "/profile";
    private String signUpPage = "/signup";
    private final String userLoginEmail = "email@ejao.ru";
    private final String userPassword = "ejao";
    Logger LOG = LoggerFactory.getLogger(WebTest.class);
    WebDriverInitializer webDriverInitial;
    @BeforeClass
    public void setUp() {
        driver = webDriverInitial.initWebDriver();

    }
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    public void homePageTestHomeButon(){
        driver.get(baseUrl);
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomeButtonIsDisplayed(),"Ожидаем true так как кнопка Home должен быть отображен");
    }
    @Test
    public void homePageTestLoginButton(){
        driver.get(baseUrl);
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isLoginButtonIsDisplayed(),"Ожидаем true так как кнопка Login должен быть отображен");
    }
    @Test
    public void homePageTestSignUpButton(){
        driver.get(baseUrl);
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isSignUpButtonIsDisplayed(),"Ожидаем true так как кнопка Sign Up должен быть отображен");
    }





}
