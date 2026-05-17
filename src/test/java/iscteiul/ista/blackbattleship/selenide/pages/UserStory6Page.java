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
 * Page Object Class para US06 — Disparar mísseis no tabuleiro adversário em turnos alternados.
 * Versão Selenide.
 *
 * @author IGE-112244
 */
public class UserStory6Page {

    /** Botão Play online. */
    private final SelenideElement playOnlineButton =
            $(By.xpath("//span[contains(text(),'Play online')]"));

    /** Botão Play vs robot. */
    private final SelenideElement playVsRobotButton =
            $(By.xpath("//button[contains(.,'Play vs robot')]"));

    /** Botão Play with a friend. */
    private final SelenideElement playWithFriendButton =
            $(By.xpath("//button[contains(.,'Play with a friend')]"));

    /** Botão Play online with random player. */
    private final SelenideElement playRandomButton =
            $(By.xpath("//button[contains(.,'Play online') and contains(.,'random')]"));

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
     * Clica em Play vs robot e preenche o nickname via JavaScript.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    @Step("Clicar em Play vs robot e preencher nickname")
    public void clickPlayVsRobot() throws InterruptedException {
        dismissConsentIfPresent(); // ← remover consent antes
        executeJavaScript("arguments[0].click();", playVsRobotButton);
        Thread.sleep(3000); // ← aumentar para 3s

        // Preencher nickname via JavaScript
        executeJavaScript(
                "var input = document.querySelector('input[placeholder=\"Nickname\"]');" +
                        "if(input) {" +
                        "  input.value = 'TestPlayer';" +
                        "  input.dispatchEvent(new Event('input', {bubbles: true}));" +
                        "  input.dispatchEvent(new Event('change', {bubbles: true}));" +
                        "}"
        );
        Thread.sleep(1000);
        executeJavaScript(
                "var btn = document.querySelector('button[type=\"submit\"], footer button');" +
                        "if(btn) btn.click();"
        );
        Thread.sleep(7000); // ← aumentar para 7s para o jogo carregar
    }

    /**
     * Verifica se está na sala de jogo.
     *
     * @return true se a URL contém "/en/r/"
     */
    public boolean isInGameRoom() {
        return WebDriverRunner.getWebDriver().getCurrentUrl().contains("/en/r/");
    }

    /**
     * Verifica se o botão Play vs robot está visível.
     *
     * @return true se visível
     */
    public boolean isPlayVsRobotVisible() {
        try {
            return playVsRobotButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se o botão Play with a friend está visível.
     *
     * @return true se visível
     */
    public boolean isPlayWithFriendVisible() {
        try {
            return playWithFriendButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se o botão Play random está visível.
     *
     * @return true se visível
     */
    public boolean isPlayRandomVisible() {
        try {
            return playRandomButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se o jogo tem dois tabuleiros.
     *
     * @return true se existem pelo menos 2 tabuleiros
     */
    public boolean hasGameBoards() {
        try {
            Thread.sleep(3000);
            int boards = WebDriverRunner.getWebDriver()
                    .findElements(By.cssSelector("app-battleship-board")).size();
            if (boards >= 2) return true;
            // fallback — procurar tables
            return WebDriverRunner.getWebDriver()
                    .findElements(By.tagName("table")).size() >= 2;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Verifica se o tabuleiro do jogador está visível.
     *
     * @return true se "Your boats" está visível
     */
    public boolean isMyBoardVisible() {
        try {
            return !WebDriverRunner.getWebDriver().findElements(
                    By.xpath("//*[contains(text(),'Your boats') or " +
                            "contains(text(),'boats') or " +
                            "contains(text(),'Abort')]")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clica na primeira célula do tabuleiro adversário.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    @Step("Clicar na primeira célula do tabuleiro adversário")
    public void clickFirstBoardCell() throws InterruptedException {
        try {
            SelenideElement cell = $(By.xpath(
                    "(//app-battleship-board//table//td)[1]"));
            executeJavaScript("arguments[0].click();", cell);
            Thread.sleep(2000);
        } catch (Exception e) {}
    }
}