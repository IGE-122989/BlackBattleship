// UserStory6.java — versão final
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
 * Page Object Class para US06 — Disparar mísseis no tabuleiro adversário em turnos alternados.
 */
public class UserStory6 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//span[contains(text(),'Play online')]")
    public WebElement playOnlineButton;

    @FindBy(xpath = "//button[contains(.,'Play vs robot')]")
    public WebElement playVsRobotButton;

    @FindBy(xpath = "//button[contains(.,'Play with a friend')]")
    public WebElement playWithFriendButton;

    @FindBy(xpath = "//button[contains(.,'Play online') and contains(.,'random')]")
    public WebElement playRandomButton;

    @FindBy(xpath = "//input[@placeholder='Nickname']")
    public WebElement nicknameInput;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    public WebElement continueButton;

    @FindBy(xpath = "//button[contains(text(),'Abort game')]")
    public WebElement abortGameButton;

    public UserStory6(WebDriver driver) {
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

    public void clickPlayVsRobot() throws InterruptedException {
        WebElement robotBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[contains(.,'Play vs robot')]")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", robotBtn);
        Thread.sleep(2000);

        // Preencher nickname
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

    public boolean isInGameRoom() {
        return driver.getCurrentUrl().contains("/en/r/");
    }

    public boolean isAttackTurnVisible() {
        try {
            Thread.sleep(3000); // aguardar Angular renderizar
            return !driver.findElements(
                    By.xpath("//*[contains(text(),'Attack your opponent')]")).isEmpty() ||
                    !driver.findElements(
                            By.xpath("//*[contains(@class,'turn') or contains(@class,'attack')]")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isMyBoardVisible() {
        try {
            Thread.sleep(3000);
            return !driver.findElements(
                    By.xpath("//*[contains(text(),'Your boats')]")).isEmpty() ||
                    !driver.findElements(
                            By.xpath("//*[contains(text(),'boats') or contains(text(),'fleet')]")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasGameBoards() {
        return driver.findElements(By.tagName("table")).size() >= 2;
    }

    public boolean isAbortButtonVisible() {
        try {
            Thread.sleep(3000);
            return !driver.findElements(
                    By.xpath("//button[contains(text(),'Abort game')]")).isEmpty() ||
                    !driver.findElements(
                            By.xpath("//*[contains(text(),'Abort')]")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public void clickFirstBoardCell() throws InterruptedException {
        // Clicar na primeira célula do tabuleiro adversário
        try {
            WebElement cell = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("(//app-battleship-board//table//td)[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cell);
            Thread.sleep(2000);
        } catch (Exception e) {}
    }

    public boolean isPlayVsRobotVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(playVsRobotButton));
            return playVsRobotButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPlayWithFriendVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(playWithFriendButton));
            return playWithFriendButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isPlayRandomVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(playRandomButton));
            return playRandomButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}