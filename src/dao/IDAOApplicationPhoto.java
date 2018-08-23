package dao;

import entities.ApplicationPhoto;

import java.util.List;

public interface IDAOApplicationPhoto {
    boolean insertPhoto(String applicationID, String path);
    List<ApplicationPhoto> getAllPhotosFor(String appID);
}
