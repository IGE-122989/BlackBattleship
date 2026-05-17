package iscteiul.ista.blackbattleship.selenide123022;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Test Class para a UserStory78 (Selenide).
 * Testa a navegação para a página de Privacy Policy
 * através do link no rodapé.
 *
 * @author Rita (123022)
 */
public class UserStory78SelenideTest {

    private UserStory78Selenide userStory78Page;

    /**
     * Configuração inicial antes de todos os testes.
     */
    @BeforeAll
    static void setupAll() {
        Configuration.browser = "chrome";
        Configuration.browserSize = "1050x652";
        Configuration.timeout = 8000;
    }

    /**
     * Inicializa a página antes de cada teste.
     */
    @BeforeEach
    void setup() {
        userStory78Page = new UserStory78Selenide();
        userStory78Page.openPage();
        userStory78Page.scrollToTop();
    }

    /**
     * Fecha o browser após cada teste.
     */
    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    /**
     * Testa que o link "Privacy" está visível no rodapé.
     */
    @Test
    void testPrivacyLinkIsVisible() {
        Assertions.assertTrue(userStory78Page.isPrivacyLinkVisible(),
                "O link Privacy deve estar visível no rodapé da página");
    }

    /**
     * Testa que ao clicar em "Privacy" o utilizador é redirecionado
     * para a página de política de privacidade.
     */
    @Test
    void testNavigationToPrivacyPolicy() {
        userStory78Page.clickPrivacyLink();
        Assertions.assertTrue(
                userStory78Page.getCurrentUrl().contains("privacy-policy"),
                "O URL deve conter 'privacy-policy' após clicar no link Privacy"
        );
    }
}