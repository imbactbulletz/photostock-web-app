package dao;

import entities.User;
import utils.SafeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOUser extends DAOAbstractDatabase<User> implements IDAOUser {

    // Queries
    private final String LOGIN = "SELECT * FROM USER WHERE USERNAME = \"%s\" AND PASSWORD = \"%s\" AND NOT ACCOUNT_STATUS = 'deactivated'";
    private final String REGISTER = "INSERT INTO USER (USERNAME, PASSWORD, EMAIL, COUNTRY) VALUES ( \"%s\", \"%s\", \"%s\", \"%s\")";
    private final String ACTIVATE = "UPDATE USER SET ACCOUNT_STATUS = \"active\" WHERE USERNAME = \"%s\" and ACCOUNT_STATUS = \"unverified\"";
    private final String GET_USER = "SELECT * FROM USER WHERE USERNAME = \"%s\"";
    private final String CHANGE_PASSWORD = "UPDATE USER SET PASSWORD = \"%s\", ACCOUNT_STATUS = 'active' WHERE USERNAME = \"%s\"";
    private final String GET_ALL_USERS = "SELECT * FROM USER";
    private final String DELETE_USER = "DELETE FROM USER WHERE USERNAME = \"%s\"";
    private final String ADD_OPERATOR = "INSERT INTO USER (USERNAME,PASSWORD,EMAIL,COUNTRY, ACCOUNT_TYPE) VALUES ( \"%s\", \"%s\", \"%s\", \"%s\", 'operator')";
    private final String PROMOTE_TO_VENDOR = "UPDATE USER SET ACCOUNT_TYPE = 'vendor' WHERE USERNAME = '%s'";
    private final String INSERT_MODERATOR = "INSERT INTO USER (USERNAME,PASSWORD,EMAIL,COMPANY, ACCOUNT_TYPE) VALUES ('%s','%s','%s','%s', 'moderator')";
    private final String GET_MODERATORS_FOR_COMPANY = "SELECT * FROM USER WHERE COMPANY = '%s'";
    private final String REMOVE_COMPANY_FROM_MEMBER = "UPDATE USER SET COMPANY = NULL WHERE USERNAME = '%s'";
    private final String ASSIGN_USER_TO_COMPANY = "UPDATE USER SET COMPANY = '%s' WHERE USERNAME = '%s'";
    private final String RATE_USER = "UPDATE USER SET RATING = (RATING + %s) / (TIMES_RATED + 1), TIMES_RATED = TIMES_RATED + 1 WHERE USERNAME = '%s'";

    public DAOUser() {
        super(User.class);
    }

    @Override
    public User login(User user) {
        Connection connection = createConnection();
        User result = null;

        // validation
        if (connection == null || user.getUsername() == null || user.getPassword() == null || user.getUsername().equals("")
                || user.getPassword().equals("")) {

            return null;
        }

        String query = String.format(LOGIN, user.getUsername(), user.getPassword());

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                connection.close();
                return null;
            } else {
                result = readFromResultSet(resultSet);
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public User register(User user) {
        Connection connection = createConnection();

        // validation
        if (connection == null || user.getUsername() == null || user.getPassword() == null || user.getEmail() == null || user.getUsername().equals("")
                || user.getPassword().equals("") || user.getEmail().equals("")) {

            return null;
        }

        // validating the non-required parameters
        if (user.getCountry() == null) {
            user.setCountry("");
        }

        String query = String.format(REGISTER, user.getUsername(), user.getPassword(), user.getEmail(), user.getCountry());

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            connection.close();


            // table didn't change
            if (result == 0) {
                return null;
            }

            //  table changed, logging the user in and returning the full object
            else {
                return this.login(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean activate(String username) {
        Connection connection = createConnection();

        // validating data
        if (connection == null || username == null) {
            return false;
        }

        String query = String.format(ACTIVATE, username);


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            int resultSet = preparedStatement.getUpdateCount();
            connection.close();

            if (resultSet == 0) {
                return false;
            } else
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public User getUser(String username) {
        Connection connection = createConnection();

        if (connection == null || username == null) {
            return null;
        }

        String query = String.format(GET_USER, username);

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() == false) {
                connection.close();
                return null;
            }

            // returns the User from RS
            return readFromResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean changePassword(User user) {
        Connection connection = createConnection();

        // validating data
        if (connection == null || user == null | user.getUsername() == null || user.getPassword() == null) {
            return false;
        }

        String query = String.format(CHANGE_PASSWORD, user.getPassword(), user.getUsername());


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            int resultSet = preparedStatement.getUpdateCount();
            connection.close();

            if (resultSet == 0) {
                return false;
            } else
                return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        Connection connection = createConnection();
        List<User> users = new ArrayList<User>();

        if (connection == null) {
            return null;
        }


        try {
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();


            while (resultSet.next()) {
                users.add(readFromResultSet(resultSet));
            }

            connection.close();
            return users;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean deleteUser(String username) {
        Connection connection = createConnection();

        if(connection == null || username == null){
            return false;
        }

        String query = String.format(DELETE_USER, username);


        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            if(result == 0){
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
    public boolean addUser(User user) {
        Connection connection = createConnection();

        // validation
        if (connection == null || user.getUsername() == null || user.getPassword() == null || user.getEmail() == null || user.getUsername().equals("")
                || user.getPassword().equals("") || user.getEmail().equals("")) {

            return false;
        }

        // validating the non-required parameters
        if (user.getCountry() == null) {
            user.setCountry("");
        }

        String query = String.format(ADD_OPERATOR, user.getUsername(), user.getPassword(), user.getEmail(), user.getCountry());

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            connection.close();


            // table didn't change
            if (result == 0) {
                return false;
            }

            //  table changed, logging the user in and returning the full object
            else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            closeConnection(connection);
            return false;
        }
    }

    @Override
    public boolean promoteToVendor(String username) {
        Connection connection = createConnection();
        if(connection == null || username == null){
            return false;
        }

        String query = String.format(PROMOTE_TO_VENDOR, username);

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
    public boolean changeSettings(String username, String password, String creditCard, String deactivate) {
        Connection connection = createConnection();

        if(connection == null || username == null){
            return false;
        }

        String query = "UPDATE USER SET ";
        int hasPrevious = 0;
        if(password != null){
            query += "PASSWORD ='" + password + "'";
            hasPrevious++;
        }

        if(creditCard != null){
            if(hasPrevious != 0)
                query += ",";
            query += " CREDIT_CARD ='" + creditCard + "'";
            hasPrevious++;
        }

        if(deactivate != null && Boolean.valueOf(deactivate)){
            if(hasPrevious != 0)
                query += ",";
            query += " ACCOUNT_STATUS = 'deactivated' ";
            hasPrevious++;
        }

        if(password == null && creditCard == null && deactivate == null){
            return false;
        }

        query += " WHERE USERNAME = '" + username + "'";

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
    public boolean insertModerator(String username, String password, String email, String companyName) {
        Connection connection = createConnection();

        if(connection == null || username == null || password == null || email == null || companyName == null){
            return false;
        }

        String query = String.format(INSERT_MODERATOR, username, password, email, companyName);

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
    public List<User> getMembersFor(String companyName) {
        Connection connection = createConnection();

        if(connection == null || companyName == null){
            return null;
        }

        String query = String.format(GET_MODERATORS_FOR_COMPANY, companyName);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<User> users = new ArrayList<>();

            while(resultSet.next()){
                User tmp = readFromResultSet(resultSet);
                users.add(tmp);
            }

            return users;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean removeMembership(String username) {
        Connection connection = createConnection();

        if(connection == null || username == null)
            return false;

        String query = String.format(REMOVE_COMPANY_FROM_MEMBER, username);

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
    public boolean assignUserToCompany(String username, String companyName) {
        Connection connection = createConnection();

        if(connection == null || username == null || companyName == null)
            return false;

        String query = String.format(ASSIGN_USER_TO_COMPANY, companyName, username);

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
    public boolean rateUser(String username, String rating) {
        Connection connection = createConnection();

        if(connection == null || username == null || SafeConverter.toSafeDouble(rating) == 0)
            return false;

        String query = String.format(RATE_USER, rating, username);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            if(result == 0)
                return false;

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return true;
        }
    }


}
