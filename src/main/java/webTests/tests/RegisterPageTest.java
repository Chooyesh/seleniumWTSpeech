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
    private final String registerPageURL =baseUrl+ "/signup"; // signup урл
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
        Assert.assertEquals(registerPage.getEmailValidationMessage(),validMessage, "Был отправлен email: "+userLoginEmail);
    }

    @Test
    public void isEmailInputRequired() {
        driver.get(registerPageURL);
         registerPage = new RegisterPage(driver);
        System.out.println(registerPage.getEmailRequiredAttribute());
        Assert.assertEquals(registerPage.getEmailRequiredAttribute(),"true", "Ожидаем true, так как по условиям поле должно быть required");
    }
    @Test
    public void isUserNameInputRequired() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.getUserNameRequiredAttribute());
        Assert.assertEquals(registerPage.getUserNameRequiredAttribute(),"false", "Ожидаем false, так как по условиям поле не должно быть required");
    }
    @Test
    public void isPasswordInputRequired() {
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.getEmailRequiredAttribute());
        Assert.assertEquals(registerPage.getEmailRequiredAttribute(),"true", "Ожидаем true, так как по условиям поле должно быть required");
    }

    @Test
    public void isButtonEnabled(){
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.isSignUpButtonEnabled());
        Assert.assertEquals(registerPage.isSignUpButtonEnabled(), false, "Ожидаем false так как поля ввода данных не заполнены и кнопка должна быть выключена");
    }

    @Test
    public void isEmailInputEnabled(){
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.isEmailInputAccessible());
        Assert.assertEquals(registerPage.isEmailInputAccessible(), true, "Ожидаем true, так как поле Email должно быть доступно для ввода данных");
    }
    @Test
    public void isPasswordInputEnabled(){
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.isPasswordInputAccessible());
        Assert.assertEquals(registerPage.isPasswordInputAccessible(), true, "Ожидаем true, так как поле Password должно быть доступно для ввода данных");

    }
    @Test
    public void isNameInputEnabled(){
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        System.out.println(registerPage.isNameInputAccessible());
        Assert.assertEquals(registerPage.isNameInputAccessible(), true, "Ожидаем true, так как поле Name должно быть доступно для ввода данных");
    }
    @Test(dataProvider = "invalidUsernames", dataProviderClass = TestData.class)
    public void testInvalidUsernames(String userName){
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        registerPage.enterUserName(userName);
        System.out.println(registerPage.getUserNameValidationMessage());

        Assert.assertNotEquals(registerPage.getUserNameValidationMessage(),"","Ожидаемое значение не должно быть пустым"); //todo
    }
    @Test(dataProvider = "invalidUsernames", dataProviderClass = TestData.class)
    public void testInvalidPasswords(String userName){
        driver.get(registerPageURL);
        registerPage = new RegisterPage(driver);
        registerPage.enterUserName(userName);
        System.out.println(registerPage.getUserNameValidationMessage());
        //Assert.assertEquals(registerPage.isNameInputAccessible(), true, "Ожидаем true, так как поле Name должно быть доступно для ввода данных");
    }

}
