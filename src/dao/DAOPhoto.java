package dao;

import entities.Photo;
import utils.SafeConverter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOPhoto extends DAOAbstractDatabase<Photo> implements IDAOPhoto {


    // queries
    private static final String INSERT_PHOTO = "INSERT INTO PHOTO (TITLE, CATEGORY, DESCRIPTION, UPLOADEDBY, PATH, DATE_UPLOADED) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')";
    private static final String COUNT_FOR_LAST_N_DAYS = "SELECT * FROM PHOTO WHERE UPLOADEDBY = '%s' AND DATE_UPLOADED BETWEEN CURDATE() - INTERVAL %d DAY AND CURDATE()";
    private static final String DELETE_PHOTO = "DELETE FROM PHOTO WHERE ID = %s";
    private static final String GET_ALL_PHOTOS_FOR_USER = "SELECT * FROM PHOTO WHERE UPLOADEDBY = '%s'";
    private static final String GET_PENDING_PHOTOS = "SELECT * FROM PHOTO WHERE APPROVED = 'false'";
    private static final String SET_PHOTO_STATUS = "UPDATE PHOTO SET APPROVED = '%s' WHERE ID = %s";

    public DAOPhoto(){
        super(Photo.class);
    }

    @Override
    public int insertPhoto(String title, String category, String description, String uploadedBy, String path, Date date) {
        Connection connection = createConnection();

        if(connection == null || title == null || category == null || description == null || uploadedBy == null || path == null || date == null){
            return -1;
        }

        String query = String.format(INSERT_PHOTO, title, category, description, uploadedBy, path, date.toString());

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate(query);

            if(result == 0){
                return -1;
            }

            query = String.format("SELECT * FROM PHOTO WHERE title = '%s' AND CATEGORY = '%s' AND DESCRIPTION = '%s'AND UPLOADEDBY = '%s' AND PATH = '%s'", title, category, description, uploadedBy, path);
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery(query);

            if(resultSet.next()){
                Photo tmp = readFromResultSet(resultSet);
                return tmp.getId();
            }

            return -1;
        }
        catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }



    @Override
    public int getCountBy(String username, int daysOffset) {
        Connection connection = createConnection();

        if(connection == null || username == null)
            return -1;

        String query = String.format(COUNT_FOR_LAST_N_DAYS, username, daysOffset);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.last();

            return  resultSet.getRow();
        }
        catch(Exception e){
         e.printStackTrace();
         return -1;
        }
    }

    @Override
    public boolean deletePhoto(String photoID) {
        Connection connection = createConnection();

        if(connection == null || photoID == null || SafeConverter.toSafeInt(photoID) == 0){
            return false;
        }

        String query = String.format(DELETE_PHOTO, photoID);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            if(result == 0)
                return false;

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Photo> getPhotosForUser(String username) {
        Connection connection = createConnection();

        if(connection == null || username == null)
            return null;

        String query = String.format(GET_ALL_PHOTOS_FOR_USER, username);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();


            List<Photo> photos = new ArrayList<>();
            while(resultSet.next()){
                Photo tmp = readFromResultSet(resultSet);
                photos.add(tmp);
            }

            return photos;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Photo> getPendingPhotos() {
        Connection connection = createConnection();

        if(connection == null){
            return null;
        }

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PENDING_PHOTOS);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Photo> photos = new ArrayList<>();

            while(resultSet.next()){
                Photo tmp = readFromResultSet(resultSet);
                photos.add(tmp);
            }

            return photos;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setPhotoStatus(String photoID, String status) {
        Connection connection = createConnection();

        if(connection == null || photoID == null || status == null){
            return false;
        }

        String query = String.format(SET_PHOTO_STATUS, status.equals("true") ? "true": "declined", photoID);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            if(result == 0)
                return false;

            return true;
        }

        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
