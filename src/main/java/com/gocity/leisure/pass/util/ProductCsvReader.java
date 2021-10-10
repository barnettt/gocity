package com.gocity.leisure.pass.util;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.io.Reader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.gocity.leisure.pass.entities.Category;
import com.gocity.leisure.pass.entities.Product;
import com.opencsv.CSVReader;

public class ProductCsvReader {

    public List<String[]> loadProductData(Reader reader) throws IOException {
        List<String[]> list = new ArrayList<>();
        CSVReader csvReader = new CSVReader(reader);
        String[] line;
        int count = 0;
        while ((line = csvReader.readNext()) != null) {
            if (count != 0) { // skip the first row, column identifiers
                list.add(line);
            }
            count++;
        }
        reader.close();
        csvReader.close();
        return list;
    }


    public List<Product> readProductData(List<String[]> productData) {
        return productData.stream().map(ProductCsvReader::getProduct).collect(toList());
    }

    private static Product getProduct(final String[] productData) {
        Category category = new Category(3, "test category", LocalDateTime.now());
        return new Product(productData[0], productData[1], productData[2],
                productData[3], formatDate(productData[4]), formatDate(productData[5]),
                formatDate(productData[6]), category);
    }

    private static LocalDateTime formatDate(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        // check date for number of chars to first slash append 0.
        String day = date.substring(0, date.indexOf('/'));
        if (day.length() == 1) {
            date = "0" + date;
        }
        int ndx = date.indexOf(' ');
        String datePart = date.substring(0, ndx);
        String hours = date.substring(ndx).trim();
        if (hours.length() == 4) {
            date = datePart + " " + "0" + hours;
        }
        // check the hours after the space prepend a 0
        return LocalDateTime.parse(date, format);
    }
}
