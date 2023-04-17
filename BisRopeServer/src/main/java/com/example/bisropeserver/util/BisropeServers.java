// This code defines a utility class named "BisropeServers" which contains methods for managing chat servers and their associated rooms and users.

package com.example.bisropeserver.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.example.bisropeserver.domain.Server;
import org.apache.commons.lang3.RandomStringUtils;

public class BisropeServers {

    // Define private static instance variables of HashMap and HashSet types to store active servers and used codes
    private static HashMap<String,Server> activeServers = new HashMap<>();
    private static HashSet<String> usedCodes = new HashSet<>();

    // Define a public method to create a new server with a specified name and generate a unique ID and room code
    public String createServer(String name){
        String id = generatingRandomUpperAlphanumericString();
        String firstRoom = generatingRandomUpperAlphanumericString();
        Server server = new Server();
        server.setName(name);
        server.setID(id);
        server.addRoomCodes(firstRoom);
        activeServers.put(id,server);
        return id;
    }

    // Define a public method to retrieve a server with a specified code
    public Server getServer(String code){
        return activeServers.get(code);
    }

    // Define a public method to generate a unique random upper alphanumeric string of length 5
    public String generatingRandomUpperAlphanumericString() {
        String generatedString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        // generating unique room code
        while (usedCodes.contains(generatedString)) {
            generatedString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        }
        usedCodes.add(generatedString);
        return generatedString;
    }

    // Define a public method to add a user to a server with a specified code
    public void joinServer(String code, String user){
        activeServers.get(code).addUsers(user);
    }

    // Define a public method to add a new room to a server with a specified code
    public void addRoomToServer(String code) {
        activeServers.get(code).addRoomCodes(code);
    }

    // Define a public method to check if a server with a specified code exists
    public boolean isServer(String code){
        if(activeServers.containsKey(code)){return true;}
        else{return false;}
    }

    // Define a public method to retrieve the code of a server with a specified name
    public String getServerCode(String name){
        for (String key : activeServers.keySet()) {
            if(activeServers.get(key).getName().equals(name)){
                return key;
            }
        }
        return "something went wrong";
    }

    // Define a public method to retrieve a list of room codes for a server with a specified name
    public ArrayList getRoomList(String name){
        String code = getServerCode(name);

        if(activeServers.containsKey(code)){
            return activeServers.get(code).getRoomCodes();
        }
        ArrayList<String> list = null;
        return list;
    }
}