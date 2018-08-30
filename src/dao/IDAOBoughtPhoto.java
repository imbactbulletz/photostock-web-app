package dao;

import entities.BoughtPhoto;

import java.util.List;

public interface IDAOBoughtPhoto {
    boolean addBoughtPhoto(String username, String photoID, String photoResolutionID);
    boolean hasBoughtPhoto(String username, String photoID);
    List<BoughtPhoto> getBoughtPhotos(String username);
}
