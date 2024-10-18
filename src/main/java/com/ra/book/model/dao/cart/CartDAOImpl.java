package com.ra.book.model.dao.cart;

import com.ra.book.model.entity.cart.CartItem;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CartDAOImpl implements CartDAO {
    @Autowired
    private SessionFactory sessionFactory;


    @Override
    public void addToCart(CartItem cartItem) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
           CartItem oldCartItem = findCartByUserIdAndProductId(cartItem.getUser().getId(),cartItem.getBook().getId());
           int totalQty =  oldCartItem.getQuantity() + cartItem.getQuantity();
           if (oldCartItem != null) {
               if (totalQty < cartItem.getBook().getStock()) {
                   oldCartItem.setQuantity(totalQty);
               }
            } else {
               oldCartItem = new CartItem();
               oldCartItem.setQuantity(cartItem.getQuantity());
               oldCartItem.setBook(cartItem.getBook());
               oldCartItem.setUser(cartItem.getUser());
           }
           tx = session.beginTransaction();
           session.saveOrUpdate(oldCartItem);
           tx.commit();
        }catch (Exception e){
            tx.rollback();
            e.printStackTrace();
        }



    }

    private CartItem findCartByUserIdAndProductId(int userId, int bookId) {
        try (Session session = sessionFactory.openSession()) {
            session.createQuery("from CartItem c where c.book.id = :bookId and  c.user.id = :userId")
                    .setParameter("bookId", bookId)
                    .setParameter("userId", userId)
                    .getSingleResult();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
