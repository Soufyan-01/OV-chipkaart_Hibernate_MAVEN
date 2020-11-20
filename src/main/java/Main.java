
import com.fasterxml.classmate.AnnotationConfiguration;
import dao.AdresDAO;
import dao.OVChipkaartDAO;
import dao.ProductDAO;
import dao.ReizigerDAO;
import dao.hibernateDAO.AdresDAOHibernate;
import dao.hibernateDAO.OVChipkaartDAOHibernate;
import dao.hibernateDAO.ProductDAOHibernate;
import dao.hibernateDAO.ReizigerDAOHibernate;
import domain.Adres;
import domain.OVChipkaart;
import domain.Product;
import domain.Reiziger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.io.ObjectInputFilter;
import java.nio.charset.CharsetEncoder;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;


/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {

    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;


    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();

    }

    public static void main(String[] args) throws SQLException {

        //testFetchAll();

        ReizigerDAO rdao = new ReizigerDAOHibernate(getSession());
        AdresDAO adao = new AdresDAOHibernate(getSession());
        OVChipkaartDAO odao = new OVChipkaartDAOHibernate(getSession());
        ProductDAO pdao = new ProductDAOHibernate(getSession());


        testReizigerDAOHibernate(rdao);
        testAdresDAOHibernate(adao, rdao);
        testOVChipkaartDAOHibernate(odao, rdao);
        testProductDAOHibernate(pdao, rdao, odao);


    }


    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testReizigerDAOHibernate(ReizigerDAO rdao) throws SQLException{
        System.out.println("\n---------- Test ReizigerDAOHibernate -------------");



        // ReizigerDAOHibernate.findall()
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAOHibernate.findAll() geeft de volgende reizigers:");
        for(Reiziger reiziger : reizigers){
            System.out.println(reiziger);
        }
        System.out.println();

        //ReizigerDAOHibernate.save()
        System.out.print("[Test] Eerst " + rdao.findAll().size() + " reizigers, na ReizigerDAOHibernate.save() ");
        String gbdatum = "1999-11-11";
        Reiziger Henk = new Reiziger(6, "D", "", "Fifa", java.sql.Date.valueOf(gbdatum));
        //rdao.save(Henk);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size()+"\n");
//
        //updaten van naam
        System.out.println("[Test] Eerst " + "ReizigerDAOHibernate.update();");
        Henk.setVoorletters("F");
        Henk.setAchternaam("FIFA256");
        rdao.update(Henk);
        System.out.println(Henk + "\n");
//
        //verwijderen van een reiziger
        System.out.println("[TEST] reizigers: " + reizigers.size() + " ReizigerDAOHibernate.delete();");
        rdao.delete(Henk);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + "\n");

        // zoek adres op id
        System.out.println("[TEST] ReizigerDAOHibernate.findById");
        System.out.println(rdao.findById(5));
        System.out.println();

        // zoek op gbDatum
        System.out.println("[Test] ReizigerDAOHibernate.findByGbdatum() geeft de volgende reizigers: ");
        System.out.println(rdao.findByGbdatum("1998-08-11"));
        System.out.println();

    }

    private static void testAdresDAOHibernate(AdresDAO adao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test AdresDAOHibernate -------------");


        // AdresDAOHibernate.findall()
        List<Adres> adres = adao.findAll();
        System.out.println("[Test] AdresDAOHibernate.findAll() geeft de volgende adressen:");
        for (Adres adres1 : adres) {
            System.out.println(adres1);
        }
        System.out.println();

        //AdresDAOHibernate.save()
        System.out.print("[Test] Eerst " + adao.findAll().size() + " adressen, na AdresDAOHibernate.save() ");

        String gbdatum = "2000-12-12";
        Reiziger Henk2 = new Reiziger(7, "E", "van", "WW", java.sql.Date.valueOf(gbdatum));
        rdao.save(Henk2);

        Adres nwAdres = new Adres(6, "2806NJ", "4", "Walraven", "Gouda", Henk2);
        //adao.save(nwAdres);
        adres = adao.findAll();
        System.out.println(adres.size()+"\n");

        //updaten van naam
        System.out.println("[Test] Eerst " + "AdresDAOHibernate.update();");
        nwAdres.setHuisnummer("100");
        adao.update(nwAdres);
        System.out.println(nwAdres + "\n");

        // zoeken op reiziger
        System.out.println("[Test] AdresDAOHibernate.findByReiziger: ");
        System.out.println(adao.findByReiziger(Henk2));
        System.out.println();

        // zoek adres op id
        System.out.println("[TEST] AdresDAOHibernate.findById");
        System.out.println(adao.findById(6));
        System.out.println();

        //verwijderen van een reiziger
        System.out.println("[TEST] Adressen: " + adres.size() + " AdresDAOHibernate.delete();");
        adao.delete(nwAdres);
        adres = adao.findAll();
        System.out.println(adres.size() + "\n");

    }
    private static void testOVChipkaartDAOHibernate(OVChipkaartDAO odao, ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test OVChipkaartDAOHibernate -------------");

        // AdresDAOHibernate.findall()
        List<OVChipkaart> ovChipkaarts = odao.findAll();
        System.out.println("[Test] OVChipkaartDAOHibernate.findAll() geeft de volgende OVChipkaarten:");
        for (OVChipkaart ovChipkaart : ovChipkaarts) {
            System.out.println(ovChipkaart);
        }
        System.out.println();

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1998-09-09";
        Reiziger Henk3 = new Reiziger(10, "K", "den", "Henk3", java.sql.Date.valueOf(gbdatum));

        rdao.save(Henk3);

        OVChipkaart ovChipkaart1 = new OVChipkaart(123456, Date.valueOf("2022-12-12"), 1, 100.00, Henk3);

        //save
        System.out.print("[Test] Eerst " + ovChipkaarts.size() + " ovchip, na OVChipkaartDAO.save() ");
        //odao.save(ovChipkaart1);
        ovChipkaarts = odao.findAll();
        System.out.println(ovChipkaarts.size() + " ovchipkaarten\n");


        //update
        System.out.println("[Test] Eerst " + "OVChipkaart.update();");
        System.out.println("voor wijziging: " + ovChipkaart1);
        ovChipkaart1.setSaldo(2000.00);
        odao.update(ovChipkaart1);
        System.out.println("Na wijziging: " + ovChipkaart1 + "\n");

        // zoeken op reiziger
        System.out.println("[Test] OVChipkaartDAO.findByReiziger: ");
        System.out.println(odao.findByReiziger(Henk3));
        System.out.println();

        // zoek adres op id
        System.out.println("[TEST] OVChipkaartDAOHibernate.findById");
        System.out.println(odao.findByKaartNummer(79625));
        System.out.println();

        //verwijderen van een reiziger
        System.out.println("[TEST] OVChipkaarten: " + ovChipkaarts.size() + " OVChipkaartDAOHibernate.delete();");
        odao.delete(ovChipkaart1);
        ovChipkaarts = odao.findAll();
        System.out.println(ovChipkaarts.size() + "\n");

    }
    private static void testProductDAOHibernate(ProductDAO pdao, ReizigerDAO rdao, OVChipkaartDAO odao) throws SQLException {
        System.out.println("\n---------- Test ProductDAOHibernate -------------");

        // AdresDAOHibernate.findall()
        List<Product> products = pdao.findAll();
        System.out.println("[Test] ProductDAOHibernate.findAll() geeft de volgende producten:");
        for (Product product : products) {
            System.out.println(product);
        }
        System.out.println();

        Product product = new Product(1111, "test-product", "voor-de-test", 10.99);
        OVChipkaart ovChipkaart1 = new OVChipkaart(2000, Date.valueOf("2022-12-12"), 2, 10.00, rdao.findById(5));
        ovChipkaart1.addProduct(product);
        product.addOVKaart(ovChipkaart1);
        odao.update(ovChipkaart1);
        pdao.update(product);

        //save
        System.out.print("[Test] Eerst " + products.size() + " product(en), na ProductDAOHibernate.save() ");
        pdao.save(product);
        products = pdao.findAll();
        System.out.println(products.size() + " producten\n");

        //update
        System.out.println("[Test] Eerst " + "ProductenDAOHibernate.update();");
        System.out.println("voor wijziging: " + product);
        product.setPrijs(2000.00);
        pdao.update(product);
        System.out.println("Na wijziging: " + product + "\n");

        // zoeken op reiziger
        System.out.println("[Test] ProductDAOHibernate.findByOVChipkaart: ");
        System.out.println(pdao.findByOVChipkaart(ovChipkaart1));
        System.out.println();

        // zoek adres op id
        System.out.println("[TEST] ProductDAOHibernate.findById");
        System.out.println(pdao.findById(3));
        System.out.println();

        //verwijderen van een reiziger
        System.out.println("[TEST] Producten: " + products.size() + " ProductDAOHibernate.delete();");
        pdao.delete(product);
        products = pdao.findAll();
        System.out.println(products.size() + "\n");

//
//        Product p = pdao.findById(3);
//        System.out.println(p);
//
//        OVChipkaart o = p.getOvChipkaarts().get(0);
//        System.out.println(o);
//
//        Product p2 = o.getProducts().get(1);
//        System.out.println(p2);
//
//        OVChipkaart o2 = p2.getOvChipkaarts().get(0);
//        System.out.println(o2);
//
//        Product p3 = o.getProducts().get(2);
//        System.out.println(p3);
//
//        OVChipkaart o3 = p3.getOvChipkaarts().get(0);
//        System.out.println(o3);
    }



}