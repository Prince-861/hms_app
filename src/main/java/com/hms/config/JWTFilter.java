package com.hms.config;

import com.hms.entity.AppUser;
import com.hms.repository.AppUserRepository;
import com.hms.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

//here we have used the @Component so that we can perform the dependency injection.
@Component
public class JWTFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private AppUserRepository userRepository;

    public JWTFilter(JWTService jwtService, AppUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    //    here request is a reference variable.
//    The incoming url with the token will automatically come to this request object.This is all happening internally behind the scene.
//    So, what happens here, whenever an incoming request comes, automatically that incoming request has to come here when that incoming request which has token.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");// so from the incoming url, it will go the header and from that header it will the read the Authorization, and the Authorization has the token
        System.out.println(token);
        if(token!= null && token.startsWith("Bearer ")){
            String tokenVal = token.substring(8,token.length() - 1);//here we are starting with the 8th position as "Bearer " is coming in the start and it has the length 7 so we are skipping the length of the Bearer
            String username = jwtService.getUsername(tokenVal);
            Optional<AppUser> opUsername = userRepository.findByUsername(username);
            if(opUsername.isPresent()){//here Optional object will avoid the NullPointerException.//Further code will be about we will tell spring security that this is the valid token, please process the request and send the response back.

            }
            
        }
        filterChain.doFilter(request,response);//doInternalFilter() method should run after login //by adding this line we are telling spring security that do not send all the urls here. Only the urls that comes with the token let it come here and the urls that comes without token please don't send it here.
    }
}
