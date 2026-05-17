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
 * Page Object Class para US12 — Míssil simples que causa dano numa única casa.
 * Versão Selenide.
 *
 * @author IGE-112244
 */
public class UserStory12Page {

    /** Botão Play online. */
    private final SelenideElement playOnlineButton =
            $(By.xpath("//span[contains(text(),'Play online')]"));

    /** Botão Play vs robot. */
    private final SelenideElement playVsRobotButton =
            $(By.xpath("//button[contains(.,'Play vs robot')]"));

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
        dismissConsentIfPresent();
        executeJavaScript("arguments[0].click();", playVsRobotButton);
        Thread.sleep(3000);
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
        Thread.sleep(7000);
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
     * Clica numa célula do tabuleiro adversário pelo índice.
     *
     * @param index índice da célula a clicar
     * @throws InterruptedException se a thread for interrompida
     */
    @Step("Clicar na célula {index} do tabuleiro adversário")
    public void clickBoardCell(int index) throws InterruptedException {
        Thread.sleep(1000);
        var allCells = WebDriverRunner.getWebDriver().findElements(
                By.xpath("//app-battleship-board//table//td"));
        int targetIndex = allCells.size() > 100 ? 100 + index : index;
        if (targetIndex < allCells.size()) {
            executeJavaScript("arguments[0].scrollIntoView(true);",
                    allCells.get(targetIndex));
            executeJavaScript("arguments[0].click();",
                    allCells.get(targetIndex));
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
}
