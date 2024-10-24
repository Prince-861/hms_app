package com.hms.repository;

import com.hms.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
//  This finder method job is to check into the database--> is this username is present in the database--> and if yes --> Then do not allow the signup.
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByEmail(String Email);//If this email is present in the database then do not allow the signup.
}