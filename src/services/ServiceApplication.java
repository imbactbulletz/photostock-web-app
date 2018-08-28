package services;

import dao.DAOApplication;
import entities.Application;

import java.util.List;

public class ServiceApplication implements IServiceApplication{
    protected DAOApplication dao;

    public ServiceApplication(){
        dao = new DAOApplication();
    }

    @Override
    public boolean makeApplication(String applicant) {
        return this.dao.makeApplication(applicant);
    }

    @Override
    public boolean hasPendingApplication(String applicant) {
        return this.dao.hasPendingApplication(applicant);
    }

    @Override
    public String getApplicationID(String applicant) {
        return this.dao.getApplicationID(applicant);
    }

    @Override
    public List<Application> getPendingApplications() {
        return this.dao.getPendingApplications();
    }

    @Override
    public boolean rateApplication(String username, String rating) {
        return this.dao.rateApplication(username, rating);
    }

    @Override
    public boolean makeApplicationForCompany(String username, String companyName) {
        return this.dao.makeApplicationForCompany(username, companyName);
    }

    @Override
    public List<Application> getPendingCompanyApplications(String companyName) {
        return this.dao.getPendingCompanyApplications(companyName);
    }

    @Override
    public boolean setApplicationStatus(String applicant, String status) {
        return this.dao.setApplicationStatus(applicant, status);
    }
}
