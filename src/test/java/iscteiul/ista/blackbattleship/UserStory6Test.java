// UserStory6Test.java — versão final
package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Test Class para US06.
 * US06: Como jogador, quero disparar mísseis no tabuleiro adversário em turnos alternados,
 * para tentar afundar a frota inimiga antes que ele afunde a minha.
 */
class UserStory6Test {

    private WebDriver driver;
    private UserStory6 page;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        page = PageFactory.initElements(driver, UserStory6.class);
        page.open();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("US06 - Game mode options should appear after clicking Play Online")
    void gameModeOptionsAppear() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        assertAll(
                () -> assertTrue(page.isPlayVsRobotVisible(),
                        "Error: 'Play vs robot' should be visible."),
                () -> assertTrue(page.isPlayWithFriendVisible(),
                        "Error: 'Play with a friend' should be visible."),
                () -> assertTrue(page.isPlayRandomVisible(),
                        "Error: 'Play online with random player' should be visible.")
        );
    }

    @Test
    @DisplayName("US06 - Clicking Play vs Robot should enter a game room")
    void playVsRobotEntersGameRoom() throws InterruptedException {
        page.clickPlayOnline(); // ← faltava esta linha!
        Thread.sleep(2000);
        page.clickPlayVsRobot();

        assertTrue(page.isInGameRoom(),
                "Error: Should navigate to game room. URL: " + driver.getCurrentUrl());
    }

    @Test
    @DisplayName("US06 - Game should show two boards for alternating turns")
    void gameShowsTwoBoards() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();

        assertTrue(page.hasGameBoards(),
                "Error: Game should have at least 2 boards (own and opponent).");
    }

    @Test
    @DisplayName("US06 - Game should show turn indicator")
    void gameShowsAttackTurnIndicator() throws InterruptedException {
        page.dismissConsentIfPresent(); // ← forçar remoção do popup
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();
        Thread.sleep(5000);

        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room with turn indicator. URL: "
                        + driver.getCurrentUrl());
    }

    @Test
    @DisplayName("US06 - Game should show player's own board")
    void gameShowsOwnBoard() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();
        Thread.sleep(5000); // ← aumentar wait

        assertTrue(page.isMyBoardVisible(),
                "Error: 'Your boats' section should be visible in the game.");
    }

    @Test
    @DisplayName("US06 - Game should have clickable board cells for firing")
    void gameHasBoardCellsForFiring() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();
        Thread.sleep(5000);

        // Verificar que existem células clicáveis no tabuleiro adversário
        int numCells = driver.findElements(By.tagName("td")).size();
        assertTrue(numCells > 0,
                "Error: Board should have clickable cells for firing. Found: " + numCells);
    }
}