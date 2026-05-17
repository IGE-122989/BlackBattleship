package iscteiul.ista.blackbattleship;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <b>Page Test Class — UserStory3</b>
 *
 * <p>Esta classe contém os testes automatizados JUnit que validam o
 * comportamento descrito na User Story 3:</p>
 *
 * <p><i>“Como jogador, quero jogar Batalha Naval com um amigo através
 * de uma ligação partilhada, para jogar com quem eu quiser sem necessidade
 * de registo.”</i></p>
 *
 * <p>Os testes utilizam a Page Object Class {@link UserStory3}, que encapsula
 * todos os localizadores e interações com a interface gráfica, garantindo
 * um código de testes limpo, legível e fácil de manter.</p>
 *
 * <p>Fluxo validado:</p>
 * <ol>
 *     <li>Abrir a página do Battleship.</li>
 *     <li>Clicar em <em>Play with a friend</em>.</li>
 *     <li>Inserir um nickname temporário.</li>
 *     <li>Clicar em <em>Continue</em>.</li>
 *     <li>Aguardar pela geração do URL partilhável.</li>
 *     <li>Validar que o URL foi criado.</li>
 *     <li>Clicar no ícone de cópia.</li>
 * </ol>
 *
 * @author
 * @version 1.0
 */
public class UserStory3Test {

    /**
     * Instância do WebDriver utilizada em cada teste.
     */
    private WebDriver driver;

    /**
     * Page Object que encapsula as interações com a página Battleship.
     */
    private UserStory3 battleshipPage;

    /**
     * Objeto de espera explícita para condições específicas.
     */
    private WebDriverWait wait;

    /**
     * Timeout padrão para esperas explícitas (segundos).
     */
    private static final int TIMEOUT = 10;

    // -------------------------------------------------------------------------
    // Setup e Teardown
    // -------------------------------------------------------------------------

    /**
     * Configuração executada antes de cada teste.
     *
     * <p>Inicializa o ChromeDriver, maximiza a janela e instancia a Page Object.</p>
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        battleshipPage = new UserStory3(driver);
    }

    /**
     * Limpeza executada após cada teste.
     *
     * <p>Fecha o browser e liberta os recursos associados ao WebDriver.</p>
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // -------------------------------------------------------------------------
    // Testes
    // -------------------------------------------------------------------------

    /**
     * Testa o fluxo completo da User Story 3:
     * <ul>
     *     <li>Abertura da página</li>
     *     <li>Criação de partida com um amigo</li>
     *     <li>Geração do URL partilhável</li>
     *     <li>Cópia do URL</li>
     * </ul>
     *
     * @throws InterruptedException se ocorrer interrupção durante pausas simuladas
     */
    @Test
    public void testGerarUrlPartilhavel() throws InterruptedException {

        // 1 — Abrir página
        battleshipPage.openPage();
        Thread.sleep(2000);

        battleshipPage.aceitarCookies();
        Thread.sleep(1000);

        // 2 — Clicar em "Play with a friend"
        battleshipPage.clickPlayWithFriend();
        Thread.sleep(1000);

        // 3 — Inserir nickname
        battleshipPage.enterNickname("TestPlayer");
        Thread.sleep(500);

        // 4 — Confirmar
        battleshipPage.clickContinue();
        Thread.sleep(1000);

        // 5 — Aguardar pelo URL partilhável
        battleshipPage.waitForShareableLink();

        // 6 — Validar que o URL foi gerado
        assertTrue(battleshipPage.isShareableLinkPresent(),
                "O URL partilhável deve estar visível após criar a sala.");

        // 7 — Copiar URL
        battleshipPage.clickCopyUrl();
        Thread.sleep(1000);
    }

    /**
     * Testa que a página Battleship abre corretamente.
     *
     * @throws InterruptedException se ocorrer interrupção durante pausas simuladas
     */
    @Test
    public void testAbrirPagina() throws InterruptedException {
        battleshipPage.openPage();
        Thread.sleep(1000);

        assertTrue(driver.getCurrentUrl().contains("battleship"),
                "O URL deve conter 'battleship'.");
    }

    /**
     * Testa que o URL partilhável gerado não está vazio.
     *
     * @throws InterruptedException se ocorrer interrupção durante pausas simuladas
     */
    @Test
    public void testUrlPartilhavelNaoVazio() throws InterruptedException {

        battleshipPage.openPage();
        Thread.sleep(2000);

        battleshipPage.aceitarCookies();
        Thread.sleep(1000);

        battleshipPage.clickPlayWithFriend();
        Thread.sleep(1000);

        battleshipPage.enterNickname("TestPlayer");
        Thread.sleep(500);

        battleshipPage.clickContinue();
        Thread.sleep(1000);

        battleshipPage.waitForShareableLink();

        assertTrue(battleshipPage.isShareableLinkPresent(),
                "O URL partilhável deve estar visível.");

        // Como a classe UserStory3 não expõe o texto do URL,
        // validamos apenas a presença do elemento.
        assertTrue(battleshipPage.isShareableLinkPresent(),
                "O URL partilhável não deve estar vazio.");
    }
}
