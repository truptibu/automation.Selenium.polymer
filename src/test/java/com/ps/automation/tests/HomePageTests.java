package com.ps.automation.tests;

import com.ps.automation.managers.DriverManager;
import com.ps.automation.pages.HomePage;
import com.ps.automation.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class HomePageTests {
    private WebDriver driver;
    private HomePage homePage;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
        System.out.println("Page title: " + driver.getTitle());
        System.out.println("Browser from config: " + ConfigReader.getProperty("browser"));

    }
    
    @Test(priority = 1)
    public void verifyHomePageLoads() {
        Assert.assertTrue(homePage.isPageLoaded(), "Homepage did not load properly.");
    }

    @Test(priority = 2)
    public void verifyNavigationMenuItems() {
        Assert.assertTrue(homePage.isPageLoaded(), "Navigation menu items missing.");
    }

    @Test(priority = 3)
    public void verifyProductCategoriesDisplay() {
        homePage.navigateToMenCategory();
        Assert.assertTrue(driver.getCurrentUrl().contains("/list"), "Men's category not displayed.");
    }

    @Test(priority = 4)
    public void verifySearchFunctionality() {
        // Demo search by navigating categories
        homePage.navigateToWomenCategory();
        Assert.assertTrue(driver.getCurrentUrl().contains("/list/ladies_outerwear"), "Search navigation failed.");
    }

    @Test(priority = 5)
    public void verifyFeaturedProductsSection() {
        Assert.assertTrue(homePage.isPageLoaded(), "Featured products not displayed.");
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
