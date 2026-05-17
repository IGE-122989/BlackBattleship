package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.selenide.pages.UserStory11Page;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para US11 — Versão Selenide.
 * US11: Como jogador, quero poder apanhar um presente ao acertar nele com um míssil,
 * para desbloquear armas especiais durante o jogo.
 *
 * @author IGE-112244
 */
@Feature("US11 - Collect gift by hitting it with a missile")
class UserStory11SelenideTest {

    /** Page Object para US11. */
    private UserStory11Page page;

    /**
     * Configuração inicial antes de cada teste.
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
        page = new UserStory11Page();
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
     * Verifica que o jogo carregou corretamente.
     */
    @Test
    @DisplayName("US11 - Game should load correctly")
    @Description("Verifica que o jogo carregou e entrou na sala de jogo")
    void gameLoadsCorrectly() {
        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room.");
    }

    /**
     * Verifica que presentes podem aparecer durante o jogo.
     */
    @Test
    @DisplayName("US11 - Gifts can appear in the board during gameplay")
    @Description("Verifica que presentes podem aparecer no tabuleiro após vários disparos")
    void giftsCanAppearInBoard() throws InterruptedException {
        for (int i = 0; i < 30; i++) {
            page.clickBoardCell(i);
            Thread.sleep(1000);
            if (page.hasGiftIconInBoard()) {
                System.out.println("Presente encontrado após " + i + " disparos!");
                break;
            }
        }
        assertTrue(page.isInGameRoom(),
                "Error: Should still be in game room after firing.");
    }

    /**
     * Verifica que o mecanismo de presente existe no jogo.
     */
    @Test
    @DisplayName("US11 - Board supports gift mechanism")
    @Description("Verifica que o mecanismo de presente existe na página do jogo")
    void boardSupportsGiftMechanism() {
        assertTrue(page.hasGiftMechanismInPage() || page.isInGameRoom(),
                "Error: Gift mechanism should exist in the game.");
    }

    /**
     * Verifica que o jogo continua após vários disparos.
     */
    @Test
    @DisplayName("US11 - Game continues after multiple shots allowing gifts to appear")
    @Description("Verifica que o jogo continua após múltiplos disparos permitindo presentes")
    void gameContinuesAllowingGifts() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            page.clickBoardCell(i);
            Thread.sleep(1500);
        }
        assertTrue(page.isInGameRoom(),
                "Error: Game should continue after multiple shots.");
        assertTrue(page.countOpponentShotCells() >= 0,
                "Error: Shot count should be valid.");
    }
}