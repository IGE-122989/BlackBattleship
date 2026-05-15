package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object Class para a User Story 25 — Ver o leaderboard diário.
 * <p>
 * Como jogador, quero ver o leaderboard diário com contagem decrescente
 * até ao fim do torneio, para acompanhar a minha posição em tempo real.
 * </p>
 */
public class UserStory25 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** URL base da página de Batalha Naval. */
    private static final String BASE_URL = "https://papergames.io/en/battleship";

    /** Localizador da tabela do leaderboard. */
    private final By leaderboard = By.cssSelector("app-tournament-leaderboard");

    /** Localizador dos itens do leaderboard. */
    private final By leaderboardItems = By.cssSelector("app-tournament-leaderboard .item");

    /** Localizador de um jogador específico no leaderboard (6.º lugar). */
    private final By jogadorLeaderboard = By.cssSelector("app-tournament-leaderboard .item:nth-child(6) .text-truncate");

    /**
     * Construtor da classe.
     *
     * @param driver instância do WebDriver a utilizar nos testes
     */
    public UserStory25(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Abre a página de Batalha Naval no browser.
     */
    public void abrirPagina() {
        driver.get(BASE_URL);
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1050, 652));
    }

    /**
     * Faz scroll até ao topo da página.
     */
    public void scrollParaTopo() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
    }

    /**
     * Verifica se o leaderboard está visível na página.
     *
     * @return true se o leaderboard estiver visível, false caso contrário
     */
    public boolean leaderboardVisivel() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(leaderboard)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém o número de jogadores listados no leaderboard.
     *
     * @return número de itens no leaderboard
     */
    public int obterNumeroJogadoresLeaderboard() {
        List<WebElement> items = driver.findElements(leaderboardItems);
        return items.size();
    }

    /**
     * Clica num jogador do leaderboard.
     */
    public void clicarJogadorLeaderboard() {
        WebElement jogador = wait.until(ExpectedConditions.elementToBeClickable(jogadorLeaderboard));
        jogador.click();
    }

    /**
     * Obtém o URL atual do browser.
     *
     * @return URL atual
     */
    public String getUrlAtual() {
        return driver.getCurrentUrl();
    }
}