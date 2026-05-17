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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.jetbrains.com/");

        // Aceitar cookies
        try {
            WebElement acceptCookies = driver.findElement(
                    By.cssSelector("button.ch2-btn.ch2-btn-primary"));
            acceptCookies.click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Banner de cookies não apareceu");
        }

        mainPage = new MainPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    public void search() throws InterruptedException {
        mainPage.searchButton.click();

        Thread.sleep(1000);

        WebElement searchField =
                driver.findElement(By.cssSelector("[data-test-id='search-input']"));

        searchField.sendKeys("Selenium");

        Thread.sleep(1000);

        WebElement submitButton =
                driver.findElement(By.cssSelector("button[data-test='full-search-button']"));

        submitButton.click();

        Thread.sleep(1000);

        WebElement searchPageField =
                driver.findElement(By.cssSelector("input[data-test-id='search-input']"));

        assertEquals("Selenium", searchPageField.getAttribute("value"));
    }

    @Test
    public void toolsMenu() throws InterruptedException {
        mainPage.toolsMenu.click();

        Thread.sleep(1000);

        WebElement menuPopup = driver.findElement(By.cssSelector("div[data-test='main-submenu']"));
        assertTrue(menuPopup.isDisplayed());

        Thread.sleep(1000);
    }

    @Test
    public void navigationToAllTools() throws InterruptedException {
        mainPage.toolsMenu.click();
        Thread.sleep(1000);

        mainPage.findYourToolsButton.click();
        Thread.sleep(2000);

        assertTrue(driver.getCurrentUrl().contains("/products/"));
        assertEquals("All Developer Tools and Products by JetBrains", driver.getTitle());

    }
}