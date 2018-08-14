package services;

import dao.DAOCompany;
import entities.Company;

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
}
