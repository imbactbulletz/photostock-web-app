package dao;

import entities.ApplicationPhoto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOApplicationPhoto extends DAOAbstractDatabase<ApplicationPhoto> implements IDAOApplicationPhoto {

    // Queries
    private final String INSERT_PHOTO = "INSERT INTO APPLICATION_PHOTO (APPLICATION, PATH) VALUES (%s, \"%s\")";
    private final String GET_ALL_PHOTOS = "SELECT * FROM APPLICATION_PHOTO WHERE APPLICATION = %s";

    public DAOApplicationPhoto() {
        super(ApplicationPhoto.class);
    }

    @Override
    public boolean insertPhoto(String applicationID, String path) {
        Connection connection = createConnection();

        if(connection == null || applicationID == null || path == null){
            return false;
        }

        try{
            String query = String.format(INSERT_PHOTO, applicationID, path);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            int result = preparedStatement.executeUpdate();

            if(result != 0){
                return true;
            }

            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<ApplicationPhoto> getAllPhotosFor(String appID) {
        Connection connection = createConnection();
        List<ApplicationPhoto> applicationPhotos = new ArrayList<>();

        if(connection == null || appID == null){
            return null;
        }

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(String.format(GET_ALL_PHOTOS, appID));
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                ApplicationPhoto tmp = readFromResultSet(resultSet);
                applicationPhotos.add(tmp);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return applicationPhotos;
    }

}
