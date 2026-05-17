package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.selenide.AllureSelenide;
import iscteiul.ista.blackbattleship.selenide.pages.UserStory4Page;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para US04 — Versão Selenide.
 * US04: Como jogador, quero jogar Batalha Naval contra um adversário aleatório online,
 * para encontrar oponentes rapidamente sem ter de convidar ninguém.
 *
 * @author IGE-112244
 */
@Feature("US04 - Play online against random opponent")
class UserStory4SelenideTest {

    /** Page Object para US04. */
    private UserStory4Page page;

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
        page = new UserStory4Page();
    }

    /**
     * Limpeza após cada teste.
     */
    @AfterEach
    void tearDown() {
        Selenide.closeWebDriver();
    }

    /**
     * Verifica que a página carrega com o título correto.
     */
    @Test
    @DisplayName("US04 - Battleship page should load with correct title")
    @Description("Verifica que a página do battleship carrega com o título correto")
    void pageTitleIsCorrect() {
        assertTrue(page.isPageTitleCorrect(),
                "Error: Page title should contain 'battleship'.");
    }

    /**
     * Verifica que o botão Play Online está visível.
     */
    @Test
    @DisplayName("US04 - Play Online button should be visible")
    @Description("Verifica que o botão Play Online está visível na página")
    void playOnlineButtonIsVisible() {
        assertTrue(page.isPlayOnlineButtonVisible(),
                "Error: 'Play online' button should be visible.");
    }

    /**
     * Verifica que a opção de adversário aleatório aparece após clicar Play Online.
     */
    @Test
    @DisplayName("US04 - Random opponent option should appear after clicking Play Online")
    @Description("Verifica que a opção de jogar contra adversário aleatório aparece")
    void randomOptionAppearsAfterPlayOnline() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        assertTrue(page.isRandomOptionVisible(),
                "Error: 'Play online with a random player' option should be visible.");
    }

    /**
     * Verifica o fluxo completo de entrar em matchmaking.
     */
    @Test
    @DisplayName("US04 - Clicking Play with random should enter matchmaking")
    @Description("Verifica que clicar em Play random entra em matchmaking ou sala de jogo")
    void clickRandomEntersMatchmaking() throws InterruptedException {
        page.clickPlayOnline();
        Thread.sleep(2000);
        page.clickPlayRandom();
        assertTrue(page.isInMatchmaking(),
                "Error: Should enter matchmaking or game. URL: " +
                        com.codeborne.selenide.WebDriverRunner.getWebDriver().getCurrentUrl());
    }
}
