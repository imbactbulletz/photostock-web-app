package services;

import dao.DAOCategory;
import entities.Category;

import java.util.List;

public class ServiceCategory implements IServiceCategory {

    protected DAOCategory dao;

    public ServiceCategory(){
        this.dao = new DAOCategory();
    }

    @Override
    public List<Category> getAllCategories() {
        return this.dao.getAllCategories();
    }

    @Override
    public boolean addCategory(String name) {
        return this.dao.addCategory(name);
    }

    @Override
    public boolean deleteCategory(String name) {
        return this.dao.deleteCategory(name);
    }
}
