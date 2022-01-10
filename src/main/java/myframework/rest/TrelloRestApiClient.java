package myframework.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.io.InputStream;

public class TrelloRestApiClient {

  private static RequestSpecification appSpec;
  private static final ObjectMapper objectMapper = new ObjectMapper();

  private static void createAppSpecification() {
    appSpec =
        new RequestSpecBuilder()
            .setBaseUri("https://api.trello.com")
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .addQueryParam("key", "d121241c04e3ab65eb59f6c67c1c5170")
            .addQueryParam("token", "1c41cf67773360b6dc750fe420288779bf34c89d8b5e50d610f533e6a08ab986")
            .setBody("{\"key\": \"d121241c04e3ab65eb59f6c67c1c5170\", \"token\": \"1c41cf67773360b6dc750fe420288779bf34c89d8b5e50d610f533e6a08ab986\"}")
            .build();

    objectMapper
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        .registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
  }

  private static void configRestAssured() {
    RestAssured.config =
        RestAssuredConfig.config()
            .objectMapperConfig(
                new ObjectMapperConfig().jackson2ObjectMapperFactory((aClass, s) -> objectMapper));
  }

  public static RequestSpecification getAppSpec() {
    configRestAssured();
    createAppSpecification();
    return appSpec;
  }

  @Step("Extract value from json")
  public static String getValue(Response response, String jsonPath){
    return response.jsonPath().getObject(jsonPath, String.class);
  }

  @Step("Deserialize response into Java objects")
  public static <T> T asPojo(Response response, Class<T> tClass) throws IOException {
    InputStream inputStream = response.getBody().asInputStream();
    return objectMapper.readValue(inputStream, tClass);
  }
}
