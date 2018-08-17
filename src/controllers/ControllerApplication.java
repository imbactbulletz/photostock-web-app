package controllers;

import services.IServiceApplication;
import services.ServiceApplication;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/app")
public class ControllerApplication {
    private IServiceApplication service;

    public ControllerApplication(){
        this.service = new ServiceApplication();
    }

    @POST
    @Path("/makeApplication")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean makeApplication(String username){
        return this.service.makeApplication(username);
    }
}
