//package com.ps.automation.pages;
//
//import java.time.Duration;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.SearchContext;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//public class HomePage {
//    private WebDriver driver;
//
//    public HomePage(WebDriver driver) {
//        this.driver = driver;
//    }
//
//
//    /**
//     * Get the root shadow DOM of <shop-app>
//     */
//    private SearchContext getShopAppShadowRoot() {
//        WebElement shopApp = driver.findElement(By.cssSelector("shop-app"));
//        return shopApp.getShadowRoot();
//    }
//
//    /**
//     * Check if homepage loaded by verifying the toolbar is present
//     */
//    public boolean isPageLoaded() {
//        try {
//            SearchContext shopAppRoot = getShopAppShadowRoot();
//            WebElement toolbar = shopAppRoot.findElement(By.cssSelector("app-toolbar"));
//            return toolbar.isDisplayed();
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * Navigate to Men’s category
//     */
//    public void navigateToMenCategory() {
//        SearchContext shopAppRoot = getShopAppShadowRoot();
//        WebElement shopHome = shopAppRoot.findElement(By.cssSelector("shop-home"));
//        SearchContext shopHomeRoot = shopHome.getShadowRoot();
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement menLink = wait.until(driver -> {
//            try {
//                return shopHomeRoot.findElement(By.cssSelector("a[href*='mens_outerwear']"));
//            } catch (Exception e) {
//                return null;
//            }
//        });
//
//        if (menLink != null) {
//            menLink.click();
//        } else {
//            throw new RuntimeException("Men's category link not found in <shop-home>");
//        }
//    }
//
//
//    /**
//     * Navigate to Women’s category
//     */
//    public void navigateToWomenCategory() {
//        SearchContext shopAppRoot = getShopAppShadowRoot();
//        WebElement shopHome = shopAppRoot.findElement(By.cssSelector("shop-home"));
//        SearchContext shopHomeRoot = shopHome.getShadowRoot();
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement womenLink = wait.until(driver -> {
//            try {
//                return shopHomeRoot.findElement(By.cssSelector("a[href*='ladies_outerwear']"));
//            } catch (Exception e) {
//                return null;
//            }
//        });
//
//        if (womenLink != null) {
//            womenLink.click();
//        } else {
//            throw new RuntimeException("Women's category link not found in <shop-home>");
//        }
//    }
//
//
//    public void openCart() {
//        SearchContext shopAppRoot = getShopAppShadowRoot();
//        WebElement header = shopAppRoot.findElement(By.cssSelector("shop-header"));
//        SearchContext headerRoot = header.getShadowRoot();
//
//        // find the cart icon button
//        WebElement cartButton = headerRoot.findElement(By.cssSelector("a[href='/cart']"));
//        cartButton.click();
//    }
//
//}

package com.ps.automation.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private SearchContext getShopAppShadowRoot() {
        try {
            // Wait for page load
            Thread.sleep(2000);

            // Wait for shop-app and get its shadow root
            WebElement shopApp = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("shop-app")));
            return wait.until(driver -> {
                try {
                    return shopApp.getShadowRoot();
                } catch (Exception e) {
                    return null;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException("Failed to get shop-app shadow root: " + e.getMessage());
        }
    }

    public void navigateToMenCategory() {
        try {
            SearchContext shopAppRoot = getShopAppShadowRoot();
            
            // Wait for shop-home
            WebElement shopHome = wait.until(driver -> {
                try {
                    return (WebElement) shopAppRoot.findElement(By.cssSelector("shop-home"));
                } catch (Exception e) {
                    return null;
                }
            });

            SearchContext shopHomeRoot = shopHome.getShadowRoot();

            // Find and click men's category link
            WebElement menLink = wait.until(driver -> {
                try {
                    return (WebElement) shopHomeRoot.findElement(By.cssSelector("a[href='/list/mens_outerwear']"));
                } catch (Exception e) {
                    return null;
                }
            });

            menLink.click();

            // Wait for navigation
            wait.until(ExpectedConditions.urlContains("mens_outerwear"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to men's category: " + e.getMessage());
        }
    }

    public void navigateToWomenCategory() {
        try {
            SearchContext shopAppRoot = getShopAppShadowRoot();
            
            // Wait for shop-home
            WebElement shopHome = wait.until(driver -> {
                try {
                    return (WebElement) shopAppRoot.findElement(By.cssSelector("shop-home"));
                } catch (Exception e) {
                    return null;
                }
            });

            SearchContext shopHomeRoot = shopHome.getShadowRoot();

            // Find and click women's category link
            WebElement womenLink = wait.until(driver -> {
                try {
                    return (WebElement) shopHomeRoot.findElement(By.cssSelector("a[href='/list/ladies_outerwear']"));
                } catch (Exception e) {
                    return null;
                }
            });

            womenLink.click();

            // Wait for navigation
            wait.until(ExpectedConditions.urlContains("ladies_outerwear"));
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to women's category: " + e.getMessage());
        }
    }

    public boolean isPageLoaded() {
        try {
            SearchContext shopAppRoot = getShopAppShadowRoot();
            WebElement toolbar = wait.until(driver -> {
                try {
                    return (WebElement) shopAppRoot.findElement(By.cssSelector("app-toolbar"));
                } catch (Exception e) {
                    return null;
                }
            });
            return toolbar.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

