package controllers;

import entities.*;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import services.*;
import utils.FileUtil;
import utils.Mailer;
import utils.SafeConverter;

import javax.ws.rs.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Path("/photo")
public class ControllerPhoto {
    private IServicePhoto servicePhoto;
    private IServicePhotoResoluton servicePhotoResoluton;
    private IServiceBoughtPhoto serviceBoughtPhoto;
    private IServiceUser serviceUser;
    private IServiceResolution serviceResolution;
    private IServiceCompany serviceCompany;

    public ControllerPhoto(){
        this.servicePhoto = new ServicePhoto();
        this.servicePhotoResoluton = new ServicePhotoResolution();
        this.serviceBoughtPhoto = new ServiceBoughtPhoto();
        this.serviceUser = new ServiceUser();
        this.serviceResolution = new ServiceResolution();
        this.serviceCompany = new ServiceCompany();
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


        // getting photo    Company getCompanyByName(String companyName);
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

        for(Photo photo : photos){
            String photoPath = photo.getPath();

            BufferedImage image = FileUtil.readBufferedImage(photoPath);

            BufferedImage temp = FileUtil.resizeImage(image,640, 480);
            temp = FileUtil.putWatermark("PHOTOSTOCK", temp);


            String encodedImage = FileUtil.encodeImage(temp);

            photo.setData(encodedImage);
        }

        return photos;
    }

    @GET
    @Path("/getPhotoResolutions={id}")
    @Produces("application/json")
    public List<PhotoResolution> getPhotoResolutions(@PathParam("id") String id){
        return this.servicePhotoResoluton.getResolutionsForPhoto(id);
    }

    @POST
    @Path("/checkOut")
    @Produces("application/json")
    public boolean checkOut(MultipartFormDataInput input) throws Exception {

        String username = input.getFormDataPart("username", String.class, null);

        int size = input.getFormData().size();


        String[] bundle;
        for(int i = 0; i < size - 1 ; i++){
            bundle = input.getFormDataPart(String.valueOf(i), String.class, null).split(";");


            // getting relevant data
            Photo boughtPhoto = this.servicePhoto.getPhotoByID(bundle[0]);
            User user = this.serviceUser.getUser(username);
            PhotoResolution photoResolution = this.servicePhotoResoluton.getPhotoResolutionByID(bundle[1]);
            Resolution resolution = this.serviceResolution.getResolutionByName(photoResolution.getName());

            // sending image to the user in the format they bought
            String path = boughtPhoto.getPath();
            int width = resolution.getWidth();
            int height = resolution.getHeight();
            BufferedImage image = FileUtil.readBufferedImage(path);
            // resizing image
            image = FileUtil.resizeImage(image, width, height);
            String email = user.getEmail();
            File file = FileUtil.imageToFile(image);
            Mailer.sendPhoto( email, file);

            // sending notification to the vendor
            username = boughtPhoto.getUploadedBy();
            user = serviceUser.getUser(username);
            email = user.getEmail();
            Mailer.sendNotification(email);

            // sending notification to the company
            String companyName = user.getCompany();
            if( companyName != null && !companyName.trim().equals("")){
                Company company = this.serviceCompany.getCompanyByName(companyName);
                email = company.getEmail();
                Mailer.sendNotification(email);
            }
            this.serviceBoughtPhoto.addBoughtPhoto(username, bundle[0], bundle[1]);
        }

        return true;
    }

    @GET
    @Path("/ratePhoto={photoID},user={username},rating={rating}")
    @Produces("application/json")
    public boolean hasBoughtPhoto(@PathParam("photoID") String photoID, @PathParam("username") String username,
                                  @PathParam("rating") String rating){
        if(this.serviceBoughtPhoto.hasBoughtPhoto(username, photoID)){
            return this.servicePhoto.ratePhoto(photoID, rating);
        }

        return false;
    }

    @GET
    @Path("/getBoughtPhotosForUser={username}")
    @Produces("application/json")
    public List<Photo> getBoughtPhotosForUser(@PathParam("username") String username){

        List<BoughtPhoto> boughtPhotos = serviceBoughtPhoto.getBoughtPhotos(username);

        List<Photo> photos = new ArrayList<>();

        for(BoughtPhoto boughtPhoto : boughtPhotos){

            int photoResolutionID = boughtPhoto.getResolutionID();

            PhotoResolution photoResolution = servicePhotoResoluton.getPhotoResolutionByID(String.valueOf(photoResolutionID));
            Resolution resolution = serviceResolution.getResolutionByName(photoResolution.getName());


            Photo photo = this.servicePhoto.getPhotoByID(String.valueOf(boughtPhoto.getPhotoID()));

            String path = photo.getPath();
            int width = resolution.getWidth();
            int height = resolution.getHeight();
            BufferedImage image = FileUtil.readBufferedImage(path);
            // resizing image
            image = FileUtil.resizeImage(image, width, height);


            String encodedImage = FileUtil.encodeImage(image);

            photo.setData(encodedImage);

            photos.add(photo);
        }


        return photos;
    }
}
