package com.keenable.assignment1.keenable_hc.controller;

import com.keenable.assignment1.keenable_hc.model.User;
import com.keenable.assignment1.keenable_hc.model.UserUpdateRequest;
import com.keenable.assignment1.keenable_hc.model.UserValidateRequest;
import com.keenable.assignment1.keenable_hc.service.UserService;
import com.keenable.assignment1.keenable_hc.service.BulkUserRegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;
import java.io.*;
import java.util.List;
import java.util.Optional;



@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BulkUserRegistrationService bulkUserRegistrationService;

    // Register a single user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    // Bulk register users

    @PostMapping("/bulk-register")
    public CompletableFuture<ResponseEntity<List<User>>> bulkRegisterUsers(@RequestBody List<User> users) {
        return bulkUserRegistrationService.bulkRegister(users)
                .thenApply(registeredUsers -> ResponseEntity.ok(registeredUsers));
    }

    // Bulk upload users via CSV file
    @PostMapping("/bulk-upload")
    public CompletableFuture<ResponseEntity<List<User>>>  bulkUploadUsers(@RequestParam("file") MultipartFile file) throws IOException {
    // public CompletableFuture<ResponseEntity<List<User>>> bulkUploadUsers(@ModelAttribute UserBulkUploadRequest req) throws IOException {
        // file = req.getFile();
        System.out.println("File received: " + file.getOriginalFilename());
        List<User> users = userService.parseCSVFile(file);
        return bulkUserRegistrationService.bulkRegister(users)
                .thenApply(registeredUsers -> ResponseEntity.ok(registeredUsers));
    }

    // Retrieve a user by ID
    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName) {
        Optional<User> user = userService.getUserByUserName(userName);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Retrieve all users
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Update user details
    @PutMapping("/{userName}")
    public ResponseEntity<User> updateUser(@PathVariable String userName, @RequestBody UserUpdateRequest req) {
        Optional<User> user = userService.updateUserFullName(userName, req.getName());
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Delete a user by ID (soft delete)
    @DeleteMapping("/{userName}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userName) {
        if (userService.deleteUser(userName)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete a user by ID (soft delete)
    @PostMapping("/validate_user")
    public ResponseEntity<User> validateUserWithPassword( @RequestBody UserValidateRequest req) {
        Optional<User> user = userService.validateUser(req.getUserName(), req.getPassword());
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.FORBIDDEN).build());
    }

    // Retrieve a user by password (optional, for demo purposes)
    @GetMapping("/by-password/{password}")
    public ResponseEntity<User> getUserByPassword(@PathVariable String password) {
        Optional<User> user = userService.getUserByPassword(password);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}