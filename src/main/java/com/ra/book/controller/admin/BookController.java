package com.ra.book.controller.admin;

import com.ra.book.model.entity.Book;
import com.ra.book.model.entity.Category;
import com.ra.book.model.entity.dto.BookDTO;
import com.ra.book.model.entity.dto.CategoryDTO;
import com.ra.book.model.service.Book.BookService;
import com.ra.book.model.service.Category.CategoryService;
import com.ra.book.model.service.UploadFile.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UploadFileService uploadService;
    public static String oldNameBook = "";
    public  Double maxPrice ;
//    @GetMapping
//    public String index(
//            @RequestParam(name = "page",defaultValue = "0") Integer page,
//            @RequestParam(name = "size",defaultValue = "5") Integer size,
//            Model model) {
//        Search search = new Search();
//        List<Category> categories = categoryService.findAll();
//        List<Book> books = bookService.findAll();
//        // Tìm kiếm
//        List<Book> filteredList = books.stream()
//                .filter(p -> search == null || p.getName().toLowerCase().contains(search.getCategory().getName().toLowerCase()))
//                .collect(Collectors.toList());
//
//        // Lọc theo loại sản phẩm
//        if (search.getCategory().getId() != 0) {
//            filteredList = filteredList.stream()
//                    .filter(p -> p.getCategory().getName().equalsIgnoreCase(search.getCategory().getName()))
//                    .collect(Collectors.toList());
//        }
//
//        // Sắp xếp theo giá
//        if (search.isSort()) {
//            filteredList.sort(Comparator.comparing(Book::getPrice));
//        } else {
//            filteredList.sort(Comparator.comparing(Book::getPrice).reversed());
//        }
//        model.addAttribute("categories", categories);
//        model.addAttribute("books", books);
//        model.addAttribute("search", search);
//        if(page +1 > bookService.totalPages(search.getPageSize()) || page < 0){
//            page = (bookService.totalPages(size) - 1);
//        }
//        model.addAttribute("totalPages",categoryService.totalPages(search.getPageSize()));
//        model.addAttribute("size", size);
//        model.addAttribute("book", bookService.findAll(page, search.getPageSize()));
//        model.addAttribute("page", page);
//        System.out.println((page));
//        return "admin/book/index";
//    }
int totalPages;
    @GetMapping
    public String index(
            @RequestParam(name = "page",defaultValue = "0") int page,
            @RequestParam(name = "sort", defaultValue = "") String sort,
            @RequestParam(name = "searchKey",defaultValue = "") String searchKey,
            @RequestParam(name = "categoryId", defaultValue = "-1") int categoryId,
            @RequestParam(name = "pageSize",defaultValue = "5") int size,
            @RequestParam(name = "priceMin", required = false) Double priceMin,
            @RequestParam(name = "priceMax",required = false) Double priceMax,
            Model model) {
        List<Book> bookList ;
        if(priceMax == null){
            priceMax = bookService.maxPrice();
        }
        if(priceMin == null){
            priceMin = 0.0;
        }
//        if(categoryId != -1) {
            totalPages = bookService.totalPages( searchKey,  categoryId,  sort,  priceMin,  priceMax,  size);
            bookList= bookService.findAll(page, searchKey, categoryId, sort, priceMin, priceMax, size);
            System.out.println(totalPages);
            System.out.println(bookList.size());

//        } else {
//            totalPages = bookService.totalPages(searchKey,  sort,  priceMin,  priceMax,  size);
//            bookList= bookService.findAll(page, searchKey, sort,priceMin,priceMax, size);
//        }
        if(page +1 > totalPages|| page < 0){
            page = totalPages - 1;
        }
        model.addAttribute("sort",sort);
        model.addAttribute("searchKey",searchKey);
        model.addAttribute("categoryId",categoryId);
        model.addAttribute("pageSize",size);
        model.addAttribute("priceMin",priceMin);
        model.addAttribute("priceMax",priceMax);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("books", bookList);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("page", page);
        return "admin/book/index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<Category> categories = categoryService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("book", new BookDTO());
        return "admin/book/add";
    }
    @PostMapping("/add")
    public String create( @Valid @ModelAttribute("book") BookDTO bookDTO, BindingResult result, Model model) {
        List<Category> categories = categoryService.findAll();
        if (result.hasErrors() || bookDTO.getImage().getSize() <= 0) {
            model.addAttribute("book", bookDTO);
            model.addAttribute("errImg", "Choose file !");
            model.addAttribute("categories", categories);
            return "admin/book/add";
        }
            if(bookService.create(bookDTO)){
                return "redirect:/admin/book";
            }
            return "redirect:/admin/book/add";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        Book book = bookService.findById(id);
        List<Category> categories = categoryService.findAll();
        BookDTO bookDTO = new BookDTO();
        bookDTO.setName(book.getName());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setPublisher(book.getPublisher());
        bookDTO.setPrice(book.getPrice());
        bookDTO.setDescription(book.getDescription());
        bookDTO.setStatus(book.isStatus());
        bookDTO.setCategory(book.getCategory());
        model.addAttribute("bookDTO", bookDTO);
        model.addAttribute("categories", categories);
        model.addAttribute("book", book);
        return "admin/book/edit";
    }
    @PostMapping("/edit/{id}")
    public String update( @Valid @ModelAttribute("bookDTO") BookDTO bookDTO,
//                          @ModelAttribute("book") Book book,
                          BindingResult result,
                          @PathVariable int id,
                          Model model) {
        if(result.hasErrors()) {

            List<Category> categories = categoryService.findAll();
            model.addAttribute("bookDTO", bookDTO);
            model.addAttribute("categories", categories);
            return "admin/book/edit";
        }
        Book bookEntity = bookService.findById(id);
        bookEntity.setName(bookDTO.getName());
        bookEntity.setAuthor(bookDTO.getAuthor());
        bookEntity.setPublisher(bookDTO.getPublisher());
        bookEntity.setPrice(bookDTO.getPrice());
        bookEntity.setDescription(bookDTO.getDescription());
        bookEntity.setStatus(bookDTO.isStatus());
        bookEntity.setCategory(bookDTO.getCategory());
        if (bookDTO.getImage().getSize()>0) {
            String bookImage = uploadService.uploadFile (bookDTO.getImage());
            bookEntity.setImage(bookImage);
        }
        if(bookService.update(bookEntity)){
            return "redirect:/admin/book";
        }
        return "redirect:/admin/book/edit" + id;
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        bookService.delete(id);
        return "redirect:/admin/book";
    }
}
