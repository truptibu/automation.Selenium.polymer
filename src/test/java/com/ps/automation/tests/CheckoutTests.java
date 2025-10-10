//package com.ps.automation.tests;
//
//import com.ps.automation.managers.DriverManager;
//import com.ps.automation.pages.HomePage;
//import com.ps.automation.pages.ProductListingPage;
//import com.ps.automation.pages.ProductDetailPage;
//import com.ps.automation.pages.CartPage;
//import com.ps.automation.pages.CheckoutPage;
//import com.ps.automation.utils.ConfigReader;
//import org.openqa.selenium.JavascriptExecutor;
//import org.openqa.selenium.WebDriver;
//import org.testng.Assert;
//import org.testng.annotations.*;
//
//public class CheckoutTests {
//    private WebDriver driver;
//    private HomePage homePage;
//    private ProductListingPage listingPage;
//    private ProductDetailPage detailPage;
//    private CartPage cartPage;
//    private CheckoutPage checkoutPage;
//
//    @BeforeMethod
//    public void setup() {
//        driver = DriverManager.getDriver();
//        driver.get(ConfigReader.getProperty("url"));
//        waitForPageLoad();
//        homePage = new HomePage(driver);
//        listingPage = new ProductListingPage(driver);
//        detailPage = new ProductDetailPage(driver);
//        cartPage = new CartPage(driver);
//        checkoutPage = new CheckoutPage(driver);
//    }
//
//    private void waitForPageLoad() {
//        sleep(5000);
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        for (int i = 0; i < 10; i++) {
//            try {
//                Boolean ready = (Boolean) js.executeScript(
//                    "let app = document.querySelector('shop-app');" +
//                    "return app && app.shadowRoot !== null;");
//                if (ready != null && ready) {
//                    sleep(1000);
//                    break;
//                }
//                sleep(500);
//            } catch (Exception e) {
//                sleep(500);
//            }
//        }
//    }
//
//    private void sleep(int millis) {
//        try {
//            Thread.sleep(millis);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    private void addProductToCart() {
//        homePage.navigateToMenCategory();
//        sleep(2000);
//        listingPage.selectProduct(0);
//        detailPage.waitForDetailPageLoad();
//        detailPage.selectSize("XS");
//        detailPage.selectQuantity("1");
//        detailPage.clickAddToCart();
//    }
//
//    @Test(priority = 1, description = "Verify checkout navigation functionality")
//    public void verifyCheckoutNavigation() {
//        addProductToCart();
//        sleep(2000);
//        
//        checkoutPage.navigateToCheckout();
//        
//        Assert.assertTrue(checkoutPage.isOnCheckoutPage(), "Should navigate to checkout page");
//        System.out.println("✓ Checkout navigation working correctly");
//    }
//
//    @Test(priority = 2, description = "Verify checkout page loads successfully")
//    public void verifyCheckoutPageLoads() {
//        checkoutPage.navigateToCheckout();
//        sleep(2000);
//        
//        Assert.assertTrue(checkoutPage.isOnCheckoutPage(), "Checkout page should load");
//        System.out.println("✓ Checkout page loads successfully");
//    }
//
//    @Test(priority = 3, description = "Verify checkout form is present")
//    public void verifyCheckoutFormPresent() {
//        addProductToCart();
//        sleep(2000);
//        
//        checkoutPage.navigateToCheckout();
//        
//        Assert.assertTrue(checkoutPage.isCheckoutFormDisplayed(), "Checkout form should be present");
//        System.out.println("✓ Checkout form is present");
//    }
//
//    @Test(priority = 4, description = "Verify checkout page accessible with cart items")
//    public void verifyCheckoutAccessibleWithItems() {
//        int cartCountBefore = cartPage.getCartItemCount();
//        System.out.println("Cart count before: " + cartCountBefore);
//        
//        addProductToCart();
//        sleep(2000);
//        
//        int cartCountAfter = cartPage.getCartItemCount();
//        System.out.println("Cart count after: " + cartCountAfter);
//        
//        Assert.assertTrue(cartCountAfter > cartCountBefore, "Cart should have items");
//        
//        checkoutPage.navigateToCheckout();
//        Assert.assertTrue(checkoutPage.isOnCheckoutPage(), "Should access checkout with items in cart");
//        
//        System.out.println("✓ Checkout accessible with cart items");
//    }
//
//    @Test(priority = 5, description = "Verify checkout URL is correct")
//    public void verifyCheckoutURL() {
//        checkoutPage.navigateToCheckout();
//        sleep(2000);
//        
//        String currentUrl = driver.getCurrentUrl();
//        System.out.println("Current URL: " + currentUrl);
//        
//        Assert.assertTrue(currentUrl.contains("/checkout"), "URL should contain /checkout");
//        System.out.println("✓ Checkout URL is correct");
//    }
//
//    @AfterMethod
//    public void tearDown() {
//        if (driver != null) {
//            DriverManager.quitDriver();
//        }
//    }
//}

package com.ps.automation.tests;

import com.ps.automation.managers.DriverManager;
import com.ps.automation.pages.HomePage;
import com.ps.automation.pages.ProductListingPage;
import com.ps.automation.pages.ProductDetailPage;
import com.ps.automation.pages.CartPage;
import com.ps.automation.pages.CheckoutPage;
import com.ps.automation.utils.ConfigReader;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class CheckoutTests {
    private WebDriver driver;
    private HomePage homePage;
    private ProductListingPage listingPage;
    private ProductDetailPage detailPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod
    public void setup() {
        driver = DriverManager.getDriver();
        driver.get(ConfigReader.getProperty("url"));
        waitForPageLoad();
        homePage = new HomePage(driver);
        listingPage = new ProductListingPage(driver);
        detailPage = new ProductDetailPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    private void waitForPageLoad() {
        sleep(5000);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 0; i < 10; i++) {
            try {
                Boolean ready = (Boolean) js.executeScript(
                    "let app = document.querySelector('shop-app');" +
                    "return app && app.shadowRoot !== null;");
                if (ready != null && ready) {
                    sleep(1000);
                    break;
                }
                sleep(500);
            } catch (Exception e) {
                sleep(500);
            }
        }
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void addProductToCart() {
        homePage.navigateToMenCategory();
        sleep(2000);
        listingPage.selectProduct(0);
        detailPage.waitForDetailPageLoad();
        detailPage.selectSize("XS");
        detailPage.selectQuantity("1");
        detailPage.clickAddToCart();
    }

    private void navigateAndVerifyCheckout(String message) {
        checkoutPage.navigateToCheckout();
        sleep(2000);
        Assert.assertTrue(checkoutPage.isOnCheckoutPage(), "Should navigate to checkout page");
        System.out.println("✓ " + message);
    }

    @Test(priority = 1, description = "Verify checkout navigation functionality")
    public void verifyCheckoutNavigation() {
        addProductToCart();
        sleep(2000);
        navigateAndVerifyCheckout("Checkout navigation working correctly");
    }

    @Test(priority = 2, description = "Verify checkout page loads successfully")
    public void verifyCheckoutPageLoads() {
        navigateAndVerifyCheckout("Checkout page loads successfully");
    }

    @Test(priority = 3, description = "Verify checkout form is present")
    public void verifyCheckoutFormPresent() {
        addProductToCart();
        sleep(2000);
        checkoutPage.navigateToCheckout();
        Assert.assertTrue(checkoutPage.isCheckoutFormDisplayed(), "Checkout form should be present");
        System.out.println("✓ Checkout form is present");
    }

    @Test(priority = 4, description = "Verify checkout page accessible with cart items")
    public void verifyCheckoutAccessibleWithItems() {
        int cartCountBefore = cartPage.getCartItemCount();
        addProductToCart();
        sleep(2000);
        int cartCountAfter = cartPage.getCartItemCount();
        
        System.out.println("Cart count before: " + cartCountBefore);
        System.out.println("Cart count after: " + cartCountAfter);
        Assert.assertTrue(cartCountAfter > cartCountBefore, "Cart should have items");
        
        checkoutPage.navigateToCheckout();
        Assert.assertTrue(checkoutPage.isOnCheckoutPage(), "Should access checkout with items in cart");
        System.out.println("✓ Checkout accessible with cart items");
    }

    @Test(priority = 5, description = "Verify checkout URL is correct")
    public void verifyCheckoutURL() {
        checkoutPage.navigateToCheckout();
        sleep(2000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL: " + currentUrl);
        Assert.assertTrue(currentUrl.contains("/checkout"), "URL should contain /checkout");
        System.out.println("✓ Checkout URL is correct");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            DriverManager.quitDriver();
        }
    }
}

