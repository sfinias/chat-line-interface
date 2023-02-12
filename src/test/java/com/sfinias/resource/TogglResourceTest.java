package com.sfinias.resource;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;

import com.sfinias.service.TogglService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.util.Collections;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
@TestHTTPEndpoint(TogglResource.class)
class TogglResourceTest {

    @InjectMock
    @RestClient
    TogglService togglService;

    @BeforeAll
    static void beforeAll() {

    }

    @Test
    void getProjects() {

        Mockito.when(togglService.getPersonalProjects(anyString())).thenReturn(Collections.emptyList());
        when().get("/projects")
                .then()
                .statusCode(200)
                .body(is("[]"));
    }
}