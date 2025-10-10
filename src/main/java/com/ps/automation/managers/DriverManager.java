//package com.ps.automation.managers;
//
//import com.ps.automation.utils.ConfigReader;
//import io.github.bonigarcia.wdm.WebDriverManager;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;
//
//public class DriverManager {
//    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
//
//    public static WebDriver getDriver() {
//        if (driver.get() == null) {
//            String browser = ConfigReader.getProperty("browser");
//
//            if (browser.equalsIgnoreCase("chrome")) {
//                WebDriverManager.chromedriver().setup();
//                driver.set(new ChromeDriver());
//            } else if (browser.equalsIgnoreCase("firefox")) {
//                WebDriverManager.firefoxdriver().setup();
//                driver.set(new FirefoxDriver());
//            } else {
//                throw new IllegalArgumentException("Browser not supported: " + browser);
//            }
//
//            driver.get().manage().window().maximize();
//        }
//        return driver.get();
//    }
//
//    public static void quitDriver() {
//        if (driver.get() != null) {
//            driver.get().quit();
//            driver.remove();
//        }
//    }
//}
//
package com.ps.automation.managers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            // Initialize WebDriver here (no external WebDriverFactory needed)
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            driver.set(new ChromeDriver(options));
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
