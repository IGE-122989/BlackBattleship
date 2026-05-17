package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.qameta.allure.*;
import iscteiul.ista.blackbattleship.selenide.pages.UserStory39Selenide;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <b>Page Test Class — UserStory39 (Selenide + Allure)</b>
 *
 * <p>Valida o fluxo completo de compra de moedas virtuais na loja
 * Papergames.io:</p>
 * <p><i>"Como jogador, quero comprar moedas virtuais na loja, para adquirir
 * itens de personalização."</i></p>
 *
 * @version 2.0 (Selenide)
 */
@Epic("Batalha Naval")
@Feature("Loja de moedas virtuais")

public class UserStory39SelenideTest {


    private UserStory39Selenide shopPage;

    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x800";
        Configuration.timeout = 15000;
        shopPage = new UserStory39Selenide();
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    // -----------------------------------------------------------------------
    // Testes
    // -----------------------------------------------------------------------

    /**
     * Testa o fluxo completo: navegar até à loja, validar pacotes,
     * fazer login e verificar popup de pagamento.
     */
    @Test
    @Story("US39 - Comprar moedas virtuais")
    @Description("Fluxo completo: Shop → Coins → login → popup de pagamento Xsolla")
    @Severity(SeverityLevel.BLOCKER)
    public void testComprarMoedasVirtuaisCompleto() {
        shopPage.abrirPagina();
        shopPage.aceitarCookies();
        shopPage.clicarShop();
        shopPage.clicarCoins();
        shopPage.aguardarPacotesMoedas();

        assertTrue(shopPage.pacotesMoedasVisiveis(),
                "Os pacotes de moedas devem estar visíveis.");
        assertTrue(shopPage.pacoteMinimoVisivel(),
                "O pacote de USD 1 deve estar presente.");
        assertTrue(shopPage.pacoteMaximoVisivel(),
                "O pacote de USD 99 deve estar presente.");

        shopPage.clicarComprarPacoteMinimo();
        shopPage.fazerLogin("cfrla@iscte-iul.pt", "SeleniumTest");
        shopPage.esperarNomeUtilizador();

        assertEquals("Carol", shopPage.getNomeUtilizador(),
                "O nome do utilizador autenticado deve ser 'Carol'.");

        shopPage.clicarComprarPacoteMinimo();

        // Aguardar que o Xsolla carregue após o login
        try { Thread.sleep(5000); } catch (InterruptedException ignored) {}

        assertTrue(shopPage.popupPagamentoVisivel(),
                "O popup de pagamento deve estar visível.");
    }

    /**
     * Testa o acesso direto à página de moedas virtuais.
     */
    @Test
    @Story("US39 - Acesso direto à loja de moedas")
    @Description("Verifica que a página de moedas é acessível diretamente pelo URL")
    @Severity(SeverityLevel.NORMAL)
    public void testAcessoDiretoLojaCoins() {
        shopPage.abrirPaginaCoins();
        shopPage.aceitarCookies();
        shopPage.aguardarPacotesMoedas();

        assertTrue(com.codeborne.selenide.WebDriverRunner
                        .getWebDriver().getCurrentUrl().contains("/shop/virtual-coins"),
                "O URL deve conter '/shop/virtual-coins'.");

        assertTrue(shopPage.pacotesMoedasVisiveis(),
                "Os pacotes de moedas devem estar visíveis.");
    }

    /**
     * Testa que os preços dos pacotes extremos estão corretos.
     */
    @Test
    @Story("US39 - Preços dos pacotes")
    @Description("Verifica que o pacote mínimo custa USD 1 e o máximo USD 99")
    @Severity(SeverityLevel.CRITICAL)
    public void testPrecosPacotesExtremos() {
        shopPage.abrirPaginaCoins();
        shopPage.aceitarCookies();
        shopPage.aguardarPacotesMoedas();

        assertTrue(shopPage.getTextoBotaoPacoteMinimo()
                        .contains(UserStory39Selenide.PRECO_PACOTE_MINIMO),
                "O pacote mínimo deve custar USD 1.");

        assertTrue(shopPage.getTextoBotaoPacoteMaximo()
                        .contains(UserStory39Selenide.PRECO_PACOTE_MAXIMO),
                "O pacote máximo deve custar USD 99.");
    }

    /**
     * Testa que a gama completa de pacotes está presente.
     */
    @Test
    @Story("US39 - Gama completa de pacotes")
    @Description("Verifica que os pacotes mínimo e máximo estão presentes com os preços corretos")
    @Severity(SeverityLevel.NORMAL)
    public void testGamaPacotesCompleta() {
        shopPage.abrirPaginaCoins();
        shopPage.aceitarCookies();
        shopPage.aguardarPacotesMoedas();

        assertTrue(shopPage.pacoteMinimoVisivel(),
                "O pacote de USD 1 deve estar presente.");
        assertTrue(shopPage.pacoteMaximoVisivel(),
                "O pacote de USD 99 deve estar presente.");
        assertTrue(shopPage.getTextoBotaoPacoteMinimo()
                .contains(UserStory39Selenide.PRECO_PACOTE_MINIMO));
        assertTrue(shopPage.getTextoBotaoPacoteMaximo()
                .contains(UserStory39Selenide.PRECO_PACOTE_MAXIMO));
    }

}
