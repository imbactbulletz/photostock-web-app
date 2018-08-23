package controllers;

import entities.Application;
import entities.ApplicationPhoto;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import services.*;
import utils.FileUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


@Path("/app")
public class ControllerApplication {
    private IServiceApplication applicationService;
    private IServiceApplicationPhoto applicationPhotoService;
    private IServiceUser userService;

    public ControllerApplication(){
        this.applicationService = new ServiceApplication();
        this.applicationPhotoService = new ServiceApplicationPhoto();
        this.userService = new ServiceUser();
    }


    @POST
    @Path("/hasPendingApplication")
    @Consumes("application/json")
    @Produces("application/json")
    public boolean hasPendingApplication(String username){
        return this.applicationService.hasPendingApplication(username);
    }

    @POST
    @Path("/uploadTestPhotos")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public boolean uploadTestPhotos(MultipartFormDataInput input){

        try {
            // getting the username of the user that is uploading the photos
            String username = input.getFormDataPart("username", String.class, null);



            // trying to make an application for the given username
            boolean makeApp = this.applicationService.makeApplication(username);


            // if a pending application already exists
            if(makeApp == false){
                return false;
            }

            // getting the map with dataForm objects
            Map<String, List<InputPart>> uploadedForm = input.getFormDataMap();
            // removing username from the map so we can iterate through files
            uploadedForm.remove("username");

            // getting app id for username
            String applicationID = this.applicationService.getApplicationID(username);

            // iterating through files
            for (Map.Entry<String, List<InputPart>> entry : uploadedForm.entrySet()) {
                // each InputPart represents a photo
                InputPart photo = uploadedForm.get(entry.getKey()).get(0);
                // photo header (contains title and other info)
                MultivaluedMap<String, String> header = photo.getHeaders();

                // getting the filename from header
                String filename = FileUtil.getFileName(header);

                // getting the image content (it's located in the body of the InputPart)
                InputStream file_is = photo.getBody(InputStream.class, null);

                // saving file to disk
                String file_path = FileUtil.saveFile(file_is, filename);

                // inserting photo into db
                this.applicationPhotoService.insertPhoto(applicationID, file_path);

            }
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }

    }


    @GET
    @Path("/getApplicationPhotos={applicant}")
    @Produces("application/json")
    public List<ApplicationPhoto> getApplicationPhotos(@PathParam("applicant") String applicant) throws Exception {

        // getting application ID for a given username
         String appID = applicationService.getApplicationID(applicant);

         if(appID == null){
             return null;
         }

         // getting all application photos that relate to application ID
         List<ApplicationPhoto> applicationPhotos = applicationPhotoService.getAllPhotosFor(appID);


         // encoding images as base64 and setting as data into ApplicationPhoto
        for(ApplicationPhoto applicationPhoto : applicationPhotos){
            // getting photo path
            String photoPath = applicationPhoto.getPath();
            // creating an image out of it
            BufferedImage image = FileUtil.readBufferedImage(photoPath);
            // encoding in base64
            String encodedImage = FileUtil.encodeImage(image);
            // setting the encoded image as a model data
            applicationPhoto.setData(encodedImage);
        }

        return applicationPhotos;
    }

    @GET
    @Path("/getPendingApplications")
    @Produces("application/json")
    public List<Application> getPendingApplications(){
        return this.applicationService.getPendingApplications();
    }


    @GET
    @Path("/rateApplication/applicant={applicant}/rating={rating}")
    @Produces("application/json")
    public boolean rateApplication(@PathParam("applicant") String applicant, @PathParam("rating") String rating){

        if( applicant == null || rating == null){
            return false;
        }

        boolean res = this.applicationService.rateApplication(applicant, rating);

        if(!res){
            return false;
        }

        Integer int_rating = Integer.valueOf(rating);

        if(int_rating > 4){
            this.userService.promoteToVendor(applicant);
        }


        return true;
    }
}
