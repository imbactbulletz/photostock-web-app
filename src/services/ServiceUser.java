package services;

import dao.DAOUser;
import entities.User;

public class ServiceUser implements IServiceUser{
    protected DAOUser dao;

    public ServiceUser(){
        dao = new DAOUser();
    }

    @Override
    public User login(User user) {
        return this.dao.login(user);
    }

    @Override
    public User register(User user) {
        return this.dao.register(user);
    }
}
