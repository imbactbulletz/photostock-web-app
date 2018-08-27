package controllers;

import entities.Company;
import services.IServiceCompany;
import services.IServiceUser;
import services.ServiceCompany;
import services.ServiceUser;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/company")
public class ControllerCompany {
    private IServiceCompany serviceCompany;
    private IServiceUser serviceUser;

    public ControllerCompany(){
        this.serviceCompany = new ServiceCompany();
        this.serviceUser = new ServiceUser();
    }

    @POST
    @Path("/register")
    @Consumes("application/json")
    @Produces("application/json")
    public Response register(Company company){
        Company result = this.serviceCompany.register(company);

        if(result == null){
            return Response.noContent().build();
        }

        return Response.ok(result).build();
    }

    @GET
    @Path("/getPendingCompanies")
    @Produces("application/json")
    public List<Company> getPendingCompanies(){
        return this.serviceCompany.getPendingCompanies();
    }


    @GET
    @Path("/setCompanyStatus={status},id={id}")
    @Produces("application/json")
    public boolean setCompanyStatus(@PathParam("status") String status, @PathParam("id") String id){

        boolean setSuccessfully = this.serviceCompany.setCompanyStatus(id, status);

        // checking whether company got its status set
        if(setSuccessfully){
            // if company was activated then we're making a moderator for it
            if(status.equals("true")){
                Company company  = this.serviceCompany.getCompanyByID(id);

                String username = company.getName().replaceAll("\\s+", ""); // removes all whitespaces
                username = username.toLowerCase();

                String password = UUID.randomUUID().toString();

                String email = company.getEmail();

                String companyName = company.getName();

                serviceUser.insertModerator(username, password, email, companyName);
            }

            return true;
        }

        return false;
    }
}
