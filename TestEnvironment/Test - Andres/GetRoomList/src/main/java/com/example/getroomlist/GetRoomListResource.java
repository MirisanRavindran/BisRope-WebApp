package com.example.getroomlist;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
@Path("/rooms")
public class GetRoomListResource {

    private static ArrayList<String> roomList = new ArrayList<String>();

    @GET
    @Path("/roomlist")
    @Produces("text/plain")
    public Response getRoomList() {
        String list = roomList.toString();
        System.out.println(list);

        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity(list)
                .build();
    }

    @POST
    @Consumes("text/plain")
    public Response handlePostRequest(String body) {
        roomList.add(body);
        return Response.status(201).entity("Room added successfully").build();
    }

    @Path("/test")
    @Produces("text/plain")
    public String test(){
        return "helloworld";
    }

    @GET
    @Path("/random")
    @Produces("text/plain")
    public String generatingRandomUpperAlphanumericString() {
        String generatedString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        // generating unique room code
        while (roomList.contains(generatedString)){
            generatedString = RandomStringUtils.randomAlphanumeric(5).toUpperCase();
        }
        roomList.add(generatedString);

        return generatedString;
    }
}