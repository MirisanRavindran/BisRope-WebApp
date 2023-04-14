package com.example.bisropeserver.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Post;
import jakarta.ws.rs.Produces;
import com.example.bisropeserver.*;
import com.example.bisropeserver.util.BisropeServers;
import com.example.bisropeserver.util.BisropeUsers;;

@Path("/bisrope-server")
public class BisropeResource {

    static BisropeUsers users = new BisropeUsers();
    static BisropeServers servers = new BisropeServers();


    @POST
    @Produces("text/plain")
    @Path("/create-account/{username}/{password}")
    public Response createAccount(@PathParam("username") String username, @PathParam("password") String password) throws Exception {
        // Check if email is already in use
        if (users.isUser(username)) {
            return Response.status(401).entity("Username already in use.").build();
        }
    
        // Create new user
        users.createUser(username, password);
    
        // Return success response
        return Response.ok("User created successfully.").build();
    }

    @GET
    @Path("/login/{username}/{password}")
    @Produces("text/plain")
    public Response login(@PathParam("username") String username, @PathParam("password") String password) {
        // Check if user exists and password is correct
        if (!users.isPassword(username, password)) {
            return Response.status(401).entity("Invalid username or password.").build();
        }

        // User exists and password is correct, return user information
        return Response.ok(username).build();
    }

    @POST
    @Path("/create-server/{server-name}/{username}")
    @Produces("text/plain")
    public Response createServer(@PathParam("server-name") String name, @PathParam("username") String username) {
        // Create a new server with the given name
        String id = servers.createServer(name);
        users.getUser(username).addServer(id);
        // Return the server ID in the response body
        return Response.ok(id).build();
    }

    @GET
    @Path("/get-server-list/{username}")
    @Produces("text/plain")
    public Response getServerList(@PathParam("username") String username) {
        String list = users.getUser(username).getServerList().toString();
        return Response.ok(list).build();
    }

    @POST
    @Path("/join-server/{server-id}/{username}")
    public void joinServer(@PathParam("server-id") String id, @PathParam("username") String username) {
        if(!servers.isServer(id)){
            return Response.status(401).entity("Invalid server ID.").build();
        }
        servers.joinServer(id, username);
        users.addServertoUser(id, username);
    }

    @GET
    @Path("/get-server-rooms/{server-id}")
    @Produces("text/plain")
    public Response getServerRooms(@PathParam("server-id") String id) {
        String list = servers.getServer(id).getRoomCodes().toString();
        return Response.ok(list).build();
    }

    @GET
    @Path("/get-server-name/{server-id}")
    @Produces("text/plain")
    public Response getServerName(@PathParam("server-id") String id) {
        String name = servers.getServer(id).getName();
        return Response.ok(name).build();
    }

    @GET
    @Path("/get-server-users/{server-id}")
    @Produces("text/plain")
    public Response getServerUsers(@PathParam("server-id") String id) {
        String list = servers.getServer(id).getUserList().toString();
        return Response.ok(list).build();
    }

    @POST
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