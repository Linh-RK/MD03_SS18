package com.ra.book.model.service.Book;

import com.ra.book.model.dao.Book.BookDAO;
import com.ra.book.model.entity.Book;
import com.ra.book.model.entity.dto.BookDTO;
import com.ra.book.model.service.UploadFile.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookDAO bookDAO;
    @Autowired
    private UploadFileService uploadFileService;
    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }
    @Override
    public Boolean create(BookDTO book) {
        String image = "";
        if (book.getImage() != null) {
             image = uploadFileService.uploadFile(book.getImage());
            System.out.println(image);
        }
//        upload file
//        convert
        Book bookEntity = new Book();
        bookEntity.setName(book.getName());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setPublisher(book.getPublisher());
        bookEntity.setPrice(book.getPrice());
        bookEntity.setImage(image);
        bookEntity.setDescription(book.getDescription());
        bookEntity.setStatus(book.isStatus());
        bookEntity.setCategory(book.getCategory());

        return bookDAO.create(bookEntity);
    }

    @Override
    public Book findById(int id) {
        return bookDAO.findById(id);
    }



    @Override
    public Boolean update(Book book) {
        return bookDAO.update(book);
    }

    @Override
    public void delete(int id) {
    bookDAO.delete(id);
    }

    @Override
    public int totalPages(String searchKey, int categoryId, String sort, Double priceMin, Double priceMax, int pageSize) {
        return (int) Math.ceil((double) bookDAO.totalElement( searchKey,  categoryId,  sort,  priceMin,  priceMax) / pageSize);
    }

    @Override
    public List<Book> findAll(int page, String searchKey, int categoryId, String sort, Double priceMin, Double priceMax, int pageSize) {
        return bookDAO.findAll(page, searchKey, categoryId, sort, pageSize,priceMin, priceMax);
    }
    @Override
    public Double maxPrice() {
        return bookDAO.findAll().stream().map(Book::getPrice).max(Comparator.comparingDouble(Double::doubleValue)).orElse(0.0);
    }

//    @Override
//    public int totalPages(String searchKey, int categoryId, Double priceMin, Double priceMax) {
//        return (int) Math.ceil((double) bookDAO.totalElement( searchkey, categoryId) / pageSize);
//    }
//
//    @Override
//    public int totalPages(int pageSize, String searchkey) {
//        return (int) Math.ceil((double) bookDAO.totalElement( searchkey) / pageSize);
//    }
//
//    @Override
//    public List<Book> findAll(int page, String searchkey, int categoryId, String sort, int pageSize,Double priceMin, Double priceMax) {
//        return bookDAO.findAll(page, searchkey, categoryId, sort, pageSize,priceMin, priceMax);
//    }
//
//    @Override
//    public List<Book> findAll(int page, String searchKey,int categoryId, String sort, int pageSize, Double priceMin, Double priceMax) {
//        return bookDAO.findAll(page, searchKey,categoryId, sort, pageSize,priceMin,priceMax);
//    }


}
