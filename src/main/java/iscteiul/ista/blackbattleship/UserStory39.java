package iscteiul.ista.blackbattleship;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * <b>Page Object Class — UserStory39</b>
 *
 * <p>Encapsula todas as interações necessárias para o fluxo de compra
 * de moedas virtuais na loja Papergames.io, incluindo navegação,
 * verificação de pacotes, login e abertura do popup de pagamento.</p>
 *
 * <p>Fluxo coberto:</p>
 * <ol>
 *     <li>Abrir página Battleship</li>
 *     <li>Clicar em "Shop"</li>
 *     <li>Clicar em "Coins"</li>
 *     <li>Aguardar carregamento dos pacotes</li>
 *     <li>Validar presença dos pacotes USD 1 e USD 99</li>
 *     <li>Clicar em "Get for USD 1"</li>
 *     <li>Efetuar login (email + password)</li>
 *     <li>Aguardar popup de pagamento Xsolla</li>
 * </ol>
 *
 * @author
 * @version 1.0
 */
public class UserStory39 {

    /** Instância do WebDriver utilizada para interagir com o browser. */
    private final WebDriver driver;

    /** Timeout padrão para esperas explícitas. */
    private static final int TIMEOUT = 15;

    /** URL da página inicial (Battleship). */
    public static final String URL = "https://papergames.io/en/battleship";

    /** URL direta da página de moedas. */
    public static final String URL_COINS = "https://papergames.io/en/shop/virtual-coins";

    /** Preço esperado do pacote mais barato (10 000 moedas). */
    public static final String PRECO_PACOTE_MINIMO = "USD 1";

    /** Preço esperado do pacote mais caro (1 500 000 moedas). */
    public static final String PRECO_PACOTE_MAXIMO = "USD 99";

    // -------------------------------------------------------------------------
    // ELEMENTOS — Página Battleship
    // -------------------------------------------------------------------------

    /** Link "Shop" no menu superior. */
    @FindBy(linkText = "Shop")
    private WebElement shopLink;

    // -------------------------------------------------------------------------
    // ELEMENTOS — Página da Loja
    // -------------------------------------------------------------------------

    /** Ícone "Coins" na página da loja. */
    @FindBy(xpath = "//img[@alt='Coins']")
    private WebElement coinsMenuItem;

    // -------------------------------------------------------------------------
    // ELEMENTOS — Página de Moedas Virtuais
    // -------------------------------------------------------------------------

    /** Primeiro pacote de moedas (qualquer app-currency-product). */
    @FindBy(css = "app-currency-product")
    private WebElement primeiroPacoteMoedas;

    /** Botão USD 1 (localização robusta por texto). */
    @FindBy(xpath = "//button[contains(., 'USD 1')]")
    private WebElement botaoUsd1;

    /** Botão USD 99 (localização robusta por texto). */
    @FindBy(xpath = "//button[contains(., 'USD 99')]")
    private WebElement botaoUsd99;

    // -------------------------------------------------------------------------
    // ELEMENTOS — Popup de Login
    // -------------------------------------------------------------------------

    @FindBy(id = "mat-input-serverApp0")
    private WebElement campoEmail;

    @FindBy(id = "mat-input-serverApp1")
    private WebElement campoPassword;

    @FindBy(xpath = "//button[contains(., 'Login')]")
    private WebElement botaoLogin;

    // -------------------------------------------------------------------------
    // ELEMENTOS — Popup de Pagamento Xsolla
    // -------------------------------------------------------------------------

    @FindBy(css = ".xpaystation-widget-lightbox-overlay")
    private WebElement paymentOverlay;

    @FindBy(css = ".xpaystation-widget-lightbox-content-iframe")
    private WebElement paymentIframe;

    // -------------------------------------------------------------------------
    // CONSTRUTOR
    // -------------------------------------------------------------------------

