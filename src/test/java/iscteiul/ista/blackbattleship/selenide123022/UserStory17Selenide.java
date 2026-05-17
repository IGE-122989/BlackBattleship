package iscteiul.ista.blackbattleship.selenide123022;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Page Object Class para a UserStory17 (Selenide).
 * Testa o fluxo de iniciar uma partida online:
 * abrir a página e clicar no botão "Play Online".
 *
 * @author Rita (123022)
 */
public class UserStory17Selenide {

    /** URL base da aplicação */
    private static final String URL = "https://papergames.io/en/battleship";

    /** Botão "Play Online" */
    private final SelenideElement playOnlineButton = $(".gap-3:nth-child(1) > .d-flex");

    /**
     * Abre a página de Batalha Naval.
     */
    public void openPage() {
        open(URL);
    }

    /**
     * Clica no botão "Play Online".
     */
    public void clickPlayOnline() {
        playOnlineButton.shouldBe(visible).click();
    }

    /**
     * Verifica se o botão "Play Online" está visível.
     *
     * @return true se o botão estiver visível
     */
    public boolean isPlayOnlineButtonVisible() {
        return playOnlineButton.is(visible);
    }
}