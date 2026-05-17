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
import java.util.List;

/**
 * Page Object Class para US11 — Apanhar presente ao acertar nele com um míssil.
 */
public class UserStory11 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//span[contains(text(),'Play online')]")
    public WebElement playOnlineButton;

    public UserStory11(WebDriver driver) {
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
                    "document.querySelectorAll('.fc-consent-root, .fc-dialog-overlay," +
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

    public void clickBoardCell(int index) throws InterruptedException {
        Thread.sleep(1000);
        List<WebElement> allCells = driver.findElements(
                By.xpath("//app-battleship-board//table//td"));
        int targetIndex = allCells.size() > 100 ? 100 + index : index;
        if (targetIndex < allCells.size()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", allCells.get(targetIndex));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", allCells.get(targetIndex));
            Thread.sleep(2000);
        }
    }

    public boolean hasGiftIconInBoard() {
        // Verificar se existe ícone de presente no tabuleiro
        return !driver.findElements(By.xpath(
                "//app-battleship-board//*[@data-icon='gift']")).isEmpty();
    }

    public int countGiftsInBoard() {
        return driver.findElements(By.xpath(
                "//app-battleship-board//*[@data-icon='gift']")).size();
    }

    public boolean hasGiftInOpponentBoard() {
        Object result = ((JavascriptExecutor) driver).executeScript(
                "var boards = document.querySelectorAll('app-battleship-board table');" +
                        "if (boards.length < 2) return false;" +
                        "return boards[1].querySelector('[data-icon=\"gift\"]') !== null;"
        );
        return result != null && (Boolean) result;
    }

    public boolean hasSpecialWeaponUnlocked() {
        // Verificar se alguma arma especial foi desbloqueada após apanhar presente
        return !driver.findElements(By.xpath(
                "//*[contains(@class,'weapon') or contains(@class,'special') or " +
                        "contains(@class,'powerup') or contains(@class,'unlock')]")).isEmpty();
    }

    public int countOpponentShotCells() {
        Object result = ((JavascriptExecutor) driver).executeScript(
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
}