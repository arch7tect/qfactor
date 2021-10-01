package ru.neoflex.qfactor;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GlResourceTest {


    @Test
    public void testCurrenciesEndpoint() {
//        given()
//                .when().get("/api/gl/glaccount")
//                .then()
//                .statusCode(200)
//                .body("size()", is(0));
    }

}