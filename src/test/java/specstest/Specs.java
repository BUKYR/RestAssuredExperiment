package specstest;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class Specs {

    public static RequestSpecification request = with()
            .baseUri("https://reqres.in/api")
            .contentType(ContentType.JSON)
            .log().uri();

    public static ResponseSpecification response = with()
            .expect().statusCode(200);





}