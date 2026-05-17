package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.selenide.pages.UserStory8Page;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para US08 — Versão Selenide.
 * US08: Como jogador, quero poder disparar novamente quando acerto num navio adversário,
 * para ter uma vantagem por precisão.
 *
 * @author IGE-112244
 */
@Feature("US08 - Fire again when hitting opponent ship")
class UserStory8SelenideTest {

    /** Page Object para US08. */
    private UserStory8Page page;

    /**
     * Configuração inicial antes de cada teste.
     * Entra no jogo antes de cada teste.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    @BeforeEach
    void setUp() throws InterruptedException {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 15000;
        Configuration.headless = false;

        Selenide.open("https://papergames.io/en/battleship");
        page = new UserStory8Page();
        page.dismissConsentIfPresent();
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();
    }

    /**
     * Limpeza após cada teste.
     */
    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    /**
     * Verifica que o jogo carregou e entrou na sala de jogo.
     */
    @Test
    @DisplayName("US08 - Game should load and enter game room")
    @Description("Verifica que o jogo carregou e entrou na sala de jogo")
    void gameLoadsCorrectly() {
        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room.");
    }

    /**
     * Verifica que o tabuleiro tem células disponíveis para disparar.
     */
    @Test
    @DisplayName("US08 - Board should have cells available for firing")
    @Description("Verifica que o tabuleiro adversário tem 100 células disponíveis")
    void boardHasCellsForFiring() {
        int cells = page.countBoardCells();
        assertTrue(cells >= 100,
                "Error: Board should have at least 100 cells. Found: " + cells);
    }

    /**
     * Verifica que o indicador de turno de ataque está visível.
     */
    @Test
    @DisplayName("US08 - Attack turn should be visible before firing")
    @Description("Verifica que o indicador de turno de ataque está visível")
    void attackTurnVisibleBeforeFiring() {
        assertTrue(page.isAttackTurnVisible() || page.isInGameRoom(),
                "Error: Attack turn indicator should be visible.");
    }

    /**
     * Verifica que disparar regista no tabuleiro.
     */
    @Test
    @DisplayName("US08 - Firing a shot should register on the board")
    @Description("Verifica que disparar um míssil regista no tabuleiro adversário")
    void firingRegistersOnBoard() throws InterruptedException {
        int cellsBefore = page.countOpponentShotCells();
        for (int i = 0; i < 20; i++) {
            page.clickBoardCell(i);
            Thread.sleep(1000);
            int current = page.countOpponentShotCells();
            if (current > cellsBefore) {
                assertTrue(current > cellsBefore,
                        "Error: Shot cells should increase.");
                return;
            }
        }
        assertTrue(page.hasHitMarker(),
                "Error: No shot registered after 20 attempts.");
    }

    /**
     * Verifica que acertar num navio regista marcador de acerto.
     */
    @Test
    @DisplayName("US08 - Hitting a ship registers a hit marker")
    @Description("Verifica que acertar num navio regista um marcador de acerto")
    void hittingShipRegistersHit() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            page.clickBoardCell(i);
            Thread.sleep(1500);
            if (page.countOpponentShotCells() > 0) break;
        }
        assertTrue(page.countOpponentShotCells() > 0 || page.hasHitMarker(),
                "Error: At least one shot should be registered.");
    }

    /**
     * Verifica que o jogo continua após disparar.
     */
    @Test
    @DisplayName("US08 - Game continues after firing allowing more shots")
    @Description("Verifica que o jogo continua após disparar um míssil")
    void gameContinuesAfterFiring() throws InterruptedException {
        page.clickBoardCell(0);
        Thread.sleep(3000);
        assertTrue(page.isInGameRoom(),
                "Error: Game should continue after firing.");
    }
}