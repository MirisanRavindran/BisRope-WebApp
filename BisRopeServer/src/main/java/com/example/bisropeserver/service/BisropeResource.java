package com.example.bisropeserver.service;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("/hello-world")
public class BisropeResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}