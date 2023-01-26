package specstest;

import org.junit.jupiter.api.Test;
import specstest.models.*;


import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static specstest.Specs.request;
import static specstest.Specs.response;
import static org.assertj.core.api.Assertions.*;


public class RestAssuredTest {


    @Test
    void testForSingleUserWhitModel() {

        UserData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(response)
                .log().body()
                .extract().as(UserData.class);

        assertThat(data.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
        assertThat(data.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");

    }

    @Test
    void checkEmailInUserListForSingleUser() {
        given()
                .spec(request)
                .when()
                .get("/users?page=2")
                .then()
                .spec(response)
                .body("data.findAll{it.id == 7}.email", hasItem("michael.lawson@reqres.in"));
    }

    @Test
    void testGetListUserArrayCount() {
        given()
                .spec(request)
                .when()
                .get("/users?page=2")
                .then()
                .spec(response)
                .log().body()
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









}
