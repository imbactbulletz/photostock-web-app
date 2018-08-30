package dao;

public interface IDAOBoughtPhoto {
    boolean addBoughtPhoto(String username, String photoID, String photoResolutionID);
    boolean hasBoughtPhoto(String username, String photoID);
}
