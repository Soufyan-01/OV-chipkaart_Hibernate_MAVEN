package dao.hibernateDAO;

import dao.AdresDAO;
import dao.ReizigerDAO;
import domain.Adres;
import domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {

    private Session session;
    private ReizigerDAO rdao;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }


    @Override
    public Adres findById(int ID) {
        try {
            Adres adres = (Adres) session.get(Adres.class, ID);
            return adres;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean save(Adres adres) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.saveOrUpdate(adres);
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
    public boolean update(Adres adres) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.merge(adres);
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
    public boolean delete(Adres adres) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            adres = session.get(Adres.class, adres.getID());
            if(adres != null){
                session.delete(adres);
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
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            Adres adres = (Adres) session.createQuery("SELECT adres FROM Adres adres WHERE adres.reiziger = :reiziger")
                    .setParameter("reiziger", reiziger)
                    .getResultList()
                    .get(0);
            return adres;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Adres> findAll() {
        try {
            Query query = session.createQuery("SELECT adres FROM Adres adres ");
            return query.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

