package controllers;

import entities.User;
import services.IServiceUser;
import services.ServiceUser;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/user")
public class ControllerUser {
    private IServiceUser service;

    public ControllerUser(){
        this.service = new ServiceUser();
    }

    @POST
    @Path("/login")
    @Consumes("application/json")
    @Produces("application/json")
    public Response login(User user){
        User result = this.service.login(user);

        if(result == null){
            return Response.noContent().build();
        }

        return Response.ok(result).build();
    }

    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(User user){
        User result = this.service.register(user);

        if(result == null){
            return Response.noContent().build();
        }

        return Response.ok(result).build();
    }
}
