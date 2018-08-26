package services;

import entities.Photo;

import java.sql.Date;
import java.util.List;

public interface IServicePhoto {
    int insertPhoto(String title, String category, String description, String uploadedBy, String path, Date date);
    int getCountBy(String username, int daysOffset);
    boolean deletePhoto(String photoID);
    List<Photo> getPhotosFor(String username);
}
