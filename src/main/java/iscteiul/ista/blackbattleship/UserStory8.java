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
 * Page Object Class para US08 — Disparar novamente ao acertar num navio adversário.
 */
public class UserStory8 {

    private final WebDriver driver;
    private final WebDriverWait wait;

    @FindBy(xpath = "//span[contains(text(),'Play online')]")
    public WebElement playOnlineButton;

    public UserStory8(WebDriver driver) {
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

    public boolean isAttackTurnVisible() {
        return !driver.findElements(
                By.xpath("//*[contains(text(),'Attack your opponent')]")).isEmpty();
    }

    public boolean hasCellBeenShot() {
        // Células disparadas têm classe diferente de "undefined"
        // Acerto: contém "ship-cell" ou "circle-dark" ou classe != "undefined"
        return !driver.findElements(
                By.xpath("//app-battleship-board//table//td[not(contains(@class,'undefined'))" +
                        " and not(@class='ng-star-inserted')]")).isEmpty();
    }

    public void clickBoardCell(int index) throws InterruptedException {
        Thread.sleep(2000); // aguardar Angular renderizar

        // Esperar que existam células
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//app-battleship-board//table//td")));

        List<WebElement> allCells = driver.findElements(
                By.xpath("//app-battleship-board//table//td"));

        System.out.println("Total células (ambos tabuleiros): " + allCells.size());

        // O tabuleiro adversário — células acima de 100 (após o próprio)
        // ou simplesmente clicar na célula de índice 100+index
        int targetIndex = allCells.size() > 100 ? 100 + index : index;

        if (targetIndex < allCells.size()) {
            WebElement cell = allCells.get(targetIndex);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].scrollIntoView(true);", cell);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].click();", cell);
            Thread.sleep(2000);
            System.out.println("Clicou célula " + targetIndex);
        } else {
            System.out.println("Célula " + targetIndex + " não existe. Total: " + allCells.size());
        }
    }

    public int countShotCells() {
        // Células disparadas no tabuleiro adversário têm classe "tz"
        // O tabuleiro adversário está nas células 100-199 (índice 100+)
        List<WebElement> allCells = driver.findElements(
                By.xpath("//app-battleship-board//table//td[contains(@class,'tz')]"));

        System.out.println("Total células tz em ambos tabuleiros: " + allCells.size());

        // Filtrar apenas as do 2º tabuleiro usando o atributo class do td
        // As células do adversário têm índices diferentes das nossas
        // Contar todas as tz — o nosso tabuleiro também pode ter tz (navios)
        // mas após disparo o 2º tabuleiro terá mais
        return allCells.size();
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
        int count = result != null ? ((Long) result).intValue() : 0;
        System.out.println("Células disparadas no 2º tabuleiro: " + count);
        return count;
    }

    public boolean hasHitMarker() {
        Object result = ((JavascriptExecutor) driver).executeScript(
                "var boards = document.querySelectorAll('app-battleship-board table');" +
                        "if (boards.length < 2) return false;" +
                        "return boards[1].innerHTML.indexOf('hit') >= 0 || " +
                        "       boards[1].innerHTML.indexOf('fire') >= 0;"
        );
        return result != null && (Boolean) result;
    }

    public int countBoardCells() {
        Object result = ((JavascriptExecutor) driver).executeScript(
                "var boards = document.querySelectorAll('app-battleship-board table');" +
                        "if (boards.length < 2) return 0;" +
                        "return boards[1].querySelectorAll('td').length;"
        );
        return result != null ? ((Long) result).intValue() : 0;
    }

    public boolean hasMissMarker() {
        // Falha tem "tz" mas sem "hit"
        return !driver.findElements(
                By.xpath("(//app-battleship-board)[2]//table//td[contains(@class,'tz')]" +
                        "//*[not(contains(@class,'hit'))]")).isEmpty();
    }
}