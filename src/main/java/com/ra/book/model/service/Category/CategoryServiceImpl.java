package com.ra.book.model.service.Category;

import com.ra.book.model.dao.Category.CategoryDAO;
import com.ra.book.model.entity.Category;
import com.ra.book.model.entity.dto.CategoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryDAO categoryDAO;
    @Override
    public List<Category> findAll(int page, int size) {
        return categoryDAO.findAll(page, size);
    }

    @Override
    public List<Category> findAll() {
        return categoryDAO.findAll();
    }

    @Override
    public Boolean create(CategoryDTO category) {
        Category categoryEntity = new Category();
        categoryEntity.setName(category.getName());
        categoryEntity.setStatus(category.isStatus());
        return categoryDAO.create(categoryEntity);
    }

    @Override
    public Category findById(int id) {
        return categoryDAO.findById(id);
    }

    @Override
    public Boolean update(CategoryDTO categoryDTO,int id) {
        Category categoryEntity = new Category();
        categoryEntity.setId(id);
        categoryEntity.setName(categoryDTO.getName());
        categoryEntity.setStatus(categoryDTO.isStatus());
        return categoryDAO.update(categoryEntity);
    }

    @Override
    public void delete(int id) {
    categoryDAO.delete(id);
    }

    @Override
    public boolean checkCategoryNameExits(String value,String oldName) {
        return categoryDAO.findByName(value, oldName);
    }

    @Override
    public int totalPages(int size)
    {
        return (int) Math.ceil((double) categoryDAO.totalElement() / size);
    }
}
