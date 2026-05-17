package iscteiul.ista.blackbattleship.selenide123022;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Page Object Class para a UserStory2 (Selenide).
 * Testa o fluxo de jogar contra o robot:
 * abrir a página, clicar em "Play vs Robot", preencher o nickname e continuar.
 *
 * @author Rita (123022)
 */
public class UserStory2Selenide {

    /** URL base da aplicação */
    private static final String URL = "https://papergames.io/en/battleship";

    /** Botão "Play vs Robot" */
    private final SelenideElement playVsRobotButton = $(".w-100:nth-child(2) > .btn .flex-grow-1");

    /** Campo de texto para o nickname */
    private final SelenideElement nicknameInput = $(".input-xl");

    /** Botão "Continue" para submeter o nickname */
    private final SelenideElement continueButton = $(".p-3 > .btn");

    /**
     * Abre a página de Batalha Naval.
     */
    public void openPage() {
        open(URL);
    }

    /**
     * Clica no botão "Play vs Robot".
     */
    public void clickPlayVsRobot() {
        playVsRobotButton.shouldBe(visible).click();
    }

    /**
     * Preenche o campo de nickname com o valor fornecido.
     *
     * @param nickname o nickname a inserir
     */
    public void enterNickname(String nickname) {
        nicknameInput.shouldBe(visible).setValue(nickname);
    }

    /**
     * Clica no botão "Continue" para iniciar o jogo.
     */
    public void clickContinue() {
        continueButton.shouldBe(visible).click();
    }

    /**
     * Verifica se o botão "Continue" está visível.
     *
     * @return true se o botão estiver visível
     */
    public boolean isContinueButtonVisible() {
        return continueButton.is(visible);
    }
}