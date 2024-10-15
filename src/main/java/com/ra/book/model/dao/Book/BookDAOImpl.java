package com.ra.book.model.dao.Book;

import com.ra.book.model.entity.Book;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Repository
public class BookDAOImpl implements BookDAO {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Book> findAll() {
        try(Session session = sessionFactory.openSession();) {
            return session.createQuery("from Book",Book.class).list();
        }catch (HibernateException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean create(Book book) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.save(book);
            session.getTransaction().commit();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public Book findById(int id) {
        try(Session session = sessionFactory.openSession();) {
            return session.get(Book.class, id);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean update(Book book) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.update(book);
            session.getTransaction().commit();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public void delete(int id) {
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            session.delete(session.get(Book.class, id));
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally {
            session.close();
        }
    }

    @Override
    public int totalElement(String searchKey, int categoryId, String sort, Double priceMin, Double priceMax) {
        return result(searchKey, categoryId,sort, priceMin, priceMax).size();
    }

    @Override
    public List<Book> findAll(int page, String searchKey, int categoryId, String sort, int pageSize, Double priceMin, Double priceMax) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "from Book b where b.name like :searchKey and b.price between :priceMin and :priceMax";

            // Nếu có categoryId khác -1 thì thêm điều kiện lọc category
            if (categoryId != -1) {
                hql += " and b.category.id = :categoryId";
            }

            // Xử lý sắp xếp
            if (!sort.isEmpty()) {
                if (sort.equals("asc")) {
                    hql += " order by b.price asc";
                } else {
                    hql += " order by b.price desc";
                }
            } else {
                hql += " order by b.id asc";  // Mặc định sắp xếp theo id
            }

            // Tạo query
            Query<Book> query = session.createQuery(hql, Book.class)
                    .setParameter("searchKey", "%" + searchKey + "%")
                    .setParameter("priceMin", priceMin)
                    .setParameter("priceMax", priceMax)
                    .setFirstResult(page * pageSize)  // Offset
                    .setMaxResults(pageSize);         // Page size

            // Nếu categoryId khác -1, thêm điều kiện lọc category
            if (categoryId != -1) {
                query.setParameter("categoryId", categoryId);
            }

            // Trả về danh sách sách
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Trả về danh sách rỗng hoặc xử lý khác nếu có lỗi
        return findAll();
    }


    private List<Book> result(String searchKey, int categoryId, String sort, Double priceMin, Double priceMax) {
        try (Session session = sessionFactory.openSession()) {
            String hql = "select b from Book b where b.name like :searchKey and b.price between :priceMin and :priceMax ";
            if (categoryId != -1) {
                hql += " and b.category.id = :categoryId";
            }
            List<Book> bookList;
            if (categoryId != -1) {
                bookList = session.createQuery(hql, Book.class)
                        .setParameter("searchKey", "%" + searchKey + "%")
                        .setParameter("priceMin", priceMin)
                        .setParameter("priceMax", priceMax)
                        .setParameter("categoryId", categoryId)
                        .getResultList();
            }
            else
            {
                bookList = session.createQuery(hql, Book.class)
                        .setParameter("searchKey", "%" + searchKey + "%")
                        .setParameter("priceMin", priceMin)
                        .setParameter("priceMax", priceMax)
                        .getResultList();
            }
            return bookList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return findAll();
    }

//    @Override
//    public List<Book> findAll(int page, String searchKey,int categoryId, String sort, int pageSize, Double priceMin, Double priceMax) {
//        return result(searchKey,categoryId,sort, priceMin,priceMax).subList(page*pageSize, pageSize);
//    }

//    @Override
//    public long totalElement(String searchkey, int categoryId) {
//        try (Session session = sessionFactory.openSession()) {
//            String hql = "select count(*) from Book b where b.name like :searchKey and b.category.id = :categoryId";
//            Query<Long> query = session.createQuery(hql, Long.class)
//                    .setParameter("searchKey", "%" + searchkey + "%")
//                    .setParameter("categoryId",categoryId);
//            return query.getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//    @Override
//    public long totalElement(String searchkey) {
//        try (Session session = sessionFactory.openSession()) {
//            String hql = "select count(*) from Book b where b.name like :searchKey";
//            Query<Long> query = session.createQuery(hql, Long.class)
//                    .setParameter("searchKey", "%" + searchkey + "%");
//            return query.getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }


//    @Override
//    public List<Book> findAll(int page, String searchKey, int categoryId, String sort, int pageSize) {
//        try (Session session = sessionFactory.openSession()) {
//            String hql = "from Book b where b.name like :_searchKey and b.category.id = :_categoryId";
//            if (!sort.isEmpty()) {
//                if (sort.equals("asc")) {
//                    hql += " order by b.price asc";
//                } else {
//                    hql += " order by b.price desc";
//                }
//            } else {
//                hql += " order by b.id asc";  // Mặc định sắp xếp theo id
//            }
//            Query<Book> query = session.createQuery(hql, Book.class)
//                    .setParameter("_searchKey", "%" + searchKey + "%")
//                    .setParameter("_categoryId",categoryId)
//                    .setFirstResult(page * pageSize)  // Offset
//                    .setMaxResults(pageSize);         // Page size
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    @Override
//    public List<Book> findAll(int page, String searchKey, String sort, int pageSize) {
//        try (Session session = sessionFactory.openSession()) {
//            String hql = "from Book b where b.name like :searchKey";
//            if (!sort.isEmpty()) {
//                if (sort.equals("asc")) {
//                    hql += " order by b.price asc";
//                } else {
//                    hql += " order by b.price desc";
//                }
//            } else {
//                hql += " order by b.id asc";  // Mặc định sắp xếp theo id
//            }
//            Query<Book> query = session.createQuery(hql, Book.class)
//                    .setParameter("searchKey", "%" + searchKey + "%")
//                    .setFirstResult(page * pageSize)  // Offset
//                    .setMaxResults(pageSize);         // Page size
//
//            return query.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

}
