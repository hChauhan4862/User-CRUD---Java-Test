package com.keenable.assignment1.keenable_hc.service;

import com.keenable.assignment1.keenable_hc.model.User;
import com.keenable.assignment1.keenable_hc.repository.UserRepository;
import com.keenable.assignment1.keenable_hc.util.PasswordUtil;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Collectors;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public User registerUser(User user) {
        if (userRepository.existsByUserName(user.getUserName())) {
            throw new RuntimeException("Username already exists.");
        }
        // Encrypt password
        String passString = PasswordUtil.convertUsernameToPassword(user.getUserName());
        user.setPassword(PasswordUtil.generateEncryptedPassword(passString));
        userRepository.save(user);
        user.setPassword(passString); // Return password as required in project instruction
        return user;
    }

    // Get user by UserName
    public Optional<User> getUserByUserName(String username) {
        return userRepository.findByUserNameAndIsDeletedFalseAndIsAdminFalse(username).map(existingUser -> {
            existingUser.setPassword(null);
            return existingUser;
        });
    }

    // Validate user by UserName and Password
    public Optional<User> validateUser(String username, String password) {
        return userRepository.findByUserNameAndIsDeletedFalse(username).filter(existingUser -> {
            return PasswordUtil.isPasswordMatch(password, existingUser.getPassword());
        }).map(existingUser -> {
            existingUser.setPassword(null);
            return existingUser;
        });
    }

    // Get all users
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        users.stream().map(user -> {
            // Process each user
            user.setPassword(PasswordUtil.convertUsernameToPassword(user.getUserName()));
            return user;
        }).collect(Collectors.toList());

        return users;
    }

    // Update user details
    public Optional<User> updateUserFullName(String username, String name) {
        return userRepository.findByUserNameAndIsDeletedFalseAndIsAdminFalse(username).map(existingUser -> {
            existingUser.setName(name);
            userRepository.save(existingUser);
            existingUser.setPassword(null);
            return existingUser;
        });
    }

    // Soft delete user by ID
    public boolean deleteUser(String username) {
        return userRepository.findByUserNameAndIsDeletedFalseAndIsAdminFalse(username).map(existingUser -> {
            existingUser.setDeleted(true);
            userRepository.save(existingUser);
            return true;
        }).orElse(false);
    }

    // Check if any of the usernames already exist
    public boolean existsAnyByUserName(String[] usernames) {
        return userRepository.existsByUserNameIn(usernames);
    }

    // Retrieve a user by password (for demo purposes)
    public Optional<User> getUserByPassword(String password) {
        String un = PasswordUtil.convertPasswordToUsername(password);
        return userRepository.findByUserNameAndIsDeletedFalseAndIsAdminFalse(un).map(existingUser -> {
            existingUser.setPassword(null);
            return existingUser;
        });
    }


    // Parse CSV file to extract user data
    public List<User> parseCSVFile(MultipartFile file) throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                // Assuming CSV has columns: userName, name, password
                User user = new User();
                user.setUserName(csvRecord.get("username"));
                user.setName(csvRecord.get("name"));
                users.add(user);
            }
        } catch (IOException e) {
            throw new IOException("Failed to parse CSV file: " + e.getMessage());
        }
        return users;
    }
}