package com.hms.service;

import com.hms.entity.AppUser;
import com.hms.payload.LoginDto;
import com.hms.repository.AppUserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private AppUserRepository appUserRepository;
    private JWTService jwtService;

    public UserService(AppUserRepository appUserRepository, JWTService jwtService) {
        this.appUserRepository = appUserRepository;
        this.jwtService = jwtService;
    }

    //    This method will verify the username and password and return the String value
    public String verifyLogin(LoginDto dto){
        Optional<AppUser> opUser = appUserRepository.findByUsername(dto.getUsername());//it will take the username from here and based on the username it will fetch the data from the database.
        if(opUser.isPresent()){
            AppUser appUser = opUser.get();//here we are getting the data from the database //here we are converting the optional object into the entity object.
            if(BCrypt.checkpw(dto.getPassword(),appUser.getPassword())) {//checkpw() takes two parameters plain password and hashed password//In the database we have the encrypted password so for the comparison between the original dto password and the database(encrypted password) password.
            //generate token
                String token = jwtService.generateToken(appUser.getUsername());
                return token;
            }
        }else{
            return null;
        }
        return null;
    }

}
