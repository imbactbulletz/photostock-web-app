package dao;

import entities.Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DAOApplication extends DAOAbstractDatabase<Application> implements IDAOApplication {

    //Queries
    private final String MAKE_APP = "INSERT INTO APPLICATION (APPLICANT) VALUES (\"%s\")";
    private final String GET_PENDING_APPS_FOR_APPLICANT = "SELECT * FROM APPLICATION WHERE APPLICANT = \"%s\" AND STATUS = 'pending'";

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
        String query = String.format(GET_PENDING_APPS_FOR_APPLICANT, applicant);

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
}
