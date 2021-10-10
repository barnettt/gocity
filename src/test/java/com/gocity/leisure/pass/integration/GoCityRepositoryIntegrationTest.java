package com.gocity.leisure.pass.integration;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.leisure.pass.dto.CategoryDto;
import com.gocity.leisure.pass.dto.ProductDto;
import com.gocity.leisure.pass.repository.CategoryRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * testing the application with h2 repository
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoCityRepositoryIntegrationTest {

    private static final String LOCALHOST = "http://localhost:";
    @LocalServerPort
    Integer port;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void when_Get_all_prod_thenOK() {

        ExtractableResponse<Response> response = RestAssured
                .given().port(port)
                .contentType("application/json")
                .when().request("GET", LOCALHOST + port + "/gocity/v1.0/products")
                .then().statusCode(200)
                .extract();
        List<ProductDto> products = response.body().jsonPath().getList(".", ProductDto.class);
        assertThat(products).allSatisfy(office ->assertThat(products).isNotEmpty());
    }

    @Test
    public void when_insert_update_product_thenOK() throws Exception {

        // create a new product
        String json = getProductAsJson();
        ExtractableResponse<Response> response = RestAssured
                .given().port(port)
                .contentType("application/json")
                .body(json)
                .when().request("PUT", LOCALHOST + port + "/gocity/v1.0/products")
                .then().statusCode(HttpStatus.SC_OK)
                .extract();
        ProductDto product = response.body().jsonPath().getObject(".", ProductDto.class);
        assertThat(product).hasFieldOrPropertyWithValue("name", "Test Product")
                .hasFieldOrPropertyWithValue("description", "Rest assured test product");
        assertThat(product.getId()).isPositive();

        // update the new product
        product.setName("Rest assured update");
        product.setDescription("Rest assured test description");

        response = RestAssured
                .given().port(port)
                .contentType("application/json")
                .body(getJsonString(product))
                .when().request("POST", LOCALHOST + port + "/gocity/v1.0/products/{id}", product.getId())
                .then().statusCode(HttpStatus.SC_OK)
                .extract();
        product = response.body().jsonPath().getObject(".", ProductDto.class);
        assertThat(product).hasFieldOrPropertyWithValue("name", "Rest assured update")
                .hasFieldOrPropertyWithValue("description", "Rest assured test description");
        assertThat(product.getId()).isPositive();
    }

    private String getProductAsJson() throws JsonProcessingException {
        LocalDateTime dateTime = LocalDateTime.now();
        ProductDto product = ProductDto.builder()
                .name("Test Product")
                .description("Rest assured test product")
                .creationDate(dateTime)
                .updatedDate(dateTime)
                .lastPurchasedDate(dateTime)
                .categoryId(4)
                .category(CategoryDto.builder().id(4).categoryName("Electric")
                        .build()).build();
        return getJsonString(product);
    }

    private String getJsonString(final ProductDto product) throws JsonProcessingException {
        return mapper.writeValueAsString(product);
    }
}
