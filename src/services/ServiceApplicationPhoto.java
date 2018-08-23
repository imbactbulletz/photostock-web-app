package services;

import dao.DAOApplicationPhoto;
import entities.ApplicationPhoto;

import java.util.List;

public class ServiceApplicationPhoto implements IServiceApplicationPhoto {

    protected DAOApplicationPhoto dao;

    public ServiceApplicationPhoto(){
        this.dao = new DAOApplicationPhoto();
    }

    @Override
    public boolean insertPhoto(String applicationID, String path) {
        return this.dao.insertPhoto(applicationID, path);
    }

    @Override
    public List<ApplicationPhoto> getAllPhotosFor(String applicationID) {
        return this.dao.getAllPhotosFor(applicationID);
    }
}
