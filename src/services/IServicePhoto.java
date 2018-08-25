package services;

public interface IServicePhoto {
    int insertPhoto(String title, String category, String description, String uploadedBy, String path);
}
