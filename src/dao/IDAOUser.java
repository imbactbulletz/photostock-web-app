package dao;

import entities.User;

public interface IDAOUser {
    User login(User user);
    User register(User user);
    boolean activate(String username);
    User getUser(String username);
}
