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
}
