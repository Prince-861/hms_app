package com.hms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

//When we are telling that this class I am giving to the SpringBoot(when this class is loading to the springboot means acutally)
//the method with the @Bean annotation is actually returning one object with the Http object with the configuration details present in it
//and this configuration details is what Springboot has to study and analyze the incoming method and automatically take a required action.

//whenever I am making any http request, there are chances that my application can be attacked by some hackers.
//first attack he can do is CSRF(Cross-Site Request Forgery) attack

@Configuration
public class SecurityConfig {
    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }


//    below is the particular method where we will configure which url can be accessed by whom?
//    The method name can be anything but the method return type should be SecurityFilterChain
//    @Bean will do--> whatever http object is there now, in this object it would configure which
//    url will work what way and this object we are actually giving to the Springboot.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
//        Mentioning HttpSecurity http means whatever incoming http request are there from postman(like -->  http://localhost:8080/api/v1/users/message) that will be captured in this
//       when we develop an api, from where this api got accessed-->from some frontend framework(like angular, react)
//        when we enable the cors() policy then we can tell that only an api from a particular domain can be accessed.
//        So angular, postman is client so we can enable the cors() policy and tell that this can only be accessed from the postman of this IP address(something like that).

//        h(cd)2
        http.csrf().disable().cors().disable();//we will get error because it will work with the springboot 3.0.0 version, this can also work with the older version of springboot.

        http.addFilterBefore(jwtFilter,AuthorizationFilter.class);//If we want to doFilterInternal() method  to run then this line must be added.//First it will run the jwtFilter and then it will run the AuthorizationFilters  //if we do not add this line, our JWT Filter will not run.
        //haap(here we are telling that don't secure any url keep all url open).
        http.authorizeHttpRequests().anyRequest().permitAll();//this line of code will configure our springboot security telling that keep all the urls open. Do not secure any url for time being, later we will tell that which url we have to secure and which to not.

        //below line will create the object
//        The above all configuration details we are putting into the http  object that's why we are accessing with the (http.) in h(cd)2 and haap
        return http.build(); //This will manufacture an http object with the above configuration details, and here since we are using the @Bean so it will handover
                      // this object to the Spring IOC and spring IOC will manage that object.
    }
}
