package ru.neoflex.qfactor;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;
import ru.neoflex.qfactor.refs.controllers.RefsResource;

import javax.inject.Inject;

import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class RefsResourceTest {
    @Inject
    RefsResource refsResource;

    @Test
    @TestSecurity(user = "testUser", roles = {"admin", "user"})
    public void testCurrenciesEndpoint() {
        refsResource.getPartyList("", Optional.empty(), 0, 20);
        given()
                .when().get("/refs/currency")
                .then()
                .statusCode(200)
                .body("size()", is(0));
    }

}