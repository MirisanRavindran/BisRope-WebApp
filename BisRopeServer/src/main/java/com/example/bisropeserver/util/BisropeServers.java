package com.example.bisropeserver.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.example.bisropeserver.domain.Server;
import org.apache.commons.lang3.RandomStringUtils;
public class BisropeServers {
    private static HashMap<String,Server> activeServers = new HashMap<>();
    private static HashSet<String> usedCodes = new HashSet<>();

    public void createServer(String name){
        String id = generatingRandomUpperAlphanumericString();
        String firstRoom = generatingRandomUpperAlphanumericString();
        Server server = new Server();
        server.setName(name);
        server.setID(id);
        server.addRoomCodes(firstRoom);

        activeServers.put(id,server);
    }
    public Server getServer(String code){
        return activeServers.get(code);
    }
    private String generatingRandomUpperAlphanumericString() {
        String generatedString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        // generating unique room code
        while (usedCodes.contains(generatedString)) {
            generatedString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        }
        usedCodes.add(generatedString);
        return generatedString;
    }
    public void joinServer(String user, String code){
        activeServers.get(code).addUsers(user);
    }

    public void addRoomToServer(String code) {
        activeServers.get(code).addRoomCodes(code);
    }
    public boolean isRoom(String code){
        if(activeServers.containsKey(code)){return true;}

        else{return false;}
    }
}
