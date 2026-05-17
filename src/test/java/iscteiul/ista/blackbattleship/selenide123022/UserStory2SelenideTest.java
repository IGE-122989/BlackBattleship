package iscteiul.ista.blackbattleship.selenide123022;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Page Test Class para a UserStory2 (Selenide).
 * Testa o fluxo de iniciar uma partida contra o robot,
 * preenchendo o nickname "rita" e clicando em Continue.
 *
 * @author Rita (123022)
 */
public class UserStory2SelenideTest {

    private UserStory2Selenide userStory2Page;

    /**
     * Configuração inicial antes de todos os testes.
     * Define o browser como Chrome e o tamanho da janela.
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
        userStory2Page = new UserStory2Selenide();
        userStory2Page.openPage();
    }

    /**
     * Fecha o browser após cada teste.
     */
    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    /**
     * Testa que é possível iniciar uma partida contra o robot
     * introduzindo um nickname e clicando em Continue.
     */
    @Test
    void testPlayVsRobotWithNickname() {
        userStory2Page.clickPlayVsRobot();
        userStory2Page.enterNickname("rita");
        Assertions.assertTrue(userStory2Page.isContinueButtonVisible(),
                "O botão Continue deve estar visível após preencher o nickname");
        userStory2Page.clickContinue();
    }
}