package iscteiul.ista.blackbattleship.selenide.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.$$;

/**
 * <b>Page Object Class — UserStory77 (Selenide)</b>
 *
 * <p>Encapsula todas as interações com as páginas envolvidas no fluxo
 * de consulta do changelog da plataforma Papergames.io.</p>
 *
 * <p>Fluxo coberto:</p>
 * <ol>
 *   <li>Abrir página da Batalha Naval.</li>
 *   <li>Clicar no link "Changelog" no menu de navegação.</li>
 *   <li>Verificar título e versões listadas na página do Changelog.</li>
 * </ol>
 *
 * @version 2.0 (Selenide)
 */

public class UserStory77Selenide {

    // -----------------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------------

    /** URL da página da Batalha Naval (ponto de entrada do fluxo). */
    public static final String URL = "https://papergames.io/en/battleship";

    /** URL direta da página do Changelog. */
    public static final String URL_CHANGELOG = "https://papergames.io/en/changelog";

    /** Texto esperado no título da página do Changelog. */
    public static final String TITULO_ESPERADO = "Changelog";

    // -----------------------------------------------------------------------
    // Localizadores (SelenideElement)
    // -----------------------------------------------------------------------

    /**
     * Link "Changelog" no menu de navegação da página da Batalha Naval.
     */
    private final SelenideElement changelogLink =
            $x("//a[contains(text(),'Changelog')]");

    /**
     * Título principal (h1) da página do Changelog,
     * identificado pelo atributo id="changelog".
     */
    private final SelenideElement changelogTitle =
            $("#changelog");

    /**
     * Primeiro elemento de versão (h2) na lista do Changelog.
     */
    private final SelenideElement primeiraVersao =
            $("h2");

    // -----------------------------------------------------------------------
    // Métodos de página
    // -----------------------------------------------------------------------

    /**
     * Abre a página da Batalha Naval no browser.
     */
    @Step("Abrir página Battleship")
    public void abrirPagina() {
        open(URL);
    }

    /**
     * Abre diretamente a página do Changelog.
     */
    @Step("Abrir página Changelog diretamente")
    public void abrirPaginaChangelog() {
        open(URL_CHANGELOG);
    }

    /**
     * Aceita o banner de cookies, se estiver visível.
     */
    @Step("Aceitar cookies")
    public void aceitarCookies() {
        try {
            $x("//button[contains(., 'Consent')]")
                    .shouldBe(Condition.visible, Duration.ofSeconds(8))
                    .click();
        } catch (Exception e) {
            System.out.println("Banner de cookies não apareceu ou já foi fechado.");
        }
    }

    /**
     * Clica no link "Changelog" no menu de navegação.
     */
    @Step("Clicar no link Changelog")
    public void clicarChangelog() {
        changelogLink.shouldBe(Condition.visible).click();
    }

    /**
     * Verifica se o título da página do Changelog está visível.
     *
     * @return {@code true} se o título estiver visível
     */
    @Step("Verificar título da página Changelog")
    public boolean tituloPaginaVisivel() {
        return changelogTitle.isDisplayed();
    }

    /**
     * Obtém o texto do título principal (h1) da página do Changelog.
     *
     * @return texto do título, esperado ser "Changelog"
     */
    @Step("Obter texto do título")
    public String getTextTitulo() {
        return changelogTitle.shouldBe(Condition.visible).getText();
    }

    /**
     * Verifica se existe pelo menos uma versão listada no Changelog.
     *
     * @return {@code true} se existir pelo menos um elemento h2
     */
    @Step("Verificar existência de versões listadas")
    public boolean existemVersoesListadas() {
        return $$("h2").size() > 0 && primeiraVersao.isDisplayed();
    }

    /**
     * Obtém o elemento do título do Changelog.
     * Útil para esperas explícitas na classe de testes.
     *
     * @return SelenideElement do título h1
     */
    public SelenideElement getChangelogTitle() {
        return changelogTitle;
    }

    /**
     * Obtém o texto da primeira versão listada no Changelog.
     *
     * @return texto do primeiro elemento h2
     */
    @Step("Obter texto da primeira versão")
    public String getTextoPrimeiraVersao() {
        return primeiraVersao.shouldBe(Condition.visible, Duration.ofSeconds(10)).getText();
    }

}
