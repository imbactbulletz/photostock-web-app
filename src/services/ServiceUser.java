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

    @Override
    public boolean activate(String username) {
        return this.dao.activate(username);
    }

    @Override
    public User getUser(String username) {
        return this.dao.getUser(username);
    }

    @Override
    public boolean changePassword(User user) {
        return this.dao.changePassword(user);
    }


}
