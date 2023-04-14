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


    @POST
    @Produces("text/plain")
    @Path("/create-account/{username}/{password}")
    public Response createAccount(@PathParam("username") String username, @PathParam("password") String password) throws Exception {
        if (users.isUser(username)) {
            return Response.status(401).entity("Username already in use.").build();
        }
    
        users.createUser(username, password);
    
        return Response.ok("User created successfully.").build();
    }

    @GET
    @Path("/login/{username}/{password}")
    @Produces("text/plain")
    public Response login(@PathParam("username") String username, @PathParam("password") String password) {

        if (!users.isPassword(username, password)) {
            return Response.status(401).entity("Invalid username or password.").build();
        }

        return Response.ok(username).build();
    }

    @POST
    @Path("/create-server/{server-name}/{username}")
    @Produces("text/plain")
    public Response createServer(@PathParam("server-name") String name, @PathParam("username") String username) {
        String id = servers.createServer(name);
        users.getUser(username).addServer(id);
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
    public Response joinServer(@PathParam("server-id") String id, @PathParam("username") String username) {
        if(!servers.isServer(id)){
            return Response.status(401).entity("Invalid server ID.").build();
        }
        servers.joinServer(id, username);
        users.addServertoUser(id, username);
        return Response.status(200).entity("Server Joined").build();
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