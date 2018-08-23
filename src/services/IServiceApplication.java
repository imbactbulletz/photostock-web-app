package services;

import entities.Application;

import java.util.List;

public interface IServiceApplication {
    boolean makeApplication(String applicant);
    boolean hasPendingApplication(String applicant);
    String getApplicationID(String applicant);
    List<Application> getPendingApplications();
    boolean rateApplication(String username, String rating);
}
