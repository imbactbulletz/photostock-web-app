package services;

import dao.DAOPhoto;

import java.sql.Date;

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
}
