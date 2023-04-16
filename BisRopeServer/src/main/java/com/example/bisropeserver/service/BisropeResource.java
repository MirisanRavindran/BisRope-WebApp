package com.example.bisropeserver.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Produces;
import com.example.bisropeserver.util.BisropeServers;
import com.example.bisropeserver.util.BisropeUsers;
import jakarta.ws.rs.core.Response;


@Path("/bisrope-server")
public class BisropeResource {

    static BisropeUsers users = new BisropeUsers();
    static BisropeServers servers = new BisropeServers();


    @GET
    @Produces("text/plain")
    @Path("/create-account/{username}/{password}")
    public Response createAccount(@PathParam("username") String username, @PathParam("password") String password) throws Exception {
        if (users.isUser(username)) {
            return Response.status(401).entity("Username already in use.").build();
        }
    
        users.createUser(username, password);

        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity("user created successfully")
                .build();
    }

    @GET
    @Path("/login/{username}/{password}")
    @Produces("text/plain")
    public Response login(@PathParam("username") String username, @PathParam("password") String password) {

        if (!users.isPassword(username, password)) {
            return Response.status(401).entity("Invalid username or password.").build();
        }

        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity(username)
                .build();
    }

    @GET
    @Path("/create-server/{server-name}/{username}")
    @Produces("text/plain")
    public Response createServer(@PathParam("server-name") String name, @PathParam("username") String username) {
        String id = servers.createServer(name);
        servers.getServer(id).addUsers(username);
        users.getUser(username).addServer(id);
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity(id)
                .build();
    }

    @GET
    @Path("/get-server-list/{username}")
    @Produces("text/plain")
    public Response getServerList(@PathParam("username") String username) {
        String list = users.getUser(username).getServerList().toString();
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity(list)
                .build();
    }

    @GET
    @Path("/join-server/{server-id}/{username}")
    public Response joinServer(@PathParam("server-id") String id, @PathParam("username") String username) {
        if(!servers.isServer(id)){
            return Response.status(401).entity("Invalid server ID.").build();
        }
        servers.joinServer(id, username);
        users.addServertoUser(id, username);
        return Response.status(200).entity("Server Joined").header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/get-server-rooms/{server-id}")
    @Produces("text/plain")
    public Response getServerRooms(@PathParam("server-id") String id) {
        String list = servers.getServer(id).getRoomCodes().toString();
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity(list)
                .build();
    }

    @GET
    @Path("/get-server-name/{server-id}")
    @Produces("text/plain")
    public Response getServerName(@PathParam("server-id") String id) {
        String name = servers.getServer(id).getName();
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity(name)
                .build();
    }

    @GET
    @Path("/get-server-users/{server-id}")
    @Produces("text/plain")
    public Response getServerUsers(@PathParam("server-id") String id) {
        String list = servers.getServer(id).getUserList().toString();
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity(list)
                .build();
    }

    @GET
    @Path("/add-room/{server-id}")
    public void addRoom(@PathParam("server-id") String id) {
        servers.getServer(id).addRoomCodes(servers.generatingRandomUpperAlphanumericString());
    }

    @GET
    @Produces("text/plain")
    @Path("/test")
    public String test() {
        return "Hello, World!";
    }
}