package dao;

import entities.Company;

import java.util.List;

public interface IDAOCompany {
    Company login(Company company);
    Company register(Company company);
    List<Company> getPendingCompanies();
    boolean setCompanyStatus(String companyID, String status);
}
