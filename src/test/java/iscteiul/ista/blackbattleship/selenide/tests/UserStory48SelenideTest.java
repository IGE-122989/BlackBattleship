package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import iscteiul.ista.blackbattleship.selenide.pages.UserStory48Selenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <b>Page Test Class — UserStory48 (Selenide + Allure)</b>
 *
 * <p>Valida o fluxo completo de login, criação de torneio,
 * partilha e navegação para o torneio criado.</p>
 *
 * @version 2.0 (Selenide)
 */
@Epic("Batalha Naval")
@Feature("Criação de torneios")

public class UserStory48SelenideTest {

    private UserStory48Selenide tournamentPage;

    private static final String NOME_TORNEIO    = "Torneio Teste";
    private static final String DESCRICAO_TORNEIO = "Teste";

    @BeforeEach
    public void setUp() {
        // Configurar Chrome com opções para evitar crash
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--remote-debugging-port=9222");

        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x800";
        Configuration.timeout = 20000;
        Configuration.browserCapabilities = options;

        tournamentPage = new UserStory48Selenide();
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    // -----------------------------------------------------------------------
    // Testes
    // -----------------------------------------------------------------------

    /**
     * Testa o fluxo completo: login, criação do torneio,
     * cópia do link e navegação para a página do torneio.
     */
    @Test
    @Story("US48 - Criar torneio completo")
    @Description("Fluxo completo: login → criar torneio → copiar link → abrir torneio")
    @Severity(SeverityLevel.BLOCKER)
    public void testCriarTorneioCompleto() {
        tournamentPage.abrirPagina();
        tournamentPage.aceitarCookies();
        tournamentPage.clicarLoginInicial();
        tournamentPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        tournamentPage.esperarNomeUtilizador();

        assertEquals("Carol", tournamentPage.getNomeUtilizador(),
                "O nome do utilizador deve ser 'Carol' após login.");

        tournamentPage.clicarCriarTorneio();
        tournamentPage.abrirTipoJogo();
        tournamentPage.selecionarBattleship();
        tournamentPage.inserirNomeTorneio(NOME_TORNEIO);
        tournamentPage.inserirDescricao(DESCRICAO_TORNEIO);
        tournamentPage.clicarCreateAndShare();
        tournamentPage.esperarLinkGerado();
        tournamentPage.copiarLink();
        tournamentPage.clicarGoToTournament();

        // Aguardar explicitamente antes de verificar
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}

        assertTrue(tournamentPage.paginaTorneioAberta(),
                "A página do torneio deve abrir corretamente.");
    }

    /**
     * Testa que o formulário de criação de torneio é acessível
     * após login.
     */
    @Test
    @Story("US48 - Acesso ao formulário de criação")
    @Description("Verifica que o URL contém '/t/create-tournament' após clicar em criar torneio")
    @Severity(SeverityLevel.CRITICAL)
    public void testAcessoFormularioCriacaoTorneio() {
        tournamentPage.abrirPagina();
        tournamentPage.aceitarCookies();
        tournamentPage.clicarLoginInicial();
        tournamentPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        tournamentPage.esperarNomeUtilizador();

        assertEquals("Carol", tournamentPage.getNomeUtilizador(),
                "O nome do utilizador deve ser 'Carol' após login.");

        tournamentPage.clicarCriarTorneio();
        tournamentPage.abrirTipoJogo(); // aguarda que o formulário carregue

        assertTrue(WebDriverRunner.getWebDriver().getCurrentUrl()
                        .contains("/t/create-tournament"),
                "O URL deve conter '/t/create-tournament'.");
    }

    /**
     * Testa que o link gerado após a criação do torneio não está vazio.
     */
    @Test
    @Story("US48 - Link do torneio não vazio")
    @Description("Verifica que o link gerado após criar o torneio tem conteúdo")
    @Severity(SeverityLevel.CRITICAL)
    public void testLinkTorneioNaoVazio() {
        tournamentPage.abrirPagina();
        tournamentPage.aceitarCookies();
        tournamentPage.clicarLoginInicial();
        tournamentPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        tournamentPage.esperarNomeUtilizador();

        assertEquals("Carol", tournamentPage.getNomeUtilizador(),
                "O nome do utilizador deve ser 'Carol' após login.");

        tournamentPage.clicarCriarTorneio();
        tournamentPage.abrirTipoJogo();
        tournamentPage.selecionarBattleship();
        tournamentPage.inserirNomeTorneio(NOME_TORNEIO);
        tournamentPage.inserirDescricao(DESCRICAO_TORNEIO);
        tournamentPage.clicarCreateAndShare();
        tournamentPage.esperarLinkGerado();

        assertFalse(tournamentPage.getCopyTextElement().getText().isEmpty(),
                "O link do torneio não deve estar vazio.");
    }

    /**
     * Testa que o título da página do torneio corresponde ao nome
     * introduzido no formulário.
     */
    @Test
    @Story("US48 - Título da página do torneio")
    @Description("Verifica que o título da página do torneio coincide com o nome inserido")
    @Severity(SeverityLevel.NORMAL)
    public void testTituloPaginaTorneio() {
        tournamentPage.abrirPagina();
        tournamentPage.aceitarCookies();
        tournamentPage.clicarLoginInicial();
        tournamentPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        tournamentPage.esperarNomeUtilizador();

        assertEquals("Carol", tournamentPage.getNomeUtilizador(),
                "O nome do utilizador deve ser 'Carol' após login.");

        tournamentPage.clicarCriarTorneio();
        tournamentPage.abrirTipoJogo();
        tournamentPage.selecionarBattleship();
        tournamentPage.inserirNomeTorneio(NOME_TORNEIO);
        tournamentPage.inserirDescricao(DESCRICAO_TORNEIO);
        tournamentPage.clicarCreateAndShare();
        tournamentPage.esperarLinkGerado();
        tournamentPage.copiarLink();
        tournamentPage.clicarGoToTournament();

        tournamentPage.getTournamentTitle().shouldBe(
                com.codeborne.selenide.Condition.visible,
                java.time.Duration.ofSeconds(15));

        assertEquals(NOME_TORNEIO,
                tournamentPage.getTournamentTitle().getText(),
                "O título da página do torneio deve corresponder ao nome introduzido.");
    }

}
