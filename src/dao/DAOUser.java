package dao;

import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOUser extends DAOAbstractDatabase<User> implements IDAOUser{

    // Queries
    private final String LOGIN = "SELECT * FROM USER WHERE USERNAME = \"%s\" AND PASSWORD = \"%s\"";


    public DAOUser() {
        super(User.class);
    }

    @Override
    public User login(User user) {
        Connection connection = createConnection();
        User result = null;

        if(connection == null || user.getUsername() == null || user.getPassword() == null || user.getUsername().equals("")
        || user.getPassword().equals("")){

            return null;
        }

        System.out.println("[DBG] " + user.getUsername() + " " + user.getPassword());

        String query = String.format(LOGIN, user.getUsername(), user.getPassword());

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                connection.close();
                return null;
            }

            else{
                result = readFromResultSet(resultSet);
                connection.close();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public User register(User user) {
        return null;
    }
}
