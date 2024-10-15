package com.ra.book.model.dao.Category;

import com.ra.book.model.entity.Category;
import com.ra.book.model.entity.dto.CategoryDTO;

import java.util.List;

public interface CategoryDAO {
    List<Category> findAll(int page, int size);
    List<Category> findAll();
    Boolean create(Category category);
    Category findById(int id);
    Boolean update(Category category);
    void delete(int id);
//    List<Category> findByName(String name);
    boolean findByName(String value,String oldName);
    long totalElement();
}
