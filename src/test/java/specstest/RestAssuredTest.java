package specstest;

import org.junit.jupiter.api.Test;
import specstest.models.*;


import static io.restassured.RestAssured.given;
import static specstest.Specs.request;
import static specstest.Specs.response;
import static org.assertj.core.api.Assertions.*;



public class RestAssuredTest {


    @Test
    void checkBodyResponseOfSingleUserData() {

    SingleUserBodyData data = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(response)
                .log().body()
                .extract().as(SingleUserBodyData.class);

        assertThat(data.getData().getId()).isEqualTo(2);
        assertThat(data.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
        assertThat(data.getData().getFirstName()).isEqualTo("Janet");
        assertThat(data.getData().getLastName()).isEqualTo("Weaver");
        assertThat(data.getData().getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");

        assertThat(data.getSupport().getUrl()).isEqualTo("https://reqres.in/#support-heading");
        assertThat(data.getSupport().getText()).isEqualTo("To keep ReqRes free, contributions towards server costs are appreciated!");

    }

    @Test
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











}
