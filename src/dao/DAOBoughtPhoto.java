package dao;

import entities.BoughtPhoto;
import utils.SafeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOBoughtPhoto extends DAOAbstractDatabase<BoughtPhoto> implements IDAOBoughtPhoto {

    // queries
    private static final String INSERT_BOUGHT_PHOTO = "INSERT INTO BOUGHT_PHOTO (PHOTOID, BOUGHT_BY, RESOLUTIONID) VALUES (%s, '%s', %s)";
    private static final String HAS_USER_BOUGHT_PHOTO = "SELECT * FROM BOUGHT_PHOTO WHERE BOUGHT_BY = '%s' AND  PHOTOID = %s";
    private static final String GET_BOUGHT_PHOTOS_FOR_USER = "SELECT * FROM BOUGHT_PHOTO WHERE BOUGHT_BY = '%s'";
    public DAOBoughtPhoto(){
        super(BoughtPhoto.class);
    }

    @Override
    public boolean addBoughtPhoto(String username, String photoID, String photoResolutionID) {
        Connection connection = createConnection();

        if(connection == null || username == null || SafeConverter.toSafeInt(photoID) == 0 || SafeConverter.toSafeInt(photoResolutionID) == 0)
            return false;

        String query = String.format(INSERT_BOUGHT_PHOTO, photoID, username, photoResolutionID);

        try{
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
    public boolean hasBoughtPhoto(String username, String photoID) {
        Connection connection = createConnection();

        if(connection == null || username == null || SafeConverter.toSafeInt(photoID) == 0)
            return false;

        String query = String.format(HAS_USER_BOUGHT_PHOTO, username, photoID);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

            return false;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<BoughtPhoto> getBoughtPhotos(String username) {
        Connection connection = createConnection();

        if(connection == null || username == null)
            return null;

        String query = String.format(GET_BOUGHT_PHOTOS_FOR_USER, username);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<BoughtPhoto> boughtPhotos = new ArrayList<>();
            while(resultSet.next()){
                BoughtPhoto photo = readFromResultSet(resultSet);
                boughtPhotos.add(photo);
            }

            return boughtPhotos;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
