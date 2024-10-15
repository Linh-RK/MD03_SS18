package com.ra.book.model.validation;

import com.ra.book.model.entity.dto.CategoryDTO;
import com.ra.book.model.service.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.ra.book.controller.admin.AdminCategoryController.oldName;

@Component
public class UniqueValidate implements ConstraintValidator<Unique, String> {
    @Autowired
    private CategoryService categoryService;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return !categoryService.checkCategoryNameExits(value, oldName);
    }



}