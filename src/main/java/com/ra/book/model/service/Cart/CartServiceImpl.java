package com.ra.book.model.service.Cart;

import com.ra.book.model.dao.cart.CartDAO;
import com.ra.book.model.entity.User;
import com.ra.book.model.entity.cart.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private CartDAO cartDao;

    @Override
    public void addToCart(CartItem cartItem) {
        User user = (User) httpSession.getAttribute("userLogin");
        cartItem.setUser(user);

        cartDao.addToCart(cartItem);
    }
}
