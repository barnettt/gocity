package com.gocity.leisure.pass.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/gocity")
public class LeisurePassController {
    @RequestMapping("/products")
    String hello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }
}
