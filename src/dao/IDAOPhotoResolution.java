package dao;

import entities.PhotoResolution;

import java.util.List;

public interface IDAOPhotoResolution {

    boolean insertPhotoResolution(int photoID, String name, double price);
    List<PhotoResolution> getResolutionsForPhoto(String photoID);
}
