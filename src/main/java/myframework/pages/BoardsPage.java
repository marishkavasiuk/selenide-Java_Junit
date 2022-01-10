package myframework.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.*;
import static java.lang.String.format;
import static myframework.extension.PlatformResolver.page;

@Component
public class BoardsPage extends BasePage {

    private static final String BOARD_CREATION_BUTTON = "//span[contains(., 'Create new board')]";
    private static final String BOARD_NEW_TITLE_INPUT = "//input[contains(@aria-label, 'Add board title')]";
    private static final String CREATE_BOARD_BUTTON = "//button[contains(., 'Create board')]";
    private static final String BOARD_TITLES = "//div[@class=\"board-tile-details-name\"]/div";
    private static final String BOARD = "//div[@class=\"board-tile-details-name\"]/div[contains(.,\"%s\")]";
    private static final String BOARDS_CONTENT = "//div[@class='content-all-boards']";

    @Step
    public BoardPage createBoard(String boardName) {
            $x(BOARD_CREATION_BUTTON).click();
            $x(BOARD_NEW_TITLE_INPUT).shouldBe(Condition.visible).sendKeys(boardName);
            sleep(2000);
            $x(CREATE_BOARD_BUTTON).click();
            return page(BoardPage.class);
    }

    @Step
    public boolean isBoardAvailable(String boardName) {
        $x(BOARDS_CONTENT).shouldBe(Condition.visible);
        $x(BOARD_TITLES).shouldBe(Condition.visible);
        if(!$$x(BOARD_TITLES).texts().isEmpty()) {
            return $$x(BOARD_TITLES).texts().contains(boardName);
        }
        return false;
    }

    @Step
    public BoardPage goToBoard(String boardName) {
        $x(format(BOARD, boardName)).shouldBe(Condition.visible).click();
        return page(BoardPage.class);
    }
}
