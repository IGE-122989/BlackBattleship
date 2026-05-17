package iscteiul.ista.blackbattleship.selenide.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import iscteiul.ista.blackbattleship.selenide.pages.UserStory3Selenide;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * <b>Page Test Class — UserStory3 (Selenide + Allure)</b>
 *
 * <p>Valida o comportamento descrito na User Story 3:</p>
 * <p><i>"Como jogador, quero jogar Batalha Naval com um amigo através
 * de uma ligação partilhada, para jogar com quem eu quiser sem necessidade
 * de registo."</i></p>
 *
 * @version 2.0 (Selenide)
 */
@Epic("Batalha Naval")
@Feature("Jogar com um amigo")

public class UserStory3SelenideTest {


    private UserStory3Selenide battleshipPage;

    @BeforeEach
    public void setUp() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1280x800";
        Configuration.timeout = 10000;
        battleshipPage = new UserStory3Selenide();
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }

    // -----------------------------------------------------------------------
    // Testes
    // -----------------------------------------------------------------------

    /**
     * Testa o fluxo completo: abrir página, criar sala,
     * gerar URL partilhável e copiá-lo.
     */
    @Test
    @Story("US3 - Gerar URL partilhável")
    @Description("Fluxo completo: abrir página, criar sala, gerar e copiar URL partilhável")
    @Severity(SeverityLevel.BLOCKER)
    public void testGerarUrlPartilhavel() {
        battleshipPage.openPage();
        battleshipPage.aceitarCookies();
        battleshipPage.clickPlayWithFriend();
        battleshipPage.enterNickname("TestPlayer");
        battleshipPage.clickContinue();
        battleshipPage.waitForShareableLink();

        assertTrue(battleshipPage.isShareableLinkPresent(),
                "O URL partilhável deve estar visível após criar a sala.");

        battleshipPage.clickCopyUrl();
    }

    /**
     * Testa que a página Battleship abre corretamente.
     */
    @Test
    @Story("US3 - Abrir página")
    @Description("Verifica que a página Battleship abre e o URL contém 'battleship'")
    @Severity(SeverityLevel.NORMAL)
    public void testAbrirPagina() {
        battleshipPage.openPage();

        assertTrue(WebDriverRunner.getWebDriver().getCurrentUrl().contains("battleship"),
                "O URL deve conter 'battleship'.");
    }

    /**
     * Testa que o URL partilhável gerado não está vazio.
     */
    @Test
    @Story("US3 - URL não vazio")
    @Description("Verifica que o URL partilhável está presente após criar a sala")
    @Severity(SeverityLevel.CRITICAL)
    public void testUrlPartilhavelNaoVazio() {
        battleshipPage.openPage();
        battleshipPage.aceitarCookies();
        battleshipPage.clickPlayWithFriend();
        battleshipPage.enterNickname("TestPlayer");
        battleshipPage.clickContinue();
        battleshipPage.waitForShareableLink();

        assertTrue(battleshipPage.isShareableLinkPresent(),
                "O URL partilhável não deve estar vazio.");
    }

}
