package dao;

import entities.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOCategory extends DAOAbstractDatabase<Category> implements IDAOCategory {
    // queries
    private static final String GET_ALL_QUERIES = "SELECT * FROM CATEGORY";
    private static final String ADD_CATEGORY = "INSERT INTO CATEGORY (NAME) VALUES ('%s')";
    private static final String DELETE_CATEGORY = "DELETE FROM CATEGORY WHERE NAME = '%s'";

    public DAOCategory(){
        super(Category.class);
    }

    @Override
    public List<Category> getAllCategories() {
        Connection connection = createConnection();

        if(connection == null){
            return null;
        }

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_QUERIES);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Category> categories = new ArrayList<>();

            while(resultSet.next()){
                Category tmp = readFromResultSet(resultSet);
                categories.add(tmp);
            }

            return categories;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean addCategory(String name) {
        Connection connection = createConnection();

        if(connection == null || name == null)
            return false;

        String query = String.format(ADD_CATEGORY, name);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            if(result != 0 ){
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
    public boolean deleteCategory(String name) {
        Connection connection = createConnection();

        if(connection == null || name == null){
            return false;
        }

        String query = String.format(DELETE_CATEGORY, name);

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
