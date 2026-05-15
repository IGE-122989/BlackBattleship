package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <b>Page Test Class — UserStory48</b>
 *
 * <p>Valida o fluxo completo de login, criação de torneio,
 * partilha e navegação para o torneio criado.</p>
 *
 * <p>Utiliza exclusivamente os métodos definidos em {@link UserStory48}.</p>
 */
public class UserStory48Test {

    /**
     * Instância do WebDriver utilizada em cada teste.
     */
    private WebDriver driver;

    /**
     * Page Object que encapsula as operações das páginas do fluxo de torneio.
     */
    private UserStory48 tournamentPage;

    /**
     * Objeto de espera explícita para aguardar condições específicas.
     */
    private WebDriverWait wait;

    /**
     * Tempo máximo de espera explícita (segundos).
     */
    private static final int TIMEOUT = 30;


    /**
     * Nome do torneio utilizado nos testes.
     */
    private static final String NOME_TORNEIO = "Torneio Teste";

    /**
     * Descrição do torneio utilizada nos testes.
     */
    private static final String DESCRICAO_TORNEIO = "Teste";


    /**
     * Tempo de pausa muito curta (ms) — simula o tempo entre teclas ao
     * escrever num campo de texto, ou entre ações muito rápidas.
     */
    private static final int PAUSA_MUITO_CURTA = 500;

    /**
     * Tempo de pausa curta (ms) — simula o tempo que um utilizador demora
     * a observar o resultado de uma ação simples (ex: clique num botão).
     */
    private static final int PAUSA_CURTA = 1000;

    /**
     * Tempo de pausa média (ms) — simula o tempo que um utilizador demora
     * a ler e processar informação antes de agir (ex: ler o link gerado).
     */
    private static final int PAUSA_MEDIA = 2000;

