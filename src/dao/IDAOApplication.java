package dao;

public interface IDAOApplication {
    boolean makeApplication(String applicant);
    boolean hasPendingApplication(String applicant);
    String getApplicationID(String applicant);
}
