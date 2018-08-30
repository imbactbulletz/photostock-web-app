package services;

import dao.DAOBoughtPhoto;

public class ServiceBoughtPhoto implements IServiceBoughtPhoto {


    protected DAOBoughtPhoto dao;


    public ServiceBoughtPhoto(){
        this.dao = new DAOBoughtPhoto();
    }

    @Override
    public boolean addBoughtPhoto(String username, String photoID, String resolutionPhotoID) {
        return this.dao.addBoughtPhoto(username, photoID, resolutionPhotoID);
    }
}
