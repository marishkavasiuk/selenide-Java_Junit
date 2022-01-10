package myframework.test.api;

import io.restassured.response.ValidatableResponse;
import myframework.model.BoardDto;
import myframework.rest.service.BoardServiceRestApiCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static myframework.rest.TrelloRestApiClient.getAppSpec;
import static myframework.test.util.TestData.BOARD_NAME;
import static myframework.test.util.TestData.boardsToDelete;
import static org.junit.jupiter.api.Assertions.*;

class BoardTests {
    public static final String GET_BOARD = "/1/boards/%s";
    public static final String GET_BOARDS = "/1/members/me/boards";
    public static final String CREATE_BOARD = "/1/boards";

    @Test
    void creationBoardTest() {
        ValidatableResponse response = given().spec(getAppSpec())
                .with().queryParam("name", BOARD_NAME)
                .post(CREATE_BOARD)
                .then()
                .statusCode(HTTP_OK);
        boardsToDelete.add(BOARD_NAME);

        assertEquals(response.extract().path("name"), BOARD_NAME);
        assertFalse(response.extract().path("closed"));

        String id = response.extract().path("id");
        assertTrue(Arrays.stream(given().spec(getAppSpec())
                .get(GET_BOARDS).then().extract().as(BoardDto[].class)).anyMatch(x->x.getId().equals(id)));
    }

    @Test
    void getBoardTest() {
        String id = given().spec(getAppSpec())
                .with().queryParam("name", BOARD_NAME)
                .post(CREATE_BOARD).then().extract().path("id");
        boardsToDelete.add(BOARD_NAME);
        ValidatableResponse response = given().spec(getAppSpec())
                .get(format(GET_BOARD, id)).then()
                .statusCode(HTTP_OK);

        assertEquals(response.extract().path("name"), BOARD_NAME);
    }

    @Test
    void getBoardsTest() {
        int sizeBeforeCreation = given().spec(getAppSpec())
                .get(GET_BOARDS).then().extract().as(BoardDto[].class).length;
        given().spec(getAppSpec())
                .with().queryParam("name", BOARD_NAME)
                .post(CREATE_BOARD);
        boardsToDelete.add(BOARD_NAME);
        int sizeAfterCreation = given().spec(getAppSpec())
                .get(GET_BOARDS).then().extract().as(BoardDto[].class).length;
        assertEquals(sizeBeforeCreation  + 1, sizeAfterCreation);
    }

    @Test
    void closingBoardTest() {
        String id = given().spec(getAppSpec())
                .with().queryParam("name", BOARD_NAME)
                .post(CREATE_BOARD)
                .then()
                .extract().path("id");
        given().spec(getAppSpec())
                .with().queryParam("closed", true)
                .put(format(GET_BOARD, id))
                .then()
                .statusCode(HTTP_OK);
        ValidatableResponse response = given().spec(getAppSpec())
                .get(format(GET_BOARD, id)).then().statusCode(HTTP_OK);

        assertTrue(response.extract().path("closed"));
    }

    @Test
    void deletingBoardTest() {
        String id = given().spec(getAppSpec())
                .with().queryParam("name", BOARD_NAME)
                .post(CREATE_BOARD)
                .then()
                .extract().path("id");

        given().spec(getAppSpec())
                .delete(format(GET_BOARD, id))
                .then()
                .statusCode(HTTP_OK);

        given().spec(getAppSpec())
                .get(format(GET_BOARD, id))
                .then()
                .statusCode(HTTP_NOT_FOUND);
    }

    @AfterEach
    public void tearDown() {
        boardsToDelete.forEach(BoardServiceRestApiCommand::closeBoard);
    }
}
