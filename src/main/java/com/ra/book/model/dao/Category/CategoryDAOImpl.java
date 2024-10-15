package com.ra.book.model.dao.Category;

import com.ra.book.model.entity.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CategoryDAOImpl implements CategoryDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Category> findAll(int page, int size) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "from Category ";

            return session.createQuery(hql, Category.class)// Thêm "%" vào keyword để tìm kiếm
                    .setFirstResult(page * size) // Thiết lập offset, bắt đầu từ page * size
                    .setMaxResults(size) // Thiết lập số kết quả tối đa cho một trang
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Category> findAll() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("from Category",Category.class).list();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean create(Category category) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public Category findById(int id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(Category.class, id);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public Boolean update(Category category) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.update(category);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
            return false;
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.delete(session.get(Category.class, id));
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }


    @Override
    public boolean findByName(String categoryName, String oldName) {
        try (Session session = sessionFactory.openSession()) {
            Long count = session.createQuery("select count(c) from Category as c where c.name = :categoryName and c.name != :oldName", Long.class)
                    .setParameter("categoryName", categoryName)
                    .setParameter("oldName", oldName)
                    .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while checking if product exists by name", e);
        }
    }

    @Override
    public long totalElement() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select count(c) from Category c ";

            return session.createQuery(hql, Long.class)// Thêm "%" vào keyword để tìm kiếm
                    .getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
