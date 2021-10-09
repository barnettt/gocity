package com.gocity;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.gocity.leisure.pass.GoCityApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(classes = {GoCityApplication.class})
class GoCityApplicationTests {
    @Autowired
    ApplicationContext ctx;

    @Test
    void contextLoads() {
        assertNotNull(ctx);
    }

}
