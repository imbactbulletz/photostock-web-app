package services;

import entities.User;

public interface IServiceUser {
    User login(User user);
    User register(User user);
    boolean activate(String username);
    User getUser(String username);
    boolean changePassword(User user);
}
