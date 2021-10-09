package com.gocity.leisure.pass.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.gocity.leisure.pass.entities.Category;
import com.gocity.leisure.pass.entities.Product;
import com.gocity.leisure.pass.repository.CategoryRepository;
import com.gocity.leisure.pass.repository.ProductRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;


@Slf4j
@NoArgsConstructor
public class LoadProductTable {

    ProductRepository repository;
    CategoryRepository categoryRepository;

    @Value("${fileName}")
    String fileName;

    @Value("#{new Boolean(${loadFromFile})}")
    Boolean loadFromFile;

    public long bootStrapProducts(ApplicationContext ctx) {
        long numberOfRecords = 0;
        boolean isLoadFromFile = loadFromFile;
        if (ctx.containsBean("loadStrapDbData") && isLoadFromFile) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

                ProductCsvReader scvReader = (ProductCsvReader) ctx.getAutowireCapableBeanFactory().getBean("loadStrapDbData");
                List<String[]> data = scvReader.loadProductData(br);
                List<Product> products = scvReader.readProductData(data);
                numberOfRecords = addProductsToDb(products);
            } catch (Exception ex) {
                log.error("Unable to parse or load the Product data File..... exiting");
                System.exit(1);
            }
        }
        return numberOfRecords;
    }


    private long addProductsToDb(final List<Product> products) {
        log.info("adding products");
        AtomicLong count = new AtomicLong(0);
        products.forEach(product -> {
            Optional<Category> category = categoryRepository.findById(product.getCategoryId());
            product.setCategory(category.orElse(new Category(999, "Unknown", LocalDateTime.now())));
            repository.save(product);
            count.incrementAndGet();
        });
        return count.get();
    }

    @Autowired
    public void setRepository(final ProductRepository repository) {
        this.repository = repository;
    }

    @Autowired
    public void setCategoryRepository(final CategoryRepository repository) {
        this.categoryRepository = repository;
    }
}
