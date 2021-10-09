package com.gocity.leisure.pass.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

import com.gocity.leisure.pass.repository.CategoryRepository;
import com.gocity.leisure.pass.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@ActiveProfiles({"test"})
@SpringBootTest()
@TestPropertySource(
        locations = "classpath:application-test.properties")
class LoadProductDataTest {

    // could not override repository bean either by using @Bean, @Primary in a test config
    // or the by setting the property "spring.main.allow-bean-definition-overriding=true"
    @Mock
    ProductRepository repository = mock(ProductRepository.class);
    @Mock
    CategoryRepository categoryRepository = mock(CategoryRepository.class);

    @Autowired
    LoadProductTable loadProductTable;

    @Autowired
    ApplicationContext ctx;

    public LoadProductDataTest() {
    }

    @BeforeEach
    void setUp() {
        loadProductTable.setRepository(repository);
        loadProductTable.setCategoryRepository(categoryRepository);
    }

    @Test
    void shouldApplyProductsToDatabase() {
        long numberOfRecords = loadProductTable.bootStrapProducts(ctx);
        assertThat(numberOfRecords, is(3L));
    }
}
