package services;

import dao.DAOApplication;

public class ServiceApplication implements IServiceApplication{
    protected DAOApplication dao;

    public ServiceApplication(){
        dao = new DAOApplication();
    }

    @Override
    public boolean makeApplication(String applicant) {
        return this.dao.makeApplication(applicant);
    }
}
