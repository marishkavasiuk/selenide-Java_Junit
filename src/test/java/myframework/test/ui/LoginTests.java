package myframework.test.ui;

import myframework.pages.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;


import static myframework.config.ConfigStrategy.testConfiguration;
import static myframework.extension.PlatformResolver.page;
import static myframework.pages.BasePage.waitSomeTime;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("regression")
class LoginTests extends TestBase {

    @Test
    void testCorrectLogin() {
        page(HomePage.class).goToLoginPage();
        String expectedBoardPageTitle = "Boards | Trello";
        String expectedLoggedOutPage = "Logged out of Trello";

        page(LoginPage.class).loginAs(testConfiguration.getTrelloAdminName(), testConfiguration.getTrelloAdminPass());
        assertEquals(expectedBoardPageTitle, page(BoardsPage.class).getTitle());

        LogOutPage logOutPage = page(LoginPage.class).logout();
        waitSomeTime(4000);
        assertEquals(logOutPage.getTitle(), expectedLoggedOutPage);
    }
}
