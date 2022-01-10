package myframework.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static myframework.extension.DriverManager.getDriver;

public abstract class BasePage {
    public static final String HEADER_LOGO_XPATH = "//a[contains(@aria-label, 'Back to home')]";
    public static final String MEMBER_MENU_BUTTON = "//button[contains(@aria-label, 'Open member menu')]";
    public static final String LOG_OUT_MENU_ITEM = "//span[contains(., \"Log out\")]";
    public static final String LOGOUT_SUBMIT = "logout-submit";

    @Step
    public BoardsPage goToHomepage() {
        $x(HEADER_LOGO_XPATH).shouldBe(Condition.visible).click();
        return page(BoardsPage.class);
    }

    @Step
    public LogOutPage logout() {
        $x(MEMBER_MENU_BUTTON).click();
        $x(LOG_OUT_MENU_ITEM).shouldBe(Condition.visible).click();
        $(By.id(LOGOUT_SUBMIT)).click();
        return page(LogOutPage.class);
    }

    @Step
    public String getTitle() {
        return getDriver().getTitle();
    }

    public static void waitSomeTime(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
