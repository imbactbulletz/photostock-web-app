package services;

import dao.DAOPhoto;
import entities.Photo;

import java.sql.Date;
import java.util.List;

public class ServicePhoto implements IServicePhoto {

    protected DAOPhoto dao;

    public ServicePhoto(){
        this.dao = new DAOPhoto();
    }

    @Override
    public int insertPhoto(String title, String category, String description, String uploadedBy, String path, Date date) {
        return this.dao.insertPhoto(title, category, description, uploadedBy, path, date);
    }

    @Override
    public int getCountBy(String username, int daysOffset) {
        return this.dao.getCountBy(username, daysOffset);
    }

    @Override
    public boolean deletePhoto(String photoID) {
        return this.dao.deletePhoto(photoID);
    }

    @Override
    public List<Photo> getPhotosFor(String username) {
        return this.dao.getPhotosForUser(username);
    }

    @Override
    public List<Photo> getPendingPhotos() {
        return this.dao.getPendingPhotos();
    }

    @Override
    public boolean setPhotoStatus(String photoID, String status) {
        return this.dao.setPhotoStatus(photoID, status);
    }
}
