package dao;

import entities.Category;

import java.util.List;

public interface IDAOCategory {
    List<Category> getAllCategories();
    boolean addCategory(String name);
    boolean deleteCategory(String name);
}
