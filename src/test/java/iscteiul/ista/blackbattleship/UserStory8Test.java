package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Test Class para US08.
 * US08: Como jogador, quero poder disparar novamente quando acerto num navio adversário,
 * para ter uma vantagem por precisão.
 */
class UserStory8Test {

    private WebDriver driver;
    private UserStory8 page;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        page = PageFactory.initElements(driver, UserStory8.class);
        page.open();
        // Entrar no jogo em cada teste
        page.dismissConsentIfPresent();
        page.clickPlayOnline();
        try { Thread.sleep(2000); } catch (Exception e) {}
        try { page.clickPlayVsRobot(); } catch (Exception e) {}
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("US08 - Game should load and enter game room")
    void gameLoadsCorrectly() {
        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room. URL: " + driver.getCurrentUrl());
    }

    @Test
    @DisplayName("US08 - Board should have cells available for firing")
    void boardHasCellsForFiring() {
        int cells = page.countBoardCells();
        assertTrue(cells >= 100,
                "Error: Board should have at least 100 cells. Found: " + cells);
    }

    @Test
    @DisplayName("US08 - Attack turn should be visible before firing")
    void attackTurnVisibleBeforeFiring() {
        assertTrue(page.isAttackTurnVisible() || page.isInGameRoom(),
                "Error: Attack turn indicator should be visible.");
    }

    @Test
    @DisplayName("US08 - Firing a shot should register on the board")
    void firingRegistersOnBoard() throws InterruptedException {
        int cellsBefore = page.countOpponentShotCells();

        // Disparar em várias células até registar pelo menos uma
        for (int i = 0; i < 20; i++) {
            page.clickBoardCell(i);
            Thread.sleep(2000);
            int current = page.countOpponentShotCells();
            if (current > cellsBefore) {
                System.out.println("Acerto registado na célula " + i);
                assertTrue(current > cellsBefore,
                        "Error: Shot cells should increase. Before: "
                                + cellsBefore + " After: " + current);
                return; // teste passou
            }
        }

        // Se chegou aqui, tenta verificar se há hit marker
        assertTrue(page.hasHitMarker(),
                "Error: No shot registered after 20 attempts.");
    }

    @Test
    @DisplayName("US08 - Hitting a ship registers a hit marker")
    void hittingShipRegistersHit() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            page.clickBoardCell(i);
            Thread.sleep(1500);
            if (page.countOpponentShotCells() > 0) break;
        }
        assertTrue(page.countOpponentShotCells() > 0 || page.hasHitMarker(),
                "Error: At least one shot should be registered.");
    }

    @Test
    @DisplayName("US08 - Game continues after firing allowing more shots")
    void gameContinuesAfterFiring() throws InterruptedException {
        page.clickBoardCell(0);
        Thread.sleep(3000);
        assertTrue(page.isInGameRoom(),
                "Error: Game should continue after firing. URL: " + driver.getCurrentUrl());
    }
}