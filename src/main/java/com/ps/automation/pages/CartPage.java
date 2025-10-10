package com.ps.automation.pages;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public CartPage(WebDriver driver) {
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

    private String getCartModalScript() {
        return "let cartModal = app.shadowRoot.querySelector('shop-cart-modal');" +
               "if (cartModal?.shadowRoot) return cartModal.shadowRoot;" +
               "let ironPages = app.shadowRoot.querySelector('iron-pages');" +
               "if (ironPages) {" +
               "  let cart = ironPages.querySelector('shop-cart');" +
               "  if (cart?.shadowRoot) return cart.shadowRoot;" +
               "}" +
               "return null;";
    }

    public void openCart() {
        try {
            sleep(3000);
            Boolean clicked = (Boolean) js.executeScript(
                getBaseScript() +
                "let selectors = ['shop-cart-button', 'app-header shop-cart-button', 'app-toolbar shop-cart-button'];" +
                "for (let sel of selectors) {" +
                "  let btn = app.shadowRoot.querySelector(sel);" +
                "  if (btn) { btn.click(); return true; }" +
                "}" +
                "return false;");
            
            if (clicked == null || !clicked) {
                throw new RuntimeException("Cart button not found");
            }
            
            sleep(2000);
            System.out.println("✓ Cart opened successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to open cart: " + e.getMessage());
        }
    }

    public int getCartItemCount() {
        try {
            sleep(1000);
            Long count = (Long) js.executeScript(
                getBaseScript() +
                "let cartBtn = app.shadowRoot.querySelector('shop-cart-button');" +
                "if (cartBtn?.shadowRoot) {" +
                "  for (let el of cartBtn.shadowRoot.querySelectorAll('*')) {" +
                "    let num = parseInt(el.textContent.trim());" +
                "    if (!isNaN(num) && num > 0) return num;" +
                "  }" +
                "}" +
                "if (cartBtn) {" +
                "  let numItems = parseInt(cartBtn.getAttribute('num-items'));" +
                "  if (!isNaN(numItems)) return numItems;" +
                "}" +
                "if (app.cart?.length) return app.cart.length;" +
                "try {" +
                "  let cart = JSON.parse(localStorage.getItem('shop-cart'));" +
                "  if (Array.isArray(cart)) return cart.length;" +
                "} catch(e) {}" +
                "return 0;");
            
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isCartEmpty() {
        try {
            sleep(1000);
            Boolean isEmpty = (Boolean) js.executeScript(
                getBaseScript() +
                getCartModalScript() +
                "if (cartModal) {" +
                "  return cartModal.querySelectorAll('shop-cart-item').length === 0;" +
                "}" +
                "return true;");
            
            return isEmpty != null && isEmpty;
        } catch (Exception e) {
            return true;
        }
    }

    public void removeFirstItemFromCart() {
        try {
            sleep(1000);
            Boolean removed = (Boolean) js.executeScript(
                getBaseScript() +
                getCartModalScript() +
                "if (cartModal) {" +
                "  let firstItem = cartModal.querySelector('shop-cart-item');" +
                "  if (firstItem?.shadowRoot) {" +
                "    let removeBtn = firstItem.shadowRoot.querySelector('button, [role=\"button\"], .remove');" +
                "    if (removeBtn) { removeBtn.click(); return true; }" +
                "  }" +
                "}" +
                "return false;");
            
            if (removed == null || !removed) {
                throw new RuntimeException("Remove button not found");
            }
            
            sleep(2000);
            System.out.println("✓ Item removed from cart");
        } catch (Exception e) {
            throw new RuntimeException("Failed to remove item: " + e.getMessage());
        }
    }

    public void clearCart() {
        try {
            int itemCount = getCartItemCount();
            for (int i = 0; i < itemCount; i++) {
                removeFirstItemFromCart();
                sleep(1000);
            }
            System.out.println("✓ Cart cleared successfully");
        } catch (Exception e) {
            throw new RuntimeException("Failed to clear cart: " + e.getMessage());
        }
    }

    public boolean isCartIconDisplayed() {
        try {
            sleep(3000);
            wait.until(driver -> {
                Boolean ready = (Boolean) js.executeScript(
                    "let app = document.querySelector('shop-app');" +
                    "return app && app.shadowRoot !== null;");
                return ready != null && ready;
            });
            
            Boolean displayed = (Boolean) js.executeScript(
                getBaseScript() +
                "return app.shadowRoot.querySelector('shop-cart-button') !== null;");
            
            return displayed != null && displayed;
        } catch (Exception e) {
            return false;
        }
    }
}

