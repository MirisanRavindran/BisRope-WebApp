package com.example.bisropeserver.domain;

import java.util.ArrayList;

public class Server {
    private ArrayList<String> roomCodes = new ArrayList<String>();
    private ArrayList<String> userList = new ArrayList<String>();
    private String name;
    private String id;

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

    public void addRoomCodes(String code){
        this.roomCodes.add(code);
    }

    public void addUsers(String users){
        this.userList.add(users);
    }

        public void setName(String name){
        this.name=name;
    }


    public void setID(String id){
        this.id=id;
    }

}
