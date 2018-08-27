package services;

import dao.DAOUser;
import entities.User;

import java.util.List;

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

    @Override
    public List<User> getAllUsers() {
        return this.dao.getAllUsers();
    }

    @Override
    public boolean deleteUser(String username) {
        return this.dao.deleteUser(username);
    }

    @Override
    public boolean addUser(User user) {
        return this.dao.addUser(user);
    }

    @Override
    public boolean promoteToVendor(String username) {
        return this.dao.promoteToVendor(username);
    }

    @Override
    public boolean changeSettings(String username, String password, String creditCard, String deactivate) {
        return this.dao.changeSettings(username, password, creditCard, deactivate);
    }

    @Override
    public boolean insertModerator(String username, String password, String email, String companyName) {
        return this.dao.insertModerator(username, password, email, companyName);
    }


}
