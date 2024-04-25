package webTests.tests;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import webTests.config.Configuration;
import webTests.config.WebDriverInitializer;
import webTests.pageObjects.HomePage;

public class HomePageTest {
    private final WebDriverInitializer webDriverInitializer = new WebDriverInitializer();
    private final Configuration config = new Configuration();
    private WebDriver driver;
    private final String baseUrl = config.getBaseUrl(); // стартовый урл

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
