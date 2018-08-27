package controllers;

import entities.Company;
import services.IServiceCompany;
import services.ServiceCompany;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

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

    @GET
    @Path("/getPendingCompanies")
    @Produces("application/json")
    public List<Company> getPendingCompanies(){
        return this.service.getPendingCompanies();
    }


    @GET
    @Path("/setCompanyStatus={status},id={id}")
    @Produces("application/json")
    public boolean setCompanyStatus(@PathParam("status") String status, @PathParam("id") String id){
        return this.service.setCompanyStatus(id, status);
    }
}
