package services;

import entities.User;

public interface IServiceUser {
    User login(User user);
    User register(User user);
}
