package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.payload.TokenDto;
import com.hms.repository.AppUserRepository;
import com.hms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
//    Actually this repository layer should come in the service layer(this we have to do by urself only).
    private AppUserRepository appUserRepository;
    private UserService userService;

    public UserController(AppUserRepository appUserRepository, UserService userService) {
        this.appUserRepository = appUserRepository;
        this.userService = userService;
    }

//    This  should return the Dto but we are returning the AppUser(do it urself)
//    Here we are using the ? because this method will return the different-different kinds of values.
//    Do not give the @PostMapping annotation and stop, give the url also otherwise when we give the @PostMapping in other method in the same class then it will give the ambiguity.
    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody AppUser user ){ //so we will send the data to it and it should be saved in the database
//        But before saving the data to the database we should check --> do we have user with this email_id or the username with this email_id
//        If it is present then we can't create the user again(for that we will have to write the logic).-->we will go to the repository layer and we will develop one finder method.

        Optional<AppUser> opUsername = appUserRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()){
            return new ResponseEntity<>("Username already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<AppUser> opEmail = appUserRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()){
            return new ResponseEntity<>("Email already taken", HttpStatus.INTERNAL_SERVER_ERROR);
        }
//        when both the conditions (regarding the username and email existence gets false then we can go for the below condition)
//        Here we will encrypt the password before saving the password into the database.
        String encryptedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5));//here log_rounds = 5, that will give 2^5 rounds of encryption.
        user.setPassword(encryptedPassword);
        AppUser savedUser = appUserRepository.save(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

    @GetMapping("/message")
    public String getMessage(){
        return "hello";
    }

//  The job of this method is to verify the username and password
//   @RequestBody will copy the data from the JSON to dto
//    HttpStatus.UNAUTHORIZED means certain features of application we cannot use(like in hotstar when we want to use the VIP access then we can't use but we can use some of the free service provided by it).
//    Authentication means login is successful or not.
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto){
        String token = userService.verifyLogin(dto);
        if(token!=null){
            TokenDto tokenDto = new TokenDto();//since in postman we do not return the jwt token as it is rather we will return it as the json object.
            tokenDto.setToken(token);
            tokenDto.setType("JWT");
            return new ResponseEntity<>(tokenDto, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid username/password", HttpStatus.FORBIDDEN); //FORBIDDEN(403) means we are not able to use(means we can't do login at all) .
        }
    }
}
