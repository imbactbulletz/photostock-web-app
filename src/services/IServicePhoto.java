package services;

import java.sql.Date;

public interface IServicePhoto {
    int insertPhoto(String title, String category, String description, String uploadedBy, String path, Date date);
    int getCountBy(String username, int daysOffset);
}
