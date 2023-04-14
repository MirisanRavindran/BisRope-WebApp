package com.example.bisropeserver.util;

import java.util.HashMap;
import com.example.bisropeserver.domain.User;

public class BisropeUsers {
    private static HashMap<String,User> bisropeUsers = new HashMap<>();

    public User getUser(String email){
        return bisropeUsers.get(email);
    }

    public void createUser(String email, String username, String password){
        User user = new User(username, password, email);
        bisropeUsers.put(email,user);
    }

    public boolean isUser(String email){
        if(bisropeUsers.containsKey(email)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isPassword(String email, String password){
        if (bisropeUsers.containsKey(email)){
            if (bisropeUsers.get(email).equals(password)){
                return true;
            }
        }
    }
}
