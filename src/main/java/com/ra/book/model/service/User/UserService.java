package com.ra.book.model.service.User;

import com.ra.book.model.entity.User;

public interface UserService {
    boolean register(User user);

    User login(User user);
}
