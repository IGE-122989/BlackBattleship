package iscteiul.ista.blackbattleship.selenide.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

/**
 * <b>Page Object Class — UserStory48 (Selenide)</b>
 *
 * <p>Encapsula todas as operações necessárias para o fluxo de criação
 * de um torneio após login.</p>
 *
 * <p>Fluxo coberto:</p>
 * <ol>
 *   <li>Abrir página Battleship.</li>
 *   <li>Fazer login.</li>
 *   <li>Clicar em "Criar Torneio".</li>
 *   <li>Selecionar tipo de jogo (Battleship).</li>
 *   <li>Inserir nome e descrição.</li>
 *   <li>Clicar em "Create and share".</li>
 *   <li>Copiar link e abrir torneio.</li>
 * </ol>
 *
 * @version 2.0 (Selenide)
 */

public class UserStory48Selenide {

    // -----------------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------------

    /** URL da página inicial (Battleship). */
    public static final String URL = "https://papergames.io/en/battleship";

    // -----------------------------------------------------------------------
    // Localizadores (SelenideElement)
    // -----------------------------------------------------------------------

    /** Botão Login na página inicial. */
    private final SelenideElement botaoLoginInicial =
            $(".btn-outline-dark");

    /** Campo email no popup de login. */
    private final SelenideElement campoEmail =
            $("#mat-input-serverApp0");

    /** Campo password no popup de login. */
    private final SelenideElement campoPassword =
            $("#mat-input-serverApp1");

    /** Botão de submissão do login. */
    private final SelenideElement botaoLogin =
            $x("//button[.//span[contains(text(),'Login')]]");

    /** Nome do utilizador autenticado. */
    private final SelenideElement nomeUtilizador =
            $(".name-credit div");

    /** Botão "Criar Torneio". */
    private final SelenideElement botaoCriarTorneio =
            $("a[href*='create-tournament']");

    /** Caixa de seleção do tipo de jogo. */
    private final SelenideElement caixaTipoJogo =
            $("#mat-select-value-serverApp0");

    /** Opção Battleship no dropdown. */
    private final SelenideElement opcaoBattleship =
            $("#mat-option-serverApp0 .fw-bold");

    /** Campo nome do torneio. */
    private final SelenideElement campoNomeTorneio =
            $("#mat-input-serverApp2");

    /** Campo descrição do torneio. */
    private final SelenideElement campoDescricao =
            $("#mat-input-serverApp3");

    /** Botão "Create and share". */
    private final SelenideElement botaoCreateAndShare =
            $(".btn");

    /** Elemento com o link gerado do torneio. */
    private final SelenideElement linkGerado =
            $(".copy-text");

    /** Botão de copiar o link. */
    private final SelenideElement botaoCopiar =
            $(".copy-text fa-icon");

    /** Botão "Go to tournament". */
    private final SelenideElement botaoGoToTournament =
            $(".p-3 > .btn");

    /** Título (h1) da página do torneio. */
    private final SelenideElement tournamentTitle =
            $("mat-toolbar h1");

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
     * Clica no botão Login da página inicial.
     */
    @Step("Clicar em Login")
    public void clicarLoginInicial() {
        botaoLoginInicial.shouldBe(Condition.visible).click();
    }

    /**
     * Preenche email e password e clica em Login.
     *
     * @param email    email do utilizador
     * @param password password do utilizador
     */
    @Step("Fazer login com {email}")
    public void fazerLogin(String email, String password) {
        campoEmail.shouldBe(Condition.visible).sendKeys(email);
        campoPassword.sendKeys(password);
        botaoLogin.shouldBe(Condition.enabled).click();

        // Aguarda que o redirect pós-login complete
        try { Thread.sleep(3000); } catch (InterruptedException e) { }
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
     * Clica em "Criar Torneio".
     */
    @Step("Clicar em Criar Torneio")
    public void clicarCriarTorneio() {
        botaoCriarTorneio.shouldBe(Condition.visible, Duration.ofSeconds(20)).click();
    }

    /**
     * Abre a caixa de seleção do tipo de jogo e aguarda que fique clicável.
     */
    @Step("Abrir dropdown tipo de jogo")
    public void abrirTipoJogo() {
        caixaTipoJogo.shouldBe(Condition.enabled, Duration.ofSeconds(30)).click();
    }

    /**
     * Seleciona a opção Battleship no dropdown.
     */
    @Step("Selecionar Battleship")
    public void selecionarBattleship() {
        opcaoBattleship.shouldBe(Condition.visible).click();
    }

    /**
     * Preenche o nome do torneio.
     *
     * @param nome nome a inserir
     */
    @Step("Inserir nome do torneio: {nome}")
    public void inserirNomeTorneio(String nome) {
        campoNomeTorneio.shouldBe(Condition.visible).sendKeys(nome);
    }

    /**
     * Preenche a descrição do torneio.
     *
     * @param descricao descrição a inserir
     */
    @Step("Inserir descrição do torneio")
    public void inserirDescricao(String descricao) {
        campoDescricao.shouldBe(Condition.visible).sendKeys(descricao);
    }

    /**
     * Clica em "Create and share".
     */
    @Step("Clicar em Create and Share")
    public void clicarCreateAndShare() {
        botaoCreateAndShare.shouldBe(Condition.enabled).click();
    }

    /**
     * Aguarda que o link do torneio seja gerado e fique visível.
     */
    @Step("Aguardar link do torneio")
    public void esperarLinkGerado() {
        linkGerado.shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    /**
     * Obtém o elemento do link partilhável.
     *
     * @return SelenideElement do link
     */
    public SelenideElement getCopyTextElement() {
        return linkGerado;
    }

    /**
     * Copia o link do torneio.
     */
    @Step("Copiar link do torneio")
    public void copiarLink() {
        linkGerado.shouldBe(Condition.visible).click();
    }

    /**
     * Clica em "Go to tournament".
     */
    @Step("Clicar em Go to Tournament")
    public void clicarGoToTournament() {
        botaoGoToTournament.shouldBe(Condition.visible).click();
    }

    /**
     * Verifica se a página do torneio abriu corretamente.
     *
     * @return {@code true} se o título do torneio estiver presente
     */
    @Step("Verificar página do torneio")
    public boolean paginaTorneioAberta() {
        // shouldBe(visible) aguarda automaticamente até ao timeout
        // em vez de exists() que verifica imediatamente
        try {
            tournamentTitle.shouldBe(Condition.visible, Duration.ofSeconds(20));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém o elemento do título da página do torneio.
     *
     * @return SelenideElement do título h1
     */
    public SelenideElement getTournamentTitle() {
        return tournamentTitle;
    }

}
