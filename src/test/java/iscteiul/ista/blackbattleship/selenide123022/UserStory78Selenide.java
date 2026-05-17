package iscteiul.ista.blackbattleship.selenide123022;

import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import com.codeborne.selenide.SelenideElement;

public class UserStory78Selenide {

    private static final String URL = "https://papergames.io/en/battleship";

    private final SelenideElement privacyLink = $(By.linkText("Privacy"));

    public void openPage() {
        open(URL);
    }

    public void scrollToTop() {
        executeJavaScript("window.scrollTo(0,0)");
    }

    public void clickPrivacyLink() {
        privacyLink.shouldBe(visible).click();
        // Sem switchTo() - navega na mesma tab
        sleep(2000);
    }

    public boolean isPrivacyLinkVisible() {
        return privacyLink.is(visible);
    }

    public String getCurrentUrl() {
        return webdriver().driver().url();
    }
}