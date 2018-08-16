package services;

import entities.User;

import java.util.List;

public interface IServiceUser {
    User login(User user);
    User register(User user);
    boolean activate(String username);
    User getUser(String username);
    boolean changePassword(User user);
    List<User> getAllUsers();
}
