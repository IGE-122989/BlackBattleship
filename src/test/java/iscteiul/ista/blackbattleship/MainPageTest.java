package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

class MainPageTest {

    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        mainPage = PageFactory.initElements(driver, MainPage.class);
        mainPage.open();
        mainPage.acceptCookiesIfPresent();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void search() throws InterruptedException {
        mainPage.search("IntelliJ IDEA");

        assertTrue(
                driver.getCurrentUrl().contains("q=IntelliJ"),
                "Error: URL should contain search query parameter. URL: " + driver.getCurrentUrl());
    }

    @Test
    void navigateToIDEs() {
        mainPage.navigateTo("https://www.jetbrains.com/ides/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("ides"));
        assertTrue(driver.getCurrentUrl().contains("ides"),
                "Error: URL should contain 'ides'.");
        assertFalse(driver.getTitle().isEmpty(),
                "Error: page title should not be empty.");
    }

    @Test
    void navigateToAllProducts() {
        mainPage.navigateTo("https://www.jetbrains.com/products/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("products"));
        assertTrue(driver.getCurrentUrl().contains("products"),
                "Error: URL should contain 'products'.");
        assertFalse(driver.getTitle().isEmpty(),
                "Error: page title should not be empty.");
    }
}