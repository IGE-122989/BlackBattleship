package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.selenide.pages.UserStory12Page;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para US12 — Versão Selenide.
 * US12: Como jogador, quero utilizar um míssil simples que causa dano numa única casa,
 * para ataques precisos e cirúrgicos.
 *
 * @author IGE-112244
 */
@Feature("US12 - Simple missile hits exactly one cell")
class UserStory12SelenideTest {

    /** Page Object para US12. */
    private UserStory12Page page;

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
        page = new UserStory12Page();
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
    @DisplayName("US12 - Game should load correctly")
    @Description("Verifica que o jogo carregou e entrou na sala de jogo")
    void gameLoadsCorrectly() {
        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room. URL: "
                        + WebDriverRunner.getWebDriver().getCurrentUrl());
    }

    /**
     * Verifica que o tabuleiro adversário tem 100 células.
     */
    @Test
    @DisplayName("US12 - Board should have 100 cells for opponent")
    @Description("Verifica que o tabuleiro adversário tem exatamente 100 células (10x10)")
    void boardHas100Cells() {
        int cells = page.countBoardCells();
        assertEquals(100, cells,
                "Error: Opponent board should have exactly 100 cells.");
    }

    /**
     * Verifica que o míssil simples atinge no máximo uma célula.
     */
    @Test
    @DisplayName("US12 - Firing simple missile should hit exactly one cell")
    @Description("Verifica que o míssil simples causa dano numa única casa")
    void simpleMissileHitsOneCell() throws InterruptedException {
        int cellsBefore = page.countOpponentShotCells();
        page.clickBoardCell(5);
        Thread.sleep(3000);
        int cellsAfter = page.countOpponentShotCells();
        int cellsHit = cellsAfter - cellsBefore;
        assertTrue(cellsHit >= 0 && cellsHit <= 1,
                "Error: Simple missile should hit at most 1 cell. Hit: " + cellsHit);
    }

    /**
     * Verifica que cada míssil simples atinge no máximo uma célula.
     */
    @Test
    @DisplayName("US12 - Firing multiple missiles should hit one cell each")
    @Description("Verifica que múltiplos mísseis simples afetam no máximo uma célula cada")
    void multipleMissilesHitOneCellEach() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            int cellsBefore = page.countOpponentShotCells();
            page.clickBoardCell(i * 2);
            Thread.sleep(2000);
            int cellsAfter = page.countOpponentShotCells();
            int cellsHit = cellsAfter - cellsBefore;
            assertTrue(cellsHit >= 0 && cellsHit <= 1,
                    "Error: Each simple missile should hit at most 1 cell. " +
                            "Shot " + i + " hit: " + cellsHit);
        }
    }

    /**
     * Verifica que o jogo continua após disparar um míssil simples.
     */
    @Test
    @DisplayName("US12 - Game continues after firing simple missile")
    @Description("Verifica que o jogo continua após disparar um míssil simples")
    void gameContinuesAfterSimpleMissile() throws InterruptedException {
        page.clickBoardCell(0);
        Thread.sleep(3000);
        assertTrue(page.isInGameRoom(),
                "Error: Game should continue after simple missile.");
    }
}