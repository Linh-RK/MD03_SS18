package com.ra.book.model.entity;

import com.ra.book.model.validation.Unique;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String name;

    private boolean status;
    @OneToMany(mappedBy = "category")
    private Set<Book> products;
}




