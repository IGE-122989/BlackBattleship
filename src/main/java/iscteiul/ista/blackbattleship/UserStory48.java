package iscteiul.ista.blackbattleship;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * <b>Page Object Class — UserStory48</b>
 *
 * <p>Encapsula todas as operações necessárias para o fluxo de criação
 * de um torneio após login, conforme o cenário gravado no Selenium IDE.</p>
 *
 * <p>Fluxo coberto:</p>
 * <ol>
 *     <li>Abrir página Battleship</li>
 *     <li>Clicar em Login</li>
 *     <li>Inserir email e password</li>
 *     <li>Validar nome do utilizador</li>
 *     <li>Clicar em “Criar Torneio”</li>
 *     <li>Selecionar tipo de jogo</li>
 *     <li>Inserir nome e descrição</li>
 *     <li>Clicar em “Create and share”</li>
 *     <li>Copiar link</li>
 *     <li>Abrir torneio</li>
 * </ol>
 */
public class UserStory48 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public static final String URL = "https://papergames.io/en/battleship";

    // -------------------------------------------------------------------------
    // ELEMENTOS — Login
    // -------------------------------------------------------------------------

    @FindBy(css = ".btn-outline-dark")
    private WebElement botaoLoginInicial;

    @FindBy(id = "mat-input-serverApp0")
    private WebElement campoEmail;

    @FindBy(id = "mat-input-serverApp1")
    private WebElement campoPassword;

    @FindBy(css = ".w-100:nth-child(1) > .btn-secondary > .front")
    private WebElement botaoLogin;

    @FindBy(css = ".name-credit div")
    private WebElement nomeUtilizador;

    // -------------------------------------------------------------------------
    // ELEMENTOS — Criar Torneio
    // -------------------------------------------------------------------------

    /**
     * Título (h1) na toolbar da página do torneio, que exibe o nome do
     * torneio criado.
     * Localizador CSS: mat-toolbar h1
     */
    @FindBy(css = "mat-toolbar h1")
    private WebElement tournamentTitle;

    @FindBy(css = ".position-relative > .front")
    private WebElement botaoCriarTorneio;

    @FindBy(css = "#mat-select-value-serverApp0")
    private WebElement caixaTipoJogo;

    @FindBy(css = "#mat-option-serverApp0 .fw-bold")
    private WebElement opcaoBattleship;

    @FindBy(id = "mat-input-serverApp2")
    private WebElement campoNomeTorneio;

    @FindBy(id = "mat-input-serverApp3")
    private WebElement campoDescricao;

    @FindBy(css = ".btn")
    private WebElement botaoCreateAndShare;

    @FindBy(css = ".copy-text")
    private WebElement linkGerado;

    @FindBy(css = ".fa-copy")
    private WebElement botaoCopiar;

    @FindBy(css = ".p-3 > .btn")
    private WebElement botaoGoToTournament;

    // -------------------------------------------------------------------------
    // CONSTRUTOR
    // -------------------------------------------------------------------------

    public UserStory48(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE OPERAÇÃO
    // -------------------------------------------------------------------------

    /** Abre a página inicial. */
    public void abrirPagina() {
        driver.get(URL);
    }

    /** Clica no botão Login da página inicial. */
    public void clicarLoginInicial() {
        botaoLoginInicial.click();
    }

    /** Preenche email e password e clica em Login. */
    public void fazerLogin(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(campoEmail));
        campoEmail.sendKeys(email);
        campoPassword.sendKeys(password);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botaoLogin);
    }

    /** Aguarda até que o nome do utilizador apareça. */
    public void esperarNomeUtilizador() {
        wait.until(ExpectedConditions.visibilityOf(nomeUtilizador));
    }

    /** Obtém o nome do utilizador autenticado. */
    public String getNomeUtilizador() {
        return nomeUtilizador.getText();
    }

    /** Clica em “Criar Torneio”. */
    public void clicarCriarTorneio() {
        botaoCriarTorneio.click();
    }

    /** Abre a caixa de seleção do tipo de jogo. */
    public void abrirTipoJogo() {
        caixaTipoJogo.click();
    }

    /** Seleciona o jogo Battleship. */
    public void selecionarBattleship() {
        opcaoBattleship.click();
    }

    /** Preenche o nome do torneio. */
    public void inserirNomeTorneio(String nome) {
        campoNomeTorneio.sendKeys(nome);
    }

    /** Preenche a descrição do torneio. */
    public void inserirDescricao(String descricao) {
        campoDescricao.sendKeys(descricao);
    }

    /** Clica em “Create and share”. */
    public void clicarCreateAndShare() {
        botaoCreateAndShare.click();
    }

    /** Aguarda que o link seja gerado. */
    public void esperarLinkGerado() {
        wait.until(ExpectedConditions.visibilityOf(linkGerado));
    }

    /** Copia o link do torneio. */
    public void copiarLink() {
        botaoCopiar.click();
    }

    /** Clica em “Go to tournament”. */
    public void clicarGoToTournament() {
        botaoGoToTournament.click();
    }

    /** Verifica se a página do torneio abriu. */
    public boolean paginaTorneioAberta() {
        return !driver.findElements(By.cssSelector("mat-toolbar h1")).isEmpty();
    }

    /**
     * Obtém o elemento do título da página do torneio.
     * Útil para verificações adicionais na classe de testes.
     *
     * @return o WebElement correspondente ao título do torneio
     */
    public WebElement getTournamentTitle() {
        return tournamentTitle;
    }


    /**
     * Obtém o elemento do link partilhável do torneio.
     * Útil para verificações adicionais na classe de testes.
     *
     * @return o WebElement correspondente ao link partilhável
     */
    public WebElement getCopyTextElement() {
        return linkGerado;
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
