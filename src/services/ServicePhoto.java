package services;

import dao.DAOPhoto;

public class ServicePhoto implements IServicePhoto {

    protected DAOPhoto dao;

    public ServicePhoto(){
        this.dao = new DAOPhoto();
    }

    @Override
    public int insertPhoto(String title, String category, String description, String uploadedBy, String path) {
        return this.dao.insertPhoto(title, category, description, uploadedBy, path);
    }
}
