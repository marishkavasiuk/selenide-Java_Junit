package myframework.test.ui;

import myframework.pages.BoardPage;
import myframework.pages.BoardsPage;
import myframework.pages.HomePage;
import myframework.pages.LoginPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static myframework.config.ConfigStrategy.testConfiguration;
import static myframework.extension.PlatformResolver.page;
import static myframework.rest.service.BoardServiceRestApiCommand.closeBoard;
import static myframework.rest.service.BoardServiceRestApiCommand.createBoard;
import static myframework.test.util.TestData.BOARD_NAME;
import static myframework.test.util.TestData.CARD_NAME;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("regression")
class CardTests extends TestBase {

    private static BoardsPage boardsPage;

    @BeforeEach
    void setUp() {
        page(HomePage.class).goToLoginPage();
        boardsPage = page(LoginPage.class).loginAs(testConfiguration.getTrelloAdminName(),
                testConfiguration.getTrelloAdminPass());
    }

    @Test
    void cardCreationTest() {
        createBoard(BOARD_NAME);
        boardsPage.goToBoard(BOARD_NAME)
                .addCard(CARD_NAME)
                .goToHomepage()
                .goToBoard(BOARD_NAME);

        assertTrue(page(BoardPage.class).isCardAvailable(CARD_NAME));
    }

    @AfterEach
    void tearDown() {
        closeBoard(BOARD_NAME);
    }
}
