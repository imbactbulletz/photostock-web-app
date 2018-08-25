package dao;

import entities.Photo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOPhoto extends DAOAbstractDatabase<Photo> implements IDAOPhoto {


    // queries
    private static final String INSERT_PHOTO = "INSERT INTO PHOTO (TITLE, CATEGORY, DESCRIPTION, UPLOADEDBY, PATH) VALUES ('%s', '%s', '%s', '%s', '%s')";


    public DAOPhoto(){
        super(Photo.class);
    }

    @Override
    public int insertPhoto(String title, String category, String description, String uploadedBy, String path) {
        Connection connection = createConnection();

        if(connection == null || title == null || category == null || description == null || uploadedBy == null || path == null){
            return -1;
        }

        String query = String.format(INSERT_PHOTO, title, category, description, uploadedBy, path);

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
}
