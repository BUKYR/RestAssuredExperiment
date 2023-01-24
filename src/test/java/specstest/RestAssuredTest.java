package specstest;

import org.junit.jupiter.api.Test;
import specstest.models.*;


import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specstest.Specs.request;
import static specstest.Specs.response;

public class RestAssuredTest {


    @Test
    void testGetListUserArrayCount() {
        given()
                .spec(request)
                .when()
                .get("/users?page=2")
                .then()
                .spec(response)
                .body("data.size()", is(6));
    }

    @Test
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

    @Test
    void testOfDeleteUser() {

        given()
                .spec(request)
                .when()
                .delete("/users/2")
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
                .post("https://reqres.in/api/register")
                .then()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void checkFullResponseBody() {

        given()
                .spec(request)
                .when()
                .get("/unknown/2")
                .then()
                .log().body()
                .body("data.name", is("fuchsia rose"));

    }

    @Test
    void testModelForSingleUser() {

       UserData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(response)
                .log().body()
                .extract().as(UserData.class);
        assertEquals(2, data.getData());

    }







}