package dao;

import com.sun.org.apache.regexp.internal.RE;
import entities.Company;
import utils.SafeConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DAOCompany extends DAOAbstractDatabase<Company> implements IDAOCompany{

    // Queries
    private final String REGISTER = "INSERT INTO COMPANY (NAME, ADDRESS, MEMBERSHIP, PIB, PROVISION_PERCENT) VALUES (\"%s\", \"%s\", \"%s\", \"%s\", %f)";
    public DAOCompany(){
        super(Company.class);
    }

    @Override
    public Company login(Company company) {
        return null;
    }

    @Override
    public Company register(Company company) {
        System.out.println("ALERT ZENERAAAAAAAAAAAAAAAL");
        Connection connection = createConnection();

        if(connection == null || company.getName() == null || company.getMembership() == null || company.getPib() == null
        || company.getName().equals("") || company.getMembership().equals("") || company.getPib().equals("")){
            return null;
        }

        // validating the non-required parameters
        if(company.getAddress() == null){
            company.setAddress("");
        }


        String query = String.format(REGISTER, company.getName(), company.getAddress(), company.getMembership(), company.getPib(), SafeConverter.toSafeFloat(company.getProvision()));

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
}