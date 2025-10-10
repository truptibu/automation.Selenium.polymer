package com.ps.automation.tests;

import com.ps.automation.managers.DriverManager;
import com.ps.automation.pages.HomePage;
import com.ps.automation.pages.ProductListingPage;
import com.ps.automation.pages.ProductDetailPage;
import com.ps.automation.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class ProductDetailTests {
    private WebDriver driver;
    private HomePage homePage;
    private ProductListingPage listingPage;
    private ProductDetailPage detailPage;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        homePage = new HomePage(driver);
        listingPage = new ProductListingPage(driver);
        detailPage = new ProductDetailPage(driver);
    }

    private void navigateToProductDetail() {
        homePage.navigateToMenCategory();
        sleep(2000);
        listingPage.selectProduct(0);
        detailPage.waitForDetailPageLoad();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test(priority = 1, description = "Verify product detail page loads successfully")
    public void verifyProductDetailPageLoad() {
        homePage.navigateToMenCategory();
        sleep(2000);
        listingPage.selectProduct(0);
        
        Assert.assertTrue(detailPage.waitForDetailPageLoad(), "Product detail page should load");
        System.out.println("✓ Product detail page loaded successfully");
    }

    @Test(priority = 2, description = "Verify product title and price are displayed")
    public void verifyProductTitleAndPrice() {
        navigateToProductDetail();
        
        String title = detailPage.getProductTitle();
        String price = detailPage.getProductPrice();
        
        Assert.assertFalse(title.isEmpty(), "Product title should be displayed");
        Assert.assertFalse(price.isEmpty(), "Product price should be displayed");
        
        System.out.println("✓ Product Title: " + title);
        System.out.println("✓ Product Price: " + price);
    }

    @Test(priority = 3, description = "Verify product image is displayed")
    public void verifyProductImage() {
        navigateToProductDetail();
        
        Assert.assertTrue(detailPage.isProductImageDisplayed(), "Product image should be displayed");
        System.out.println("✓ Product image is displayed");
    }

    @Test(priority = 4, description = "Verify size and quantity selection dropdowns")
    public void verifySizeAndQuantityDropdowns() {
        navigateToProductDetail();
        
        Assert.assertTrue(detailPage.isSizeDropdownDisplayed(), "Size dropdown should be displayed");
        Assert.assertTrue(detailPage.isQuantityDropdownDisplayed(), "Quantity dropdown should be displayed");
        
        System.out.println("✓ Size dropdown is displayed");
        System.out.println("✓ Quantity dropdown is displayed");
    }

    @Test(priority = 5, description = "Verify add to cart functionality")
    public void verifyAddToCartFunctionality() {
        navigateToProductDetail();
        
        Assert.assertTrue(detailPage.isAddToCartButtonDisplayed(), "Add to cart button should be displayed");
        
        int cartCountBefore = detailPage.getCartItemCount();
        System.out.println("Cart count before: " + cartCountBefore);
        
        detailPage.selectSize("XS");
        detailPage.selectQuantity("1");
        detailPage.clickAddToCart();
        
        int cartCountAfter = detailPage.getCartItemCount();
        System.out.println("Cart count after: " + cartCountAfter);
        
        Assert.assertTrue(cartCountAfter > cartCountBefore, "Cart item count should increase after adding product");
        System.out.println("✓ Add to cart functionality working correctly");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverManager.quitDriver();
        }
    }
}
