package com.ra.book.model.service.User;

import com.ra.book.model.dao.user.UserDAO;
import com.ra.book.model.entity.Role;
import com.ra.book.model.entity.User;
import com.ra.book.model.entity.constants.RoleName;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private HttpSession httpSession;

    @Autowired
    private UserDAO userDao;

    @Override
    public boolean register(User user)
    {
        return userDao.register(user);
    }

    @Override
    public User login(User user)
    {
        User userLogin = userDao.login(user);

        httpSession.setAttribute("userLogin", userLogin);

        return userLogin;
    }

}
