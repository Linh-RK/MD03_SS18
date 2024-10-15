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
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private boolean status;
    @OneToMany(mappedBy = "category")
    private Set<Book> products;

//    public Category() {
//    }
//
//    public Category(int id, String name, boolean status, Set<Book> products) {
//        this.id = id;
//        this.name = name;
//        this.status = status;
//        this.products = products;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public boolean isStatus() {
//        return status;
//    }
//
//    public void setStatus(boolean status) {
//        this.status = status;
//    }
//
//    public Set<Book> getProducts() {
//        return products;
//    }
//
//    public void setProducts(Set<Book> products) {
//        this.products = products;
//    }
}




