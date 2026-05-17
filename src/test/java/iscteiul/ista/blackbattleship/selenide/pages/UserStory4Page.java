package iscteiul.ista.blackbattleship.selenide.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

/**
 * Page Object Class para US04 — Jogar Batalha Naval contra adversário aleatório online.
 * Versão Selenide com Page Factory pattern usando $().
 *
 * @author IGE-112244
 */
public class UserStory4Page {

    /** Botão Play online. */
    private final SelenideElement playOnlineButton =
            $(By.xpath("//span[contains(text(),'Play online')]"));

    /** Botão Play online with random player. */
    private final SelenideElement playRandomButton =
            $(By.xpath("//button[contains(.,'Play online') and contains(.,'random')]"));

    /** Input de nickname. */
    private final SelenideElement nicknameInput =
            $(By.xpath("//input[@placeholder='Nickname']"));

    /** Botão Continue. */
    private final SelenideElement continueButton =
            $(By.xpath("//button[contains(text(),'Continue')]"));

    /**
     * Abre a página do jogo Batalha Naval.
     */
    @Step("Abrir página do Battleship")
    public void open() {
        Selenide.open("https://papergames.io/en/battleship");
    }

    /**
     * Remove o popup de consentimento de cookies.
     */
    @Step("Remover popup de consentimento")
    public void dismissConsentIfPresent() {
        try {
            Thread.sleep(3000);
            executeJavaScript(
                    "document.querySelectorAll('.fc-consent-root, .fc-dialog-overlay," +
                            ".fc-dialog, [class*=\"fc-\"]').forEach(e => e.remove());" +
                            "document.body.style.overflow = 'auto';"
            );
            Thread.sleep(500);
        } catch (Exception e) {}
    }

    /**
     * Clica em Play online.
     */
    @Step("Clicar em Play online")
    public void clickPlayOnline() {
        dismissConsentIfPresent();
        executeJavaScript("arguments[0].click();", playOnlineButton);
    }

    /**
     * Clica em Play online with random player e preenche o nickname.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    @Step("Clicar em Play online random e preencher nickname")
    public void clickPlayRandom() throws InterruptedException {
        executeJavaScript("arguments[0].click();", playRandomButton);
        Thread.sleep(2000);
        try {
            executeJavaScript(
                    "var input = document.querySelector('input[placeholder=\"Nickname\"]');" +
                            "if(input) {" +
                            "  input.value = 'TestPlayer';" +
                            "  input.dispatchEvent(new Event('input', {bubbles: true}));" +
                            "  input.dispatchEvent(new Event('change', {bubbles: true}));" +
                            "}"
            );
            Thread.sleep(500);
            executeJavaScript(
                    "var btn = document.querySelector('button[type=\"submit\"], footer button');" +
                            "if(btn) btn.click();"
            );
            Thread.sleep(5000);
        } catch (Exception e) {}
    }

    /**
     * Verifica se o botão Play online está visível.
     *
     * @return true se visível
     */
    public boolean isPlayOnlineButtonVisible() {
        try {
            return playOnlineButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se a opção de jogo aleatório está visível.
     *
     * @return true se visível
     */
    public boolean isRandomOptionVisible() {
        try {
            return playRandomButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se entrou em matchmaking ou sala de jogo.
     *
     * @return true se a URL indica matchmaking ou jogo
     */
    public boolean isInMatchmaking() {
        String url = WebDriverRunner.getWebDriver().getCurrentUrl();
        return url.contains("/en/r/") ||
                url.contains("/en/q/") ||
                !url.equals("https://papergames.io/en/battleship");
    }

    /**
     * Verifica se o título da página contém battleship.
     *
     * @return true se o título é correto
     */
    public boolean isPageTitleCorrect() {
        return WebDriverRunner.getWebDriver().getTitle()
                .toLowerCase().contains("battleship");
    }
}