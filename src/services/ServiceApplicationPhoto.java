package services;

import dao.DAOApplicationPhoto;

public class ServiceApplicationPhoto implements IServiceApplicationPhoto {

    protected DAOApplicationPhoto dao;

    public ServiceApplicationPhoto(){
        this.dao = new DAOApplicationPhoto();
    }

    @Override
    public boolean insertPhoto(String applicationID, String path) {
        return this.dao.insertPhoto(applicationID, path);
    }
}
