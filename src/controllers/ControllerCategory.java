package controllers;

import entities.Category;
import services.ServiceCategory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/category")
public class ControllerCategory {
    private ServiceCategory serviceCategory;

    public ControllerCategory(){
        this.serviceCategory = new ServiceCategory();
    }

    @GET
    @Path("/getAll")
    @Produces("application/json")
    public List<Category> getAllCategories(){
        return this.serviceCategory.getAllCategories();
    }
}
