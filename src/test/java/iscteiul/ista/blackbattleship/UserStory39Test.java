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
 * <b>Page Test Class — UserStory39</b>
 *
 * <p>Valida o fluxo completo de compra de moedas virtuais na loja
 * Papergames.io, conforme descrito na User Story 39:</p>
 *
 * <p><i>“Como jogador, quero comprar moedas virtuais na loja, para adquirir
 * itens de personalização (pacotes de 10 000 a 1 500 000 moedas,
 * com descontos progressivos até 34%).”</i></p>
 *
 * <p>Este teste utiliza exclusivamente os métodos definidos na
 * {@link UserStory39}, garantindo isolamento entre lógica de teste
 * e lógica de interação com a interface gráfica.</p>
 *
 * @author
 * @version 1.0
 * @see UserStory39
 */
public class UserStory39Test {

    /** Instância do WebDriver utilizada em cada teste. */
    private WebDriver driver;

    /** Page Object que encapsula as operações do fluxo da loja. */
    private UserStory39 shopPage;

    /** Timeout padrão para esperas explícitas. */
    private static final int TIMEOUT = 30;

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
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));

        driver.manage().deleteAllCookies();

        shopPage = new UserStory39(driver);
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
    // Teste principal — fluxo completo
    // -------------------------------------------------------------------------

    /**
     * Testa o fluxo completo da UserStory39:
     * <ol>
     *     <li>Abrir página Battleship</li>
     *     <li>Clicar em "Shop"</li>
     *     <li>Clicar em "Coins"</li>
     *     <li>Aguardar carregamento dos pacotes</li>
     *     <li>Validar pacotes USD 1 e USD 99</li>
     *     <li>Clicar em "Get for USD 1"</li>
     *     <li>Efetuar login</li>
     *     <li>Clicar novamente em "Get for USD 1"</li>
     *     <li>Aguardar popup Xsolla</li>
     * </ol>
     */
    @Test
    public void testComprarMoedasVirtuaisCompleto() throws InterruptedException {

        // 1 — Abrir página inicial
        shopPage.abrirPagina();
        Thread.sleep(2000);

        shopPage.aceitarCookies();

        // 2 — Clicar em "Shop"
        shopPage.clicarShop();
        Thread.sleep(3000);

        // 3 — Clicar em "Coins"
        shopPage.clicarCoins();
        Thread.sleep(1500);

        // 4 — Aguardar pacotes
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("app-currency-product")));
        Thread.sleep(1000);

        // 5 — Validar pacotes
        assertTrue(shopPage.pacotesMoedasVisiveis(),
                "Os pacotes de moedas devem estar visíveis.");

        assertTrue(shopPage.pacoteMinimoVisivel(),
                "O pacote de USD 1 deve estar presente.");

        assertTrue(shopPage.pacoteMaximoVisivel(),
                "O pacote de USD 99 deve estar presente.");

        Thread.sleep(2000);

        // 6 — Clicar em "Get for USD 1"
        shopPage.clicarComprarPacoteMinimo();
        Thread.sleep(2000);

        // 7 — Login
        shopPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        Thread.sleep(3000);

        // Esperar que o nome do utilizador apareça
        shopPage.esperarNomeUtilizador();

        // Validar que o login foi bem-sucedido
        assertEquals("Carol", shopPage.getNomeUtilizador(),
                "O nome do utilizador autenticado deve ser 'Carol'");

        // 8 — Clicar novamente após login
        shopPage.clicarComprarPacoteMinimo();
        Thread.sleep(5000);

        // 9 — Verificar popup Xsolla
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("iframe[src*='paystation']")));

        assertTrue(shopPage.popupPagamentoVisivel(),
                "O popup de pagamento deve estar visível.");

        Thread.sleep(2000);
    }

    // -------------------------------------------------------------------------
    // Testes adicionais
    // -------------------------------------------------------------------------

    /**
     * Testa o acesso direto à página de moedas virtuais.
     */
    @Test
    public void testAcessoDiretoLojaCoins() throws InterruptedException {

        shopPage.abrirPaginaCoins();
        Thread.sleep(2000);

        shopPage.aceitarCookies();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("app-currency-product")));

        assertTrue(driver.getCurrentUrl().contains("/shop/virtual-coins"),
                "O URL deve conter '/shop/virtual-coins'.");

        assertTrue(shopPage.pacotesMoedasVisiveis(),
                "Os pacotes de moedas devem estar visíveis.");

        Thread.sleep(1000);
    }

    /**
     * Testa que os preços dos pacotes extremos estão corretos.
     */
    @Test
    public void testPrecosPacotesExtremos() throws InterruptedException {

        shopPage.abrirPaginaCoins();

        Thread.sleep(2000);

        shopPage.aceitarCookies();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("app-currency-product")));

        Thread.sleep(1000);

        assertTrue(shopPage.getTextoBotaoPacoteMinimo()
                        .contains(UserStory39.PRECO_PACOTE_MINIMO),
                "O pacote mínimo deve custar USD 1.");

        assertTrue(shopPage.getTextoBotaoPacoteMaximo()
                        .contains(UserStory39.PRECO_PACOTE_MAXIMO),
                "O pacote máximo deve custar USD 99.");
    }

    /**
     * Testa que a gama completa de pacotes está presente.
     */
    @Test
    public void testGamaPacotesCompleta() throws InterruptedException {

        shopPage.abrirPaginaCoins();
        Thread.sleep(2000);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("app-currency-product")));

        Thread.sleep(1000);

        assertTrue(shopPage.pacoteMinimoVisivel(),
                "O pacote de USD 1 deve estar presente.");

        assertTrue(shopPage.pacoteMaximoVisivel(),
                "O pacote de USD 99 deve estar presente.");

        assertTrue(shopPage.getTextoBotaoPacoteMinimo()
                .contains(UserStory39.PRECO_PACOTE_MINIMO));

        assertTrue(shopPage.getTextoBotaoPacoteMaximo()
                .contains(UserStory39.PRECO_PACOTE_MAXIMO));

        Thread.sleep(1000);
    }
}

