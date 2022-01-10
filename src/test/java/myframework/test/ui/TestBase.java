package myframework.test.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverProvider;
import myframework.extension.DriverManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;

import static java.net.URI.create;
import static myframework.config.ConfigStrategy.testConfiguration;

public class TestBase {
    private static String runType;

    @BeforeEach
    public void beforeOwnMethod(TestInfo testInfo) {
        selectConfiguration();
        LogManager.getLogger().info("   ---=== The tests run on the: " + runType + " environment ===---");
        Selenide.open(Configuration.baseUrl);
        LogManager.getLogger().info("RUNNING " + testInfo.getDisplayName());
    }

    private static void selectConfiguration() {
        runType = !StringUtils.isEmpty(System.getProperty("run.on.env")) ? System.getProperty("run.on.env")
                : testConfiguration.getEnv();
        if (runType.equals("remote")) {
            setupRemoteConfiguration();
        } else {
            setupLocalConfiguration();
        }
    }

    private static void setupLocalConfiguration() {
        Configuration.browser = !StringUtils.isEmpty(System.getProperty("browser")) ?
                System.getProperty("browser"): "chrome";
        if (Configuration.browser.equals("mobile")) {
            System.setProperty("chromeoptions.mobileEmulation", "deviceName=Pixel 2 XL");
        }
        Configuration.browserSize = "1280x1024";
        setupBaseConfiguration();
    }

    private static void setupRemoteConfiguration() {
        Configuration.browser = SelenoidWebDriverProvider.class.getName();
        setupBaseConfiguration();
    }

    private static void setupBaseConfiguration() {
        Configuration.screenshots = true;
        Configuration.timeout = 25000;
        Configuration.baseUrl = testConfiguration.getBaseUrl();
    }

    public static class SelenoidWebDriverProvider implements WebDriverProvider {
        @Nonnull
        public WebDriver createDriver(DesiredCapabilities capabilities) {
            DesiredCapabilities browser = new DesiredCapabilities();
            browser.setBrowserName(!StringUtils.isEmpty(System.getProperty("browser")) ?
                    System.getProperty("browser"): "chrome");
            browser.setCapability("enableVNC", true);
            capabilities.setCapability("enableVideo", false);
            capabilities.setCapability("screenResolution", "1920x1080x24");
            capabilities.setAcceptInsecureCerts(true);
            try {
                return new RemoteWebDriver(
                        create("http://localhost:4444/wd/hub").toURL(), browser
                );
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @AfterEach
    public void afterMethod(){
        DriverManager.clearCookies();
        DriverManager.getDriver().quit();
    }
}

