package com.ra.book.controller;

import com.ra.book.model.entity.Book;
import com.ra.book.model.entity.Category;
import com.ra.book.model.entity.User;
import com.ra.book.model.entity.cart.CartItem;
import com.ra.book.model.service.Book.BookService;
import com.ra.book.model.service.Cart.CartService;
import com.ra.book.model.service.Category.CategoryService;
import com.ra.book.model.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private BookService bookService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CategoryService categoryService;
@GetMapping
    public String index(Model model) {
    List<Book> books = bookService.findAll();
    List<Category> categories = categoryService.findAll();
    model.addAttribute("books", books);
    model.addAttribute("categories", categories);
    return "user/index";
}

    @PostMapping("/cart/add")
    public String handleAddToCart(@ModelAttribute CartItem cartItem, Model model) {
        cartService.addToCart(cartItem);
        return "redirect:/user";
    }

}
