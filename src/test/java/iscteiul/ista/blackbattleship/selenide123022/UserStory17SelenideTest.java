package iscteiul.ista.blackbattleship.selenide123022;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Test Class para a UserStory17 (Selenide).
 * Testa que o botão "Play Online" está disponível
 * e que é possível clicar nele.
 *
 * @author Rita (123022)
 */
public class UserStory17SelenideTest {

    private UserStory17Selenide userStory17Page;

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
        userStory17Page = new UserStory17Selenide();
        userStory17Page.openPage();
    }

    /**
     * Fecha o browser após cada teste.
     */
    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    /**
     * Testa que o botão "Play Online" está visível na página principal.
     */
    @Test
    void testPlayOnlineButtonIsVisible() {
        Assertions.assertTrue(userStory17Page.isPlayOnlineButtonVisible(),
                "O botão Play Online deve estar visível na página principal");
    }

    /**
     * Testa que é possível clicar no botão "Play Online".
     */
    @Test
    void testClickPlayOnline() {
        userStory17Page.clickPlayOnline();
    }
}
