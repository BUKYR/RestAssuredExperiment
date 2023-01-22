package demowebshop;

import com.codeborne.selenide.*;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

public class RegisterWithApi {

    String  cookie = "26C65B3C204EB8B8C19FD7FC9368EA7BAE547C6516113FAE285CE0B683438C8DB05CB6B5F666F511C4F50696BEB6576FC5EA098916E230B33A9EBDF6BD97CCD7B06B0263F22ED7E2A35438367E92CF1ADD3C928F6C14BFE41983575AD57289129367AF0936ED5C10454CFE0C23A53C98520172E6B64375407F612453065DE9D4C62BF1AF975DA39A20BD176C4EBAB1FF",
            endPoint = "productreviews/36",
            title = "Cool pants",
            reviewText = "These are the best pants in your life";

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demowebshop.tricentis.com/";
        Configuration.baseUrl = "https://demowebshop.tricentis.com/";
    }


    @Test
    void testAddingReview() {
        given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("NOPCOMMERCE.AUTH", cookie)
                .formParam("AddProductReview.Title",  title)
                .formParam("AddProductReview.ReviewText", reviewText)
                .formParam("AddProductReview.Rating", "5")
                .formParam("add-review", "Submit+review")
                .when()
                .post(endPoint)
                .then()
                .statusCode(200);

        open(endPoint);
        $(".product-review-list").lastChild().shouldHave(text(title)).shouldHave(text(reviewText));
    }
}
