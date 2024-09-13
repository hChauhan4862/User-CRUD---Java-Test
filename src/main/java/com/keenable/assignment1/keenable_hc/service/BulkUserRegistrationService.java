package com.keenable.assignment1.keenable_hc.service;

import com.keenable.assignment1.keenable_hc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class BulkUserRegistrationService {

    @Autowired
    private UserService userService;

    // Method to handle bulk registration using multithreading
    @Async
    public CompletableFuture<List<User>> bulkRegister(List<User> users) {
        // existsAnyByUserName is used to check if any of the usernames already exist
        if (userService.existsAnyByUserName(users.stream().map(User::getUserName).toArray(String[]::new))) {
            throw new RuntimeException("One or more usernames already exist.");
        }

        List<CompletableFuture<User>> futures = users.stream()
                .map(user -> CompletableFuture.supplyAsync(() -> userService.registerUser(user)))
                .collect(Collectors.toList());

        // Wait for all threads to complete and collect the results
        List<User> registeredUsers = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(registeredUsers);
    }
}