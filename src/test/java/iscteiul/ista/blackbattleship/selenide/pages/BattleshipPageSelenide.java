package iscteiul.ista.blackbattleship.selenide.pages;

import com.codeborne.selenide.*;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

/**
 * Page Object Class base para o jogo Battleship usando Selenide.
 * Partilhada pelas US04, US06, US08, US11 e US12.
 *
 * @author IGE-112244
 */
public class BattleshipPageSelenide {

    /** Botão Play online. */
    private final SelenideElement playOnlineButton =
            $(By.xpath("//span[contains(text(),'Play online')]"));

    /** Botão Play vs robot. */
    private final SelenideElement playVsRobotButton =
            $(By.xpath("//button[contains(.,'Play vs robot')]"));

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
     * Abre a página do jogo Battleship.
     */
    @Step("Abrir página do Battleship")
    public void open() {
        Selenide.open("https://papergames.io/en/battleship");
    }

    /**
     * Remove o popup de consentimento de cookies.
     */
    @Step("Remover popup de consentimento")
    public void dismissConsent() {
        try {
            executeJavaScript(
                    "document.querySelectorAll('.fc-consent-root, [class*=\"fc-\"]')" +
                            ".forEach(e => e.remove());" +
                            "document.body.style.overflow='auto';"
            );
            Thread.sleep(500);
        } catch (Exception e) {
            // Sem popup — continuar
        }
    }

    /**
     * Clica em Play online.
     */
    @Step("Clicar em Play online")
    public void clickPlayOnline() {
        dismissConsent();
        executeJavaScript("arguments[0].click();", playOnlineButton);
    }

    /**
     * Clica em Play vs robot e preenche o nickname.
     *
     * @throws InterruptedException se a thread for interrompida
     */
    @Step("Clicar em Play vs robot e preencher nickname")
    public void clickPlayVsRobot() throws InterruptedException {
        // Remover consent antes de qualquer clique
        dismissConsent();
        Thread.sleep(1000);

        executeJavaScript("arguments[0].click();", playVsRobotButton);
        Thread.sleep(2000);

        // Remover consent novamente — pode aparecer após o clique
        dismissConsent();
        Thread.sleep(500);

        try {
            // Usar JavaScript para tornar o input visível e interagível
            executeJavaScript(
                    "var input = document.querySelector('input[placeholder=\"Nickname\"]');" +
                            "if(input) {" +
                            "  input.style.display = 'block';" +
                            "  input.style.visibility = 'visible';" +
                            "  input.removeAttribute('hidden');" +
                            "  input.closest('mat-dialog-container') && " +
                            "    (input.closest('mat-dialog-container').style.display = 'block');" +
                            "}"
            );
            Thread.sleep(500);

            // Preencher via JavaScript
            executeJavaScript(
                    "var input = document.querySelector('input[placeholder=\"Nickname\"]');" +
                            "if(input) {" +
                            "  input.value = 'TestPlayer';" +
                            "  input.dispatchEvent(new Event('input', {bubbles: true}));" +
                            "  input.dispatchEvent(new Event('change', {bubbles: true}));" +
                            "}"
            );
            Thread.sleep(500);

            // Clicar em Continue via JavaScript
            executeJavaScript(
                    "var btn = document.querySelector('button[type=\"submit\"], footer button');" +
                            "if(btn) btn.click();"
            );
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Erro no nickname: " + e.getMessage());
        }
    }

    /**
     * Clica em Play online with random player e preenche o nickname.
     *
     * @param nickname o nome a usar no jogo
     * @throws InterruptedException se a thread for interrompida
     */
    @Step("Clicar em Play online random com nickname: {nickname}")
    public void clickPlayRandom(String nickname) throws InterruptedException {
        executeJavaScript("arguments[0].click();", playRandomButton);
        Thread.sleep(2000);
        try {
            nicknameInput.shouldBe(Condition.visible).setValue(nickname);
            Thread.sleep(500);
            executeJavaScript("arguments[0].click();", continueButton);
            Thread.sleep(5000);
        } catch (Exception e) {
            // Sem popup de nickname
        }
    }

    /**
     * Verifica se o jogador está na sala de jogo.
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
        return playVsRobotButton.isDisplayed();
    }

    /**
     * Verifica se o botão Play random está visível.
     *
     * @return true se visível
     */
    public boolean isPlayRandomVisible() {
        return playRandomButton.isDisplayed();
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

    /**
     * Clica numa célula do tabuleiro adversário.
     *
     * @param index índice da célula a clicar
     * @throws InterruptedException se a thread for interrompida
     */
    @Step("Clicar na célula {index} do tabuleiro adversário")
    public void clickBoardCell(int index) throws InterruptedException {
        Thread.sleep(1000);
        ElementsCollection cells = $$(By.xpath(
                "//app-battleship-board//table//td"));
        int targetIndex = cells.size() > 100 ? 100 + index : index;
        if (targetIndex < cells.size()) {
            executeJavaScript("arguments[0].scrollIntoView(true);",
                    cells.get(targetIndex));
            executeJavaScript("arguments[0].click();",
                    cells.get(targetIndex));
            Thread.sleep(2000);
        }
    }

    /**
     * Conta células disparadas no tabuleiro adversário.
     *
     * @return número de células disparadas
     */
    public int countOpponentShotCells() {
        Object result = executeJavaScript(
                "var boards = document.querySelectorAll('app-battleship-board table');" +
                        "if (boards.length < 2) return 0;" +
                        "var cells = boards[1].querySelectorAll('td');" +
                        "var count = 0;" +
                        "cells.forEach(function(td) {" +
                        "  var inner = td.innerHTML;" +
                        "  if (inner.indexOf('hit') >= 0 || inner.indexOf('fire') >= 0 || " +
                        "      inner.indexOf('ship-cell') >= 0) count++;" +
                        "});" +
                        "return count;"
        );
        return result != null ? ((Long) result).intValue() : 0;
    }

    /**
     * Verifica se existe marcador de acerto no tabuleiro adversário.
     *
     * @return true se existe marcador de acerto
     */
    public boolean hasHitMarker() {
        Object result = executeJavaScript(
                "var boards = document.querySelectorAll('app-battleship-board table');" +
                        "if (boards.length < 2) return false;" +
                        "return boards[1].innerHTML.indexOf('hit') >= 0;"
        );
        return result != null && (Boolean) result;
    }

    /**
     * Conta células do tabuleiro adversário.
     *
     * @return número de células do tabuleiro adversário
     */
    public int countBoardCells() {
        Object result = executeJavaScript(
                "var boards = document.querySelectorAll('app-battleship-board table');" +
                        "if (boards.length < 2) return 0;" +
                        "return boards[1].querySelectorAll('td').length;"
        );
        return result != null ? ((Long) result).intValue() : 0;
    }

    /**
     * Verifica se existe ícone de presente no tabuleiro.
     *
     * @return true se existe presente
     */
    public boolean hasGiftIconInBoard() {
        return !$$(By.xpath(
                "//app-battleship-board//*[@data-icon='gift']")).isEmpty();
    }
}