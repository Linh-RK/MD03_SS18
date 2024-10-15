package com.ra.book.model.service.Book;

import com.ra.book.model.entity.Book;
import com.ra.book.model.entity.dto.BookDTO;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Boolean create(BookDTO book);
    Book findById(int id);
    Boolean update(Book book);
    void delete(int id);
    int totalPages(String searchKey, int categoryId, String sort, Double priceMin, Double priceMax, int pageSize);
    Double maxPrice();
    List<Book> findAll(int page, String searchKey, int categoryId, String sort, Double priceMin, Double priceMax, int pageSize);

}
