package webTests.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    private WebDriver driver;
    private  WebDriverWait wait;
    private final By emailInput = By.xpath("/html/body/section/div[2]/div/div/div/form/div[1]/div/input");
    private final By passwordInput = By.xpath("/html/body/section/div[2]/div/div/div/form/div[3]/div/input");
    private final By userNameInput = By.xpath("/html/body/section/div[2]/div/div/div/form/div[2]/div/input");
    private final By loginButton = By.xpath("/html/body/section/div[2]/div/div/div/form/button");
    private final By checkBox = By.xpath("/html/body/section/div[2]/div/div/div/form/button");

    public RegisterPage (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofMillis(5000));
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }
    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }
    public void enterUserName(String name) {
        driver.findElement(userNameInput).sendKeys(name);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickCheckbox() {
        driver.findElement(checkBox).click();
    }

    public String getEmailValidationMessage(){
        return driver.findElement(emailInput).getAttribute("validationMessage");
    }
    public String getUserNameValidationMessage(){
        return driver.findElement(userNameInput).getAttribute("validationMessage");
    }
    public String getPasswordValidationMessage(){
        return driver.findElement(passwordInput).getAttribute("validationMessage");
    }

    public String getEmailRequiredAttribute(){
        return driver.findElement(emailInput).getAttribute("required");
    }
    public String getUserNameRequiredAttribute(){
        return driver.findElement(userNameInput).getAttribute("required");
    }
    public String getPasswordRequiredAttribute(){
        return driver.findElement(passwordInput).getAttribute("required");
    }

    public boolean isSignUpButtonEnabled(){
        return driver.findElement(loginButton).isEnabled();
    }

    public boolean isPasswordInputAccessible(){
        return driver.findElement(passwordInput).isEnabled();
    }
    public boolean isEmailInputAccessible(){
        return driver.findElement(emailInput).isEnabled();
    }
    public boolean isNameInputAccessible(){
        return driver.findElement(userNameInput).isEnabled();
    }

}
