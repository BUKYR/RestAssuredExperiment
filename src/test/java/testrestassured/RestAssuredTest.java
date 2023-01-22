package testrestassured;

import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;


public class RestAssuredTest {


    String baseUrl = "https://reqres.in/api";

    @Test
    void testGetListUserArrayCount() {
        given()
                .log().uri()
                .when()
                .get(baseUrl + "/users?page=2")
                .then()
                .log().status()
                .statusCode(200)
                .body("data.size()", is(6));
    }

    @Test
    void testSchemaForSingleUser() {

        given()
                .log().uri()
                .when()
                .get(baseUrl + "/users/2")
                .then()
                .log().status()
                .log().body()
                .body(matchesJsonSchemaInClasspath("SchemaForSingleUser.json"));
    }

    @Test
    void testOfDeleteUser() {

        given()
                .log().uri()
                .when()
                .delete(baseUrl + "/users/2")
                .then()
                .statusCode(204);
    }

    @Test
    void testRegisterWithUnsuccessfulPassword() {

        String bodyRequest = "{ \"email\": \"sydney@fife\" }";

        given()
                .log().uri()
                .when()
                .body(bodyRequest)
                .post(baseUrl + "/register")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void checkFullResponseBody() {

        given()
                .log().uri()
                .when()
                .get(baseUrl + "/unknown/2")
                .then()
                .log().body()
                .body("data.name", is("fuchsia rose"));




    }







}
