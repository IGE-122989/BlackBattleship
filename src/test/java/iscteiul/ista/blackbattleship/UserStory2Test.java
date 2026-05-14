package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a User Story 2 — Jogar contra um robot.
 * <p>
 * Como jogador, quero jogar Batalha Naval contra um robô (bot),
 * para poder praticar sozinho quando não há adversários disponíveis.
 * </p>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory2Test {

    private WebDriver driver;
    private UserStory2 userStory2Page;

    /**
     * Configuração executada antes de cada teste.
     * Inicializa o WebDriver e a Page Object Class.
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        userStory2Page = new UserStory2(driver);
        userStory2Page.abrirPagina();
    }

    /**
     * Encerramento executado após cada teste.
     * Fecha o browser e termina a sessão do WebDriver.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Testa se a página de Batalha Naval abre corretamente.
     * Verifica que o URL contém "battleship".
     */
    @Test
    @Order(1)
    public void testAbrirPagina() {
        String url = userStory2Page.getUrlAtual();
        assertTrue(url.contains("battleship"), "O URL deve conter 'battleship'");
    }

    /**
     * Testa se o clique em "Play vs robot" abre o modal de nickname.
     * Verifica que o campo de nickname fica visível após clicar no botão.
     */
    @Test
    @Order(2)
    public void testClicarPlayVsRobotAbreModal() {
        userStory2Page.clicarPlayVsRobot();
        assertTrue(userStory2Page.modalNicknameVisivel(), "O modal de nickname deve aparecer após clicar em Play vs robot");
    }

    /**
     * Testa o fluxo completo de iniciar um jogo contra o robot.
     * Verifica que após introduzir o nickname e clicar em Continue,
     * o URL muda para a página do jogo.
     */
    @Test
    @Order(3)
    public void testFluxoCompletoPlayVsRobot() throws InterruptedException {
        userStory2Page.clicarPlayVsRobot();
        userStory2Page.preencherNickname("rita");
        userStory2Page.clicarContinue();
        Thread.sleep(2000);
        String urlAtual = userStory2Page.getUrlAtual();
        assertNotEquals("https://papergames.io/en/battleship", urlAtual,
                "O URL deve mudar após iniciar o jogo contra o robot");
    }
}
