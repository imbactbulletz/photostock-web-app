package controllers;

import entities.Category;
import services.ServiceCategory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

    @GET
    @Path("/addCategory={name}")
    public boolean addCategory(@PathParam("name") String name){
        return this.serviceCategory.addCategory(name);
    }

    @GET
    @Path("/deleteCategory={name}")
    public boolean deleteCategory(@PathParam("name") String name){
        return this.serviceCategory.deleteCategory(name);
    }
}
