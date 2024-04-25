package webTests.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WebDriverInitializer {

    public WebDriverWait initWebdriverWait (WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000));
        return wait;
    }
    public WebDriver initWebDriver() {
        WebDriver driver;
        WebDriverWait wait;
        System.setProperty("webdriver.chrome.driver", "D:\\Programming\\webdriver\\chromedriver.exe"); // путь к драйверу
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1500,1200");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--silent");
        options.addArguments("--remote-debugging-port=9222");
        options.setBinary("D:\\Programming\\webdriver\\chrome-win64\\chrome.exe"); // путь  к браузеру
        driver = new ChromeDriver(options);
        return driver;
    }
}
