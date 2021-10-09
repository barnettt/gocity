package com.gocity.leisure.pass.util;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import com.gocity.GoCityApplication;
import com.gocity.TestApplicationContext;
import com.gocity.leisure.pass.entities.Product;
import com.gocity.leisure.pass.repository.ProductRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles({ "test" })
@SpringBootTest()
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class LoadProductDataTest {

    // could not override repository bean either by using @Bean, @Primary in a test config
    // or the by setting the property "spring.main.allow-bean-definition-overriding=true"
    @Mock
    ProductRepository repository = mock(ProductRepository.class);

    @Autowired
    LoadProductTable loadProductTable;

    @Autowired
    ApplicationContext ctx;
    public LoadProductDataTest(){}

    @BeforeEach
    public void setUp() {
        loadProductTable.setRepository(repository);
    }
    @Test
    public void shouldApplyProductsToDatabase(){
        long numberOfRecords = loadProductTable.bootStrapProducts(ctx);
        assertThat(numberOfRecords, is(3L));
    }
}
