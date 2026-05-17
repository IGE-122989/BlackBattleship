package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Test Class para UserStoryTest4.
 * <p>
 * US04: Como jogador, quero jogar Batalha Naval contra um adversário aleatório online,
 * para encontrar oponentes rapidamente sem ter de convidar ninguém.
 * </p>
 * Baseado no cenário gravado no ficheiro TestSuite_112244.side.
 *
 * @author IGE-112244
 */
class UserStory4LLMTest {

    /** WebDriver para controlar o browser. */
    private WebDriver driver;

    /** Page Object para a UserStory4. */
    private UserStory4LLM page;

    /**
     * Configuração inicial antes de cada teste.
     * Inicializa o browser em modo incógnito e abre a página do jogo.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    @BeforeEach
    void setUp() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--window-size=550,692");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);

        // ← Criar a instância manualmente passando o driver
        page = new UserStory4LLM(driver);

        page.open();
        page.dismissConsentIfPresent();
    }

    /**
     * Limpeza após cada teste.
     * Fecha o browser.
     */
    @AfterEach
    void tearDown() {
        if (driver != null) driver.quit();
    }

    /**
     * Verifica que a página do battleship carrega com o título correto.
     * Corresponde ao comando "open /en/battleship" do Selenium IDE.
     */
    @Test
    @DisplayName("US04 - Battleship page should load with correct title")
    void pageTitleIsCorrect() {
        assertTrue(page.isPageTitleCorrect(),
                "Error: Page title should contain 'battleship'. Title: "
                        + driver.getTitle());
    }

    /**
     * Verifica que o botão "Play online with a random player" está visível.
     * Corresponde ao passo de clicar em ".btn-secondary:nth-child(2)" do Selenium IDE.
     */
    @Test
    @DisplayName("US04 - Play online with random player button should be visible")
    void playOnlineRandomButtonIsVisible() {
        assertTrue(page.isPlayOnlineRandomButtonVisible(),
                "Error: 'Play online with a random player' button should be visible.");
    }

    /**
     * Verifica que após clicar em "Play online with random player"
     * aparece o campo de nickname.
     * Corresponde aos passos de click e type do Selenium IDE.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    @Test
    @DisplayName("US04 - Clicking Play online random should show nickname input")
    void clickingPlayOnlineShowsNicknameInput() throws InterruptedException {
        page.clickPlayOnlineRandom();
        assertTrue(page.isNicknameInputVisible(),
                "Error: Nickname input should appear after clicking 'Play online random'.");
    }

    /**
     * Verifica o fluxo completo: clicar em Play online random,
     * preencher o nickname "Fabio" e clicar em Continue.
     * Corresponde ao cenário completo do Selenium IDE.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    @Test
    @DisplayName("US04 - Full flow: play random with nickname enters matchmaking")
    void fullFlowEntersMatchmaking() throws InterruptedException {
        page.clickPlayOnlineRandom();
        page.enterNickname("Fabio");
        page.clickContinue();

        assertTrue(page.isInMatchmakingOrGame(),
                "Error: Should enter matchmaking or game after completing flow. URL: "
                        + driver.getCurrentUrl());
    }
}