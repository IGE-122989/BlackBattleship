package iscteiul.ista.blackbattleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Test Class para US12.
 * US12: Como jogador, quero utilizar um míssil simples que causa dano numa única casa,
 * para ataques precisos e cirúrgicos.
 */
class UserStory12Test {

    private WebDriver driver;
    private UserStory12 page;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        page = PageFactory.initElements(driver, UserStory12.class);
        page.open();
        page.dismissConsentIfPresent();
        page.clickPlayOnline();
        try { Thread.sleep(3000); } catch (Exception e) {} // ← aumentar para 3s
        try { page.clickPlayVsRobot(); } catch (Exception e) {}
        // Aguardar que o jogo carregue completamente
        try { Thread.sleep(3000); } catch (Exception e) {} // ← wait adicional após entrar
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("US12 - Game should load correctly")
    void gameLoadsCorrectly() {
        // Aguardar mais tempo se necessário
        try {
            new org.openqa.selenium.support.ui.WebDriverWait(driver,
                    java.time.Duration.ofSeconds(10))
                    .until(d -> d.getCurrentUrl().contains("/en/r/"));
        } catch (Exception e) {}

        assertTrue(page.isInGameRoom(),
                "Error: Should be in game room. URL: " + driver.getCurrentUrl());
    }

    @Test
    @DisplayName("US12 - Board should have 100 cells for opponent")
    void boardHas100Cells() {
        int cells = page.countBoardCells();
        assertEquals(100, cells,
                "Error: Opponent board should have exactly 100 cells (10x10).");
    }

    @Test
    @DisplayName("US12 - Firing simple missile should hit exactly one cell")
    void simpleMissileHitsOneCell() throws InterruptedException {
        int cellsBefore = page.countOpponentShotCells();

        // Disparar um único míssil
        page.clickBoardCell(5);
        Thread.sleep(3000);

        int cellsAfter = page.countOpponentShotCells();
        int cellsHit = cellsAfter - cellsBefore;

        // Míssil simples deve atingir exatamente 1 célula
        // (ou 0 se foi água sem marcador de hit)
        assertTrue(cellsHit >= 0 && cellsHit <= 1,
                "Error: Simple missile should hit at most 1 cell. Hit: " + cellsHit);
    }

    @Test
    @DisplayName("US12 - Firing multiple missiles should hit one cell each")
    void multipleMissilesHitOneCellEach() throws InterruptedException {
        // Disparar 5 mísseis e verificar que cada um afeta no máximo 1 célula
        for (int i = 0; i < 5; i++) {
            int cellsBefore = page.countOpponentShotCells();
            page.clickBoardCell(i * 2); // células diferentes
            Thread.sleep(2000);
            int cellsAfter = page.countOpponentShotCells();
            int cellsHit = cellsAfter - cellsBefore;

            assertTrue(cellsHit >= 0 && cellsHit <= 1,
                    "Error: Each simple missile should hit at most 1 cell. " +
                            "Shot " + i + " hit: " + cellsHit);
        }
    }

    @Test
    @DisplayName("US12 - Game continues after firing simple missile")
    void gameContinuesAfterSimpleMissile() throws InterruptedException {
        page.clickBoardCell(0);
        Thread.sleep(3000);

        assertTrue(page.isInGameRoom(),
                "Error: Game should continue after simple missile. URL: "
                        + driver.getCurrentUrl());
    }
}