package com.ps.automation.pages;

import com.ps.automation.utils.ConfigReader;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;

        // Read wait times from config.properties
        int explicitWait = Integer.parseInt(ConfigReader.getProperty("explicit.wait"));
        int implicitWait = Integer.parseInt(ConfigReader.getProperty("implicit.wait"));
        int pageLoadTimeout = Integer.parseInt(ConfigReader.getProperty("page.load.timeout"));

        // Apply waits
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));

        // Initialize @FindBy elements
        PageFactory.initElements(driver, this);
    }

    /** Safe Click with Explicit Wait */
    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    /** Safe Typing with Wait */
    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /** Get visible text safely */
    protected String getText(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element.getText();
    }

    /** Check if element is visible */
    protected boolean isDisplayed(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Wait for URL to contain text */
    public boolean waitForUrlContains(String keyword) {
        try {
            return wait.until(ExpectedConditions.urlContains(keyword));
        } catch (TimeoutException e) {
            return false;
        }
    }

    /** Wait for page title */
    protected boolean waitForTitleContains(String titlePart) {
        try {
            return wait.until(ExpectedConditions.titleContains(titlePart));
        } catch (TimeoutException e) {
            return false;
        }
    }
}
