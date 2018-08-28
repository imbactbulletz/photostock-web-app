package controllers;

import entities.User;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import services.IServiceUser;
import services.ServiceUser;
import utils.Cryptex;
import utils.Mailer;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

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

    @POST
    @Path("/getAllUsers")
    @Produces("application/json")
    public List<User> getAllUsers(){
        return this.service.getAllUsers();
    }

    @POST
    @Path("/deleteUser")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean deleteUser(String username){
        return this.service.deleteUser(username);
    }

    @POST
    @Path("/addUser")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean addUser(User user){
        return this.service.addUser(user);
    }


    @POST
    @Path("/changeSettings")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public boolean changeSettings(MultipartFormDataInput input) throws Exception {

        String username = input.getFormDataPart("username", String.class, null);
        String password = input.getFormDataPart("password", String.class, null);
        String creditCard = input.getFormDataPart("creditCard", String.class, null);
        String deactivate = input.getFormDataPart("deactivate", String.class, null);



        return this.service.changeSettings(username, password, creditCard, deactivate);
    }

    @GET
    @Path("/hasCreditCard={username}")
    @Produces("application/json")
    public boolean hasCreditCard(@PathParam("username") String username){
        User tmp = service.getUser(username);

        if(tmp == null || tmp.getCreditcard() == null )
            return false;

        return true;
    }

    @POST
    @Path("/addModerator")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean addModerator(User user){

        return this.service.insertModerator(user.getUsername(), user.getPassword(), user.getEmail(), user.getCompany());

    }


    @GET
    @Path("/getMembersFor={companyName}")
    @Produces("application/json")
    public List<User> getMembersFor(@PathParam("companyName") String companyName){

        return this.service.getMembersFor(companyName);
    }

    @GET
    @Path("/removeMembership={username}")
    @Produces("application/json")
    public boolean removeMembership(@PathParam("username") String username){

        return this.service.removeMembership(username);
    }


    @GET
    @Path("/assignUserToCompany={companyName},username={username}")
    public boolean assignUserToCompany(@PathParam("companyName") String companyName, @PathParam("username") String username){
        return this.service.assignUserToCompany(username, companyName);
    }
}
