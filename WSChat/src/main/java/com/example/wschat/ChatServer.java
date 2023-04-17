package com.example.wschat;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONObject;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@ServerEndpoint(value="/ws/{roomID}/{user}")
public class ChatServer {

    private Map<String, String> usernames = new HashMap<String, String>();
    private static Map<String, String> roomList = new HashMap<String, String>();
    @OnOpen
    public void open(@PathParam("user") String user, @PathParam("roomID") String roomID, Session session) throws IOException, EncodeException {
        roomList.put(session.getId(), roomID); // adding userID to a room
        usernames.put(session.getId(), user);
    }

    @OnClose
    public void close(@PathParam("user") String user, Session session) throws IOException, EncodeException {
        String userId = session.getId();
        if (usernames.containsKey(userId)) {
            String username = usernames.get(userId);
            String roomID = roomList.get(userId);
            usernames.remove(userId);
            // remove this user from the roomList
            roomList.remove(roomID);
            // broadcasting it to peers in the same room
            int countPeers = 0;
            for (Session peer : session.getOpenSessions()) { //broadcast this person left the server
                if (roomList.get(peer.getId()).equals(roomID)) { // broadcast only to those in the same room
                    countPeers++; // count how many peers are left in the room
                }
            }
        }
    }


    @OnMessage
    public void handleMessage(@PathParam("user") String user, String comm, Session session) throws IOException, EncodeException {
        JSONObject jsonmsg = new JSONObject(comm);
        String message = (String) jsonmsg.get("msg");
        String type = (String) jsonmsg.get("type");
            // broadcasting it to peers in the same room
        if (type.equals("chat")) {
            for(Session peer: session.getOpenSessions()){
                // only send my messages to those in the same room
                peer.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(" + user + "): " + message + "\"}");
            }
        }
        else if (type.equals("image")) {
            for (Session peer : session.getOpenSessions()) {
                // only send my messages to those in the same room
                peer.getBasicRemote().sendText("{\"type\": \"image\", \"message\":\"" + message + "\", \"username\":\"" + user + "\"}");
            }
        }
    }

}



