package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a User Story 17 — Jogar como convidado.
 * <p>
 * Como visitante, quero jogar como convidado sem criar conta,
 * para experimentar a plataforma sem compromisso.
 * </p>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory17Test {

    private WebDriver driver;
    private UserStory17 userStory17Page;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        userStory17Page = new UserStory17(driver);
        userStory17Page.abrirPagina();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Testa se a página de Batalha Naval abre corretamente.
     */
    @Test
    @Order(1)
    public void testPaginaAbreCorretamente() {
        assertTrue(userStory17Page.getUrlAtual().contains("battleship"),
                "O URL deve conter 'battleship'");
    }

    /**
     * Testa se o botão Play online está disponível sem conta.
     */
    @Test
    @Order(2)
    public void testPlayOnlineDisponivelSemConta() {
        assertTrue(userStory17Page.playOnlineVisivel(),
                "O botão Play online deve estar visível sem necessidade de conta");
    }

    /**
     * Testa se ao clicar em Play online aparece o modal de nickname,
     * confirmando que é possível jogar como convidado sem criar conta.
     */
    @Test
    @Order(3)
    public void testClicarPlayOnlineMostraModalNickname() {
        userStory17Page.clicarPlayOnline();
        assertTrue(userStory17Page.modalNicknameVisivel(),
                "Deve aparecer o modal de nickname ao clicar em Play online como convidado");
    }
}