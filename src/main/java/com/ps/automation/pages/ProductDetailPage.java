package com.ps.automation.pages;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductDetailPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.js = (JavascriptExecutor) driver;
    }

    private String executeScript(String script, Object... args) {
        Object result = js.executeScript(script, args);
        return result != null ? result.toString() : "";
    }

    private Boolean executeBooleanScript(String script, Object... args) {
        Object result = js.executeScript(script, args);
        return result != null && (Boolean) result;
    }

    private String getBaseScript() {
        return "let app = document.querySelector('shop-app');" +
               "if (!app?.shadowRoot) return null;" +
               "let detail = app.shadowRoot.querySelector('shop-detail');" +
               "if (!detail?.shadowRoot) return null;" +
               "let root = detail.shadowRoot;";
    }

    public String getProductTitle() {
        return executeScript(getBaseScript() + "return root.querySelector('h1')?.textContent.trim() || '';");
    }

    public String getProductPrice() {
        return executeScript(getBaseScript() + "return root.querySelector('.price')?.textContent.trim() || '';");
    }

    public String getProductDescription() {
        return executeScript(getBaseScript() + "return root.querySelector('.description, #desc')?.textContent.trim() || '';");
    }

    public boolean isProductImageDisplayed() {
        try {
            Thread.sleep(3000);
            return executeBooleanScript(
                getBaseScript() +
                "if (root.querySelectorAll('img').length > 0) return true;" +
                "for (let div of root.querySelectorAll('div')) {" +
                "  if (window.getComputedStyle(div).backgroundImage !== 'none') return true;" +
                "}" +
                "return root.querySelectorAll('iron-image, shop-image, [class*=\"image\"]').length > 0;");
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSizeDropdownDisplayed() {
        return executeBooleanScript(getBaseScript() + "return root.querySelector('select') !== null;");
    }

    public boolean isQuantityDropdownDisplayed() {
        return executeBooleanScript(getBaseScript() + "return root.querySelectorAll('select').length >= 2;");
    }

    public void selectSize(String size) {
        try {
            js.executeScript(
                getBaseScript() +
                "let select = root.querySelectorAll('select')[0];" +
                "if (select) {" +
                "  select.value = arguments[0];" +
                "  select.dispatchEvent(new Event('change', {bubbles: true}));" +
                "  select.dispatchEvent(new Event('input', {bubbles: true}));" +
                "}", size);
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select size: " + e.getMessage());
        }
    }

    public void selectQuantity(String quantity) {
        try {
            js.executeScript(
                getBaseScript() +
                "let select = root.querySelectorAll('select')[1];" +
                "if (select) {" +
                "  select.value = arguments[0];" +
                "  select.dispatchEvent(new Event('change', {bubbles: true}));" +
                "  select.dispatchEvent(new Event('input', {bubbles: true}));" +
                "}", quantity);
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select quantity: " + e.getMessage());
        }
    }

    public void clickAddToCart() {
        try {
            Boolean clicked = executeBooleanScript(
                getBaseScript() +
                "let buttons = root.querySelectorAll('button');" +
                "for (let btn of buttons) {" +
                "  if (btn.textContent.toLowerCase().includes('add') || btn.textContent.toLowerCase().includes('cart')) {" +
                "    btn.click(); return true;" +
                "  }" +
                "}" +
                "if (buttons[0]) { buttons[0].click(); return true; }" +
                "return false;");
            
            if (!clicked) throw new RuntimeException("Add to cart button not found");
            Thread.sleep(4000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click add to cart: " + e.getMessage());
        }
    }

    public boolean isAddToCartButtonDisplayed() {
        return executeBooleanScript(getBaseScript() + "return root.querySelector('button') !== null;");
    }

    public int getCartItemCount() {
        try {
            Thread.sleep(2000);
            Long count = (Long) js.executeScript(
                "let app = document.querySelector('shop-app');" +
                "if (!app?.shadowRoot) return 0;" +
                
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

    public boolean waitForDetailPageLoad() {
        try {
            return wait.until(ExpectedConditions.urlContains("/detail/"));
        } catch (Exception e) {
            return false;
        }
    }
}

