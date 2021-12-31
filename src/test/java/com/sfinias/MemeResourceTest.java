package com.sfinias;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
class MemeResourceTest {

    @Test
    void testMemeEndpoint() {

        given()
                .when().get("/meme")
                .then()
                .statusCode(200)
                .body("url", notNullValue());
    }
}