package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Test Class para US04.
 * US04: Como jogador, quero jogar Batalha Naval contra um adversário aleatório online,
 * para encontrar oponentes rapidamente sem ter de convidar ninguém.
 */
class UserStory4Test {

    private WebDriver driver;
    private UserStory4 page;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        page = PageFactory.initElements(driver, UserStory4.class);
        page.open();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("US04 - Battleship page should load with correct title")
    void pageTitleIsCorrect() {
        assertTrue(page.isPageTitleCorrect(),
                "Error: Page title should contain 'battleship'. Title: "
                        + driver.getTitle());
    }

    @Test
    @DisplayName("US04 - Play Online button should be visible on battleship page")
    void playOnlineButtonIsVisible() {
        assertTrue(page.isPlayOnlineButtonVisible(),
                "Error: 'Play online' button should be visible on the battleship page.");
    }

    @Test
    @DisplayName("US04 - Random opponent option should appear after clicking Play Online")
    void randomOptionAppearsAfterPlayOnline() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);

        assertTrue(page.isRandomOptionVisible(),
                "Error: 'Play online with a random player' option should be visible.");
    }

    @Test
    @DisplayName("US04 - Clicking Play with random should enter matchmaking or game")
    void clickRandomEntersMatchmaking() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayRandom();

        assertTrue(page.isInMatchmaking(),
                "Error: Should enter matchmaking or game room after clicking random. URL: "
                        + driver.getCurrentUrl());
    }
}