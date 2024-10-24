package com.hms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/country")
public class CountryController {
//    This particular url(http://localhost:8080/api/v1/country) when we want to access it should be accessed only when
//    a token is set with this url and the token is valid.Otherwise this url will not be accessible.

    @PostMapping
    public String addCountry(){
        return "added";
    }
}
