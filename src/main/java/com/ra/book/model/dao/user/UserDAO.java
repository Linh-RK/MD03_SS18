package com.ra.book.model.dao.user;

import com.ra.book.model.entity.User;

public interface UserDAO {
    boolean register(User user);
    // tham số user { username , password } vi tu html còn trả về là User đầy đủ thông tin
    User login(User user);
}
