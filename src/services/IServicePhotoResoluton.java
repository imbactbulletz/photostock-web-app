package services;

import entities.PhotoResolution;

import java.util.List;

public interface IServicePhotoResoluton {
    boolean insertPhotoResolution(int photoID, String name, double price);
    List<PhotoResolution> getResolutionsForPhoto(String photoID);
}
