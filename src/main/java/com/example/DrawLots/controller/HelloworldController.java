package com.example.DrawLots.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloworldController {

    @GetMapping("helloworld")
    public String helloworld() {
        return "Hello World";
    }
}
