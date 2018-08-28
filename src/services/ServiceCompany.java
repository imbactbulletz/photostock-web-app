package services;

import dao.DAOCompany;
import entities.Company;

import java.util.List;

public class ServiceCompany implements IServiceCompany {
    protected DAOCompany dao;


    public ServiceCompany(){
        dao = new DAOCompany();
    }


    @Override
    public Company login(Company company) {
        return this.dao.login(company);
    }

    @Override
    public Company register(Company company) {
        return this.dao.register(company);
    }

    @Override
    public List<Company> getPendingCompanies() {
        return this.dao.getPendingCompanies();
    }

    @Override
    public boolean setCompanyStatus(String companyID, String status) {
        return this.dao.setCompanyStatus(companyID, status);
    }

    @Override
    public Company getCompanyByID(String ID) {
        return this.dao.getCompanyByID(ID);
    }

    @Override
    public List<Company> getActiveCompanies() {
        return this.dao.getActiveCompanies();
    }


}
