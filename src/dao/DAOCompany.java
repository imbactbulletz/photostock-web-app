package dao;

import entities.Company;
import utils.SafeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAOCompany extends DAOAbstractDatabase<Company> implements IDAOCompany{

    // Queries
    private final String REGISTER = "INSERT INTO COMPANY (NAME, EMAIL, ADDRESS, MEMBERSHIP, PIB, PROVISION_PERCENT) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", %f)";
    private final String GET_PENDING_COMPANIES = "SELECT * FROM COMPANY WHERE STATUS ='inactive'";
    private final String SET_COMPANY_STATUS = "UPDATE COMPANY SET STATUS = '%s' WHERE ID = %s";
    private final String GET_COMPANY_BY_ID = "SELECT * FROM COMPANY WHERE ID = %s";
    private final String GET_ACTIVE_COMPANIES = "SELECT * FROM COMPANY WHERE STATUS = 'active'";


    public DAOCompany(){
        super(Company.class);
    }

    @Override
    public Company login(Company company) {
        return null;
    }

    @Override
    public Company register(Company company) {

        Connection connection = createConnection();

        if(connection == null || company.getName() == null || company.getMembership() == null || company.getPib() == null || company.getEmail() == null
        || company.getName().equals("") || company.getMembership().equals("") || company.getPib().equals("")){
            return null;
        }

        // validating the non-required parameters
        if(company.getAddress() == null){
            company.setAddress("");
        }


        String query = String.format(REGISTER, company.getName(), company.getEmail(), company.getAddress(), company.getMembership(), company.getPib(), SafeConverter.toSafeFloat(company.getProvision()));

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();
            connection.close();

            // table unchanged
            if(result == 0){
                return null;
            }

            // table changed, returning the company
            else {

                // todo login company
                return company;
            }
        }

        catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<Company> getPendingCompanies() {
        Connection connection = createConnection();

        if(connection == null)
            return null;

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PENDING_COMPANIES);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Company> companies = new ArrayList<>();

            while(resultSet.next()){
                Company tmp  = readFromResultSet(resultSet);
                companies.add(tmp);
            }

            return companies;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean setCompanyStatus(String companyID, String status) {
        Connection connection = createConnection();

        if(connection == null || companyID == null || status == null || SafeConverter.toSafeInt(companyID) == 0)
            return false;

        String query = String.format(SET_COMPANY_STATUS, status.equals("true") ? "active": "disabled", companyID);

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
    public Company getCompanyByID(String ID) {
        Connection connection = createConnection();
        if(connection == null || ID == null ||  SafeConverter.toSafeInt(ID) == 0)
            return null;

        String query = String.format(GET_COMPANY_BY_ID, ID);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                Company tmp = readFromResultSet(resultSet);
                return tmp;
            }

            return null;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Company> getActiveCompanies() {
        Connection connection = createConnection();

        if(connection == null)
            return null;

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(GET_ACTIVE_COMPANIES);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Company> companies = new ArrayList<>();

            while(resultSet.next()){
                Company tmp = readFromResultSet(resultSet);
                companies.add(tmp);
            }

            return companies;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
