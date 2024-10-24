package com.hms;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class A {
    public static void main(String[] args) {
        //Here PasswordEncoder is the interface and BCryptPasswordEncoder is the class.
        //here we are creating the object of the BCryptPasswordEncoder() class but we are storing the address into the PasswordEncoder interface.

//        First Method to encrypt the password

//        PasswordEncoder en = new BCryptPasswordEncoder();//how we are able to do that--> here some class upcasting is happening here.So due to the class upcasting, child object address object can be stored in the parent reference variable.
//        System.out.println(en.encode("testing"));

//        --------------------------------------------------

//        Second Method to Encrypt the Password:-
        String enPwd = BCrypt.hashpw("testing", BCrypt.gensalt(3));//This method will give more security than the above method.
        System.out.println(enPwd);
    }
}
