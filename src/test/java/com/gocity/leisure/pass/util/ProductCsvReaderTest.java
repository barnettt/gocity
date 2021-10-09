package com.gocity.leisure.pass.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.gocity.leisure.pass.entities.Product;
import org.assertj.core.api.Assert;
import org.assertj.core.data.Index;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles({ "test" })
@SpringBootTest
class ProductCsvReaderTest {

    @Autowired
    private ProductCsvReader productCsvReader;

    private String row1=  "1,Knife Set,A set of knives in all shapes and sizes.,1,9/20/2020 0:01,9/20/2020 0:01,10/24/2020 0:01";
    private String row2=   "2,Plate Rack,A storage solution for plates.,1,9/20/2020 0:01,9/20/2020 0:01,10/19/2020 0:01";

    @Test
    public void shouldReadProductDataFromAnArray(){
        String[] prod1 = row1.split(",");
        String[] prod2 = row2.split(",");

        List<String[]> productData = new ArrayList<>();
        productData.add(prod1);
        productData.add(prod2);

        List<Product> products =productCsvReader.readProductData(productData);
        assertThat(products)
                .hasSize(2);
    }

    @Test
    public void shouldReadProductDataFromCsvFile() throws Exception {
        try (BufferedReader br = new BufferedReader(new FileReader("src/test/resources/products_Test_csv.csv"))) {
            List<String[]> products = productCsvReader.loadProductData(br);
            assertThat(products)
                    .hasSize(3);
        }
    }
}
