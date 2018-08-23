package services;

import entities.ApplicationPhoto;

import java.util.List;

public interface IServiceApplicationPhoto {
    boolean insertPhoto(String applicationID, String path);
    List<ApplicationPhoto> getAllPhotosFor(String applicationID);
}
