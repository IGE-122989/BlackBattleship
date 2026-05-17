package iscteiul.ista.blackbattleship.selenide.pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

/**
 * <b>Page Object Class — UserStory3 (Selenide)</b>
 *
 * <p>Fluxo coberto:</p>
 * <ol>
 *   <li>Abrir a página do Battleship.</li>
 *   <li>Clicar em Play with a friend.</li>
 *   <li>Registar um nickname de convidado.</li>
 *   <li>Confirmar clicando em Continue.</li>
 *   <li>Aguardar e verificar a criação do URL partilhável.</li>
 *   <li>Copiar o URL gerado.</li>
 * </ol>
 *
 * @version 2.0 (Selenide)
 */
public class UserStory3Selenide {


    private static final String BASE_URL = "https://papergames.io/en/battleship";

    private final SelenideElement btnPlayWithFriend =
            $(".w-100:nth-child(1) > .btn-lg .flex-grow-1");

    private final SelenideElement inputNickname =
            $(".input-xl");

    private final SelenideElement btnContinue =
            $(".p-3 > .btn");

    private final SelenideElement copyTextLink =
            $(".copy-text");

    private final SelenideElement btnCopyUrl =
            $(".fa-copy");

    @Step("Abrir página Battleship")
    public void openPage() {
        open(BASE_URL);
    }

    @Step("Aceitar cookies")
    public void aceitarCookies() {
        try {
            $x("//button[contains(., 'Consent')]")
                    .shouldBe(Condition.visible, Duration.ofSeconds(8))
                    .click();
        } catch (Exception e) {
            System.out.println("Banner de cookies não apareceu ou já foi fechado.");
        }
    }

    @Step("Clicar em 'Play with a friend'")
    public void clickPlayWithFriend() {
        btnPlayWithFriend.shouldBe(Condition.visible).click();
    }

    @Step("Inserir nickname: {nickname}")
    public void enterNickname(String nickname) {
        inputNickname.shouldBe(Condition.visible).clear();
        inputNickname.sendKeys(nickname);
    }

    @Step("Clicar em Continue")
    public void clickContinue() {
        btnContinue.shouldBe(Condition.visible).click();
    }

    @Step("Aguardar link partilhável")
    public void waitForShareableLink() {
        copyTextLink.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    @Step("Verificar presença do link partilhável")
    public boolean isShareableLinkPresent() {
        return copyTextLink.exists();
    }

    @Step("Copiar URL partilhável")
    public void clickCopyUrl() {
        // Clicar no .copy-text em vez do .fa-copy que tem aria-hidden="true"
        copyTextLink.shouldBe(Condition.visible).click();
    }
}
