package com.angel.inmemoryauthentication.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeneralController {

    @RequestMapping("/public")
    public String mpublic() {
        return "This works !";
    }

    @RequestMapping("/confidential")
    public String confidential() {
        return "I mean nothing confidential here ...";
    }
}
