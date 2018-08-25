package dao;

public interface IDAOPhoto {

    int insertPhoto(String title, String category, String description, String uploadedBy, String path);

}
