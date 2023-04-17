// This code defines a class named "Server" which will be used to manage the state of a server in a chat application.

package com.example.bisropeserver.domain;

import java.util.ArrayList;

public class Server {

    // Define private instance variables of ArrayList type to store room codes and user lists
    private ArrayList<String> roomCodes = new ArrayList<String>();
    private ArrayList<String> userList = new ArrayList<String>();

    // Define private instance variables to store the name and ID of the server
    private String name;
    private String id;

    // Define public methods to access the private instance variables
    public ArrayList<String> getRoomCodes(){
        return this.roomCodes;
    }

    public ArrayList<String> getUserList(){
        return this.userList;
    }

    public String getName(){
        return this.name;
    }

    public String getId(){
        return this.id;
    }

    // Define public methods to add room codes and users to the server's state
    public void addRoomCodes(String code){
        this.roomCodes.add(code);
    }

    public void addUsers(String users){
        this.userList.add(users);
    }

    // Define public methods to set the name and ID of the server
    public void setName(String name){
        this.name=name;
    }

    public void setID(String id){
        this.id=id;
    }
}