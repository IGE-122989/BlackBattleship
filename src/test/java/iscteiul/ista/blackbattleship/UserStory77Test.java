package iscteiul.ista.blackbattleship;


import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Page Test Class para a UserStory77 (US77).
 *
 * <p>User Story: Como utilizador, quero consultar o changelog da plataforma,
 * para saber quais as funcionalidades novas e correções de erros introduzidas
 * em cada versão.</p>
 *
 * <p>Esta classe segue o padrão Page Object Model (POM), utilizando
 * exclusivamente os métodos definidos em {@link UserStory77} para interagir
 * com as páginas web. Toda a lógica de localização de elementos está
 * encapsulada na Page Object Class, tornando este código de testes simples,
 * legível e fácil de manter.</p>
 *
 * <p>Cenário de teste baseado no ficheiro TestSuite_122989.side,
 * teste UserStoryTest3.</p>
 *
 * @author TestSuite_122989
 * @version 1.0
 * @see UserStory77
 */
public class UserStory77Test {

    /** Instância do WebDriver utilizada em cada teste. */
    private WebDriver driver;

    /** Page Object que encapsula as operações das páginas do fluxo do Changelog. */
    private UserStory77 changelogPage;

    /** Tempo máximo de espera para elementos ficarem visíveis (em segundos). */
    private static final int TIMEOUT = 30;

    /**
     * Método de configuração executado antes de cada teste.
     *
     * <p>Inicializa o ChromeDriver, maximiza a janela, define o tempo de
     * espera implícita e instancia a Page Object Class.</p>
     */
    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));

        // Instanciar a Page Object Class
        changelogPage = new UserStory77(driver);
    }

    /**
     * Método de limpeza executado após cada teste.
     *
     * <p>Fecha o browser e liberta os recursos do WebDriver.</p>
     */
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    /**
     * Testa o cenário completo da UserStory3 (US77):
     * navegação para o Changelog a partir da página da Batalha Naval.
     *
     * <p>Passos do cenário (conforme TestSuite_122989.side - UserStoryTest3):
     * <ol>
     *   <li>Abrir a página da Batalha Naval</li>
     *   <li>Clicar no link "Changelog" no menu de navegação</li>
     *   <li>Aguardar que a página do Changelog carregue</li>
     *   <li>Verificar que o título é "Changelog"</li>
     *   <li>Verificar que existe pelo menos uma versão listada</li>
     * </ol>
     * </p>
     */
    @Test
    public void testConsultarChangelogViaNavegacao() throws InterruptedException {

        // Passo 1 — Abrir a página da Batalha Naval
        changelogPage.abrirPagina();
        Thread.sleep(1000); // Pausa para observar a página carregada

        // Passo 2 — Clicar no link "Changelog"
        changelogPage.clicarChangelog();
        Thread.sleep(1000); // Pausa para observar a navegação

        // Passo 3 — Aguardar que a página do Changelog carregue (Explicit Wait)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.visibilityOf(
                changelogPage.getChangelogTitle()));
        Thread.sleep(1000); // Pausa para observar a página carregada

        // Passo 4 — Verificar que o título é "Changelog"
        assertEquals(UserStory77.TITULO_ESPERADO,
                changelogPage.getTextTitulo(),
                "O título da página deve ser 'Changelog'");

        // Passo 5 — Verificar que existe pelo menos uma versão listada
        assertTrue(changelogPage.existemVersoesListadas(),
                "Deve existir pelo menos uma versão listada no Changelog");

        Thread.sleep(2000); // Pausa para observar o resultado final
    }

    /**
     * Testa o acesso direto à página do Changelog pelo URL.
     *
     * <p>Verifica que a página do Changelog é acessível diretamente
     * pelo seu URL, sem necessidade de navegar a partir de outra página.</p>
     */
    @Test
    public void testAcessoDiretoChangelog() throws InterruptedException {

        // Abrir diretamente a página do Changelog
        changelogPage.abrirPaginaChangelog();
        Thread.sleep(1000);

        // Aguardar que a página carregue
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.visibilityOf(
                changelogPage.getChangelogTitle()));

        // Verificar o URL atual
        assertTrue(driver.getCurrentUrl().contains("/changelog"),
                "O URL deve conter '/changelog'");

        // Verificar o título da página
        assertTrue(changelogPage.tituloPaginaVisivel(),
                "O título da página do Changelog deve estar visível");

        Thread.sleep(1000);
    }

    /**
     * Testa que o título da página do Changelog tem o texto correto.
     *
     * <p>Verifica que o elemento h1 com id="changelog" contém
     * exatamente o texto "Changelog".</p>
     */
    @Test
    public void testTituloPaginaChangelog() throws InterruptedException {

        // Abrir a página do Changelog
        changelogPage.abrirPaginaChangelog();

        // Aguardar o título
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.visibilityOf(
                changelogPage.getChangelogTitle()));
        Thread.sleep(1000);

        // Verificar o texto exato do título
        assertEquals(UserStory77.TITULO_ESPERADO,
                changelogPage.getTextTitulo(),
                "O título deve ser exatamente '" + UserStory77.TITULO_ESPERADO + "'");
    }

    /**
     * Testa que o Changelog contém versões listadas.
     *
     * <p>Verifica que a página do Changelog apresenta pelo menos
     * uma entrada de versão (elemento h2), confirmando que o conteúdo
     * foi carregado corretamente.</p>
     */
    @Test
    public void testChangelogContemVersoes() throws InterruptedException {

        // Abrir a página do Changelog
        changelogPage.abrirPaginaChangelog();

        // Aguardar o conteúdo
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                changelogPage.getPrimeiraVersaoLocator()
        ));
        Thread.sleep(1000);

        // Verificar que existem versões listadas
        assertTrue(changelogPage.existemVersoesListadas(),
                "O Changelog deve conter pelo menos uma versão listada");

        // Verificar que o texto da primeira versão não está vazio
        String texto = driver.findElement(changelogPage.getPrimeiraVersaoLocator()).getText();
        assertFalse(texto.isEmpty());
        assertFalse(texto.isEmpty());
    }
}
