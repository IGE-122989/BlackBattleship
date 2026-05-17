package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Class para a User Story 2 — Jogar contra um robot.
 * <p>
 * Como jogador, quero jogar Batalha Naval contra um robô (bot),
 * para poder praticar sozinho quando não há adversários disponíveis.
 * </p>
 */
public class UserStory2 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** URL base da página de Batalha Naval. */
    private static final String BASE_URL = "https://papergames.io/en/battleship";

    /** Localizador do botão "Play vs robot". */
    private final By playVsRobotButton = By.cssSelector(".w-100:nth-child(2) > .btn .flex-grow-1");

    /** Localizador do campo de introdução do nickname. */
    private final By nicknameInput = By.cssSelector(".input-xl");

    /** Localizador do botão "Continue" no modal de nickname. */
    private final By continueButton = By.cssSelector(".p-3 > .btn");

    /**
     * Construtor da classe.
     *
     * @param driver instância do WebDriver a utilizar nos testes
     */
    public UserStory2(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    /**
     * Abre a página de Batalha Naval no browser.
     */
    public void abrirPagina() {
        driver.get(BASE_URL);
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1050, 652));
    }

    /**
     * Clica no botão "Play vs robot" para iniciar um jogo contra o robot.
     */
    public void clicarPlayVsRobot() {
        WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(playVsRobotButton));
        botao.click();
    }

    /**
     * Preenche o campo de nickname com o nome indicado.
     *
     * @param nickname o nome a utilizar no jogo
     */
    public void preencherNickname(String nickname) {
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(nicknameInput));
        campo.clear();
        campo.sendKeys(nickname);
    }

    /**
     * Clica no botão "Continue" para confirmar o nickname e iniciar o jogo.
     */
    public void clicarContinue() {
        WebElement botao = wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        botao.click();
    }

    /**
     * Verifica se o modal de nickname está visível.
     *
     * @return true se o modal estiver visível, false caso contrário
     */
    public boolean modalNicknameVisivel() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(nicknameInput)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém o URL atual do browser.
     *
     * @return URL atual
     */
    public String getUrlAtual() {
        return driver.getCurrentUrl();
    }

    /**
     * Aceita o banner de cookies da Papergames.io, se estiver visível.
     */
    public void aceitarCookies() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement consentButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[contains(., 'Consent')]")
            ));
            consentButton.click();
            System.out.println("Cookies aceites com sucesso.");
        } catch (Exception e) {
            System.out.println("Banner de cookies não apareceu ou já foi fechado.");
        }
    }

}