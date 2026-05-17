package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object Class para UserStoryTest4.
 * <p>
 * US04: Como jogador, quero jogar Batalha Naval contra um adversário aleatório online,
 * para encontrar oponentes rapidamente sem ter de convidar ninguém.
 * </p>
 * Página testada: https://papergames.io/en/battleship
 *
 * @author IGE-112244
 */
public class UserStory4LLM {

    /** WebDriver para controlar o browser. */
    private final WebDriver driver;

    /** Wait explícito para aguardar elementos. */
    private final WebDriverWait wait;

    /**
     * Botão "Play online with a random player" — CSS do Selenium IDE.
     * Corresponde ao 2º botão com classe btn-secondary.
     */
    @FindBy(css = ".btn-secondary:nth-child(2)")
    public WebElement playOnlineRandomButton;

    /**
     * Campo de input para o nickname do jogador.
     */
    @FindBy(css = ".input-xl")
    public WebElement nicknameInput;

    /**
     * Botão "Continue" para confirmar o nickname.
     */
    @FindBy(css = ".p-3 > .btn")
    public WebElement continueButton;

    /**
     * Construtor — inicializa o driver e os elementos via PageFactory.
     *
     * @param driver WebDriver a utilizar
     */
    public UserStory4LLM(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    /**
     * Abre a página do jogo Batalha Naval.
     * Aguarda que a página esteja completamente carregada.
     */
    public void open() {
        driver.get("https://papergames.io/en/battleship");
        wait.until(ExpectedConditions.jsReturnsValue(
                "return document.readyState === 'complete'"));
    }

    /**
     * Remove o popup de consentimento de cookies se presente.
     * Utiliza JavaScript para remover os elementos de overlay.
     */
    public void dismissConsentIfPresent() {
        try {
            Thread.sleep(3000);
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('.fc-consent-root, .fc-dialog-overlay," +
                            ".fc-dialog, [class*=\"fc-\"]').forEach(e => e.remove());" +
                            "document.body.style.overflow = 'auto';"
            );
            Thread.sleep(500);
        } catch (Exception e) {
            // Sem popup — continuar normalmente
        }
    }

    /**
     * Clica no botão "Play online with a random player".
     * Utiliza JavaScript para evitar bloqueio por overlays.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    public void clickPlayOnlineRandom() throws InterruptedException {
        // Remover consent se presente
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('.fc-consent-root, [class*=\"fc-\"]')" +
                            ".forEach(e => e.remove());" +
                            "document.body.style.overflow = 'auto';"
            );
            Thread.sleep(500);
        } catch (Exception e) {}

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".btn-secondary:nth-child(2)")));
        WebElement btn = driver.findElement(
                By.cssSelector(".btn-secondary:nth-child(2)"));
        Thread.sleep(1000);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        Thread.sleep(1500);
    }

    /**
     * Preenche o campo de nickname com o nome fornecido.
     *
     * @param nickname o nome a usar no jogo
     * @throws InterruptedException se a thread for interrompida
     */
    public void enterNickname(String nickname) throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".input-xl")));
        Thread.sleep(1000);

        // Usar JavaScript para interagir mesmo com overlays
        WebElement input = driver.findElement(By.cssSelector(".input-xl"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);" +
                        "arguments[0].focus();", input);
        Thread.sleep(500);

        // Limpar e escrever via JavaScript
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = '';", input);
        input.sendKeys(nickname);
        Thread.sleep(500);
    }

    /**
     * Clica no botão "Continue" para entrar no matchmaking.
     * Aguarda que o jogo carregue após clicar.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    public void clickContinue() throws InterruptedException {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".p-3 > .btn")));
        WebElement btn = driver.findElement(By.cssSelector(".p-3 > .btn"));
        Thread.sleep(500); // simular comportamento humano
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        Thread.sleep(5000); // aguardar matchmaking ou jogo carregar
    }

    /**
     * Verifica se a página do battleship carregou corretamente.
     *
     * @return true se o título contém "battleship"
     */
    public boolean isPageTitleCorrect() {
        return driver.getTitle().toLowerCase().contains("battleship");
    }

    /**
     * Verifica se o botão "Play online with a random player" está visível.
     *
     * @return true se o botão está visível
     */
    public boolean isPlayOnlineRandomButtonVisible() {
        try {
            return !driver.findElements(
                    By.cssSelector(".btn-secondary:nth-child(2)")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se o campo de nickname está visível.
     *
     * @return true se o input de nickname está presente
     */
    public boolean isNicknameInputVisible() {
        try {
            return !driver.findElements(
                    By.cssSelector(".input-xl")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se o jogador entrou em matchmaking ou sala de jogo.
     *
     * @return true se a URL indica matchmaking ou sala de jogo
     */
    public boolean isInMatchmakingOrGame() {
        String url = driver.getCurrentUrl();
        return url.contains("/en/r/") ||
                url.contains("q=") ||
                !url.equals("https://papergames.io/en/battleship");
    }


}