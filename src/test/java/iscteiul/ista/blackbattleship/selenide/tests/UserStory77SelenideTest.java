package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import iscteiul.ista.blackbattleship.selenide.pages.UserStory77Selenide;
import io.qameta.allure.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.visible;
import static org.junit.jupiter.api.Assertions.*;

/**
 * <b>Page Test Class — UserStory77 (Selenide + Allure)</b>
 *
 * <p>Valida o fluxo de consulta do changelog da plataforma:</p>
 * <p><i>"Como utilizador, quero consultar o changelog da plataforma,
 * para saber quais as funcionalidades novas e correções de erros
 * introduzidas em cada versão."</i></p>
 *
 * @version 2.0 (Selenide)
 */
@Epic("Batalha Naval")
@Feature("Changelog da plataforma")

public class UserStory77SelenideTest {

    private UserStory77Selenide changelogPage;

    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x800";
        Configuration.timeout = 10000;
        changelogPage = new UserStory77Selenide();
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    // -----------------------------------------------------------------------
    // Testes
    // -----------------------------------------------------------------------

    /**
     * Testa a navegação para o Changelog a partir da página da Batalha Naval.
     */
    @Test
    @Story("US77 - Navegar para o Changelog")
    @Description("Clica no link Changelog no menu e verifica título e versões listadas")
    @Severity(SeverityLevel.BLOCKER)
    public void testConsultarChangelogViaNavegacao() {
        changelogPage.abrirPagina();
        changelogPage.aceitarCookies();
        changelogPage.clicarChangelog();

        changelogPage.getChangelogTitle().shouldBe(visible);

        assertEquals(UserStory77Selenide.TITULO_ESPERADO,
                changelogPage.getTextTitulo(),
                "O título da página deve ser 'Changelog'.");

        assertTrue(changelogPage.existemVersoesListadas(),
                "Deve existir pelo menos uma versão listada no Changelog.");
    }

    /**
     * Testa o acesso direto à página do Changelog pelo URL.
     */
    @Test
    @Story("US77 - Acesso direto ao Changelog")
    @Description("Verifica que a página do Changelog é acessível diretamente pelo URL")
    @Severity(SeverityLevel.NORMAL)
    public void testAcessoDiretoChangelog() {
        changelogPage.abrirPaginaChangelog();

        changelogPage.getChangelogTitle().shouldBe(visible);

        assertTrue(WebDriverRunner.getWebDriver().getCurrentUrl().contains("/changelog"),
                "O URL deve conter '/changelog'.");

        assertTrue(changelogPage.tituloPaginaVisivel(),
                "O título da página do Changelog deve estar visível.");
    }

    /**
     * Testa que o título da página do Changelog tem o texto correto.
     */
    @Test
    @Story("US77 - Título correto do Changelog")
    @Description("Verifica que o h1 com id='changelog' contém exatamente o texto 'Changelog'")
    @Severity(SeverityLevel.CRITICAL)
    public void testTituloPaginaChangelog() {
        changelogPage.abrirPaginaChangelog();

        changelogPage.getChangelogTitle().shouldBe(visible);

        assertEquals(UserStory77Selenide.TITULO_ESPERADO,
                changelogPage.getTextTitulo(),
                "O título deve ser exatamente '" + UserStory77Selenide.TITULO_ESPERADO + "'.");
    }

    /**
     * Testa que o Changelog contém versões listadas e que a primeira
     * versão não tem texto vazio.
     */
    @Test
    @Story("US77 - Changelog contém versões")
    @Description("Verifica que existem versões listadas e que o texto da primeira não está vazio")
    @Severity(SeverityLevel.NORMAL)
    public void testChangelogContemVersoes() {
        changelogPage.abrirPaginaChangelog();

        changelogPage.getChangelogTitle().shouldBe(visible);

        assertTrue(changelogPage.existemVersoesListadas(),
                "O Changelog deve conter pelo menos uma versão listada.");

        String textoPrimeiraVersao = changelogPage.getTextoPrimeiraVersao();
        assertFalse(textoPrimeiraVersao.isEmpty(),
                "O texto da primeira versão não deve estar vazio.");
    }

}
