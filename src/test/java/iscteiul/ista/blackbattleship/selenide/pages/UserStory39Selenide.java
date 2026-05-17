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
 * <b>Page Object Class — UserStory39 (Selenide)</b>
 *
 * <p>Encapsula todas as interações para o fluxo de compra de moedas
 * virtuais na loja Papergames.io.</p>
 *
 * <p>Fluxo coberto:</p>
 * <ol>
 *   <li>Abrir página Battleship.</li>
 *   <li>Clicar em "Shop".</li>
 *   <li>Clicar em "Coins".</li>
 *   <li>Validar pacotes USD 1 e USD 99.</li>
 *   <li>Clicar em "Get for USD 1".</li>
 *   <li>Efetuar login.</li>
 *   <li>Aguardar popup de pagamento Xsolla.</li>
 * </ol>
 *
 * @version 2.0 (Selenide)
 */

public class UserStory39Selenide {

    // -----------------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------------

    /** URL da página inicial (Battleship). */
    public static final String URL = "https://papergames.io/en/battleship";

    /** URL direta da página de moedas. */
    public static final String URL_COINS = "https://papergames.io/en/shop/virtual-coins";

    /** Preço esperado do pacote mais barato. */
    public static final String PRECO_PACOTE_MINIMO = "USD 1";

    /** Preço esperado do pacote mais caro. */
    public static final String PRECO_PACOTE_MAXIMO = "USD 99";

    // -----------------------------------------------------------------------
    // Localizadores (SelenideElement)
    // -----------------------------------------------------------------------

    /** Link "Shop" no menu superior. */
    private final SelenideElement shopLink =
            $x("//a[.//span[contains(text(),'Shop')] or contains(text(),'Shop')]");

    /** Ícone "Coins" na página da loja. */
    private final SelenideElement coinsMenuItem =
            $x("//img[@alt='Coins']");

    /** Botão "Get for USD 1". */
    private final SelenideElement botaoUsd1 =
            $x("//button[contains(., 'USD 1')]");

    /** Botão "Get for USD 99". */
    private final SelenideElement botaoUsd99 =
            $x("//button[contains(., 'USD 99')]");

    /** Campo email no popup de login. */
    private final SelenideElement campoEmail =
            $("#mat-input-serverApp0");

    /** Campo password no popup de login. */
    private final SelenideElement campoPassword =
            $("#mat-input-serverApp1");

    /** Botão de submissão do login. */
    private final SelenideElement botaoLogin =
            $("app-sign-up-dialog button[type='submit']");

    /** Nome do utilizador autenticado. */
    private final SelenideElement nomeUtilizador =
            $(".name-credit div");

    // -----------------------------------------------------------------------
    // Métodos de página
    // -----------------------------------------------------------------------

    /**
     * Abre a página inicial (Battleship).
     */
    @Step("Abrir página Battleship")
    public void abrirPagina() {
        open(URL);
    }

    /**
     * Abre diretamente a página de moedas virtuais.
     */
    @Step("Abrir página de moedas virtuais")
    public void abrirPaginaCoins() {
        open(URL_COINS);
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
     * Clica no link "Shop" no menu superior.
     */
    @Step("Clicar em Shop")
    public void clicarShop() {
        shopLink.shouldBe(Condition.visible).click();
    }

    /**
     * Clica no ícone "Coins" na página da loja.
     */
    @Step("Clicar em Coins")
    public void clicarCoins() {
        coinsMenuItem.shouldBe(Condition.visible).click();
    }

    /**
     * Aguarda que os pacotes de moedas estejam visíveis.
     */
    @Step("Aguardar pacotes de moedas")
    public void aguardarPacotesMoedas() {
        $("app-currency-product").shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    /**
     * Verifica se pelo menos um pacote de moedas está presente.
     *
     * @return {@code true} se existir pelo menos um pacote
     */
    @Step("Verificar pacotes de moedas visíveis")
    public boolean pacotesMoedasVisiveis() {
        return $$("app-currency-product").size() > 0;
    }

    /**
     * Verifica se o pacote mínimo (USD 1) está visível.
     *
     * @return {@code true} se o botão USD 1 existir
     */
    @Step("Verificar pacote mínimo (USD 1)")
    public boolean pacoteMinimoVisivel() {
        return botaoUsd1.exists();
    }

    /**
     * Verifica se o pacote máximo (USD 99) está visível.
     *
     * @return {@code true} se o botão USD 99 existir
     */
    @Step("Verificar pacote máximo (USD 99)")
    public boolean pacoteMaximoVisivel() {
        return botaoUsd99.exists();
    }

    /**
     * Obtém o texto do botão do pacote mínimo.
     *
     * @return texto do botão (ex: "Get for USD 1")
     */
    @Step("Obter texto do botão pacote mínimo")
    public String getTextoBotaoPacoteMinimo() {
        return botaoUsd1.shouldBe(Condition.visible).getText();
    }

    /**
     * Obtém o texto do botão do pacote máximo.
     *
     * @return texto do botão (ex: "Get for USD 99")
     */
    @Step("Obter texto do botão pacote máximo")
    public String getTextoBotaoPacoteMaximo() {
        return botaoUsd99.shouldBe(Condition.visible).getText();
    }

    /**
     * Clica no botão "Get for USD 1".
     */
    @Step("Clicar em 'Get for USD 1'")
    public void clicarComprarPacoteMinimo() {
        botaoUsd1.shouldBe(Condition.visible, Condition.enabled).click();
    }

    /**
     * Efetua login no popup que aparece após clicar num pacote.
     *
     * @param email    email do utilizador
     * @param password password do utilizador
     */
    @Step("Fazer login com {email}")
    public void fazerLogin(String email, String password) {
        campoEmail.shouldBe(Condition.visible).clear();
        campoEmail.sendKeys(email);
        campoPassword.clear();
        campoPassword.sendKeys(password);
        botaoLogin.shouldBe(Condition.enabled).click();
    }

    /**
     * Aguarda até que o nome do utilizador apareça, confirmando o login.
     */
    @Step("Aguardar confirmação de login")
    public void esperarNomeUtilizador() {
        nomeUtilizador.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    /**
     * Obtém o nome do utilizador autenticado.
     *
     * @return nome do utilizador
     */
    @Step("Obter nome do utilizador")
    public String getNomeUtilizador() {
        return nomeUtilizador.getText();
    }

    /**
     * Verifica se o popup de pagamento Xsolla está visível.
     *
     * @return {@code true} se o popup estiver presente
     */
    @Step("Verificar popup de pagamento Xsolla")
    public boolean popupPagamentoVisivel() {
        try {
            $(".xpaystation-widget-lightbox-overlay")
                    .shouldBe(Condition.visible, Duration.ofSeconds(20));
            return true;
        } catch (Exception e1) {
            try {
                $x("//iframe[contains(@src,'xsolla') or contains(@src,'paystation')]")
                        .shouldBe(Condition.exist, Duration.ofSeconds(5));
                return true;
            } catch (Exception e2) {
                try {
                    $("mat-dialog-container")
                            .shouldBe(Condition.visible, Duration.ofSeconds(5));
                    return true;
                } catch (Exception e3) {
                    return false;
                }
            }
        }
    }

}
