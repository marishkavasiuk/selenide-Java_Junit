package myframework.rest.service;

import myframework.model.BoardDto;

import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.net.HttpURLConnection.HTTP_OK;
import static myframework.rest.TrelloRestApiClient.getAppSpec;

public class BoardServiceRestApiCommand {
  public static final String GET_BOARD = "/1/boards/%s";
  public static final String GET_BOARDS = "/1/members/me/boards";
  public static final String CREATE_BOARD = "/1/boards";

  public static void closeBoardApiRequest(String id) {
    given().spec(getAppSpec())
            .with().queryParam("closed", true)
            .put(format(GET_BOARD, id))
            .then()
            .statusCode(HTTP_OK);
  }
  public static void closeBoard(String boardName){
    Arrays.stream(getBoards()).filter(i->i.getName().equals(boardName)).map(BoardDto::getId)
            .forEach(BoardServiceRestApiCommand::closeBoardApiRequest);
  }

  public static BoardDto[] getBoards() {
    return given().spec(getAppSpec())
            .get(GET_BOARDS)
            .then()
            .statusCode(HTTP_OK)
            .extract().as(BoardDto[].class);
  }

  public static BoardDto createBoard(String boardName){
    return given().spec(getAppSpec())
            .with().queryParam("name", boardName)
            .post(CREATE_BOARD)
            .then()
            .statusCode(HTTP_OK)
            .extract().as(BoardDto.class);
  }

}
