package controllers;

import entities.Resolution;
import services.IServiceResolution;
import services.ServiceResolution;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/resolution")
public class ControllerResolution {
    private IServiceResolution serviceResolution;

    public ControllerResolution(){
        this.serviceResolution = new ServiceResolution();
    }

    @GET
    @Path("/getAll")
    @Produces("application/json")
    public List<Resolution> getAllResolutions(){
        return this.serviceResolution.getAllResolutions();
    }
}
