package dao.hibernateDAO;

import dao.ProductDAO;
import domain.OVChipkaart;
import domain.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class ProductDAOHibernate implements ProductDAO {

    private Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.saveOrUpdate(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.merge(product);
            session.getTransaction().commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(Product product) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            product = session.get(Product.class, product.getProduct_nummer());
            if(product != null){
                session.delete(product);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            if(transaction != null){
                transaction.rollback();
            }
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    @Override
    public Product findById(int id) {
        try {
            Product product = (Product) session.get(Product.class, id);
            return product;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        List<Product> products = new ArrayList<>();
        try {
             products = session.createQuery(("SELECT product FROM OVChipkaart ovchipkaart " +
                    "JOIN ovchipkaart.products product WHERE ovchipkaart.kaart_nummer = : kaart_nummer"))
                    .setParameter("kaart_nummer", ovChipkaart.getKaart_nummer())
                    .getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    @Override
    public List<Product> findAll() {
        try {
            Query query = session.createQuery("SELECT product FROM product product ");
            return query.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

