package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Class para a UserStory3 (US77).
 *
 * <p>User Story: Como utilizador, quero consultar o changelog da plataforma,
 * para saber quais as funcionalidades novas e correções de erros introduzidas
 * em cada versão.</p>
 *
 * <p>Esta classe segue o padrão Page Object Model (POM), encapsulando todos
 * os localizadores CSS, XPath e por texto âncora (linkText) das páginas
 * envolvidas no fluxo de consulta do changelog, bem como as operações que
 * interagem com os seus elementos. Desta forma, eventuais alterações na
 * estrutura das páginas web apenas requerem alterações nesta classe, sem
 * impacto na classe de testes.</p>
 *
 * <p>Fluxo coberto:
 * <ol>
 *   <li>Página da Batalha Naval → clicar no link "Changelog" no rodapé</li>
 *   <li>Página do Changelog → verificar título e versões listadas</li>
 * </ol>
 * </p>
 *
 * @author TestSuite_122989
 * @version 1.0
 */
public class UserStory77 {

    /** Instância do WebDriver utilizada para interagir com o browser. */
    private WebDriver driver;

    /** URL da página da Batalha Naval (ponto de entrada do fluxo). */
    public static final String URL = "https://papergames.io/en/battleship";

    /** URL direta da página do Changelog. */
    public static final String URL_CHANGELOG = "https://papergames.io/en/changelog";

    /** Texto esperado no título da página do Changelog. */
    public static final String TITULO_ESPERADO = "Changelog";

    // -------------------------------------------------------------------------
    // Elementos da página da Batalha Naval (ponto de entrada)
    // -------------------------------------------------------------------------

    /**
     * Link "Changelog" no menu de navegação/rodapé da página da Batalha Naval.
     * Localizador: linkText=Changelog
     */
    @FindBy(linkText = "Changelog")
    private WebElement changelogLink;

    // -------------------------------------------------------------------------
    // Elementos da página do Changelog
    // -------------------------------------------------------------------------

    /**
     * Título principal (h1) da página do Changelog.
     * Identificado pelo atributo id="changelog".
     * Localizador: id=changelog
     */
    @FindBy(id = "changelog")
    private WebElement changelogTitle;

    /**
     * Primeiro elemento de versão (h2) na lista do Changelog.
     * Cada versão é representada por um elemento h2 com o número
     * e data da versão (ex: "5.0.5 (2026-05-10)").
     * Localizador CSS: h2
     */
    @FindBy(css = "h2")
    private WebElement primeiraVersao;

    // -------------------------------------------------------------------------
    // Construtor
    // -------------------------------------------------------------------------

    /**
     * Construtor da classe. Inicializa o WebDriver e os elementos da página
     * através do PageFactory, que injeta os WebElements anotados com @FindBy.
     *
     * @param driver instância do WebDriver a utilizar
     */
    public UserStory77(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // -------------------------------------------------------------------------
    // Métodos de operação
    // -------------------------------------------------------------------------

    /**
     * Abre a página da Batalha Naval no browser.
     * Este é o ponto de entrada do fluxo gravado no Selenium IDE,
     * a partir do qual se navega para o Changelog.
     */
    public void abrirPagina() {
        driver.get(URL);
    }

    /**
     * Abre diretamente a página do Changelog no browser,
     * sem necessidade de navegar a partir da página da Batalha Naval.
     */
    public void abrirPaginaChangelog() {
        driver.get(URL_CHANGELOG);
    }

    /**
     * Clica no link "Changelog" presente no menu de navegação
     * ou rodapé da página, navegando para a página do Changelog.
     */
    public void clicarChangelog() {
        changelogLink.click();
    }

    /**
     * Verifica se o título da página do Changelog está visível,
     * confirmando que a página carregou corretamente.
     *
     * @return {@code true} se o título estiver visível,
     *         {@code false} caso contrário
     */
    public boolean tituloPaginaVisivel() {
        return changelogTitle.isDisplayed();
    }

    /**
     * Obtém o texto do título principal (h1) da página do Changelog.
     *
     * @return o texto do título, esperado ser "Changelog"
     */
    public String getTextTitulo() {
        return changelogTitle.getText();
    }

    /**
     * Verifica se existe pelo menos uma versão listada na página
     * do Changelog, identificada pela presença de um elemento h2.
     *
     * @return {@code true} se existir pelo menos uma versão listada,
     *         {@code false} caso contrário
     */
    public boolean existemVersoesListadas() {
        return primeiraVersao.isDisplayed();
    }

    /**
     * Obtém o elemento do título da página do Changelog.
     * Útil para verificações adicionais na classe de testes.
     *
     * @return o WebElement correspondente ao título h1 do Changelog
     */
    public org.openqa.selenium.WebElement getChangelogTitle() {
        return changelogTitle;
    }

    /**
     * Obtém o elemento da primeira versão listada no Changelog.
     * Útil para verificações adicionais na classe de testes.
     *
     * @return o WebElement correspondente ao primeiro h2 do Changelog
     */
    public WebElement getPrimeiraVersao() {
        return primeiraVersao;
    }
}
