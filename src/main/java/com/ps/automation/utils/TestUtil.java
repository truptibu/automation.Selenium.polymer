package com.ps.automation.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.io.FileHandler;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestUtil {
    public static void captureScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            FileHandler.copy(src, new File(ConfigReader.getProperty("screenshot.path") + testName + "_" + timestamp + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
