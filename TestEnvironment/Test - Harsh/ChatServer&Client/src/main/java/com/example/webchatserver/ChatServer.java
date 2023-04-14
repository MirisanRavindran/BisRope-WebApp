package com.example.webchatserver;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONObject;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@ServerEndpoint(value="/ws/{roomID}")
public class ChatServer {

    private Map<String, String> usernames = new HashMap<String, String>();
    private static Map<String, String> roomList = new HashMap<String, String>();
    @OnOpen
    public void open(@PathParam("roomID") String roomID, Session session) throws IOException, EncodeException {
        roomList.put(session.getId(), roomID); // adding userID to a room
        System.out.println("Room joined ");

        session.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(Server ): Welcome to the chat room. Please state your username to begin.\"}");
    }

    @OnClose
    public void close(Session session) throws IOException, EncodeException {
        String userId = session.getId();
        if (usernames.containsKey(userId)) {
            String username = usernames.get(userId);
            String roomID = roomList.get(userId);
            usernames.remove(userId);
            // remove this user from the roomList
            roomList.remove(roomID);


            // broadcasting it to peers in the same room
            int countPeers = 0;
            for (Session peer : session.getOpenSessions()){ //broadcast this person left the server
                if(roomList.get(peer.getId()).equals(roomID)) { // broadcast only to those in the same room
                    peer.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(Server): " + username + " left the chat room.\"}");
                    countPeers++; // count how many peers are left in the room
                }
            }

            // if everyone in the room left, save history
        }
    }

    @OnMessage
    public void handleMessage(String comm, Session session) throws IOException, EncodeException {
        String userID = session.getId(); // my id
        String roomID = roomList.get(userID); // my room
        JSONObject jsonmsg = new JSONObject(comm);
        String type = (String) jsonmsg.get("type");
        String message = (String) jsonmsg.get("msg");

        if(usernames.containsKey(userID)){ // not their first message
            String username = usernames.get(userID);
            System.out.println(username);


            // broadcasting it to peers in the same room
            for(Session peer: session.getOpenSessions()){
                // only send my messages to those in the same room
                if(roomList.get(peer.getId()).equals(roomID)) {
                    peer.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(" + username + "): " + message + "\"}");
                }
            }
        }else{ //first message is their username
            usernames.put(userID, message);
            session.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(Server ): Welcome, " + message + "!\"}");


            // broadcasting it to peers in the same room
            for(Session peer: session.getOpenSessions()){
                // only announce to those in the same room as me, excluding myself
                if((!peer.getId().equals(userID)) && (roomList.get(peer.getId()).equals(roomID))){
                    peer.getBasicRemote().sendText("{\"type\": \"chat\", \"message\":\"(Server): " + message + " joined the chat room.\"}");
                }
            }
        }

    }




}