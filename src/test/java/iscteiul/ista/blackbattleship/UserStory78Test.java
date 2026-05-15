package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a User Story 78 — Consultar a política de privacidade.
 * <p>
 * Como utilizador, quero consultar a política de privacidade da plataforma,
 * para perceber como os meus dados são tratados.
 * </p>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory78Test {

    private WebDriver driver;
    private UserStory78 userStory78Page;

    /**
     * Configuração executada antes de cada teste.
     * Inicializa o WebDriver e a Page Object Class.
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        userStory78Page = new UserStory78(driver);
        userStory78Page.abrirPagina();
    }

    /**
     * Encerramento executado após cada teste.
     * Fecha o browser e termina a sessão do WebDriver.
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Testa se o link "Privacy" está visível na página.
     * Verifica que o utilizador consegue encontrar o link para a política de privacidade.
     */
    @Test
    @Order(1)
    public void testLinkPrivacyVisivel() {
        userStory78Page.scrollParaTopo();
        assertTrue(userStory78Page.linkPrivacyVisivel(),
                "O link 'Privacy' deve estar visível na página");
    }

    /**
     * Testa a navegação para a página de política de privacidade.
     * Verifica que ao clicar no link "Privacy", o utilizador é redirecionado
     * para a página correta da política de privacidade.
     */
    @Test
    @Order(2)
    public void testNavegacaoParaPrivacyPolicy() throws InterruptedException {
        userStory78Page.scrollParaTopo();
        userStory78Page.clicarPrivacyPolicy();
        Thread.sleep(2000);
        assertTrue(userStory78Page.esPaginaPrivacidade(),
                "O URL deve conter '/en/blog/privacy-policy' após clicar no link Privacy");
    }

    /**
     * Testa se a página de política de privacidade carrega corretamente.
     * Verifica que o URL final é diferente da página inicial de Batalha Naval.
     */
    @Test
    @Order(3)
    public void testPaginaPrivacidadeCarrega() throws InterruptedException {
        userStory78Page.scrollParaTopo();
        userStory78Page.clicarPrivacyPolicy();
        Thread.sleep(2000);
        assertNotEquals("https://papergames.io/en/battleship", userStory78Page.getUrlAtual(),
                "O URL deve mudar após navegar para a política de privacidade");
    }
}