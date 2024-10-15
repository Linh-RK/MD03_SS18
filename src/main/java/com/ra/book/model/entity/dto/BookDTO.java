package com.ra.book.model.entity.dto;

import com.ra.book.model.entity.Category;
import com.ra.book.model.validation.FileNotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    @NotBlank
    private String name;
    @NotBlank
    private String author;
    @NotBlank
    private String publisher;
    @Min(1)
    private double price;
//    @FileNotNull
    private MultipartFile image;
    @NotBlank
    private String description;
    private boolean status = Boolean.TRUE;
    @NotNull
    private Category category;
}