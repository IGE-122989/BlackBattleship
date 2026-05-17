package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object Class para a User Story 78 — Consultar a política de privacidade.
 * <p>
 * Como utilizador, quero consultar a política de privacidade da plataforma,
 * para perceber como os meus dados são tratados.
 * </p>
 */
public class UserStory78 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    /** URL base da página de Batalha Naval. */
    private static final String BASE_URL = "https://papergames.io/en/battleship";

    /** URL esperado da página de política de privacidade. */
    private static final String PRIVACY_URL = "/en/blog/privacy-policy";

    /** Localizador do link "Privacy" no rodapé da página. */
    private final By privacyLink = By.linkText("Privacy");

    /**
     * Construtor da classe.
     *
     * @param driver instância do WebDriver a utilizar nos testes
     */
    public UserStory78(WebDriver driver) {
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
     * Faz scroll até ao topo da página.
     */
    public void scrollParaTopo() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
    }

    /**
     * Verifica se o link de Privacy Policy está presente na página.
     *
     * @return true se o link estiver presente, false caso contrário
     */
    public boolean linkPrivacyVisivel() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(privacyLink)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Clica no link "Privacy" para navegar para a página de política de privacidade.
     */
    public void clicarPrivacyPolicy() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(privacyLink));
        link.click();
    }

    /**
     * Verifica se a página atual é a página de política de privacidade.
     *
     * @return true se o URL contiver o caminho da política de privacidade
     */
    public boolean esPaginaPrivacidade() {
        return driver.getCurrentUrl().contains(PRIVACY_URL);
    }

    /**
     * Obtém o URL atual do browser.
     *
     * @return URL atual
     */
    public String getUrlAtual() {
        return driver.getCurrentUrl();
    }
}