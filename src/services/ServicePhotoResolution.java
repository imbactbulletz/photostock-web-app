package services;

import dao.DAOPhotoResolution;

public class ServicePhotoResolution implements IServicePhotoResoluton{
    protected DAOPhotoResolution dao;

    public ServicePhotoResolution(){
        this.dao = new DAOPhotoResolution();
    }

    @Override
    public boolean insertPhotoResolution(int photoID, String name, double price) {
       return this.dao.insertPhotoResolution(photoID, name, price);
    }
}
