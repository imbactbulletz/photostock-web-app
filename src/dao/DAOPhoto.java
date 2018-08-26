package dao;

import entities.Photo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOPhoto extends DAOAbstractDatabase<Photo> implements IDAOPhoto {


    // queries
    private static final String INSERT_PHOTO = "INSERT INTO PHOTO (TITLE, CATEGORY, DESCRIPTION, UPLOADEDBY, PATH, DATE_UPLOADED) VALUES ('%s', '%s', '%s', '%s', '%s', '%s')";
    private static final String COUNT_FOR_LAST_N_DAYS = "SELECT * FROM PHOTO WHERE UPLOADEDBY = '%s' AND DATE_UPLOADED BETWEEN CURDATE() - INTERVAL %d DAY AND CURDATE()";

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
}
