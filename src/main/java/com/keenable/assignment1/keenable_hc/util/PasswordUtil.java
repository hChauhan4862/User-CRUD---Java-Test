package com.keenable.assignment1.keenable_hc.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String encodedIndexes = "c064n75rvfo9sx21a8kyzmutqwegjl3bphdi@.";
    private static final int startingIndex = 56;

    // Method to convert username to password for testing purposes only
    public static String convertUsernameToPassword(String username) {
        username = username.toLowerCase();
        // sum of ascii values
        String newPassword = "";
        for (int i = 0; i < username.length(); i++) {
            char c = username.charAt(i);
            int asciiValue = encodedIndexes.indexOf(c) + startingIndex;
            newPassword += asciiValue;
        }
        return newPassword;
    }

    public static String convertPasswordToUsername(String password) {
        String newUsername = "";
        for (int i = 0; i < password.length(); i+=2) {
            int index = Integer.parseInt(password.substring(i, i+2)) - startingIndex;
            newUsername += encodedIndexes.charAt(index);
        }
        return newUsername;
    }

    // Method to generate an encrypted password
    public static String generateEncryptedPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    // Method to verify if a raw password matches the encrypted password
    public static boolean isPasswordMatch(String rawPassword, String encryptedPassword) {
        return passwordEncoder.matches(rawPassword, encryptedPassword);
    }
}