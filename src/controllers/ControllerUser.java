package controllers;

import entities.User;
import services.IServiceUser;
import services.ServiceUser;
import utils.Cryptex;
import utils.Mailer;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;

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

        // registration failed
        if(result == null){
            return Response.noContent().build();
        }

        String encrypedUsername = Cryptex.encrypt(result.getUsername());
        String encodedUsername = "";

        try{
            encodedUsername = URLEncoder.encode(encrypedUsername, "UTF-8");
            Mailer.sendActivationMail(encodedUsername, result.getEmail());
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return Response.ok(result).build();
    }

    @GET
    @Path("/activate/id={id}")
    @Produces("application/json")
    public boolean activate(@PathParam("id") String id){
        String decodedUsername = "";
        String decryptedUsername = "";

        try{
            decodedUsername = URLDecoder.decode(id, "UTF-8");
            decryptedUsername = Cryptex.decrypt(decodedUsername);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        if(this.service.activate(decryptedUsername)){
            return true;
        }

        return false;
    }

    @GET
    @Path("/sendPassword={username}")
    @Produces("application/json")
    public boolean sendPassword(@PathParam("username") String username){

        User result = this.service.getUser(username);

        if(result == null){
            return false;
        }
        else {
            Mailer.sendPassword(result.getEmail(), result.getPassword());
            return true;
        }
    }

    @POST
    @Path("/changePassword")
    @Produces("application/json")
    public boolean changePassword(User user){
        return this.service.changePassword(user);
    }
}
