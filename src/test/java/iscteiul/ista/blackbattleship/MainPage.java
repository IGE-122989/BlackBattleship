package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {

    // ← CAMPOS QUE FALTAVAM
    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//a[@href='/ides/' and @data-test='main-submenu-item-link']")
    public WebElement seeDeveloperToolsButton;

    @FindBy(xpath = "//a[@data-test='suggestion-link' and @href='/products/']")
    public WebElement findYourToolsButton;

    @FindBy(xpath = "//button[@data-test='main-menu-item-action' and @aria-label='Products: Open submenu']")
    public WebElement toolsMenu;

    @FindBy(css = "button[data-test='site-header-search-action']")
    public WebElement searchButton;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.jetbrains.com/");
        wait.until(ExpectedConditions.jsReturnsValue(
                "return document.readyState === 'complete'"));
    }

    public void search(String query) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
        Thread.sleep(1500);
        WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("input[type='text']")));
        searchField.sendKeys(query);
        searchField.sendKeys(Keys.RETURN);
        Thread.sleep(2000);
    }

    public void navigateTo(String url) {
        driver.get(url);
        wait.until(ExpectedConditions.jsReturnsValue(
                "return document.readyState === 'complete'"));
    }

    public void acceptCookiesIfPresent() {
        try {
            Thread.sleep(2000);
            ((JavascriptExecutor) driver).executeScript(
                    "var btns = document.querySelectorAll('[class*=\"ch2\"] button, .ch2-btn');" +
                            "for(var b of btns) { if(b.offsetParent !== null) { b.click(); break; } }"
            );
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector(".ch2-container")));
        } catch (Exception e) {
            // Sem popup
        }
    }
}