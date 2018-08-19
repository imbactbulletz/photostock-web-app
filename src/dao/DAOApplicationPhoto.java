package dao;

import entities.ApplicationPhoto;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DAOApplicationPhoto extends DAOAbstractDatabase<ApplicationPhoto> implements IDAOApplicationPhoto {

    // Queries
    private final String INSERT_PHOTO = "INSERT INTO APPLICATION_PHOTO (APPLICATION, PATH) VALUES (%s, \"%s\")";

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
}
