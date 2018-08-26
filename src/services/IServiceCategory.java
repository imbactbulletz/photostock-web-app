package services;

import entities.Category;

import java.util.List;

public interface IServiceCategory {
    List<Category> getAllCategories();
    boolean addCategory(String name);
    boolean deleteCategory(String name);
}
