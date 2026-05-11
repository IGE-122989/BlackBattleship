package iscteiul.ista.blackbattleship;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
    @FindBy(xpath = "//a[@href='/ides/' and @data-test='main-submenu-item-link']")
    public WebElement seeDeveloperToolsButton;

    @FindBy(xpath = "//a[@data-test='suggestion-link' and @href='/products/']")
    public WebElement findYourToolsButton;

    @FindBy(xpath = "//button[@data-test='main-menu-item-action' and @aria-label='Products: Open submenu']")
    public WebElement toolsMenu;

    @FindBy(css = "button[data-test='site-header-search-action']")
    public WebElement searchButton;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
