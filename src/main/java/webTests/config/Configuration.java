package webTests.config;

public class Configuration {

    //private String BASE_URL = "http://localhost:5000";
    private static final String BASE_URL = "http://213.171.5.82";
    private static final String PATH_TO_CHROME = "D:\\Programming\\webdriver\\chrome-win64\\chrome.exe";
    private static final String PATH_TO_CHROMEDRIVER =  "D:\\Programming\\webdriver\\chromedriver.exe";

    public String getBaseUrl(){
        return BASE_URL;
    }

    public String getPathToChrome(){
        return PATH_TO_CHROME;
    }

    public  String getPathToChromedriver() {
        return PATH_TO_CHROMEDRIVER;
    }
}
