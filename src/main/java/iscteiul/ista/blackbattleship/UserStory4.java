package iscteiul.ista.blackbattleship;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object Class para US04 — Jogar Batalha Naval contra adversário aleatório online.
 */
public class UserStory4 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//span[contains(text(),'Play online')]")
    public WebElement playOnlineButton;

    @FindBy(xpath = "//button[contains(.,'Play online') and contains(.,'random')]")
    public WebElement playRandomButton;

    @FindBy(xpath = "//button[contains(.,'Play vs robot')]")
    public WebElement playVsRobotButton;

    @FindBy(xpath = "//button[contains(.,'Play with a friend')]")
    public WebElement playWithFriendButton;

    @FindBy(xpath = "//input[@placeholder='Nickname']")
    public WebElement nicknameInput;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    public WebElement continueButton;

    public UserStory4(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://papergames.io/en/battleship");
        wait.until(ExpectedConditions.jsReturnsValue(
                "return document.readyState === 'complete'"));
    }

    public void dismissConsentIfPresent() {
        try {
            Thread.sleep(3000);
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('.fc-consent-root, .fc-dialog-overlay, " +
                            ".fc-dialog, [class*=\"fc-\"]').forEach(e => e.remove());" +
                            "document.body.style.overflow = 'auto';"
            );
            Thread.sleep(500);
        } catch (Exception e) {}
    }

    public void clickPlayOnline() {
        dismissConsentIfPresent();
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[contains(text(),'Play online')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    public void clickPlayRandom() throws InterruptedException {
        WebElement btn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(.,'Play online') and contains(.,'random')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        Thread.sleep(2000);

        // Preencher nickname se aparecer
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//input[@placeholder='Nickname']")));
            input.clear();
            input.sendKeys("TestPlayer");
            Thread.sleep(500);
            WebElement cont = driver.findElement(
                    By.xpath("//button[contains(text(),'Continue')]"));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cont);
            Thread.sleep(5000);
        } catch (Exception e) {}
    }

    public boolean isPlayOnlineButtonVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(playOnlineButton));
            return playOnlineButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isRandomOptionVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(playRandomButton));
            return playRandomButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInMatchmaking() {
        try {
            // Aguardando adversário ou em jogo
            return driver.getCurrentUrl().contains("/en/r/") ||
                    !driver.findElements(
                            By.xpath("//*[contains(text(),'Waiting') or " +
                                    "contains(text(),'opponent') or " +
                                    "contains(text(),'Attack')]")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPageTitleCorrect() {
        return driver.getTitle().toLowerCase().contains("battleship");
    }
}