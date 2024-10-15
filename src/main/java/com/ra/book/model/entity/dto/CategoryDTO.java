package com.ra.book.model.entity.dto;

import com.ra.book.model.entity.Book;
import com.ra.book.model.validation.Unique;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    @NotBlank(message = "tên không được để trống")
    @Unique(message = "tên đã tồn tại")
    private String name;
    private String oldName = "";
    private boolean status;
}

