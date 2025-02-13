package com.example.chat.services;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
public class UserCTServices {
    // A simple static variable to track the user count globally
    @Getter
    private static int userCount = 0;

    public static void incrementUserCount() {
        userCount++;
    }

    public static void decrementUserCount() {
        userCount--;
    }
}