package com.gocity;

import com.gocity.leisure.pass.repository.ProductRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@TestConfiguration
public class TestApplicationContext {

    @Bean
    @Primary
    public static ProductRepository productRepository() {
        return Mockito.mock(ProductRepository.class);
    }
}
