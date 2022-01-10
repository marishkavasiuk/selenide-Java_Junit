package myframework.test.ui;

import myframework.pages.BoardPage;
import myframework.pages.BoardsPage;
import myframework.pages.HomePage;
import myframework.pages.LoginPage;
import myframework.rest.service.BoardServiceRestApiCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static myframework.config.ConfigStrategy.testConfiguration;
import static myframework.extension.PlatformResolver.page;
import static myframework.pages.BasePage.waitSomeTime;
import static myframework.rest.service.BoardServiceRestApiCommand.createBoard;
import static myframework.test.util.TestData.BOARD_NAME;
import static myframework.test.util.TestData.boardsToDelete;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("regression")
class BoardTests extends TestBase {

    private static BoardsPage boardsPage;

    @BeforeEach
    void setUp() {
        page(HomePage.class).goToLoginPage();
        boardsPage = page(LoginPage.class).loginAs(testConfiguration.getTrelloAdminName(),
                testConfiguration.getTrelloAdminPass());
    }

    @Test
    void creationBoardTest() {
        BoardPage boardPage = boardsPage.createBoard(BOARD_NAME);
        boardPage.goToHomepage();
        assertTrue(boardsPage.isBoardAvailable(BOARD_NAME));
        boardsToDelete.add(BOARD_NAME);
    }

    @Test
    void closingBoardTest() {
        createBoard(BOARD_NAME);
        waitSomeTime(4000);
        page(BoardsPage.class).goToBoard(BOARD_NAME).closeBoard();
        assertFalse(boardsPage.isBoardAvailable(BOARD_NAME));
    }

    @AfterEach
    void tearDown() {
        boardsToDelete.forEach(BoardServiceRestApiCommand::closeBoard);
    }

}