    /**
     * Tempo de pausa longa (ms) — simula o tempo que um utilizador demora
     * a observar e confirmar o resultado final (ex: página do torneio).
     */
    private static final int PAUSA_LONGA = 3000;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        tournamentPage = new UserStory48(driver);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    /**
     * Testa o fluxo completo da UserStory48.
     */
    @Test
    public void testCriarTorneioCompleto() throws InterruptedException {

        // 1 — Abrir página
        tournamentPage.abrirPagina();
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.aceitarCookies();
        Thread.sleep(PAUSA_MEDIA);

        // 2 — Login
        tournamentPage.clicarLoginInicial();
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        tournamentPage.esperarNomeUtilizador();

        assertEquals("Carol", tournamentPage.getNomeUtilizador(),
                "O nome do utilizador deve ser 'Carol' após login.");

        // 3 — Criar torneio
        tournamentPage.clicarCriarTorneio();
        Thread.sleep(PAUSA_CURTA);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#mat-select-value-serverApp0")
        ));

        tournamentPage.abrirTipoJogo();
        tournamentPage.selecionarBattleship();

        tournamentPage.inserirNomeTorneio(NOME_TORNEIO);
        tournamentPage.inserirDescricao(DESCRICAO_TORNEIO);

        tournamentPage.clicarCreateAndShare();
        tournamentPage.esperarLinkGerado();

        // 4 — Copiar link
        tournamentPage.copiarLink();
        Thread.sleep(PAUSA_CURTA);

        // 5 — Abrir torneio
        tournamentPage.clicarGoToTournament();
        Thread.sleep(PAUSA_MEDIA);

        assertTrue(tournamentPage.paginaTorneioAberta(),
                "A página do torneio deve abrir corretamente.");
    }

    /**
     * Testa que o formulário de criação de torneio é acessível a partir
     * da página da Batalha Naval.
     *
     * @throws InterruptedException se a thread for interrompida durante
     *                              as pausas de simulação humana
     */
    @Test
    public void testAcessoFormularioCriacaoTorneio() throws InterruptedException {

        // Abrir a página da Batalha Naval
        // Simula: utilizador a navegar para a página
        tournamentPage.abrirPagina();
        Thread.sleep(PAUSA_MEDIA);

        tournamentPage.aceitarCookies();
        Thread.sleep(PAUSA_MEDIA);

        // 2 — Login
        tournamentPage.clicarLoginInicial();
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        tournamentPage.esperarNomeUtilizador();

        assertEquals("Carol", tournamentPage.getNomeUtilizador(),
                "O nome do utilizador deve ser 'Carol' após login.");


        // Clicar em "Create tournament"
        // Simula: utilizador a localizar e clicar no botão
        tournamentPage.clicarCriarTorneio();
        Thread.sleep(PAUSA_MEDIA); // Aguarda a navegação e o carregamento do formulário

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#mat-select-value-serverApp0")
        ));

        // Verificar que navegou para a página de criação de torneio
        assertTrue(driver.getCurrentUrl().contains("/t/create-tournament"),
                "O URL deve conter '/t/create-tournament' após clicar em " +
                        "'Create tournament'");

        Thread.sleep(PAUSA_CURTA);
    }

    /**
     * Testa que o link gerado após a criação do torneio não está vazio.
     *
     * @throws InterruptedException se a thread for interrompida durante
     *                              as pausas de simulação humana
     */
    @Test
    public void testLinkTorneioNaoVazio() throws InterruptedException {

        // Abrir a página e navegar para o formulário
        tournamentPage.abrirPagina();
        Thread.sleep(PAUSA_MEDIA);

        tournamentPage.aceitarCookies();
        Thread.sleep(PAUSA_MEDIA);

        // 2 — Login
        tournamentPage.clicarLoginInicial();
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        tournamentPage.esperarNomeUtilizador();

        assertEquals("Carol", tournamentPage.getNomeUtilizador(),
                "O nome do utilizador deve ser 'Carol' após login.");


        tournamentPage.clicarCriarTorneio();
        Thread.sleep(PAUSA_CURTA);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#mat-select-value-serverApp0")
        ));

        tournamentPage.abrirTipoJogo();
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.selecionarBattleship();

        tournamentPage.inserirNomeTorneio(NOME_TORNEIO);
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.inserirDescricao(DESCRICAO_TORNEIO);
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.clicarCreateAndShare();
        Thread.sleep(PAUSA_CURTA);

        // Aguardar o link
        wait.until(ExpectedConditions.visibilityOf(
                tournamentPage.getCopyTextElement()));
        Thread.sleep(PAUSA_MEDIA); // Simula: utilizador a verificar o link

        // Verificar que o link não está vazio
        assertFalse(tournamentPage.getCopyTextElement().getText().isEmpty(),
                "O link do torneio não deve estar vazio");

        Thread.sleep(PAUSA_LONGA);
    }

    /**
     * Testa que o título da página do torneio corresponde ao nome
     * introduzido no formulário de criação.
     *
     * @throws InterruptedException se a thread for interrompida durante
     *                              as pausas de simulação humana
     */
    @Test
    public void testTituloPaginaTorneio() throws InterruptedException {

        // Criar o torneio
        tournamentPage.abrirPagina();
        Thread.sleep(PAUSA_MEDIA);

        tournamentPage.aceitarCookies();
        Thread.sleep(PAUSA_CURTA);

        // 2 — Login
        tournamentPage.clicarLoginInicial();
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        tournamentPage.esperarNomeUtilizador();

        assertEquals("Carol", tournamentPage.getNomeUtilizador(),
                "O nome do utilizador deve ser 'Carol' após login.");


        tournamentPage.clicarCriarTorneio();
        Thread.sleep(PAUSA_CURTA);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(300));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#mat-select-value-serverApp0")
        ));
        tournamentPage.abrirTipoJogo();
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.selecionarBattleship();

        tournamentPage.inserirNomeTorneio(NOME_TORNEIO);
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.inserirDescricao(DESCRICAO_TORNEIO);
        Thread.sleep(PAUSA_CURTA);

        tournamentPage.clicarCreateAndShare();
        Thread.sleep(PAUSA_CURTA);


        tournamentPage.esperarLinkGerado();
        Thread.sleep(PAUSA_MEDIA);

        tournamentPage.copiarLink();
        Thread.sleep(PAUSA_CURTA); // Simula: utilizador a confirmar a cópia

        tournamentPage.clicarGoToTournament();
        Thread.sleep(PAUSA_CURTA); // Simula: utilizador a aguardar a navegação

        // Aguardar a página do torneio
        wait.until(ExpectedConditions.visibilityOf(
                tournamentPage.getTournamentTitle()));
        Thread.sleep(PAUSA_MEDIA); // Simula: utilizador a ler o título da página

        // Verificar o título do torneio
        assertEquals(NOME_TORNEIO,
                tournamentPage.getTournamentTitle().getText(),
                "O título da página do torneio deve corresponder ao nome introduzido");

        Thread.sleep(PAUSA_LONGA);
    }
}

