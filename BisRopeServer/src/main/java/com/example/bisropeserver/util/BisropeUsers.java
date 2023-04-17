/*
 * This class manages a collection of users in a HashMap, where the key is the user's username.
 */
package com.example.bisropeserver.util;

import java.util.HashMap;

import com.example.bisropeserver.domain.User;

public class BisropeUsers {
    private static HashMap<String, User> bisropeUsers = new HashMap<>();

    // Get user by username
    public User getUser(String username) {
        return bisropeUsers.get(username);
    }

    // Create new user and add it to the HashMap
    public void createUser(String username, String password) {
        User user = new User(username, password);
        bisropeUsers.put(username, user);
    }

    // Check if user with the given username exists in the HashMap
    public boolean isUser(String username) {
        if (bisropeUsers.containsKey(username)) {
            return true;
        } else {
            return false;
        }
    }

    // Check if the given password matches the user's password in the HashMap
    public boolean isPassword(String username, String password) {
        if (bisropeUsers.containsKey(username)) {
            if (bisropeUsers.get(username).getPassword().equals(password)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // Add server to the user's server list
    public void addServertoUser(String id, String username) {
        bisropeUsers.get(username).addServer(id);
    }
}
