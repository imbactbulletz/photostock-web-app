package controllers;

import entities.Photo;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import services.IServicePhoto;
import services.IServicePhotoResoluton;
import services.ServicePhoto;
import services.ServicePhotoResolution;
import utils.FileUtil;
import utils.SafeConverter;

import javax.ws.rs.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Path("/photo")
public class ControllerPhoto {
    private IServicePhoto servicePhoto;
    private IServicePhotoResoluton servicePhotoResoluton;

    public ControllerPhoto(){
        this.servicePhoto = new ServicePhoto();
        this.servicePhotoResoluton = new ServicePhotoResolution();
    }

    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    @Produces("application/json")
    public boolean uploadPhoto(MultipartFormDataInput input) throws Exception{

        // getting frontend form data
        String title = input.getFormDataPart("title", String.class, null);
        String category = input.getFormDataPart("category", String.class, null);
        String description = input.getFormDataPart("description", String.class, null);
        String[] resolutions = input.getFormDataPart("resolutions", String.class, null).split(";");
        String[] prices = input.getFormDataPart("prices", String.class, null).split(";");
        String username = input.getFormDataPart("username", String.class, null);


        // getting photo
        InputPart photo = input.getFormDataMap().get("photo").get(0);
        InputStream file_is = photo.getBody(InputStream.class, null);

        // saving photo
        String file_path = FileUtil.saveFile(file_is, UUID.randomUUID().toString());

        // getting today's date in SQL format
        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());

        // inserting the photo into db
        int photoID = servicePhoto.insertPhoto(title, category, description, username, file_path, currentDate);



        if(photoID == -1){
            return false;
        }

        for(int i = 0; i < resolutions.length; i++){
            this.servicePhotoResoluton.insertPhotoResolution(photoID, resolutions[i], SafeConverter.toSafeDouble(prices[i]));
        }

        return true;
    }


    @GET
    @Path("/canUpload={username}")
    @Produces("application/json")
    public boolean canUpload(@PathParam("username") String username){


        // getting the number of photos uploaded today
        int uploadedToday = this.servicePhoto.getCountBy(username, 1);

        if(uploadedToday >= 3 || uploadedToday == -1)
            return false;

        // getting the number of photos uploaded in last
        int uploadedLastWeek = this.servicePhoto.getCountBy(username, 7);

        if(uploadedLastWeek >= 8 || uploadedLastWeek == -1)
            return false;

        return true;

    }

    @GET
    @Path("/deletePhoto={id}")
    @Consumes("multipart/form-data")
    public boolean deletePhoto(@PathParam("id") String id) throws Exception {

        return this.servicePhoto.deletePhoto(id);
    }

    @GET
    @Path("/getPhotosFor={username}")
    @Produces("application/json")
    public List<Photo> getPhotosFor(@PathParam("username") String username){

        List<Photo> photos = servicePhoto.getPhotosFor(username);

        for(Photo photo : photos){
            String photoPath = photo.getPath();

            BufferedImage image = FileUtil.readBufferedImage(photoPath);

            String encodedImage = FileUtil.encodeImage(image);

            photo.setData(encodedImage);
        }

        return photos;
    }

    @GET
    @Path("/getPendingPhotos")
    @Produces("application/json")
    public List<Photo> getPendingPhotos(){
        List<Photo> photos = servicePhoto.getPendingPhotos();

        for(Photo photo : photos){
            String photoPath = photo.getPath();

            BufferedImage image = FileUtil.readBufferedImage(photoPath);

            String encodedImage = FileUtil.encodeImage(image);

            photo.setData(encodedImage);
        }

        return photos;
    }

    @GET
    @Path("/setStatus={status},id={id}")
    @Produces("application/json")
    public boolean setPhotoStatus(@PathParam("status") String status, @PathParam("id") String id){
        return this.servicePhoto.setPhotoStatus(id, status);
    }

    @GET
    @Path("/getPhotosBy={criteria},term={term}")
    @Produces("application/json")
    public List<Photo> getPhotosBy(@PathParam("criteria") String criteria, @PathParam("term") String term){


        List<Photo> photos = this.servicePhoto.getPhotosBy(criteria, term);

        return photos;
    }
}
