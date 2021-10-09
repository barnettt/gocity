package com.gocity.leisure.pass;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.GoCityApplication;
import com.gocity.leisure.pass.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles({"test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {GoCityApplication.class})
@AutoConfigureMockMvc
class LeisurePassControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void shouldReturnAllProductsToClient() throws Exception {
        String result = mvc.perform(get("/gocity/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        TypeReference<ArrayList<Product>> typeRef = new TypeReference<>() {};
        List<Product> products = mapper.readValue(result, typeRef);
        assertThat(products.size(),is(34));
    }

    @Test
    public void shouldReturnProductsCategory() throws Exception {
        String result = mvc.perform(get("/gocity/products/category{id}",2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        TypeReference<ArrayList<Product>> typeRef = new TypeReference<>(){};
        List<Product> products = mapper.readValue(result, typeRef);
        assertThat(products.size(),is(34));
    }

    @Test
    public void shouldUpdateProduct() throws Exception {
        String json = mapper.writeValueAsString(getGocityProduct());
        String result = mvc.perform(put("/gocity/products/{id}",2)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(""))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        TypeReference<ArrayList<Product>> typeRef = new TypeReference<>(){};
        List<Product> products = mapper.readValue(result, typeRef);
        assertThat(products.size(),is(34));
    }

    private Product getGocityProduct() {
        return null;
    }
}
