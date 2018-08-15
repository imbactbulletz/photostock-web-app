package dao;

import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOUser extends DAOAbstractDatabase<User> implements IDAOUser{

    // Queries
    private final String LOGIN = "SELECT * FROM USER WHERE USERNAME = \"%s\" AND PASSWORD = \"%s\"";
    private final String REGISTER = "INSERT INTO USER (USERNAME, PASSWORD, EMAIL, COUNTRY) VALUES ( \"%s\", \"%s\", \"%s\", \"%s\")";
    private final String ACTIVATE = "UPDATE USER SET ACCOUNT_STATUS = \"active\" WHERE USERNAME = \"%s\" and ACCOUNT_STATUS = \"unverified\"";
    private final String GET_USER = "SELECT * FROM USER WHERE USERNAME = \"%s\"";
    private final String CHANGE_PASSWORD = "UPDATE USER SET PASSWORD = \"%s\", ACCOUNT_STATUS = 'active' WHERE USERNAME = \"%s\"";

    public DAOUser() {
        super(User.class);
    }

    @Override
    public User login(User user) {
        Connection connection = createConnection();
        User result = null;

        // validation
        if(connection == null || user.getUsername() == null || user.getPassword() == null || user.getUsername().equals("")
        || user.getPassword().equals("")){

            return null;
        }

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
        Connection connection = createConnection();

        // validation
        if(connection == null || user.getUsername() == null || user.getPassword() == null || user.getEmail() == null || user.getUsername().equals("")
                || user.getPassword().equals("") || user.getEmail().equals("")){

            return null;
        }

        // validating the non-required parameters
        if(user.getCountry() == null){
            user.setCountry("");
        }

        String query = String.format(REGISTER, user.getUsername(), user.getPassword(), user.getEmail(), user.getCountry());

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            connection.close();


            // table didn't change
            if (result == 0) {
                return null;
            }

            //  table changed, logging the user in and returning the full object
            else{
                return this.login(user);
            }

        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean activate(String username) {
        Connection connection = createConnection();

        // validating data
        if(connection == null || username == null){
            return false;
        }

        String query = String.format(ACTIVATE, username);


        try{
           PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.executeUpdate();
           int resultSet = preparedStatement.getUpdateCount();
           connection.close();

           if(resultSet == 0){
               return false;
           }
           else
               return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(String username) {
        Connection connection = createConnection();

        if(connection == null || username == null){
            return null;
        }

        String query = String.format(GET_USER, username);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next() == false){
                connection.close();
                return null;
            }

            // returns the User from RS
            return readFromResultSet(resultSet);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean changePassword(User user) {
        System.out.println(user.getUsername() + " " + user.getPassword());
        Connection connection = createConnection();

        // validating data
        if(connection == null || user == null | user.getUsername() == null || user.getPassword() == null){
            return false;
        }

        String query = String.format(CHANGE_PASSWORD, user.getPassword(), user.getUsername());

        System.out.println(query);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            int resultSet = preparedStatement.getUpdateCount();
            connection.close();

            if(resultSet == 0){
                return false;
            }
            else
                return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
