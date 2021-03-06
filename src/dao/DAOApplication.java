package dao;

import entities.Application;
import utils.SafeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOApplication extends DAOAbstractDatabase<Application> implements IDAOApplication {

    //Queries
    private final String MAKE_APP = "INSERT INTO APPLICATION (APPLICANT) VALUES (\"%s\")";
    private final String GET_APP_FOR_APPLICANT = "SELECT * FROM APPLICATION WHERE APPLICANT = \"%s\"";
    private final String GET_ALL_APPLICATIONS = "SELECT * FROM APPLICATION WHERE STATUS ='pending'";
    private final String RATE_USER = "UPDATE APPLICATION SET RATING = %s, STATUS = '%s' WHERE APPLICANT='%s'";
    private final String APPLY_FOR_COMPANY = "UPDATE APPLICATION SET COMPANY_NAME = '%s', COMPANY_STATUS = 'pending' WHERE APPLICANT= '%s' AND STATUS = 'approved'";
    private final String GET_PENDING_APPLICATIONS_FOR_COMPANY = "SELECT * FROM APPLICATION WHERE COMPANY_STATUS ='pending' AND COMPANY_NAME = '%s'";
    private final String SET_COMPANY_STATUS = "UPDATE APPLICATION SET COMPANY_STATUS = '%s' WHERE APPLICANT = '%s'";

    public DAOApplication(){
        super(Application.class);
    }


    // makes an application for the applicant if a (pending) one doesn't exist already
    @Override
    public boolean makeApplication(String applicant) {
        Connection connection = createConnection();

        if(connection == null || applicant == null) {
            return false;
        }

        // checking how many PENDING applications user has - only one activation that is PENDING
        // may exist at once in DB
        String query = String.format(GET_APP_FOR_APPLICANT, applicant);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // no pending app exists
            if(!resultSet.next()){
                // creating the app
                query = String.format(MAKE_APP, applicant);

                preparedStatement = connection.prepareStatement(query);
                int result = preparedStatement.executeUpdate(query);

                if(result == 0){
                    return false;
                }

                return true;
            }

            // one or more pending apps exist
            else {
                // failed to create a new application because a pending one already exists
                return false;
            }

        }
        catch(Exception e){
            e.printStackTrace();
            closeConnection(connection);
            return false;
        }
    }


    // checks if pending applications exist for a given applicant
    @Override
    public boolean hasPendingApplication(String applicant) {
        Connection connection = createConnection();

        if(connection == null || applicant == null) {
            return false;
        }

        // checking how many PENDING applications user has - only one activation that is PENDING
        // may exist at once in DB
        String query = String.format(GET_APP_FOR_APPLICANT, applicant);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // no pending app exists
            if(!resultSet.next()){
                return false;
            }

            // one or more pending apps exist
            else {
                return true;
            }

        }
        catch(Exception e){
            e.printStackTrace();
            closeConnection(connection);
            return false;
        }
    }

    @Override
    public String getApplicationID(String applicant) {
        Connection connection = createConnection();

        if(connection == null || applicant == null) {
            return null;
        }

        // checking how many PENDING applications user has - only one activation that is PENDING
        // may exist at once in DB
        String query = String.format(GET_APP_FOR_APPLICANT, applicant);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // no pending app exists
            if(!resultSet.next()){
                return null;
            }

            // one or more pending apps exist
            else {
                return String.valueOf(readFromResultSet(resultSet).getId());
            }

        }
        catch(Exception e){
            e.printStackTrace();
            closeConnection(connection);
            return null;
        }
    }

    @Override
    public List<Application> getPendingApplications() {
        Connection connection = createConnection();

        if(connection == null)
            return null;

        List<Application> applications = new ArrayList<Application>();


        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_APPLICATIONS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Application application = readFromResultSet(resultSet);
                applications.add(application);
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return applications;
    }

    @Override
    public boolean rateApplication(String username, String rating) {
        Connection connection = createConnection();

        if(username == null || rating == null){
            return false;
        }

        Integer int_rating = SafeConverter.toSafeInt(rating);


        String status;

        if(int_rating > 4 ){
            status = "approved";
        }
        else{
            status = "denied";
        }
        String query = String.format(RATE_USER, rating, status, username);

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
    public boolean makeApplicationForCompany(String username, String companyName) {
        Connection connection = createConnection();

        if(connection == null || username == null || companyName == null)
            return false;

        String query = String.format(APPLY_FOR_COMPANY, companyName, username);

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
    public List<Application> getPendingCompanyApplications(String companyName) {
        Connection connection = createConnection();

        if(connection == null)
            return null;

        String query = String.format(GET_PENDING_APPLICATIONS_FOR_COMPANY, companyName);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Application> applications = new ArrayList<>();

            while(resultSet.next()){
                Application tmp = readFromResultSet(resultSet);
                applications.add(tmp);
            }

            return applications;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setApplicationStatus(String applicant, String status) {
        Connection connection = createConnection();

        if(connection == null || applicant == null || status == null){
            return false;
        }

        String query = String.format(SET_COMPANY_STATUS, status.equals("true") ? "approved" : "denied", applicant);

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
}
