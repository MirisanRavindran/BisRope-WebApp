package com.example.bisropeserver.domain;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private String email;

    private ArrayList<String> friendList = new ArrayList<String>();

    private ArrayList<String> serverList = new ArrayList<String>();

    public User(String username, String password, String email){
        this.username=username;
        this.password=password;
        this.email=email;
    }
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
