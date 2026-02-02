package com.francislainy.ecomproductservice.integrationtests;

import com.francislainy.ecomproductservice.TestcontainersConfiguration;
import com.francislainy.ecomproductservice.service.impl.ProjectConfig;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class, ProjectConfig.class})
public class ProductIT {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateProductWhenAdmin() {
        String productJson = """
                {
                    "name": "Test Product",
                    "price": 99.99
                }
                """;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productJson)
                .auth().with(user("admin").roles("ADMIN"))
                .when()
                .post("/api/v1/products")
                .then()
                .statusCode(201);
    }

    @Test
    void shouldNotCreateProductWhenUser() {
        String productJson = """
                {
                    "name": "Test Product",
                    "price": 99.99
                }
                """;

        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productJson)
                .auth().with(user("user").roles("USER"))
                .when()
                .post("/api/v1/products")
                .then()
                .statusCode(403);
    }

}
