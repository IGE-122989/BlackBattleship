package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object Class para US04 — Jogar Batalha Naval contra adversário aleatório online.
 * Página testada: https://papergames.io/en/battleship
 */
public class UserStory4 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // No UserStory4.java — adicionar localizador do modal
    @FindBy(xpath = "//div[contains(@class,'modal')] | //div[contains(@class,'dialog')] | //app-game-mode-selector | //app-play-online")
    public WebElement gameModal;

    /** Botão "Play online" na página principal */
    @FindBy(xpath = "//span[contains(text(),'Play online')]")
    public WebElement playOnlineButton;

    /** Botão "Play vs Random" ou equivalente */
    @FindBy(xpath = "//span[contains(text(),'Random')] | //button[contains(text(),'Random')]")
    public WebElement playRandomButton;

    public UserStory4(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    /** Verifica se o modal de jogo apareceu após clicar Play Online */
    public boolean isGameModalVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(gameModal));
            return gameModal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Abre a página do jogo Batalha Naval */
    public void open() {
        driver.get("https://papergames.io/en/battleship");
        wait.until(ExpectedConditions.jsReturnsValue(
                "return document.readyState === 'complete'"));
    }

    /** Clica em Play Online */
    public void clickPlayOnline() {
        wait.until(ExpectedConditions.elementToBeClickable(playOnlineButton));
        playOnlineButton.click();
    }

    /** Verifica se o botão Play Online está visível */
    public boolean isPlayOnlineButtonVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(playOnlineButton)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /** Verifica se navegou para o jogo (URL mudou) */
    public boolean isInGame() {
        try {
            wait.until(ExpectedConditions.urlContains("game"));
            return driver.getCurrentUrl().contains("game");
        } catch (Exception e) {
            return false;
        }
    }
}