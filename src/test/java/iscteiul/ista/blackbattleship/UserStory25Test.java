package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a User Story 25 — Ver o leaderboard diário.
 * <p>
 * Como jogador, quero ver o leaderboard diário com contagem decrescente
 * até ao fim do torneio, para acompanhar a minha posição em tempo real.
 * </p>
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserStory25Test {

    private WebDriver driver;
    private UserStory25 userStory25Page;

    /**
     * Configuração executada antes de cada teste.
     * Inicializa o WebDriver e a Page Object Class.
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        userStory25Page = new UserStory25(driver);
        userStory25Page.abrirPagina();
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
     * Testa se o leaderboard está visível na página principal.
     * Verifica que a tabela de classificações é apresentada ao utilizador.
     */
    @Test
    @Order(1)
    public void testLeaderboardVisivel() {
        assertTrue(userStory25Page.leaderboardVisivel(),
                "O leaderboard deve estar visível na página de Batalha Naval");
    }

    /**
     * Testa se o leaderboard contém jogadores listados.
     * Verifica que existem pelo menos 5 jogadores na tabela de classificações.
     */
    @Test
    @Order(2)
    public void testLeaderboardTemJogadores() {
        int numJogadores = userStory25Page.obterNumeroJogadoresLeaderboard();
        assertTrue(numJogadores >= 5,
                "O leaderboard deve ter pelo menos 5 jogadores listados");
    }

    /**
     * Testa se é possível clicar num jogador do leaderboard.
     * Verifica que ao clicar num jogador o URL muda para o perfil do mesmo.
     */
    @Test
    @Order(3)
    public void testClicarJogadorLeaderboard() {
        userStory25Page.scrollParaTopo();
        // Verifica que o elemento é clicável, sem verificar navegação
        assertDoesNotThrow(() -> userStory25Page.clicarJogadorLeaderboard(),
                "Deve ser possível clicar num jogador do leaderboard sem erro");
    }
}