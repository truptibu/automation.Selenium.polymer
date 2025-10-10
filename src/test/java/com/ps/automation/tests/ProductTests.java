package com.ps.automation.tests;

import com.ps.automation.managers.DriverManager;
import com.ps.automation.pages.HomePage;
import com.ps.automation.pages.ProductListingPage;
import com.ps.automation.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.List;

public class ProductTests {
    private WebDriver driver;
    private HomePage homePage;
    private ProductListingPage listingPage;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
        listingPage = new ProductListingPage(driver);
    }

    @Test(priority = 1)
    public void verifyProductGridDisplay() {
        homePage.navigateToMenCategory();
        // Wait for navigation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int productCount = listingPage.getProductCount();
        Assert.assertTrue(productCount > 0, 
            "Product grid should display items. Found: " + productCount);
    }

    @Test(priority = 2)
    public void verifyMensOuterwearCategory() {
        homePage.navigateToMenCategory();
        String categoryTitle = listingPage.getCategoryTitle();
        Assert.assertEquals(categoryTitle, "Men's Outerwear", 
            "Category title should match");
    }

    @Test(priority = 3)
    public void verifyLadiesOuterwearCategory() {
        homePage.navigateToWomenCategory();
        String categoryTitle = listingPage.getCategoryTitle();
        Assert.assertEquals(categoryTitle, "Ladies Outerwear", 
            "Category title should match");
    }

    @Test(priority = 4)
    public void verifyProductTitlesNotEmpty() {
        homePage.navigateToMenCategory();
        // Wait for navigation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> productTitles = listingPage.getProductTitles();
        Assert.assertFalse(productTitles.isEmpty(), 
            "Product titles list should not be empty");
        System.out.println("Found " + productTitles.size() + " product titles");
    }

    @Test(priority = 5)
    public void verifyProductSelection() {
        homePage.navigateToMenCategory();
        // Wait for navigation
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int count = listingPage.getProductCount();
        Assert.assertTrue(count > 0, "Products should be available");
        
        if (count > 0) {
            listingPage.selectProduct(0);
            Assert.assertTrue(listingPage.waitForUrlContains("/detail"), 
                "Should navigate to product detail page");
        }
    }
}

