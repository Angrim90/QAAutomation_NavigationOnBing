package net.ukr.oleg90.shvets;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

/**
 * @author Shvets Oleg
 * @version 1.0
 */
public  class BaseScript {
    private static String browser = BrowserType.FIREFOX;


    private  static int _case = 0;
    private static int get_case(String browser) {


        if (browser.equals("firefox")) {
            _case = 1;
        }
        return _case;
    }

    public static WebDriver getDriver() {
        switch ( get_case(browser)){
            case 1: {
                System.setProperty("webdriver.gecko.driver", "E:\\Selenium\\FirefoxDriver\\geckodriver.exe");
                return new FirefoxDriver();
            }
            default:{
                System.setProperty("webdriver.chrome.driver", "E:\\Selenium\\ChromeDriver\\chromedriver.exe");

                return new ChromeDriver();
            }
        }

    }
}
