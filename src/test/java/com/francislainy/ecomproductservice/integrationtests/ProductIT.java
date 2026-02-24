package com.francislainy.ecomproductservice.integrationtests;

import com.francislainy.ecomproductservice.TestcontainersConfiguration;
import com.francislainy.ecomproductservice.model.Product;
import com.francislainy.ecomproductservice.service.impl.ProjectConfig;
import com.francislainy.ecomproductservice.utils.TestUtils;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
@AutoConfigureMockMvc
@Import({TestcontainersConfiguration.class, ProjectConfig.class})
public class ProductIT {

    @Autowired
    MockMvc mockMvc;

    String productJson = """
            {
                "name": "Test Product",
                "price": 99.99,
                "description": "Test Description"
            }
            """;

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    void shouldCreateProductWhenAdmin() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productJson)
                .auth().with(user("admin").roles("ADMIN"))
                .when()
                .post("/api/v1/products")
                .then()
                .statusCode(201)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body("id", notNullValue(),
                        "name", equalTo("Test Product"),
                        "description", equalTo("Test Description"),
                        "price", equalTo(99.99f));
    }

    @Test
    void shouldNotCreateProductWhenUser() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productJson)
                .auth().with(user("user").roles("USER"))
                .when()
                .post("/api/v1/products")
                .then()
                .statusCode(403);
    }

    @Test
    void shouldNotCreateProductWhenUnauthenticated() {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productJson)
                .when()
                .post("/api/v1/products")
                .then()
                .statusCode(401);
    }

    @ParameterizedTest(name = "should return 400 when {1}")
    @MethodSource("invalidProducts")
    void shouldNotCrateProductWhenInvalidData(Product invalidProduct, String description) {
        given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(TestUtils.toJson(invalidProduct))
                .auth().with(user("admin").roles("ADMIN"))
                .when()
                .post("/api/v1/products")
                .then()
                .statusCode(400)
        ;
    }

    private static Stream<Arguments> invalidProducts() {
        return Stream.of(
                Arguments.of(Product.builder().name(null).description("some description").price(new BigDecimal("10")).build(), "name is null"),
                Arguments.of(Product.builder().name("").description("some description").price(new BigDecimal("10")).build(), "name is empty"),
                Arguments.of(Product.builder().name("Valid Name").description("some description").price(null).build(), "price is null"),
                Arguments.of(Product.builder().name("Valid Name").description("some description").price(new BigDecimal("-1")).build(), "price is negative"),
                Arguments.of(Product.builder().name("Valid Name").description("some description").price(new BigDecimal("0")).build(), "price is zero"),
                Arguments.of(Product.builder().name("Valid Name").description(null).price(new BigDecimal("10")).build(), "description is null"),
                Arguments.of(Product.builder().name("Valid Name").description("").price(new BigDecimal("10")).build(), "description is empty")
        );
    }


}
