package com.ra.book.model.dao.cart;

import com.ra.book.model.entity.cart.CartItem;

public interface CartDAO {
    void addToCart(CartItem cartItem);
}
