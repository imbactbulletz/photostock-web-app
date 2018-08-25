package dao;

import entities.PhotoResolution;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DAOPhotoResolution extends DAOAbstractDatabase<PhotoResolution> implements IDAOPhotoResolution {

    // queries
    private static final String INSERT_PHOTO_RESOLUTION = "INSERT INTO PHOTO_RESOLUTION (PHOTOID, NAME, PRICE) VALUES(%d, '%s', %f)";

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
}
