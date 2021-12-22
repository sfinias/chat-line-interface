package com.sfinias;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CatResourceTest {

    @Test
    public void testCatEndpoint() {

        given()
                .when().get("/cat")
                .then()
                .statusCode(200)
                .body("id", notNullValue(),
                        "url", notNullValue());
    }

    @Test
    public void testCatGifEndpoint() {

        given()
                .when().get("/cat/gif")
                .then()
                .statusCode(200)
                .body("id", notNullValue(),
                        "url", notNullValue());
    }
}