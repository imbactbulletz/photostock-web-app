package controllers;

import entities.Company;
import services.IServiceCompany;
import services.ServiceCompany;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/company")
public class ControllerCompany {
    private IServiceCompany service;

    public ControllerCompany(){
        this.service = new ServiceCompany();
    }

    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(Company company){
        Company result = this.service.register(company);

        if(result == null){
            return Response.noContent().build();
        }

        return Response.ok(result).build();
    }


}
