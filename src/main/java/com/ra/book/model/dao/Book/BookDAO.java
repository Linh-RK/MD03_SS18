package com.ra.book.model.dao.Book;

import com.ra.book.model.entity.Book;

import java.util.List;


public interface BookDAO {
    List<Book> findAll();
    Boolean create(Book book);
    Book findById(int id);
    Boolean update(Book book);
    void delete(int id);
    int totalElement(String searchKey, int categoryId, String sort, Double priceMin, Double priceMax);
    List<Book> findAll(int page, String searchKey,int categoryId, String sort, int pageSize, Double priceMin, Double priceMax);
}
