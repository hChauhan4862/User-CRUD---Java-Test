package com.keenable.assignment1.keenable_hc.config;

import com.keenable.assignment1.keenable_hc.model.User;
import com.keenable.assignment1.keenable_hc.repository.UserRepository;
import com.keenable.assignment1.keenable_hc.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminDataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if an admin user already exists
        if (!userRepository.existsByUserName("admin")) {
            // Create a new admin user
            User admin = new User();
            admin.setUserName("admin");
            admin.setName("Administrator");
            String passString = PasswordUtil.convertUsernameToPassword(admin.getUserName());
            admin.setPassword(PasswordUtil.generateEncryptedPassword(passString));
            admin.setDeleted(false); // Admin is not soft-deleted
            admin.setAdmin(true); // Admin is an admin user

            // Save the admin user
            userRepository.save(admin);

            System.out.println("Admin user created with username: admin and password: "+passString);
        } else {
            System.out.println("Admin user already exists.");
        }
    }
}