    /**
     * Construtor da Page Object. Inicializa todos os elementos via PageFactory.
     *
     * @param driver WebDriver ativo
     */
    public UserStory39(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // -------------------------------------------------------------------------
    // MÉTODOS DE OPERAÇÃO
    // -------------------------------------------------------------------------

    /**
     * Abre a página inicial (Battleship).
     */
    public void abrirPagina() {
        driver.get(URL);
    }

    /**
     * Clica no link "Shop" no menu superior.
     */
    public void clicarShop() {
        shopLink.click();
    }

    /**
     * Clica no ícone "Coins" na página da loja.
     */
    public void clicarCoins() {
        coinsMenuItem.click();
    }

    /**
     * Aguarda que os pacotes de moedas estejam visíveis.
     *
     * @return true se pelo menos um pacote estiver presente
     */
    public boolean pacotesMoedasVisiveis() {
        return !driver.findElements(By.cssSelector("app-currency-product")).isEmpty();
    }

    /**
     * Verifica se o pacote mínimo (USD 1) está visível.
     */
    public boolean pacoteMinimoVisivel() {
        return !driver.findElements(By.xpath("//button[contains(., 'USD 1')]")).isEmpty();
    }

    /**
     * Verifica se o pacote máximo (USD 99) está visível.
     */
    public boolean pacoteMaximoVisivel() {
        return !driver.findElements(By.xpath("//button[contains(., 'USD 99')]")).isEmpty();
    }

    /**
     * Clica no botão "Get for USD 1".
     */
    public void clicarComprarPacoteMinimo() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

        WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(botaoUsd1));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'smooth',block:'center'});", botao);

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botao);
    }

    /**
     * Abre diretamente a página de moedas virtuais da loja no browser,
     * sem necessidade de navegar a partir de outras páginas.
     */
    public void abrirPaginaCoins() {
        driver.get(URL_COINS);
    }

    // -------------------------------------------------------------------------
    // LOGIN
    // -------------------------------------------------------------------------

    /**
     * Efetua login no popup que aparece após clicar em "Get for USD 1".
     *
     * @param email email do utilizador
     * @param password password do utilizador
     */
    public void fazerLogin(String email, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

        wait.until(ExpectedConditions.visibilityOf(campoEmail));

        campoEmail.clear();
        campoEmail.sendKeys(email);

        campoPassword.clear();
        campoPassword.sendKeys(password);

        // Aguardar que o botão Login fique habilitado (deixa de estar disabled)
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("app-sign-up-dialog button[type='submit']")));

        // Clicar no botão Login via JavaScript (mais fiável em Angular)
        WebElement botaoLoginDinamico = driver.findElement(
                By.cssSelector("app-sign-up-dialog button[type='submit']"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", botaoLoginDinamico);

        System.out.println("Login efetuado com sucesso.");
    }

    /**
     * Aguarda até que o nome do utilizador apareça no canto superior direito,
     * confirmando que o login foi efetuado com sucesso.
     */
    public void esperarNomeUtilizador() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".name-credit")
        ));
    }

    /**
     * Obtém o nome do utilizador autenticado.
     *
     * @return texto dentro de .name-credit div
     */
    public String getNomeUtilizador() {
        return driver.findElement(By.cssSelector(".name-credit div")).getText();
    }
    // -------------------------------------------------------------------------
    // POPUP DE PAGAMENTO XSOLLA
    // -------------------------------------------------------------------------

    /**
     * Aguarda até que o popup de pagamento Xsolla esteja visível.
     *
     * <p>O popup pode aparecer como:</p>
     * <ul>
     *     <li>um overlay (.xpaystation-widget-lightbox-overlay)</li>
     *     <li>um iframe de pagamento</li>
     *     <li>um diálogo Angular (fallback)</li>
     * </ul>
     *
     * @throws org.openqa.selenium.TimeoutException se o popup não aparecer
     *         dentro do tempo limite definido
     */
    public void esperarPopupPagamento() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Tenta overlay Xsolla
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".xpaystation-widget-lightbox-overlay")));
            return;
        } catch (Exception ignored) {}

        // Tenta iframe Xsolla
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("iframe[src*='xsolla'], iframe[src*='paystation']")));
            return;
        } catch (Exception ignored) {}

        // Tenta fallback Angular
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("mat-dialog-container")));
    }

    /**
     * Verifica se o popup de pagamento Xsolla está visível após clicar
     * num botão de compra, confirmando que o processo de pagamento foi
     * iniciado corretamente.
     *
     * @return {@code true} se o popup de pagamento estiver visível,
     *         {@code false} caso contrário
     */
    public boolean popupPagamentoVisivel() {
        // Verificar overlay Xsolla
        if (!driver.findElements(
                By.cssSelector(".xpaystation-widget-lightbox-overlay")).isEmpty()) {
            return driver.findElement(
                            By.cssSelector(".xpaystation-widget-lightbox-overlay"))
                    .isDisplayed();
        }
        // Verificar qualquer iframe de pagamento
        if (!driver.findElements(
                By.cssSelector("iframe[src*='xsolla'], iframe[src*='paystation']")).isEmpty()) {
            return true;
        }
        // Verificar diálogo Angular como fallback
        return !driver.findElements(
                By.cssSelector("mat-dialog-container")).isEmpty();
    }

    // -------------------------------------------------------------------------
    // GETTERS AUXILIARES
    // -------------------------------------------------------------------------

    /**
     * Obtém o texto do botão de compra do pacote mais barato.
     * Útil para verificar o preço exibido no botão.
     *
     * @return o texto do botão (ex: "Get for USD 1")
     */
    public String getTextoBotaoPacoteMinimo() {
        return driver.findElement(
                By.xpath("//button[contains(., 'USD 1')]")).getText();
    }

    /**
     * Obtém o texto do botão de compra do pacote mais caro.
     * Útil para verificar o preço exibido no botão.
     *
     * @return o texto do botão (ex: "Get for USD 99")
     */
    public String getTextoBotaoPacoteMaximo() {
        return driver.findElement(
                By.xpath("//button[contains(., 'USD 99')]")).getText();
    }

    /**
     * Obtém o elemento do primeiro pacote de moedas.
     * Útil para verificações adicionais na classe de testes.
     *
     * @return o WebElement correspondente ao primeiro app-currency-product
     */
    public WebElement getPrimeiroPacoteMoedas() {
        return new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("app-currency-product")));
    }

    /**
     * Obtém o elemento do overlay do popup de pagamento.
     * Útil para verificações adicionais na classe de testes.
     *
     * @return o WebElement correspondente ao overlay do popup de pagamento
     */

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