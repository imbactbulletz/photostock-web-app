package dao;

import entities.Resolution;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOResolution extends DAOAbstractDatabase<Resolution> implements IDAOResolution {

    // queries
    private static final String GET_ALL_RESOLUTIONS = "SELECT * FROM RESOLUTION";
    private static final String GET_RESOLUTION_BY_NAME = "SELECT * FROM RESOLUTION WHERE NAME = '%s'";

    public DAOResolution(){
        super(Resolution.class);
    }

    @Override
    public List<Resolution> getAllResolutions() {
        Connection connection = createConnection();

        if(connection == null){
            return null;
        }

        List<Resolution> resolutions = new ArrayList<>();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_RESOLUTIONS);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Resolution tmp = readFromResultSet(resultSet);
                resolutions.add(tmp);
            }

            return resolutions;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Resolution getResolutionByName(String name) {
        Connection connection = createConnection();

        if(connection == null || name == null)
            return null;

        String query = String.format(GET_RESOLUTION_BY_NAME, name);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Resolution tmp = readFromResultSet(resultSet);
                return tmp;
            }

            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
