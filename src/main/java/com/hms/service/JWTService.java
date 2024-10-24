package com.hms.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTService {
//    This @Value annotation will automatically go to the properties file and it will fetch the key value and
//    this key value it is going to use and initialize the below variable(algorithmKey)
//    So, automatically the value from the properties file will get stored here.
    @Value("${jwt.algorithm.key}")
    private String algorithmKey;//private String algorithmKey=fdkfddj-fdjfdjfd-fdfdjfdlkj;// we can do like this but it will make our code lengthier
                                //So it is better to keep the key in the properties file so that if there is need to change the expiry time or any thing
                               //then we can do it from the properties file and we do not have to check the whole code again. So, we don't have to make our
                               //code tightly-coupled. Changing the requirements from the properties file will make it easy to modify.

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryTime;

//        When we are working to develop the token then the ingredients required to develop the token are:-
//        header, payload, signature(here algorithmKey is signature)
//        above we have signature,issuer,expiry duration but we don't have the algorithm(so we will create the
//        Algorithm variable that will come from the jwt token library.
    private Algorithm algorithm;//algorithm variable comes from the jwt token library(import com.auth0.jwt.algorithms.Algorithm;)

//    @PostConstruct annotation comes from the hibernate(import jakarta.annotation.PostConstruct;)
//    @PostConstruct this annotation will help us to run this method automatically when the project starts.
//    This is very useful, when we automatically want to run the method when we start the project
    @PostConstruct
    public void postConstruct(){
//       System.out.println(algorithmKey);
//       System.out.println(issuer);
//       System.out.println(expiryTime);

        algorithm = Algorithm.HMAC256(algorithmKey);//what it tells is--> One who completely wants to decode this token(algorithm) should have this key and without this key, the decoding of this token is not allowed.
//    that's why when we are generating the token itself, we are applying this key and generating it.

    }

//    we want to generate the token only when our login is successful
    public String generateToken(String username){
        return JWT.create()
                .withClaim("name",username)//username--> for which user I am creating this
                .withExpiresAt(new Date(System.currentTimeMillis() + expiryTime))//new Date() gives us the current date and time so we are adding the expiry time in current time //the input of this method is in millisecond.
                .withIssuer(issuer)
                .sign(algorithm); //signature
    }

//    Username in the token is called as the claim
//    Before we extract the username, we will have to verify the token.
    public String getUsername(String token){
        DecodedJWT decodedJWT = JWT.
                require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);//the secret key(signature) of the properties file has to be verified here.//if the issuer is proper then this line of code will verify the token and upon verification if everything is correct then this line of code will verify the token
//.verify(token), this token signature is also verified(the secret key of the properties file has to verified with this) and only when the incoming token signature and the signature in the properties file is verified then the token is decoded.
        return decodedJWT.getClaim("name").asString();//here we decoding the name because we have set the username as the name while generating the token. //this will get username from the token and since the return type of getClaim() method is not String then it will convert that into the String.
    }
//    --> The above method is doing two things:-
//    1) The first thing it is doing is : it takes the token and it verifies the token and after verifying it decodes the token and from the decoded token it will get the username.


}
