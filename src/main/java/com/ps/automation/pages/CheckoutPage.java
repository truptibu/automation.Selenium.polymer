package com.ps.automation.pages;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private String getBaseScript() {
        return "let app = document.querySelector('shop-app');" +
               "if (!app?.shadowRoot) return null;";
    }

    public void navigateToCheckout() {
        try {
            js.executeScript("window.location.href = '/checkout';");
            sleep(2000);
            System.out.println("✓ Navigated to checkout page");
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to checkout: " + e.getMessage());
        }
    }

    public boolean isCheckoutButtonDisplayed() {
        try {
            sleep(2000);
            Boolean displayed = (Boolean) js.executeScript(
                getBaseScript() +
                "let cartModal = app.shadowRoot.querySelector('shop-cart-modal');" +
                "if (cartModal?.shadowRoot) {" +
                "  let checkoutBtn = cartModal.shadowRoot.querySelector('a[href=\"/checkout\"], button');" +
                "  return checkoutBtn !== null;" +
                "}" +
                "return false;");
            
            return displayed != null && displayed;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickCheckoutButton() {
        try {
            sleep(2000);
            Boolean clicked = (Boolean) js.executeScript(
                getBaseScript() +
                "let cartModal = app.shadowRoot.querySelector('shop-cart-modal');" +
                "if (cartModal?.shadowRoot) {" +
                "  let checkoutBtn = cartModal.shadowRoot.querySelector('a[href=\"/checkout\"]');" +
                "  if (checkoutBtn) { checkoutBtn.click(); return true; }" +
                "}" +
                "return false;");
            
            if (clicked == null || !clicked) {
                throw new RuntimeException("Checkout button not found");
            }
            
            sleep(2000);
            System.out.println("✓ Checkout button clicked");
        } catch (Exception e) {
            throw new RuntimeException("Failed to click checkout button: " + e.getMessage());
        }
    }

    public boolean isOnCheckoutPage() {
        try {
            sleep(1000);
            return driver.getCurrentUrl().contains("/checkout");
        } catch (Exception e) {
            return false;
        }
    }

    public int getCheckoutItemCount() {
        try {
            sleep(1000);
            Long count = (Long) js.executeScript(
                getBaseScript() +
                "let ironPages = app.shadowRoot.querySelector('iron-pages');" +
                "if (ironPages) {" +
                "  let checkout = ironPages.querySelector('shop-checkout');" +
                "  if (checkout?.shadowRoot) {" +
                "    let items = checkout.shadowRoot.querySelectorAll('shop-cart-item');" +
                "    return items.length;" +
                "  }" +
                "}" +
                "return 0;");
            
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isOrderSummaryDisplayed() {
        try {
            sleep(2000);
            Boolean displayed = (Boolean) js.executeScript(
                getBaseScript() +
                "let ironPages = app.shadowRoot.querySelector('iron-pages');" +
                "if (ironPages) {" +
                "  let checkout = ironPages.querySelector('shop-checkout');" +
                "  if (checkout?.shadowRoot) {" +
                "    let items = checkout.shadowRoot.querySelectorAll('shop-cart-item');" +
                "    return items.length > 0;" +
                "  }" +
                "}" +
                "return false;");
            
            return displayed != null && displayed;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCheckoutPageTitle() {
        try {
            sleep(1000);
            String title = (String) js.executeScript(
                getBaseScript() +
                "let ironPages = app.shadowRoot.querySelector('iron-pages');" +
                "if (ironPages) {" +
                "  let checkout = ironPages.querySelector('shop-checkout');" +
                "  if (checkout?.shadowRoot) {" +
                "    let header = checkout.shadowRoot.querySelector('header, h1, .title');" +
                "    return header ? header.textContent.trim() : '';" +
                "  }" +
                "}" +
                "return '';");
            
            return title != null ? title : "";
        } catch (Exception e) {
            return "";
        }
    }

    public boolean isCheckoutFormDisplayed() {
        try {
            sleep(2000);
            Boolean displayed = (Boolean) js.executeScript(
                getBaseScript() +
                "let ironPages = app.shadowRoot.querySelector('iron-pages');" +
                "if (ironPages) {" +
                "  let checkout = ironPages.querySelector('shop-checkout');" +
                "  if (checkout?.shadowRoot) {" +
                "    let form = checkout.shadowRoot.querySelector('form, iron-form');" +
                "    return form !== null;" +
                "  }" +
                "}" +
                "return false;");
            
            return displayed != null && displayed;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean areCheckoutItemsVisible() {
        try {
            sleep(2000);
            Boolean visible = (Boolean) js.executeScript(
                getBaseScript() +
                "let ironPages = app.shadowRoot.querySelector('iron-pages');" +
                "if (ironPages) {" +
                "  let checkout = ironPages.querySelector('shop-checkout');" +
                "  if (checkout?.shadowRoot) {" +
                "    let items = checkout.shadowRoot.querySelectorAll('shop-cart-item');" +
                "    if (items.length > 0) {" +
                "      return items[0].offsetHeight > 0;" +
                "    }" +
                "  }" +
                "}" +
                "return false;");
            
            return visible != null && visible;
        } catch (Exception e) {
            return false;
        }
    }
}
