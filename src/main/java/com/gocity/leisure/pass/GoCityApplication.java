package com.gocity.leisure.pass;


import com.gocity.leisure.pass.util.LoadProductTable;
import com.gocity.leisure.pass.util.ProductCsvReader;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class GoCityApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(GoCityApplication.class, args);
        // load the data once the apps running, only the product data
        LoadProductTable productTable = (LoadProductTable) ctx.getAutowireCapableBeanFactory().getBean("loadProductTable");
        productTable.bootStrapProducts(ctx);
    }

    @Bean
    public ProductCsvReader loadStrapDbData() {
        return new ProductCsvReader();
    }

    @Bean
    public LoadProductTable loadProductTable() {
        return new LoadProductTable();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
