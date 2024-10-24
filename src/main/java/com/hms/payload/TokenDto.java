package com.hms.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TokenDto {
    private String token;
    private String type;
//    So, we will take that token and put that into the json object and return.
//    Whatever response we are giving in the response to the postman, always make a practice to set it as a json object using
//    ResponseEntity<> as the return type of the method
}
