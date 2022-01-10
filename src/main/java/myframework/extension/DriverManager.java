package myframework.extension;

import com.codeborne.selenide.WebDriverRunner;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

@Slf4j
public class DriverManager {

    public static void clearCookies() {
        getDriver().manage().deleteAllCookies();
    }

    public static void closeWindow() {
        getDriver().close();
    }

    public static WebDriver getDriver() {
        return WebDriverRunner.getWebDriver();
    }
}
