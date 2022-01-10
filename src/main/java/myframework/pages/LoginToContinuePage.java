package myframework.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static myframework.extension.PlatformResolver.page;


@Component
public class LoginToContinuePage extends BasePage{
    private static final String PASSWORD_ID = "password";
    private static final String LOGIN_BUTTON = "login-submit";
    private static final String LOGIN_TO_CONTINUE_HEADER = "//div[contains(., \"Log in to continue to:\")]";

    @Step
    public BoardsPage completeLogin(String password){
        $x(LOGIN_TO_CONTINUE_HEADER).shouldBe(Condition.visible);
        $(By.id(PASSWORD_ID)).sendKeys(password);
        $(By.id(LOGIN_BUTTON)).click();
        $(By.id(LOGIN_BUTTON)).shouldNotBe(Condition.visible);
        return page(BoardsPage.class);
    }
}
