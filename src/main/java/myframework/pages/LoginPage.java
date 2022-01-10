package myframework.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$;
import static myframework.extension.PlatformResolver.page;

@Component
public class LoginPage extends BasePage {

    private static final String USERNAME_ID = "user";
    private static final String PASSWORD_ID = "password";
    private static final String LOGIN_BUTTON_ID = "login";

    @Step
    public BoardsPage loginAs(String username, String password) {
        $(By.id(USERNAME_ID)).sendKeys(username);
        $(By.id(PASSWORD_ID)).sendKeys(password);
        $(By.id(LOGIN_BUTTON_ID)).click();
        page(LoginToContinuePage.class).completeLogin(password);
        return page(BoardsPage.class);
    }

}
