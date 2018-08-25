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
}
