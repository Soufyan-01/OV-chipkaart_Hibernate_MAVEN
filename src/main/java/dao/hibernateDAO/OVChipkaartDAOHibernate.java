package dao.hibernateDAO;

import dao.OVChipkaartDAO;
import dao.ReizigerDAO;
import domain.OVChipkaart;
import domain.Reiziger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO {

    private Session session;
    private ReizigerDAO rdao;

    public OVChipkaartDAOHibernate(ReizigerDAO rdao){
        this.rdao = rdao;
    }

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public OVChipkaart findByKaartNummer(int kaart_nummer) {
        try {
            OVChipkaart ovChipkaart = (OVChipkaart) session.get(OVChipkaart.class, kaart_nummer);
            return ovChipkaart;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.saveOrUpdate(ovChipkaart);
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
    public boolean update(OVChipkaart ovChipkaart) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            session.merge(ovChipkaart);
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
    public boolean delete(OVChipkaart ovChipkaart) {
        Transaction transaction = null;
        try {
            session.beginTransaction();
            ovChipkaart = session.get(OVChipkaart.class, ovChipkaart.getKaart_nummer());
            if(ovChipkaart != null){
                session.delete(ovChipkaart);
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
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        try {
            List<OVChipkaart> ovChipkaart = session.createQuery("SELECT ovchipkaart FROM OVChipkaart ovchipkaart WHERE ovchipkaart.reiziger = :reiziger")
                    .setParameter("reiziger", reiziger)
                    .getResultList();
            return  ovChipkaart;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        try {
            Query query = session.createQuery("SELECT ovchipkaart FROM OVChipkaart ovchipkaart ");
            return query.list();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
