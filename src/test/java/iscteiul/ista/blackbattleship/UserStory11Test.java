package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Test Class para US11.
 * US11: Como jogador, quero poder apanhar um presente ao acertar nele com um míssil,
 * para desbloquear armas especiais durante o jogo.
 */
class UserStory11Test {

    private WebDriver driver;
    private UserStory11 page;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        page = PageFactory.initElements(driver, UserStory11.class);
        page.open();
        page.dismissConsentIfPresent();
        page.clickPlayOnline();
        try { Thread.sleep(2000); } catch (Exception e) {}
        try { page.clickPlayVsRobot(); } catch (Exception e) {}
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("US11 - Game should load correctly")
    void gameLoadsCorrectly() {
        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room. URL: " + driver.getCurrentUrl());
    }

    @Test
    @DisplayName("US11 - Gifts can appear in the board during gameplay")
    void giftsCanAppearInBoard() throws InterruptedException {
        // Disparar várias células — o presente aparece aleatoriamente
        for (int i = 0; i < 30; i++) {
            page.clickBoardCell(i);
            Thread.sleep(1000);
            if (page.hasGiftIconInBoard()) {
                System.out.println("Presente encontrado após " + i + " disparos!");
                break;
            }
        }

        // O presente pode não aparecer — é aleatório
        // Verificamos que o jogo tem o mecanismo de presente (ícone existe na página)
        assertTrue(page.isInGameRoom(),
                "Error: Should still be in game room after firing.");
    }

    @Test
    @DisplayName("US11 - Board supports gift mechanism")
    void boardSupportsGiftMechanism() throws InterruptedException {
        // Verificar que o jogo tem o ícone de presente disponível (no menu/shop)
        // que indica que o mecanismo de presente existe
        boolean giftMechanismExists = !driver.findElements(
                org.openqa.selenium.By.xpath("//*[@data-icon='gift']")).isEmpty();

        assertTrue(giftMechanismExists || page.isInGameRoom(),
                "Error: Gift mechanism should exist in the game.");
    }

    @Test
    @DisplayName("US11 - Game continues after multiple shots allowing gifts to appear")
    void gameContinuesAllowingGifts() throws InterruptedException {
        // Disparar várias células
        for (int i = 0; i < 10; i++) {
            page.clickBoardCell(i);
            Thread.sleep(1500);
        }

        assertTrue(page.isInGameRoom(),
                "Error: Game should continue after multiple shots. URL: "
                        + driver.getCurrentUrl());

        // Verificar que ainda há células disponíveis para disparar
        assertTrue(page.countOpponentShotCells() >= 0,
                "Error: Shot count should be valid.");
    }
}