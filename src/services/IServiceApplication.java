package services;

public interface IServiceApplication {
    boolean makeApplication(String applicant);
    boolean hasPendingApplication(String applicant);
    String getApplicationID(String applicant);
}
