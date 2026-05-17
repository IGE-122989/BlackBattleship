package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * <b>Page Object Class — UserStory3</b>
 *
 * <p>Encapsula todas as interações com a página do jogo Battleship em
 * <a href="https://papergames.io/en/battleship">papergames.io/en/battleship</a>
 * relativas ao cenário de teste <em>UserStoryTest3</em>.</p>
 *
 * <p>Este cenário cobre o fluxo de <b>criação de uma partida com um amigo</b>:
 * <ol>
 *   <li>Abrir a página do Battleship.</li>
 *   <li>Clicar em <em>Play with a friend</em>.</li>
 *   <li>Registar um nickname de convidado (<em>TestPlayer</em>).</li>
 *   <li>Confirmar o registo clicando em <em>Continue</em>.</li>
 *   <li>Aguardar e verificar a criação do URL partilhável.</li>
 *   <li>Copiar o URL gerado para a área de transferência.</li>
 * </ol>
 * </p>
 *
 * <p>Seguindo o padrão <em>Page Object Model (POM)</em>, esta classe isola
 * todos os localizadores CSS / XPath e os métodos de interação, mantendo
 * o código de teste completamente separado dos detalhes de implementação
 * da interface gráfica.</p>
 *
 * @author  TestSuite_122989 (gerado a partir do ficheiro Selenium IDE)
 * @version 1.0
 */
public class UserStory3 {

    // -----------------------------------------------------------------------
    // Campos
    // -----------------------------------------------------------------------

    /** Instância do WebDriver utilizada em todas as operações de página. */
    private final WebDriver driver;

    /**
     * Tempo máximo de espera (em segundos) para condições explícitas.
     * Utilizado internamente em {@link #waitForShareableLink()}.
     */
    private static final int DEFAULT_TIMEOUT_SECONDS = 10;

    // -----------------------------------------------------------------------
    // Localizadores
    // -----------------------------------------------------------------------

    /**
     * URL base da aplicação sob teste.
     */
    private static final String BASE_URL = "https://papergames.io/en/battleship";

    /**
     * Localizador CSS do botão <em>Play with a friend</em>.
     * Seleciona o primeiro filho {@code .w-100} que contém um {@code .btn-lg}
     * com um elemento {@code .flex-grow-1}.
     */
    private static final By BTN_PLAY_WITH_FRIEND =
            By.cssSelector(".w-100:nth-child(1) > .btn-lg .flex-grow-1");

    /**
     * Localizador CSS do campo de texto de nickname do convidado
     * (diálogo de registo rápido).
     */
    private static final By INPUT_NICKNAME =
            By.cssSelector(".input-xl");

    /**
     * Localizador CSS do botão <em>Continue</em> no diálogo de registo
     * de convidado.
     */
    private static final By BTN_CONTINUE =
            By.cssSelector(".p-3 > .btn");

    /**
     * Localizador CSS da área que contém o URL partilhável gerado
     * após a criação da sala de jogo.
     * Utilizado tanto para aguardar a sua visibilidade como para
     * verificar a sua presença.
     */
    private static final By COPY_TEXT_LINK =
            By.cssSelector(".copy-text");

    /**
     * Localizador CSS do ícone de copiar URL (ícone Font Awesome {@code fa-copy}).
     */
    private static final By BTN_COPY_URL =
            By.cssSelector(".fa-copy");

    // -----------------------------------------------------------------------
    // Construtor
    // -----------------------------------------------------------------------

    /**
     * Constrói uma nova instância da Page Object associada ao driver fornecido.
     *
     * @param driver instância ativa do {@link WebDriver} a utilizar nas
     *               interações com o browser; não pode ser {@code null}
     */
    public UserStory3(WebDriver driver) {
        this.driver = driver;
    }

    // -----------------------------------------------------------------------
    // Métodos de página
    // -----------------------------------------------------------------------

    /**
     * Navega para a página do Battleship e define as dimensões da janela
     * do browser para 1051 × 797 píxeis, conforme o cenário de teste.
     *
     * <p>Equivale aos comandos Selenium IDE:
     * {@code open} + {@code setWindowSize}.</p>
     */
    public void openPage() {
        driver.get(BASE_URL);
        driver.manage().window().setSize(
                new org.openqa.selenium.Dimension(1051, 797));
    }

    /**
     * Clica no botão <em>Play with a friend</em> da página principal,
     * abrindo o diálogo de registo de convidado.
     *
     * <p>Localizador: {@code css=.w-100:nth-child(1) > .btn-lg .flex-grow-1}</p>
     */
    public void clickPlayWithFriend() {
        driver.findElement(BTN_PLAY_WITH_FRIEND).click();
    }

    /**
     * Escreve o nickname indicado no campo de texto do diálogo de
     * registo de convidado.
     *
     * <p>Localizador: {@code css=.input-xl}</p>
     *
     * @param nickname texto a inserir no campo de nickname;
     *                 no cenário de teste o valor é {@code "TestPlayer"}
     */
    public void enterNickname(String nickname) {
        WebElement input = driver.findElement(INPUT_NICKNAME);
        input.clear();
        input.sendKeys(nickname);
    }

    /**
     * Clica no botão <em>Continue</em> do diálogo de registo, submetendo
     * o nickname e iniciando a criação da sala de jogo.
     *
     * <p>Localizador: {@code css=.p-3 > .btn}</p>
     */
    public void clickContinue() {
        driver.findElement(BTN_CONTINUE).click();
    }

    /**
     * Aguarda até que o elemento com o URL partilhável fique visível na página,
     * com um timeout de {@value #DEFAULT_TIMEOUT_SECONDS} segundos.
     *
     * <p>Localizador: {@code css=.copy-text}</p>
     *
     * @throws org.openqa.selenium.TimeoutException se o elemento não ficar
     *         visível dentro do tempo limite
     */
    public void waitForShareableLink() {
        WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
        wait.until(ExpectedConditions.visibilityOfElementLocated(COPY_TEXT_LINK));
    }

    /**
     * Verifica se o elemento com o URL partilhável está presente no DOM.
     *
     * <p>Localizador: {@code css=.copy-text}</p>
     *
     * @return {@code true} se o elemento for encontrado; {@code false} caso contrário
     */
    public boolean isShareableLinkPresent() {
        return !driver.findElements(COPY_TEXT_LINK).isEmpty();
    }

    /**
     * Clica no ícone de copiar URL (<em>fa-copy</em>), copiando o link
     * partilhável para a área de transferência.
     *
     * <p>Localizador: {@code css=.fa-copy}</p>
     */
    public void clickCopyUrl() {
        driver.findElement(BTN_COPY_URL).click();
    }

    /**
     * Aceita o banner de cookies da Papergames.io, se estiver visível.
     */
    public void aceitarCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement consentButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(., 'Consent')]")
            ));
            consentButton.click();
            System.out.println("Cookies aceites com sucesso.");
        } catch (Exception e) {
            System.out.println("Banner de cookies não apareceu ou já foi fechado.");
        }
    }
}
