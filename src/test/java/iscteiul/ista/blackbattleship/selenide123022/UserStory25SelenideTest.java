package iscteiul.ista.blackbattleship.selenide123022;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Test Class para a UserStory25 (Selenide).
 * Testa a visibilidade e interação com o leaderboard da página principal.
 *
 * @author Rita (123022)
 */
public class UserStory25SelenideTest {

    private UserStory25Selenide userStory25Page;

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
        userStory25Page = new UserStory25Selenide();
        userStory25Page.openPage();
        userStory25Page.scrollToTop();
    }

    /**
     * Fecha o browser após cada teste.
     */
    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    /**
     * Testa que o leaderboard está visível na página principal.
     */
    @Test
    void testLeaderboardIsVisible() {
        Assertions.assertTrue(userStory25Page.isLeaderboardVisible(),
                "O leaderboard deve estar visível na página principal");
    }

    /**
     * Testa que é possível clicar num jogador do leaderboard.
     */
    @Test
    void testClickLeaderboardPlayer() {
        userStory25Page.clickLeaderboardPlayer();
    }
}