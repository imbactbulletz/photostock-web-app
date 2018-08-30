package services;

import entities.BoughtPhoto;

import java.util.List;

public interface IServiceBoughtPhoto {
    boolean addBoughtPhoto(String username, String photoID, String resolutionPhotoID);
    boolean hasBoughtPhoto(String username, String photoID);
    List<BoughtPhoto> getBoughtPhotos(String username);
}
