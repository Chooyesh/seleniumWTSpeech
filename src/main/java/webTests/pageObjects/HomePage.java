package webTests.pageObjects;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class HomePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private final By homeButton = By.xpath("//*[@id=\"navbarMenuHeroA\"]/div/a[1]");
    private final By loginButton = By.xpath("//*[@id=\"navbarMenuHeroA\"]/div/a[2]");
    private final By signUpButton = By.xpath("//*[@id=\"navbarMenuHeroA\"]/div/a[3]");



    public HomePage (WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofMillis(5000));
    }

    public boolean isHomeButtonIsDisplayed(){
        WebElement navBar =driver.findElement(homeButton);
       // WebElement navBar =wait.until(ExpectedConditions.visibilityOfElementLocated(homeButton));
       return navBar.isDisplayed();
    }
    public boolean isLoginButtonIsDisplayed(){
        WebElement navBar =driver.findElement(loginButton);
        //WebElement navBar =wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton));
        return navBar.isDisplayed();
    }
    public boolean isSignUpButtonIsDisplayed(){
        WebElement navBar =driver.findElement(signUpButton);
        //WebElement navBar =wait.until(ExpectedConditions.visibilityOfElementLocated(signUpButton));
        return navBar.isDisplayed();
    }


}
