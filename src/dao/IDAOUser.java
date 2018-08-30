package dao;

import entities.User;

import java.util.List;

public interface IDAOUser {
    User login(User user);
    User register(User user);
    boolean activate(String username);
    User getUser(String username);
    boolean changePassword(User user);
    List<User> getAllUsers();
    boolean deleteUser(String username);
    boolean addUser(User user);
    boolean promoteToVendor(String username);
    boolean changeSettings(String username, String password, String creditCard, String deactivate);
    boolean insertModerator(String username, String password, String email, String companyName);
    List<User> getMembersFor(String companyName);
    boolean removeMembership(String username);
    boolean assignUserToCompany(String username, String companyName);
    boolean rateUser(String username, String rating);
}
