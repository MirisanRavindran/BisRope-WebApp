package com.example.bisropeserver.util;

import java.util.HashMap;
import com.example.bisropeserver.domain.User;

public class BisropeUsers {
    private static HashMap<String,User> bisropeUsers = new HashMap<>();

    public User getUser(String email){
        return bisropeUsers.get(email);
    }

    public void createUser(String username, String password, String email){
        User user = new User(username, password, email);
        bisropeUsers.put(user);
    }

    public boolean isUser(String email){
        if(bisropeUsers.containsKey(email)){
            return true;
        }
        else{
            return false;
        }
    }
}
