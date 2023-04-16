package com.example.bisropeserver.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.example.bisropeserver.domain.Server;
import org.apache.commons.lang3.RandomStringUtils;
public class BisropeServers {
    private static HashMap<String,Server> activeServers = new HashMap<>();
    private static HashSet<String> usedCodes = new HashSet<>();

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
    public Server getServer(String code){
        return activeServers.get(code);
    }
    public String generatingRandomUpperAlphanumericString() {
        String generatedString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        // generating unique room code
        while (usedCodes.contains(generatedString)) {
            generatedString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        }
        usedCodes.add(generatedString);
        return generatedString;
    }
    public void joinServer(String code, String user){
        activeServers.get(code).addUsers(user);
    }

    public void addRoomToServer(String code) {
        activeServers.get(code).addRoomCodes(code);
    }
    public boolean isServer(String code){
        if(activeServers.containsKey(code)){return true;}

        else{return false;}
    }
    public String getServerCode(String name){
        for (String key : activeServers.keySet()) {
            if(activeServers.get(key).getName().equals(name)){
                return key;
            }
        }
        return "something went wrong";
    }

    public ArrayList getRoomList(String name){
        String code = getServerCode(name);

        if(activeServers.containsKey(code)){
            return activeServers.get(code).getRoomCodes();
        }
        ArrayList<String> list = null;
        return list;
    }
}
