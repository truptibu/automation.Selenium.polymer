package com.ps.automation.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductListingPage extends BasePage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public ProductListingPage(WebDriver driver) {
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

    private void scrollAndWait() {
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        sleep(3000);
    }

    private String getBaseScript() {
        return "let app = document.querySelector('shop-app');" +
               "if (!app?.shadowRoot) return null;" +
               "let list = app.shadowRoot.querySelector('shop-list');" +
               "if (!list?.shadowRoot) return null;" +
               "let root = list.shadowRoot;";
    }

    public int getProductCount() {
        try {
            scrollAndWait();
            Long count = (Long) js.executeScript(
                getBaseScript() +
                "let ul = root.querySelector('ul');" +
                "return ul ? ul.querySelectorAll('li').length : 0;");
            
            System.out.println("Found " + count + " products");
            return count != null ? count.intValue() : 0;
        } catch (Exception e) {
            System.out.println("Error getting product count: " + e.getMessage());
            return 0;
        }
    }

    public String getCategoryTitle() {
        try {
            String title = (String) js.executeScript(
                getBaseScript() +
                "let header = root.querySelector('header');" +
                "return header?.querySelector('h1')?.textContent.trim() || '';");
            
            System.out.println("Found category title: " + title);
            return title != null ? title : "";
        } catch (Exception e) {
            System.out.println("Error getting category title: " + e.getMessage());
            return "";
        }
    }
    
    public List getProductTitles() {
        try {
            scrollAndWait();
            
            @SuppressWarnings("unchecked")
            List titles = (List) js.executeScript(
                getBaseScript() +
                "let ul = root.querySelector('ul');" +
                "if (!ul) return [];" +
                "let titles = [];" +
                "ul.querySelectorAll('li').forEach(li => {" +
                "  let href = li.querySelector('a')?.getAttribute('href') || '';" +
                "  if (href) {" +
                "    let lastPart = href.split('/').pop();" +
                "    if (lastPart) titles.push(lastPart.replace(/_/g, ' ').replace(/-/g, ' '));" +
                "  }" +
                "});" +
                "return titles;");
            
            System.out.println("Extracted " + titles.size() + " product titles from href: " + titles);
            return titles != null ? titles : new ArrayList<>();
        } catch (Exception e) {
            System.out.println("Error getting product titles: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void selectProduct(int index) {
        try {
            scrollAndWait();
            
            Boolean clicked = (Boolean) js.executeScript(
                getBaseScript() +
                "let ul = root.querySelector('ul');" +
                "if (!ul) return false;" +
                "let items = ul.querySelectorAll('li');" +
                "if (arguments[0] >= items.length) return false;" +
                "let anchor = items[arguments[0]].querySelector('a');" +
                "if (anchor) { anchor.click(); return true; }" +
                "return false;", index);
            
            if (clicked == null || !clicked) {
                throw new RuntimeException("Failed to click product at index " + index);
            }
            
            System.out.println("Successfully clicked product at index " + index);
            sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException("Failed to select product: " + e.getMessage());
        }
    }

    public boolean waitForUrlContains(String keyword) {
        try {
            return wait.until(ExpectedConditions.urlContains(keyword));
        } catch (Exception e) {
            System.out.println("URL does not contain: " + keyword);
            return false;
        }
    }
}

