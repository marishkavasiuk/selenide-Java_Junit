package myframework.pages;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;
import org.springframework.stereotype.Component;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;
import static myframework.extension.PlatformResolver.page;


@Component
public class BoardPage extends BasePage {

    private static final String BOARD_NAME_XPATH = "//p[@class='_3YQVlgFHhNubo1']";
    private static final String BOARD_MENU_CONTAINER_XPATH = "//div[@class='board-menu-container']";
    private static final String SHOW_BOARD_MENU_LINK_XPATH = "//a[@class='board-header-btn mod-show-menu js-show-sidebar']";
    private static final String MORE_LINK_IN_MENU_XPATH = "//a[@class='board-menu-navigation-item-link js-open-more']";
    private static final String CLOSE_BOARD_LINK_XPATH = "//a[@class='board-menu-navigation-item-link js-close-board']";
    private static final String CLOSE_BOARD_BUTTON_XPATH = "//input[@value='Close']";
    private static final String BOARD_DELETION_LINK_XPATH = "//button[contains(.,'Permanently delete board')]";
    private static final String DELETE_BUTTON_XPATH = "//button[contains(.,'Delete')]";
    private static final String ADD_CARD = "//span[@class='js-add-a-card']";
    private static final String CARD_NAME_INPUT = "//textarea[@class='list-card-composer-textarea js-card-title']";
    private static final String SAVE_CARD = "//input[@type='submit']";
    private static final String CARD = "//span[@class=\"list-card-title js-card-name\"]";

    public String getBoardName() {
        return $x(BOARD_NAME_XPATH).getText();
    }

    @Step
    public BoardsPage closeBoard() {
        openBoardMenu();
        $x(MORE_LINK_IN_MENU_XPATH).click();
        $x(CLOSE_BOARD_LINK_XPATH).click();
        $x(CLOSE_BOARD_BUTTON_XPATH).click();
        $x(BOARD_DELETION_LINK_XPATH).click();
        $x(DELETE_BUTTON_XPATH).click();
        return page(BoardsPage.class);
    }

    @Step
    private BoardPage openBoardMenu() {
        if (!$x(BOARD_MENU_CONTAINER_XPATH).isDisplayed()) {
            $x(SHOW_BOARD_MENU_LINK_XPATH).click();
            $x(BOARD_MENU_CONTAINER_XPATH).shouldBe(Condition.visible);
        }
        return this;
    }

    @Step
    public BoardPage addCard(String cardName) {
        $x(ADD_CARD).click();
        $x(CARD_NAME_INPUT).shouldBe(Condition.visible).sendKeys(cardName);
        $x(SAVE_CARD).click();
        return this;
    }

    @Step
    public boolean isCardAvailable(String cardName) {
        $x(BOARD_MENU_CONTAINER_XPATH).shouldBe(Condition.visible);
        $x(CARD).shouldBe(Condition.visible);
        return $$x(CARD).texts().contains(cardName);
    }
}
