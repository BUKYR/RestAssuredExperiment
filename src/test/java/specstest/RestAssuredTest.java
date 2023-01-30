package specstest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import specstest.models.*;


import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertAll;
import static specstest.Specs.request;
import static specstest.Specs.response;
import static org.assertj.core.api.Assertions.*;



public class RestAssuredTest {


    @Test
    @DisplayName("Проверка тела пользователя")
    void checkBodyResponseOfSingleUserData() {

    SingleUserBodyData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(response)
                .log().body()
                .extract().as(SingleUserBodyData.class);

        assertAll("Check all keys in body",

        () -> assertThat(data.getData().getId()).isEqualTo(2),
        () -> assertThat(data.getData().getEmail()).isEqualTo("janet.weaver@reqres.in"),
        () -> assertThat(data.getData().getFirstName()).isEqualTo("Janet"),
        () -> assertThat(data.getData().getLastName()).isEqualTo("Weaver"),
        () -> assertThat(data.getData().getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg"),

        () -> assertThat(data.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading"),
        () -> assertThat(data.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!"));

    }

    @Test
    @DisplayName("Проверка тела ответа и статус кода при создании нового пользователя")
    void createNewUser() {

       RequestCreateUser requestBody = new RequestCreateUser();
       requestBody.setName("Smith");
       requestBody.setJob("Agent");

       ResponseCreateUser data = given()
                .spec(request)
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .log().body()
                .extract().as(ResponseCreateUser.class);

       //assertThat(data.getName()).isEqualTo("Smith");
       //assertThat(data.getJob()).isEqualTo("Agent");
       assertThat(data.getId()).isNotNull();
       assertThat(data.getCreatedAt()).isNotNull();

    }

    @ValueSource(ints = {13, 14, 15, 16})
    @ParameterizedTest(name = "Проверка статускода при ненайденом пользователе c id {0}")
    void checkUserNotFound(int id) {

        given()
                .spec(request)
                .when()
                .get("/users/" + id)
                .then()
                .statusCode(404);

    }

    @Test
    @DisplayName("Проверка статус кода при удалении пользователя")
    void checkStatusCodeDelete() {

        given()
                .spec(request)
                .when()
                .delete("/users/2")
                .then()
                .log().all()
                .statusCode(204);

    }

    @Test
    @DisplayName("Проверка JSON схемы тела пользователя")
    void testSchemaForSingleUser() {

        given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("SchemaForSingleUser.json"));
    }

}
