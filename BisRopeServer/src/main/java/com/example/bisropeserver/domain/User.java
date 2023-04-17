// This code defines a class named "User" which will be used to manage the state of a user in a chat application.

package com.example.bisropeserver.domain;

import java.util.ArrayList;

public class User {


    // Define private instance variables to store the user's username, password, and email address
    private String username;
    private String password;
    private String email;

    // Define private instance variables of ArrayList type to store friend and server lists
    private ArrayList<String> friendList = new ArrayList<String>();
    private ArrayList<String> serverList = new ArrayList<String>();

    // Define a constructor to create a new user object with a specified username and password
    public User(String username, String password){
        this.username=username;
        this.password=password;
    }

    // Define public methods to access and modify the private instance variables
    public String getUsername() {
        return this.username;
    }

    public String getEmail(){
        return this.email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getServerList(){
        return this.serverList;
    }

    public ArrayList<String> getFriendList(){
        return this.friendList;
    }

    public void addFriend(String friend){
        this.friendList.add(friend);
    }

    public void addServer(String server){
        this.serverList.add(server);
    }
}