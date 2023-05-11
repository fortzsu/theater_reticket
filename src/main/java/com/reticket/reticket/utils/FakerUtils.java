package com.reticket.reticket.utils;

import com.github.javafaker.Faker;
import com.reticket.reticket.domain.AppUser;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class FakerUtils {

    private static final Faker faker = new Faker();

    public static List<AppUser> generateDummyUsers(int number, PasswordEncoder passwordEncoder) {
        List<AppUser> appUsers = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            AppUser appUser = new AppUser();
            appUser.setEmail("test@gmail.com");
            appUser.setUsername("testuser");
            appUser.setPassword(passwordEncoder.encode("test1234"));
            System.out.println("USER CREDENTIAL: " + appUser.getPassword());
            appUsers.add(appUser);
        }
        return appUsers;
    }
}
