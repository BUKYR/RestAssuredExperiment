import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class RestAssuredTest {

    @Test
    void testGetListUserArrayCount() {
        given().
                log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().status()
                .statusCode(200)
                .body("data.size()", is(6));
    }
}
