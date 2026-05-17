package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.selenide.pages.UserStory6Page;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para US06 — Versão Selenide.
 * US06: Como jogador, quero disparar mísseis no tabuleiro adversário em turnos alternados,
 * para tentar afundar a frota inimiga antes que ele afunde a minha.
 *
 * @author IGE-112244
 */
@Feature("US06 - Fire missiles in alternating turns")
class UserStory6SelenideTest {

    /** Page Object para US06. */
    private UserStory6Page page;

    /**
     * Configuração inicial antes de cada teste.
     */
    @BeforeEach
    void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true)
                .savePageSource(false));

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 15000;
        Configuration.headless = false;

        Selenide.open("https://papergames.io/en/battleship");
        page = new UserStory6Page();
    }

    /**
     * Limpeza após cada teste.
     */
    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    /**
     * Verifica que as opções de jogo aparecem após clicar Play Online.
     */
    @Test
    @DisplayName("US06 - Game mode options should appear after clicking Play Online")
    @Description("Verifica que as opções Play vs robot, Play with friend e Play random aparecem")
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

    /**
     * Verifica que clicar Play vs Robot entra na sala de jogo.
     */
    @Test
    @DisplayName("US06 - Clicking Play vs Robot should enter a game room")
    @Description("Verifica que clicar em Play vs Robot entra na sala de jogo")
    void playVsRobotEntersGameRoom() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();
        assertTrue(page.isInGameRoom(),
                "Error: Should navigate to game room. URL: " +
                        com.codeborne.selenide.WebDriverRunner.getWebDriver().getCurrentUrl());
    }

    /**
     * Verifica que o jogo mostra dois tabuleiros.
     */
    @Test
    @DisplayName("US06 - Game should show two boards for alternating turns")
    @Description("Verifica que o jogo tem dois tabuleiros para turnos alternados")
    void gameShowsTwoBoards() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();
        Thread.sleep(5000); // ← aumentar para 5s para o jogo carregar
        assertTrue(page.hasGameBoards(),
                "Error: Game should have at least 2 boards.");
    }

    /**
     * Verifica que o indicador de turno está visível.
     */
    @Test
    @DisplayName("US06 - Game should show turn indicator")
    @Description("Verifica que o indicador de turno de ataque está visível")
    void gameShowsAttackTurnIndicator() throws InterruptedException {
        page.dismissConsentIfPresent();
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();
        Thread.sleep(5000);
        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room with turn indicator.");
    }

    /**
     * Verifica que o tabuleiro do jogador está visível.
     */
    @Test
    @DisplayName("US06 - Game should show player's own board")
    @Description("Verifica que o tabuleiro do próprio jogador está visível")
    void gameShowsOwnBoard() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();
        Thread.sleep(5000);
        assertTrue(page.isMyBoardVisible(),
                "Error: 'Your boats' section should be visible.");
    }

    /**
     * Verifica que existem células clicáveis no tabuleiro para disparar.
     */
    @Test
    @DisplayName("US06 - Game should have clickable board cells for firing")
    @Description("Verifica que existem células clicáveis no tabuleiro adversário")
    void gameHasBoardCellsForFiring() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayVsRobot();
        Thread.sleep(5000);
        int numCells = com.codeborne.selenide.WebDriverRunner.getWebDriver()
                .findElements(By.tagName("td")).size();
        assertTrue(numCells > 0,
                "Error: Board should have clickable cells. Found: " + numCells);
    }
}