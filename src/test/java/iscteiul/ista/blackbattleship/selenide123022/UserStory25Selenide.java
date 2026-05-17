package iscteiul.ista.blackbattleship.selenide123022;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

/**
 * Page Object Class para a UserStory25 (Selenide).
 * Testa a interação com o leaderboard:
 * abrir a página, fazer scroll ao topo e clicar num jogador do leaderboard.
 *
 * @author Rita (123022)
 */
public class UserStory25Selenide {

    /** URL base da aplicação */
    private static final String URL = "https://papergames.io/en/battleship";

    /** Item do leaderboard (6º jogador) */
    private final SelenideElement leaderboardItem = $("app-tournament-leaderboard .item:nth-child(6) .text-truncate");

    /**
     * Abre a página de Batalha Naval.
     */
    public void openPage() {
        open(URL);
    }

    /**
     * Faz scroll ao topo da página.
     */
    public void scrollToTop() {
        executeJavaScript("window.scrollTo(0,0)");
    }

    /**
     * Clica no 6º jogador do leaderboard.
     */
    public void clickLeaderboardPlayer() {
        leaderboardItem.shouldBe(visible).click();
    }

    /**
     * Verifica se o leaderboard está visível.
     *
     * @return true se o item do leaderboard estiver visível
     */
    public boolean isLeaderboardVisible() {
        return leaderboardItem.is(visible);
    }
}
