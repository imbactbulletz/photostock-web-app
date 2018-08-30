package services;

public interface IServiceBoughtPhoto {
    boolean addBoughtPhoto(String username, String photoID, String resolutionPhotoID);
    boolean hasBoughtPhoto(String username, String photoID);
}
