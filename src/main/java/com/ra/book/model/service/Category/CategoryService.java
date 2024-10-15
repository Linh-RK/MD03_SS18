package com.ra.book.model.service.Category;

import com.ra.book.model.entity.Category;
import com.ra.book.model.entity.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<Category> findAll(int page, int size);
    List<Category> findAll();
    Boolean create(CategoryDTO categoryDTO);
    Category findById(int id);
    Boolean update(CategoryDTO categoryDTO,int id);
    void delete(int id);
    boolean checkCategoryNameExits(String value, String oldName);
    int totalPages(int size);
}
