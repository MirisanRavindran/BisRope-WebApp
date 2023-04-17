/**

 This class represents a RESTful web service for the Bisrope application.
 The service allows users to create accounts, login, create and join servers, and get information about servers and their rooms.
 */
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

    // Initialize instances of BisropeUsers and BisropeServers
    static BisropeUsers users = new BisropeUsers();
    static BisropeServers servers = new BisropeServers();

    /**
     * This method creates an account for a user.
     * @param username - the username for the user
     * @param password - the password for the user
     * @return a response indicating whether the account was created successfully
     * @throws Exception if there is an error creating the account
     */
    @GET
    @Produces("text/plain")
    @Path("/create-account/{username}/{password}")
    public Response createAccount(@PathParam("username") String username, @PathParam("password") String password) throws Exception {
        // If the username already exists, return a response indicating the username is already in use
        if (users.isUser(username)) {
            return Response.status(401).entity("Username already in use.").build();
        }

        // Create the user and return a response indicating the account was created successfully
        users.createUser(username, password);
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity("user created successfully")
                .build();
    }

    /**
     * This method logs a user in to their account.
     * @param username - the username for the user
     * @param password - the password for the user
     * @return a response indicating whether the user was logged in successfully
     */
    @GET
    @Path("/login/{username}/{password}")
    @Produces("text/plain")
    public Response login(@PathParam("username") String username, @PathParam("password") String password) {
        // If the password is incorrect, return a response indicating the username or password is invalid
        if (!users.isPassword(username, password)) {
            return Response.status(401).entity("Invalid username or password.").build();
        }

        // Return a response indicating the user was logged in successfully
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity(username)
                .build();
    }

    /**
     * This method creates a server and adds a user to it.
     * @param name - the name of the server
     * @param username - the username of the user to add to the server
     * @return a response indicating whether the server was created successfully and the ID of the server
     */
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

    // HTTP GET method to retrieve a list of servers associated with a particular user
// The Path annotation specifies the URI path for this method to be invoked, which includes a username parameter
// The Produces annotation specifies the media type(s) that the method can produce, in this case text/plain
    @GET
    @Path("/get-server-list/{username}")
    @Produces("text/plain")
    public Response getServerList(@PathParam("username") String username) {
// Retrieve a list of servers associated with the given username from the users object, and convert it to a string
        String list = users.getUser(username).getServerList().toString();
// Create and return an HTTP response with a status code of 200 OK
// Set the Access-Control-Allow-Origin header to allow requests from any origin
// Set the Content-Type header to text/plain
// Set the response entity to the list of servers as a string
        return Response.status(200)
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Type", "text/plain")
                .entity(list)
                .build();
    }

    /**

     This method allows a user to join an existing server by server ID and their username.
     @param id The server ID that the user wants to join.
     @param username The username of the user who wants to join the server.
     @return A Response object containing the HTTP status code, a message indicating whether the server was successfully joined,
     and headers to allow cross-origin requests and specify the content type of the response.
     */
     @GET
     @Path("/join-server/{server-id}/{username}")
     public Response joinServer(@PathParam("server-id") String id, @PathParam("username") String username) {
         // Check if the server ID is valid
         if(!servers.isServer(id)){
         return Response.status(401).entity("Invalid server ID.").build();
         }
         // Add the user to the server and the server to the user's list of servers
         servers.joinServer(id, username);
         users.addServertoUser(id, username);
         // Return a response indicating that the user has successfully joined the server
         return Response.status(200).entity("Server Joined").header("Access-Control-Allow-Origin", "").build();
     }

    /**
     * Retrieves the list of room codes for a server by server ID.
     * @param id the ID of the server to get the room codes for
     * @return a Response object containing the list of room codes
     */
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

    /**
     * Retrieves the name of a server by server ID.
     * @param id the ID of the server to get the name for
     * @return a Response object containing the name of the server
     */
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

    /**
     * Retrieves the list of users for a server by server ID.
     * @param id the ID of the server to get the user list for
     * @return a Response object containing the list of users
     */
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

    /**
     * Generates a new room code and adds it to a server by server ID.
     * @param id the ID of the server to add the room code to
     */
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