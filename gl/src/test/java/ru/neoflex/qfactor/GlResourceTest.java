package ru.neoflex.qfactor;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GlResourceTest {


    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testCurrenciesEndpoint() {
        given()
                .when().get("/gl/glaccount")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

}