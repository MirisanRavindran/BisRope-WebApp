package com.example.bisropeserver.util;

import java.util.HashMap;
import com.example.bisropeserver.domain.User;

public class BisropeUsers {
    private static HashMap<String,User> bisropeUsers = new HashMap<>();

    public User getUser(String username){
        return bisropeUsers.get(username);
    }

    public void createUser(String username, String password){
        User user = new User(username, password,);
        bisropeUsers.put(username,user);
    }

    public boolean isUser(String username){
        if(bisropeUsers.containsKey(username)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isPassword(String username, String password){
        if (bisropeUsers.containsKey(username)){
            if (bisropeUsers.get(username).getPassword().equals(password)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public void addServertoUser(String id, String username){
        bisropeUsers.get(username).addServer(id);
    }
}
