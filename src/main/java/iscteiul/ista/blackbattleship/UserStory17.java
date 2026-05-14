package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Class para a User Story 17 — Jogar como convidado.
 * <p>
 * Como visitante, quero jogar como convidado sem criar conta,
 * para experimentar a plataforma sem compromisso.
 * </p>
 */
public class UserStory17 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** URL base da página de Batalha Naval. */
    private static final String BASE_URL = "https://papergames.io/en/battleship";

    /** Localizador do botão "Play online" (jogar com jogador aleatório). */
    private final By playOnlineButton = By.cssSelector(".gap-3:nth-child(1) > .d-flex");

    /** Localizador do modal de nickname "Who are you?". */
    private final By nicknameModal = By.cssSelector(".input-xl");

    /**
     * Construtor da classe.
     *
     * @param driver instância do WebDriver a utilizar nos testes
     */
    public UserStory17(WebDriver driver) {
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
     * Clica no botão "Play online" para jogar como convidado com um jogador aleatório.
     */
    public void clicarPlayOnline() {
        WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(playOnlineButton));
        botao.click();
    }



    /**
     * Verifica se o botão "Play online" está visível na página.
     *
     * @return true se o botão estiver visível, false caso contrário
     */
    public boolean playOnlineVisivel() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(playOnlineButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se o modal de nickname está visível.
     *
     * @return true se o modal estiver visível, false caso contrário
     */
    public boolean modalNicknameVisivel() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(nicknameModal)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
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