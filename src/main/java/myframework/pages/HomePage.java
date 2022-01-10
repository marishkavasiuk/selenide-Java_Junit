package myframework.pages;

import io.qameta.allure.Step;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$x;
import static myframework.extension.PlatformResolver.page;

@Component
public class HomePage extends BasePage {
    public static final String LOGIN_BUTTON ="//a[contains(@href,'login')]";

    @Step
    public LoginPage goToLoginPage() {
        $x(LOGIN_BUTTON).click();
        return page(LoginPage.class);
    }
}
