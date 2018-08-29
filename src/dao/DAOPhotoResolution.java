package dao;

import entities.PhotoResolution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOPhotoResolution extends DAOAbstractDatabase<PhotoResolution> implements IDAOPhotoResolution {

    // queries
    private static final String INSERT_PHOTO_RESOLUTION = "INSERT INTO PHOTO_RESOLUTION (PHOTOID, NAME, PRICE) VALUES(%d, '%s', %f)";
    private static final String GET_ALL_RESOLUTIONS_FOR_PHOTO = "SELECT * FROM PHOTO_RESOLUTION WHERE photoID = %s";

    public DAOPhotoResolution() {
        super(PhotoResolution.class);
    }


    @Override
    public boolean insertPhotoResolution(int photoID, String name, double price) {
        Connection connection = createConnection();

        if(connection == null || name == null){
            return false;
        }

        String query = String.format(INSERT_PHOTO_RESOLUTION, photoID, name, price);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            if(result == 0){
                return false;
            }

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<PhotoResolution> getResolutionsForPhoto(String photoID) {
        Connection connection = createConnection();

        if(connection == null || Integer.valueOf(photoID) == 0)
            return null;

        String query = String.format(GET_ALL_RESOLUTIONS_FOR_PHOTO, photoID);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<PhotoResolution> photoResolutions = new ArrayList<>();

            while(resultSet.next()){
                PhotoResolution tmp = readFromResultSet(resultSet);
                photoResolutions.add(tmp);
            }

            return photoResolutions;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
