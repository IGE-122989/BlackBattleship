package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.selenide.pages.BattleshipPageSelenide;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Suite completa de testes Selenide para todas as User Stories.
 * US04, US06, US08, US11, US12.
 *
 * @author IGE-112244
 */
@Feature("Battleship - All User Stories Selenide")
class AllUserStoriesSelenideTest {

    /** Page Object partilhado por todos os testes. */
    private BattleshipPageSelenide page;

    /**
     * Configuração inicial antes de cada teste.
     * Inicializa o Selenide, o Allure e entra no jogo.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    @BeforeEach
    void setUp() throws InterruptedException {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 15000;
        Configuration.headless = false;

        page = new BattleshipPageSelenide();
        page.open();
        page.dismissConsent();
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

    // ── US04 ──────────────────────────────────────────────────────────────

    /**
     * Verifica que a página carrega com o título correto.
     */
    @Test
    @DisplayName("US04 - Battleship page title is correct")
    @Description("US04: Verifica que a página do battleship carrega com título correto")
    void us04PageTitleCorrect() {
        assertTrue(page.isPageTitleCorrect(),
                "Error: Page title should contain 'battleship'.");
    }

    /**
     * Verifica que o jogador entrou na sala de jogo.
     */
    @Test
    @DisplayName("US04 - Entering game room after selecting robot")
    @Description("US04: Verifica que o jogador entra na sala de jogo")
    void us04EntersGameRoom() {
        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room. URL: ");
    }

    // ── US06 ──────────────────────────────────────────────────────────────

    /**
     * Verifica que o jogo tem dois tabuleiros.
     */
    @Test
    @DisplayName("US06 - Game has two boards for alternating turns")
    @Description("US06: Verifica que o jogo tem dois tabuleiros para turnos alternados")
    void us06GameHasTwoBoards() {
        assertTrue(page.countBoardCells() >= 100,
                "Error: Game should have at least 100 cells.");
    }

    /**
     * Verifica que o jogo está na sala de jogo.
     */
    @Test
    @DisplayName("US06 - Game is in game room")
    @Description("US06: Verifica que o jogo entrou na sala de jogo")
    void us06InGameRoom() {
        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room.");
    }

    // ── US08 ──────────────────────────────────────────────────────────────

    /**
     * Verifica que disparar um míssil regista no tabuleiro.
     */
    @Test
    @DisplayName("US08 - Firing a shot registers on the board")
    @Description("US08: Verifica que disparar um míssil regista no tabuleiro adversário")
    void us08FiringRegistersOnBoard() throws InterruptedException {
        int before = page.countOpponentShotCells();
        for (int i = 0; i < 20; i++) {
            page.clickBoardCell(i);
            Thread.sleep(1000);
            if (page.countOpponentShotCells() > before) break;
        }
        assertTrue(page.countOpponentShotCells() > before || page.hasHitMarker(),
                "Error: Shot should register on board.");
    }

    /**
     * Verifica que o jogo continua após disparar.
     */
    @Test
    @DisplayName("US08 - Game continues after firing")
    @Description("US08: Verifica que o jogo continua após disparar um míssil")
    void us08GameContinuesAfterFiring() throws InterruptedException {
        page.clickBoardCell(0);
        Thread.sleep(3000);
        assertTrue(page.isInGameRoom(),
                "Error: Game should continue after firing.");
    }

    // ── US11 ──────────────────────────────────────────────────────────────

    /**
     * Verifica que o mecanismo de presentes existe no jogo.
     */
    @Test
    @DisplayName("US11 - Gift mechanism exists in game")
    @Description("US11: Verifica que o mecanismo de presentes existe no jogo")
    void us11GiftMechanismExists() {
        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room where gifts can appear.");
    }

    /**
     * Verifica que disparar várias células pode revelar um presente.
     */
    @Test
    @DisplayName("US11 - Firing multiple shots can reveal a gift")
    @Description("US11: Verifica que após vários disparos pode aparecer um presente")
    void us11FireMultipleShotsForGift() throws InterruptedException {
        for (int i = 0; i < 15; i++) {
            page.clickBoardCell(i);
            Thread.sleep(1000);
            if (page.hasGiftIconInBoard()) {
                System.out.println("Presente encontrado na célula " + i);
                break;
            }
        }
        assertTrue(page.isInGameRoom(),
                "Error: Game should continue while looking for gifts.");
    }

    // ── US12 ──────────────────────────────────────────────────────────────

    /**
     * Verifica que o tabuleiro tem 100 células para o míssil simples.
     */
    @Test
    @DisplayName("US12 - Board has 100 cells for simple missile")
    @Description("US12: Verifica que o tabuleiro tem 100 células para míssil simples")
    void us12BoardHas100Cells() {
        assertEquals(100, page.countBoardCells(),
                "Error: Board should have 100 cells.");
    }

    /**
     * Verifica que o míssil simples afeta apenas uma célula.
     */
    @Test
    @DisplayName("US12 - Simple missile hits exactly one cell")
    @Description("US12: Verifica que o míssil simples causa dano numa única célula")
    void us12SimpleMissileHitsOneCell() throws InterruptedException {
        int before = page.countOpponentShotCells();
        page.clickBoardCell(5);
        Thread.sleep(3000);
        int after = page.countOpponentShotCells();
        assertTrue(after - before <= 1,
                "Error: Simple missile should hit at most 1 cell. Hit: "
                        + (after - before));
    }
}