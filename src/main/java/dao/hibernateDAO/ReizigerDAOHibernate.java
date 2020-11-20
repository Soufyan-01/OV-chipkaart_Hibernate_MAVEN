package dao.hibernateDAO;

import dao.AdresDAO;
import dao.OVChipkaartDAO;
import dao.ReizigerDAO;
import domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Date;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {

    private Session session;
    private AdresDAO adao;
    private OVChipkaartDAO odao;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }

    public void setOdao(OVChipkaartDAO odao){this.odao = odao;}

    @Override
    public boolean save(Reiziger reiziger) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.saveOrUpdate(reiziger);
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
    public boolean update(Reiziger reiziger) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.merge(reiziger);
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
    public boolean delete(Reiziger reiziger) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            reiziger = session.get(Reiziger.class, reiziger.getID());
            if(reiziger != null){
                session.delete(reiziger);
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
    public Reiziger findById(int id) {
        try {
            Reiziger reiziger = (Reiziger) session.get(Reiziger.class, id);
            return reiziger;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            Query query = session.createQuery("SELECT reiziger FROM Reiziger reiziger WHERE reiziger.geboortedatum = :datum")
                    .setParameter("datum", Date.valueOf(datum));
            return query.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            Query query = session.createQuery("SELECT reiziger FROM Reiziger reiziger ");
            return query.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}