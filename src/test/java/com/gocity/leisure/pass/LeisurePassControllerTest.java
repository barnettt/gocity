package com.gocity.leisure.pass;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.GoCityApplication;
import com.gocity.leisure.pass.controller.LeisurePassController;
import com.gocity.leisure.pass.db.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {GoCityApplication.class})
@AutoConfigureMockMvc
public class LeisurePassControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    LeisurePassController leisurePassController;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void shouldReturnAllProductsToClient() throws Exception {
        String result = mvc.perform(get("/gocity/products")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        TypeReference<ArrayList<Product>> typeRef = new TypeReference<>() {
        };
        List<Product> products = mapper.readValue(result, typeRef);

    }

}
