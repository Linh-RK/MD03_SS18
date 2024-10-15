package com.ra.book.model.dao.user;

import com.ra.book.model.entity.Role;
import com.ra.book.model.entity.User;
import com.ra.book.model.entity.constants.RoleName;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public boolean register(User user) {
        Set<Role> roles = new HashSet<>();
        // chỉ tìm ra role thôi vì dữ liệu role đã fix cứng
        roles.add(findByRoleName(RoleName.USER));

        // user gửi từ html lên phải có dữ liệu của 3 trường { fullName, username, password }
        user.setStatus(true);
        user.setRoles(roles);
        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
        Transaction tx = null;
        try(Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (tx != null)
            {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User login(User user) {
        try (Session session = sessionFactory.openSession()) {
            User userLogin = session.createQuery("from User u where u.username = :_username", User.class)
                    .setParameter("_username", user.getUsername())
                    .getSingleResult();
            if (userLogin != null) {
                if(BCrypt.checkpw(user.getPassword(), userLogin.getPassword())) {
                    return userLogin;
                }
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Role findByRoleName(RoleName roleName) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Role r where r.roleName = :_roleName", Role.class)
                    .setParameter("_roleName", roleName)
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
