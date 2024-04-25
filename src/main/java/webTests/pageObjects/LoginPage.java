package webTests.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final By emailInput = By.xpath("/html/body/section/div[2]/div/div/div/form/div[1]/div/input");
    private final By passwordInput = By.xpath("/html/body/section/div[2]/div/div/div/form/div[2]/div/input");
    private final By loginButton = By.xpath("/html/body/section/div[2]/div/div/div/form/button");
    private final By checkBox = By.xpath("/html/body/section/div[2]/div/div/div/form/div[3]/label/input");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofMillis(5000));
    }

    public void enterPassword(String password) {
        driver.findElement(passwordInput).sendKeys(password);
    }

    public void enterEmail(String email) {
        driver.findElement(emailInput).sendKeys(email);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void clickCheckbox() {
        driver.findElement(checkBox).click();
    }

    public String getEmailValidationMessage() {
        return driver.findElement(emailInput).getAttribute("validationMessage");
    }

    public boolean isErrorMessageDisplayed() {
        boolean disp = false;
        WebElement errorMessage = null;
        try {
            errorMessage = driver.findElement(By.xpath("/html/body/section/div[2]/div/div/div/div"));
            disp = errorMessage.isDisplayed();
        } catch (NoSuchElementException e) {
            disp = false;
        }
        return disp;
    }

    public boolean isPasswordInputAccessible() {
        return driver.findElement(passwordInput).isEnabled();
    }

    public boolean isEmailInputAccessible() {
        return driver.findElement(emailInput).isEnabled();
    }

    public boolean isLoginButtonEnable() {
        return driver.findElement(loginButton).isEnabled();
    }
}
